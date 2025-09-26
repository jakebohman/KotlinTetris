package com.example.kotlintetris.model

/*
 * Represents the complete state of a Tetris game.
 */
data class GameState(
  val board: List<TetrominoType?>, // Flattened 1D list representing the board
  val width: Int, // Width of the board
  val height: Int, // Height of the board
  val active: Piece?, // Current active piece
  val ghost: List<Point>, // Ghost piece positions
  val hold: TetrominoType?, // Held piece type
  val canHold: Boolean, // Whether holding is allowed
  val nextQueue: List<TetrominoType>, // Upcoming pieces
  val score: Int, // Current score
  val lines: Int, // Total lines cleared
  val level: Int, // Current level
  val gameOver: Boolean, // Game over status
  val paused: Boolean, // Paused status
  val flashingRows: Set<Int> = emptySet(), // Rows currently flashing
  val highScore: Int = 0 // High score
)