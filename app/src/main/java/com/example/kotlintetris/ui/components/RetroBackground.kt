package com.example.kotlintetris.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.floor

/*
   RetroBackground is a composable that draws a retro-style background
   with a dark theme, grid pattern, and scattered tetromino shapes.
 */
@Composable
fun RetroBackground(modifier: Modifier = Modifier) {
  Canvas(modifier = modifier.fillMaxSize()) {
    // Dark background
    drawRect(color = Color(0xFF1A1A2E), size = size)

    // Grid pattern
    drawGrid()

    // Scattered tetromino shapes for decoration
    drawDecorativeTetrominoes()
  }
}

private fun DrawScope.drawGrid() {
  val gridColor = Color(0xFF16213E).copy(alpha = 0.3f)
  val cellSize = 40f

  // Vertical lines
  var x = 0f
  while (x < size.width) {
    drawLine(
      color = gridColor,
      start = Offset(x, 0f),
      end = Offset(x, size.height),
      strokeWidth = 1f
    )
    x += cellSize
  }

  // Horizontal lines
  var y = 0f
  while (y < size.height) {
    drawLine(
      color = gridColor,
      start = Offset(0f, y),
      end = Offset(size.width, y),
      strokeWidth = 1f
    )
    y += cellSize
  }
}

private fun DrawScope.drawDecorativeTetrominoes() {
  val blockSize = 20f
  val alpha = 0.15f // Slightly increased alpha for better visibility

  // Tetromino colors with low alpha
  val colors = listOf(
    Color(0xFF00F0F0).copy(alpha = alpha), // I piece - cyan
    Color(0xFFF0F000).copy(alpha = alpha), // O piece - yellow
    Color(0xFFA000F0).copy(alpha = alpha), // T piece - purple
    Color(0xFF00F000).copy(alpha = alpha), // S piece - green
    Color(0xFFF00000).copy(alpha = alpha), // Z piece - red
    Color(0xFF0000F0).copy(alpha = alpha), // J piece - blue
    Color(0xFFF0A000).copy(alpha = alpha)  // L piece - orange
  )

  // Calculate edge zones
  val edgeMargin = size.width * 0.15f // 15% from each edge
  val centerStart = size.width * 0.3f
  val centerEnd = size.width * 0.7f

  // Left edge positions
  val leftPositions = listOf(
    // I pieces on left edge
    Pair(20f, size.height * 0.1f), Pair(40f, size.height * 0.1f),
    Pair(60f, size.height * 0.1f), Pair(80f, size.height * 0.1f),

    // O piece on left edge
    Pair(30f, size.height * 0.25f), Pair(50f, size.height * 0.25f),
    Pair(30f, size.height * 0.25f + 20f), Pair(50f, size.height * 0.25f + 20f),

    // T piece on left edge
    Pair(60f, size.height * 0.4f), Pair(40f, size.height * 0.4f + 20f),
    Pair(60f, size.height * 0.4f + 20f), Pair(80f, size.height * 0.4f + 20f),

    // S piece on left edge
    Pair(20f, size.height * 0.6f), Pair(40f, size.height * 0.6f),
    Pair(40f, size.height * 0.6f + 20f), Pair(60f, size.height * 0.6f + 20f),

    // L piece on left edge
    Pair(30f, size.height * 0.8f), Pair(30f, size.height * 0.8f + 20f),
    Pair(30f, size.height * 0.8f + 40f), Pair(50f, size.height * 0.8f + 40f)
  )

  // Right edge positions
  val rightPositions = listOf(
    // I piece on right edge
    Pair(size.width - 100f, size.height * 0.15f), Pair(size.width - 80f, size.height * 0.15f),
    Pair(size.width - 60f, size.height * 0.15f), Pair(size.width - 40f, size.height * 0.15f),

    // O piece on right edge
    Pair(size.width - 70f, size.height * 0.3f), Pair(size.width - 50f, size.height * 0.3f),
    Pair(size.width - 70f, size.height * 0.3f + 20f), Pair(size.width - 50f, size.height * 0.3f + 20f),

    // T piece on right edge
    Pair(size.width - 60f, size.height * 0.45f), Pair(size.width - 80f, size.height * 0.45f + 20f),
    Pair(size.width - 60f, size.height * 0.45f + 20f), Pair(size.width - 40f, size.height * 0.45f + 20f),

    // Z piece on right edge
    Pair(size.width - 80f, size.height * 0.65f), Pair(size.width - 60f, size.height * 0.65f),
    Pair(size.width - 60f, size.height * 0.65f + 20f), Pair(size.width - 40f, size.height * 0.65f + 20f),

    // J piece on right edge
    Pair(size.width - 50f, size.height * 0.85f), Pair(size.width - 70f, size.height * 0.85f + 20f),
    Pair(size.width - 50f, size.height * 0.85f + 20f), Pair(size.width - 30f, size.height * 0.85f + 20f)
  )

  val allPositions = leftPositions + rightPositions

  allPositions.forEachIndexed { index, (x, y) ->
    if (x >= 0 && y >= 0 && x < size.width - blockSize && y < size.height - blockSize) {
      val color = colors[index % colors.size]
      drawRect(
        color = color,
        topLeft = Offset(x, y),
        size = Size(blockSize, blockSize)
      )
    }
  }
}
