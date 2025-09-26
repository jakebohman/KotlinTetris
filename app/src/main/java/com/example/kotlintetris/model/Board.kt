package com.example.kotlintetris.model

/*
 * Represents the Tetris game board.
 */
class Board(
  val width: Int = 10,
  val height: Int = 22
) {
  // 1D array to represent the board cells, initialized to null (empty)
  private val cells: Array<TetrominoType?> = Array(width * height) { null }

  // Accessor to get the cell value at (x, y)
  operator fun get(x: Int, y: Int): TetrominoType? =
    if (x in 0 until width && y in 0 until height) cells[y * width + x] else null

  // Mutator to set the cell value at (x, y)
  private operator fun set(x: Int, y: Int, v: TetrominoType?) {
    if (x in 0 until width && y in 0 until height) cells[y * width + x] = v
  }

  // Check if a piece can be placed on the board without collisions or going out of bounds
  fun canPlace(piece: Piece): Boolean =
    piece.cells().all { p ->
      p.x in 0 until width && p.y in 0 until height && this[p.x, p.y] == null
    }

  // Lock a piece into the board by setting its cells to the piece's type
  fun lock(piece: Piece) {
    piece.cells().forEach { set(it.x, it.y, piece.type) }
  }

  // Get a list of all full rows (y-coordinates) on the board
  fun getFullRows(): List<Int> {
    return (0 until height).filter { y ->
      (0 until width).all { x -> this[x, y] != null }
    }
  }

  // Clear all full lines from the board and return the number of lines cleared
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

  // Get a snapshot of the current board state as a list
  fun snapshot(): List<TetrominoType?> = cells.toList()

  // Clear all cells to empty (null)
  fun clear() {
    for (i in cells.indices) cells[i] = null
  }
}
