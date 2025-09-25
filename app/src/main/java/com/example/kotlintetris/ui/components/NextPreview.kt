package com.example.kotlintetris.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlintetris.model.Piece
import com.example.kotlintetris.model.Point
import com.example.kotlintetris.model.TetrominoType

@Composable
fun NextPreview(next: TetrominoType?, modifier: Modifier = Modifier) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .background(Color.Black)
  ) {
    Text("NEXT", color = Color.White, fontFamily = FontFamily.Monospace, fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp))
    Box(
      modifier = Modifier
        .width(100.dp)
        .height(80.dp)
        .border(1.dp, Color.White)
        .background(Color.Transparent)
        .padding(4.dp)
    ) {
      Canvas(modifier = Modifier.matchParentSize()) {
        val boxSize = size.minDimension
        val cell = boxSize / 4f
        next?.let { type ->
          val p = Piece(type, 0, Point(0, 0))
          val cells = p.cells()
          val minX = cells.minOf { it.x }
          val maxX = cells.maxOf { it.x }
          val minY = cells.minOf { it.y }
          val maxY = cells.maxOf { it.y }
          val w = (maxX - minX + 1)
          val h = (maxY - minY + 1)
          val offsetX = ((4 - w) * cell) / 2f
          val offsetY = ((4 - h) * cell) / 2f
          cells.forEach { c ->
            val x = (c.x - minX) * cell + offsetX
            val y = (c.y - minY) * cell + offsetY
            drawRect(
              color = Color(type.color),
              topLeft = Offset(x, y),
              size = Size(cell - 1f, cell - 1f)
            )
          }
        }
      }
    }
  }
}
