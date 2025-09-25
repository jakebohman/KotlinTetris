package com.example.kotlintetris.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.kotlintetris.model.GameState
import com.example.kotlintetris.model.Point
import kotlin.math.floor

@Composable
fun RetroBoard(state: GameState, modifier: Modifier = Modifier) {
  val bgColor = Color(0xFF111111) // fully opaque dark grey background
  val w = 10
  val h = 20

  Canvas(modifier = modifier.aspectRatio(w / h.toFloat())) {
    // Background
    drawRect(color = bgColor, size = size)

    // Quantize cell size to integer pixels for uniform visuals
    val rawCellW = size.width / w
    val rawCellH = size.height / h
    val cellW = floor(rawCellW).coerceAtLeast(1f)
    val cellH = floor(rawCellH).coerceAtLeast(1f)
    val boardW = cellW * w
    val boardH = cellH * h
    val offsetX = floor((size.width - boardW) / 2f)
    val offsetY = floor((size.height - boardH) / 2f)

    // Visible window maps to bottom 20 rows of the model board
    val visibleStart = state.height - h

    fun cellTopLeft(x: Int, y: Int): Offset = Offset(offsetX + x * cellW, offsetY + y * cellH)

    fun blend(c1: Color, c2: Color, t: Float): Color = Color(
      red = c1.red + (c2.red - c1.red) * t,
      green = c1.green + (c2.green - c1.green) * t,
      blue = c1.blue + (c2.blue - c1.blue) * t,
      alpha = c1.alpha // keep original alpha
    )

    fun drawBlock3D(x: Int, y: Int, colorLong: Long, alpha: Float = 1f) {
      if (x !in 0 until w || y !in 0 until h) return
      val baseColor = Color(colorLong).copy(alpha = alpha)
      val topLeft = cellTopLeft(x, y)

      // Base fill
      drawRect(color = baseColor, topLeft = topLeft, size = Size(cellW, cellH))

      // 3D effect parameters (approx 10% of cell size, min 2px)
      val hiH = floor((cellH * 0.1f)).coerceAtLeast(2f)
      val shW = floor((cellW * 0.1f)).coerceAtLeast(2f)

      // Top highlight
      val hiColor = blend(baseColor, Color.White, 0.3f)
      drawRect(
        color = hiColor,
        topLeft = topLeft,
        size = Size(cellW, hiH)
      )

      // Right-side shadow
      val shColor = blend(baseColor, Color.Black, 0.3f)
      drawRect(
        color = shColor,
        topLeft = Offset(topLeft.x + cellW - shW, topLeft.y),
        size = Size(shW, cellH)
      )

      // Thin white border
      drawRect(
        color = Color.White,
        topLeft = topLeft,
        size = Size(cellW, cellH),
        style = Stroke(width = 1f)
      )
    }

    // Placed blocks with 3D effect
    state.board.forEachIndexed { index, t ->
      t?.let {
        val bx = index % state.width
        val by = index / state.width
        val vy = by - visibleStart
        if (vy in 0 until h) drawBlock3D(bx, vy, it.color)
      }
    }

    // Ghost (kept simple and semi-transparent)
    state.ghost.forEach { p: Point ->
      val vy = p.y - visibleStart
      if (p.x in 0 until w && vy in 0 until h) {
        val tl = cellTopLeft(p.x, vy)
        drawRect(
          color = Color.White.copy(alpha = 0.25f),
          topLeft = tl,
          size = Size(cellW, cellH)
        )
      }
    }

    // Active (falling) piece with 3D effect
    val activeCells = state.active?.cells()
    val activeColor = state.active?.type?.color
    if (activeCells != null && activeColor != null) {
      activeCells.forEach { p ->
        val vy = p.y - visibleStart
        if (p.x in 0 until w && vy in 0 until h) drawBlock3D(p.x, vy, activeColor)
      }
    }

    // If paused, dim overlay
    if (state.paused) {
      drawRect(color = Color(0x99000000), size = size)
    }
  }
}
