package com.example.kotlintetris.model

data class GameState(
  val board: List<TetrominoType?>,
  val width: Int,
  val height: Int,
  val active: Piece?,
  val ghost: List<Point>,
  val hold: TetrominoType?,
  val canHold: Boolean,
  val nextQueue: List<TetrominoType>,
  val score: Int,
  val lines: Int,
  val level: Int,
  val gameOver: Boolean,
  val paused: Boolean,
  val flashingRows: Set<Int> = emptySet()
)