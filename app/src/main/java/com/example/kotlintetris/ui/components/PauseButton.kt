package com.example.kotlintetris.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/*
 * A button that toggles between play and pause states.
 */
@Composable
fun PauseButton(paused: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
  val yellow = Color(0xFFFFFF00)
  Canvas(modifier = modifier.size(24.dp).clickable { onClick() }) {
    val w = size.width
    val h = size.height
    if (!paused) {
      // Two vertical bars
      val barW = w * 0.18f
      val barH = h * 0.8f
      val gap = w * 0.2f
      val leftX = (w - (barW * 2 + gap)) / 2f
      val rightX = leftX + barW + gap
      val topY = (h - barH) / 2f
      drawRect(color = yellow, topLeft = Offset(leftX, topY), size = Size(barW, barH))
      drawRect(color = yellow, topLeft = Offset(rightX, topY), size = Size(barW, barH))
    } else {
      // Play triangle
      val triW = w * 0.6f
      val triH = h * 0.6f
      val left = (w - triW) / 2f
      val top = (h - triH) / 2f
      // Draw as three points: left, bottom-left, top-left offset, pointing right
      val path = androidx.compose.ui.graphics.Path().apply {
        moveTo(left, top)
        lineTo(left, top + triH)
        lineTo(left + triW, top + triH / 2f)
        close()
      }
      drawPath(path, color = yellow)
    }
  }
}
