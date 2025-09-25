package com.example.kotlintetris.logic

import com.example.kotlintetris.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.max

class GameEngine(
  private val scope: CoroutineScope
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
      level = 1,
      gameOver = false,
      paused = false
    )
  )
  val state: StateFlow<GameState> = _state

  private var bag: MutableList<TetrominoType> = TetrominoType.bag()
  private val next: ArrayDeque<TetrominoType> = ArrayDeque()
  private var active: Piece? = null
  private var hold: TetrominoType? = null
  private var canHold = true
  private var score = 0
  private var lines = 0
  private var level = 1
  private var loopJob: Job? = null
  private var softDrop = false
  private var paused: Boolean = false

  fun start() {
    repeat(5) { enqueue() }
    spawn()
    loopJob?.cancel()
    loopJob = scope.launch { loop() }
    push()
  }

  private suspend fun loop() {
    while (true) {
      val interval = max(50L, 800L - (level - 1) * 60L)
      delay(if (softDrop) interval / 8 else interval)
      if (!paused) tick()
    }
  }

  private fun enqueue() {
    if (bag.isEmpty()) bag = TetrominoType.bag()
    next.addLast(bag.removeFirst())
  }

  private fun spawn() {
    val type = next.removeFirst()
    enqueue()
    val piece = Piece(type, 0, Point(board.width / 2, 0))
    // Assign first so a final push/gameOver can show the piece that failed to spawn
    active = piece
    if (!board.canPlace(piece)) {
      gameOver()
      return
    }
    canHold = true
  }

  private fun gameOver() {
    loopJob?.cancel()
    push(gameOver = true)
  }

  private fun tick() {
    val falling = active ?: return
    val moved = falling.move(0, 1)
    if (board.canPlace(moved)) {
      active = moved
    } else {
      board.lock(falling)
      val cleared = board.clearLines()
      if (cleared > 0) {
        score += when (cleared) {
          1 -> 100
          2 -> 300
          3 -> 500
          4 -> 800
          else -> 0
        } * level
        lines += cleared
        level = 1 + lines / 10
      }
      spawn()
    }
    push()
  }

  private fun computeGhost(piece: Piece?): List<Point> {
    var cur = piece ?: return emptyList()
    while (true) {
      val next = cur.move(0,1)
      if (board.canPlace(next)) cur = next else break
    }
    return cur.cells()
  }

  private fun push(gameOver: Boolean = false) {
    _state.value = GameState(
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
      paused = paused
    )
  }

  fun move(dx: Int) {
    val a = active ?: return
    val moved = a.move(dx,0)
    if (board.canPlace(moved)) {
      active = moved
      push()
    }
  }

  fun rotate(clockwise: Boolean) {
    val a = active ?: return
    val r = a.rotate(if (clockwise) 1 else -1)
    if (board.canPlace(r)) {
      active = r
      push()
    }
  }

  fun hardDrop() {
    val a = active ?: return
    var cur = a
    while (true) {
      val next = cur.move(0,1)
      if (board.canPlace(next)) cur = next else break
    }
    active = cur
    tick() // lock
  }

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
    push()
  }

  fun togglePause() {
    paused = !paused
    push()
  }

  fun setSoftDrop(enabled: Boolean) { softDrop = enabled }
}
