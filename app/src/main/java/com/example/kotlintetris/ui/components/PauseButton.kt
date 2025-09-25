package com.example.kotlintetris.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PauseButton(paused: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
  val bg = Color(0xFFFFEB3B) // yellow
  val bar = Color(0xFF000000) // black
  Canvas(modifier = modifier.size(24.dp).clickable { onClick() }) {
    val w = size.width
    val h = size.height
    val r = kotlin.math.min(w, h) / 2f
    // Yellow circle background
    drawCircle(color = bg.copy(alpha = if (paused) 0.7f else 1f), radius = r, center = Offset(w / 2f, h / 2f))
    // Pause bars centered
    val barW = w * 0.18f
    val barH = h * 0.6f
    val gap = w * 0.14f
    val leftX = (w - (barW * 2 + gap)) / 2f
    val rightX = leftX + barW + gap
    val topY = (h - barH) / 2f
    drawRect(color = bar, topLeft = Offset(leftX, topY), size = androidx.compose.ui.geometry.Size(barW, barH))
    drawRect(color = bar, topLeft = Offset(rightX, topY), size = androidx.compose.ui.geometry.Size(barW, barH))
  }
}
