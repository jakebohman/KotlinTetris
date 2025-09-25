package com.example.kotlintetris.model

enum class TetrominoType(val color: Long) {
  I(0xFF55FFFF),
  O(0xFFFFFF55),
  T(0xFFAA55FF),
  S(0xFF55FF55),
  Z(0xFFFF5555),
  J(0xFF5555FF),
  L(0xFFFFAA55);

  companion object {
    fun bag(): MutableList<TetrominoType> = values().toMutableList().also { it.shuffle() }
  }
}

