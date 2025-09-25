package com.example.kotlintetris.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.example.kotlintetris.model.GameState
import com.example.kotlintetris.model.Point

@Composable
fun RetroBoard(state: GameState, modifier: Modifier = Modifier) {
  val bgColor = Color(0xFF2E2E2E) // dark grey background
  val gridColor = Color.Black      // black grid lines
  val w = 10
  val h = 20

  Canvas(modifier = modifier.aspectRatio(w / h.toFloat())) {
    // Background
    drawRect(color = bgColor, size = size)

    val cellW = size.width / w
    val cellH = size.height / h

    // Visible window maps to bottom 20 rows of the model board
    val visibleStart = state.height - h

    fun drawCell(x: Int, y: Int, color: Long, alpha: Float = 1f) {
      if (x !in 0 until w) return
      if (y !in 0 until h) return
      drawRect(
        color = Color(color).copy(alpha = alpha),
        topLeft = Offset(x * cellW, y * cellH),
        size = Size(cellW, cellH)
      )
    }

    // Placed blocks
    state.board.forEachIndexed { index, t ->
      t?.let {
        val bx = index % state.width
        val by = index / state.width
        val vy = by - visibleStart
        if (vy in 0 until h) drawCell(bx, vy, it.color)
      }
    }

    // Ghost
    state.ghost.forEach { p: Point ->
      val vy = p.y - visibleStart
      drawCell(p.x, vy, 0xFFFFFFFF, 0.25f)
    }

    // Active piece
    val activeCells = state.active?.cells()
    val activeColor = state.active?.type?.color
    if (activeCells != null && activeColor != null) {
      activeCells.forEach { p ->
        val vy = p.y - visibleStart
        drawCell(p.x, vy, activeColor)
      }
    }

    // Grid lines on top (between every cell)
    for (x in 1 until w) {
      val px = x * cellW
      drawLine(color = gridColor, start = Offset(px, 0f), end = Offset(px, size.height), strokeWidth = 1f)
    }
    for (y in 1 until h) {
      val py = y * cellH
      drawLine(color = gridColor, start = Offset(0f, py), end = Offset(size.width, py), strokeWidth = 1f)
    }

    // If paused, dim overlay
    if (state.paused) {
      drawRect(color = Color(0x99000000), size = size)
    }
  }
}
