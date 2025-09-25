package com.example.kotlintetris.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.kotlintetris.model.GameState
import com.example.kotlintetris.model.Point

@Composable
fun RetroBoard(state: GameState, modifier: Modifier = Modifier) {
  val borderColor = Color.White
  val bgColor = Color.Black
  val gridColor = Color(0x33FFFFFF)
  val w = 10
  val h = 20
  val border: Dp = 4.dp
  val stroke = with(LocalDensity.current) { border.toPx() }

  Canvas(modifier = modifier.aspectRatio(w / h.toFloat())) {
    // Board rect (full canvas), border and background
    drawRect(color = bgColor, size = size)
    // inset the stroke fully inside the canvas so it's fully visible
    val inset = stroke / 2f
    drawRect(
      color = borderColor,
      topLeft = Offset(inset, inset),
      size = Size(size.width - stroke, size.height - stroke),
      style = Stroke(width = stroke)
    )

    val cellW = size.width / w
    val cellH = size.height / h
    // Grid
    for (x in 1 until w) {
      val px = x * cellW
      drawLine(color = gridColor, start = Offset(px, 0f), end = Offset(px, size.height), strokeWidth = 1f)
    }
    for (y in 1 until h) {
      val py = y * cellH
      drawLine(color = gridColor, start = Offset(0f, py), end = Offset(size.width, py), strokeWidth = 1f)
    }

    // Visible window maps to bottom 20 rows of the model board
    val visibleStart = state.height - h

    fun drawCell(x: Int, y: Int, color: Long, alpha: Float = 1f) {
      if (x !in 0 until w) return
      if (y !in 0 until h) return
      drawRect(
        color = Color(color).copy(alpha = alpha),
        topLeft = Offset(x * cellW, y * cellH),
        size = Size(cellW - 1f, cellH - 1f)
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

    // If paused, dim overlay
    if (state.paused) {
      drawRect(color = Color(0x99000000), size = size)
    }
  }
}
