package com.example.kotlintetris.logic

import com.example.kotlintetris.model.*
import com.example.kotlintetris.data.HighScoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.max

/*
    Core game engine managing game state, piece movement, gravity,
    line clears, scoring, and levels. Uses a coroutine to run the
    main game loop with gravity ticks. Game state via StateFlow for
    UI to observe and render.
 */
class GameEngine(
  private val scope: CoroutineScope,
  private val highScoreManager: HighScoreManager
) {
  private val board = Board()
  private val _state = MutableStateFlow(
    GameState(
      board = board.snapshot(),
      width = board.width,
      height = board.height,
      active = null,
      ghost = emptyList(),
      hold = null,
      canHold = true,
      nextQueue = emptyList(),
      score = 0,
      lines = 0,
      level = 0,
      gameOver = false,
      paused = false,
      highScore = highScoreManager.getHighScore()
    )
  )
  val state: StateFlow<GameState> = _state

  private var bag: MutableList<TetrominoType> = TetrominoType.bag() // current bag of pieces
  private val next: ArrayDeque<TetrominoType> = ArrayDeque() // next queue
  private var active: Piece? = null
  private var hold: TetrominoType? = null
  private var canHold = true
  private var score = 0
  private var lines = 0
  private var level = 0
  private var loopJob: Job? = null
  private var softDrop = false
  private var paused: Boolean = false

  // Lock delay handling (ms)
  private var lockStartMs: Long? = null
  private val lockDelayMs: Long = 500

  // Start the game
  fun start() {
    resetCore()
    loopJob?.cancel()
    loopJob = scope.launch { loop() }
    push()
  }

  // Restart the game
  fun restart() {
    start()
  }

  // Reset core game state without touching high score or coroutine
  private fun resetCore() {
    board.clear()
    bag = TetrominoType.bag()
    next.clear()
    active = null
    hold = null
    canHold = true
    score = 0
    lines = 0
    level = 0
    lockStartMs = null
    repeat(5) { enqueue() }
    spawn()
  }

  // Main game loop handling gravity and ticks
  private suspend fun loop() {
    while (true) {
      val interval = gravityIntervalMs(level)
      val delayMs = if (softDrop) max(1L, interval / 8L) else interval
      delay(delayMs)
      if (!paused) tick()
    }
  }

  // Gravity interval in ms based on level, approximating NES/SNES timings
  private fun gravityIntervalMs(level: Int): Long {
    val frames = when (level) {
      0 -> 48
      1 -> 43
      2 -> 38
      3 -> 33
      4 -> 28
      5 -> 23
      6 -> 18
      7 -> 13
      8 -> 8
      9 -> 6
      in 10..12 -> 5
      in 13..15 -> 4
      in 16..18 -> 3
      in 19..28 -> 2
      else -> 1
    }
    // 60 FPS -> ms per frame is ~16.666; approximate with 1000/60
    return (frames * (1000.0 / 60.0)).toLong().coerceAtLeast(1)
  }

  // Add next piece to the queue from the bag, refilling bag if empty
  private fun enqueue() {
    if (bag.isEmpty()) bag = TetrominoType.bag()
    next.addLast(bag.removeAt(0))
  }

  // Spawn the next piece from the queue
  private fun spawn() {
    val type = next.removeAt(0)
    enqueue()
    val piece = Piece(type, 0, Point(board.width / 2, 0))
    active = piece
    lockStartMs = null
    if (!board.canPlace(piece)) {
      gameOver()
      return
    }
    canHold = true
  }

  // Handle game over state
  private fun gameOver() {
    // Update high score if current score is higher
    highScoreManager.updateHighScore(score)
    loopJob?.cancel()
    push(gameOver = true)
  }

  // Lock the piece and resolve any line clears, then spawn next piece
  private fun lockAndResolve(falling: Piece) {
    // If any block is in hidden rows (top 2), it's game over per Swift behavior
    val hiddenRows = board.height - 20 // 2 when height=22
    val anyInHidden = falling.cells().any { it.y < hiddenRows }

    // Lock the piece to the board
    board.lock(falling)

    // Check for full rows, including the cells of the locked piece
    val fullRows = board.getFullRows()
    if (fullRows.isNotEmpty()) {
      // Debug: Print flashing rows
      println("DEBUG: Flashing rows: $fullRows")

      // Start flashing animation
      push(customState = state.value.copy(
        flashingRows = fullRows.toSet(),
        active = null // Clear active piece to avoid rendering over flashing rows
      ))

      // Schedule line clearing after flash duration
      scope.launch {
        delay(300) // Flash for 300ms
        val cleared = board.clearLines()
        if (cleared > 0) {
          val scoringLevel = level + 1
          score += when (cleared) {
            1 -> 40
            2 -> 100
            3 -> 300
            4 -> 1200
            else -> 0
          } * scoringLevel
          lines += cleared
          level = lines / 10
        }
        spawn() // Spawn next piece after clearing lines
        push(customState = state.value.copy(flashingRows = emptySet())) // Explicitly clear flashing rows
      }
    } else {
      // No lines to clear, spawn next piece immediately
      if (anyInHidden) {
        push()
        gameOver()
        return
      }
      spawn()
      push()
    }
  }

  // Single game tick: move piece down or lock if it can't move
  private fun tick() {
    val falling = active ?: return
    val moved = falling.move(0, 1)
    if (board.canPlace(moved)) {
      active = moved
      lockStartMs = null // moving down resets lock timer
    } else {
      val now = System.currentTimeMillis()
      if (lockStartMs == null) {
        lockStartMs = now
      } else if (now - lockStartMs!! >= lockDelayMs) {
        // lock and resolve
        active = falling
        lockStartMs = null
        lockAndResolve(falling)
        return
      }
    }
    push()
  }

  // Compute ghost piece position based on current active piece
  private fun computeGhost(piece: Piece?): List<Point> {
    var cur = piece ?: return emptyList()
    while (true) {
      val next = cur.move(0,1)
      if (board.canPlace(next)) cur = next else break
    }
    return cur.cells()
  }

  // Push current game state to StateFlow
  private fun push(gameOver: Boolean = false, customState: GameState? = null) {
    _state.value = customState ?: GameState(
      board = board.snapshot(),
      width = board.width,
      height = board.height,
      active = active,
      ghost = computeGhost(active),
      hold = hold,
      canHold = canHold,
      nextQueue = next.take(5),
      score = score,
      lines = lines,
      level = level,
      gameOver = gameOver,
      paused = paused,
      flashingRows = emptySet(),
      highScore = highScoreManager.getHighScore()
    )
  }

  // Move the piece left or right
  fun move(dx: Int) {
    val a = active ?: return
    val moved = a.move(dx,0)
    if (board.canPlace(moved)) {
      active = moved
      lockStartMs = null // movement resets lock timer
      push()
    }
  }

  // Rotate the piece, with basic wall kicks
  fun rotate(clockwise: Boolean) {
    val a = active ?: return
    val base = a.rotate(if (clockwise) 1 else -1)
    val kicks = listOf(
      Point(0, 0),
      Point(-1, 0),
      Point(1, 0),
      Point(0, -1), // up kick (screen up is y-1)
      Point(-1, -1),
      Point(1, -1)
    )
    for (k in kicks) {
      val candidate = base.move(k.x, k.y)
      if (board.canPlace(candidate)) {
        active = candidate
        lockStartMs = null // rotation resets lock timer
        push()
        return
      }
    }
  }

  // Instantly drop the piece to the lowest valid position
  fun hardDrop() {
    val a = active ?: return
    var cur = a
    var dist = 0
    while (true) {
      val next = cur.move(0,1)
      if (board.canPlace(next)) { cur = next; dist++ } else break
    }
    // Add hard drop points like Swift version
    score += dist * 2
    active = cur
    lockStartMs = null
    lockAndResolve(cur)
  }

  // Hold current piece, swapping with held piece if any
  fun hold() {
    if (!canHold) return
    val current = active ?: return
    val previousHold = hold
    hold = current.type
    if (previousHold == null) {
      spawn()
    } else {
      val swapped = Piece(previousHold, 0, Point(board.width / 2, 0))
      if (!board.canPlace(swapped)) {
        // Show attempted swapped piece then end game
        active = swapped
        gameOver()
        return
      }
      active = swapped
    }
    canHold = false
    lockStartMs = null
    push()
  }

  // Pause or unpause the game
  fun togglePause() {
    paused = !paused
    push()
  }

  // Single soft drop step, called repeatedly when soft drop is enabled
  fun singleSoftDrop() {
    val a = active ?: return
    val moved = a.move(0, 1)
    if (board.canPlace(moved)) {
      active = moved
      lockStartMs = null // movement resets lock timer
      score += 1 // Single soft drop gives 1 point
      push()
    }
  }

  // Enable or disable continuous soft drop
  fun setSoftDrop(enabled: Boolean) { softDrop = enabled }
}