package com.example.kotlintetris.model

import com.example.kotlintetris.model.Point
import com.example.kotlintetris.model.TetrominoType

data class Piece(
  val type: TetrominoType,
  val rotation: Int,
  val origin: Point
) {
  fun cells(): List<Point> {
    val rel = SHAPES[type]!![rotation % 4]
    return rel.map { Point(origin.x + it.x, origin.y + it.y) }
  }

  fun rotate(delta: Int) = copy(rotation = (rotation + delta).mod(4))
  fun move(dx: Int, dy: Int) = copy(origin = Point(origin.x + dx, origin.y + dy))

  companion object {
    private val SHAPES: Map<TetrominoType, Array<List<Point>>> = mapOf(
      TetrominoType.I to arrayOf(
        listOf(Point(-2,0), Point(-1,0), Point(0,0), Point(1,0)),
        listOf(Point(0,-1), Point(0,0), Point(0,1), Point(0,2)),
        listOf(Point(-2,1), Point(-1,1), Point(0,1), Point(1,1)),
        listOf(Point(-1,-1), Point(-1,0), Point(-1,1), Point(-1,2))
      ),
      TetrominoType.O to arrayOf(
        listOf(Point(0,0), Point(1,0), Point(0,1), Point(1,1)),
        listOf(Point(0,0), Point(1,0), Point(0,1), Point(1,1)),
        listOf(Point(0,0), Point(1,0), Point(0,1), Point(1,1)),
        listOf(Point(0,0), Point(1,0), Point(0,1), Point(1,1))
      ),
      TetrominoType.T to arrayOf(
        listOf(Point(0,0), Point(-1,0), Point(1,0), Point(0,1)),
        listOf(Point(0,0), Point(0,-1), Point(0,1), Point(1,0)),
        listOf(Point(0,0), Point(-1,0), Point(1,0), Point(0,-1)),
        listOf(Point(0,0), Point(0,-1), Point(0,1), Point(-1,0))
      ),
      TetrominoType.S to arrayOf(
        listOf(Point(0,0), Point(1,0), Point(0,1), Point(-1,1)),
        listOf(Point(0,0), Point(0,-1), Point(1,0), Point(1,1)),
        listOf(Point(0,0), Point(1,0), Point(0,1), Point(-1,1)),
        listOf(Point(0,0), Point(0,-1), Point(1,0), Point(1,1))
      ),
      TetrominoType.Z to arrayOf(
        listOf(Point(0,0), Point(-1,0), Point(0,1), Point(1,1)),
        listOf(Point(0,0), Point(0,1), Point(1,0), Point(1,-1)),
        listOf(Point(0,0), Point(-1,0), Point(0,1), Point(1,1)),
        listOf(Point(0,0), Point(0,1), Point(1,0), Point(1,-1))
      ),
      TetrominoType.J to arrayOf(
        listOf(Point(0,0), Point(-1,0), Point(1,0), Point(-1,1)),
        listOf(Point(0,0), Point(0,-1), Point(0,1), Point(1,1)),
        listOf(Point(0,0), Point(-1,0), Point(1,0), Point(1,-1)),
        listOf(Point(0,0), Point(0,-1), Point(0,1), Point(-1,-1))
      ),
      TetrominoType.L to arrayOf(
        listOf(Point(0,0), Point(-1,0), Point(1,0), Point(1,1)),
        listOf(Point(0,0), Point(0,-1), Point(0,1), Point(1,-1)),
        listOf(Point(0,0), Point(-1,0), Point(1,0), Point(-1,-1)),
        listOf(Point(0,0), Point(0,-1), Point(0,1), Point(-1,1))
      )
    )
  }
}
