package com.example.kotlintetris.model

/*
 * Enum class representing the different types of Tetrominoes in Tetris.
 */
enum class TetrominoType(val color: Long) {
  I(0xFF2CA6A6), // muted teal
  O(0xFFD1C33E), // muted yellow
  T(0xFF8B55B8), // muted purple
  S(0xFF55B855), // muted green
  Z(0xFFB84C4C), // muted red
  J(0xFF4C5FC3), // muted blue
  L(0xFFC2813E); // muted orange

  // Generates a shuffled bag of all Tetromino types
  companion object {
    fun bag(): MutableList<TetrominoType> = entries.toMutableList().also { it.shuffle() }
  }
}
