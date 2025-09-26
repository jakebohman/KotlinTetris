package com.example.kotlintetris.model

class Board(
  val width: Int = 10,
  val height: Int = 22
) {
  private val cells: Array<TetrominoType?> = Array(width * height) { null }

  operator fun get(x: Int, y: Int): TetrominoType? =
    if (x in 0 until width && y in 0 until height) cells[y * width + x] else null

  private operator fun set(x: Int, y: Int, v: TetrominoType?) {
    if (x in 0 until width && y in 0 until height) cells[y * width + x] = v
  }

  fun canPlace(piece: Piece): Boolean =
    piece.cells().all { p ->
      p.x in 0 until width && p.y in 0 until height && this[p.x, p.y] == null
    }

  fun lock(piece: Piece) {
    piece.cells().forEach { set(it.x, it.y, piece.type) }
  }

  fun getFullRows(): List<Int> {
    return (0 until height).filter { y ->
      (0 until width).all { x -> this[x, y] != null }
    }
  }

  fun clearLines(): Int {
    val fullRows = (0 until height).filter { y ->
      (0 until width).all { x -> this[x, y] != null }
    }
    if (fullRows.isEmpty()) return 0
    val newCells = Array<TetrominoType?>(width * height) { null }
    var writeY = height - 1
    for (y in (height - 1) downTo 0) {
      if (y in fullRows) continue
      for (x in 0 until width) newCells[writeY * width + x] = this[x, y]
      writeY--
    }
    for (i in cells.indices) cells[i] = newCells[i]
    return fullRows.size
  }

  fun snapshot(): List<TetrominoType?> = cells.toList()

  // Clear all cells to empty (null)
  fun clear() {
    for (i in cells.indices) cells[i] = null
  }
}
