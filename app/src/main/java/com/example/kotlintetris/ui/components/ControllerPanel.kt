package com.example.kotlintetris.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ControllerPanel(
  onLeft: () -> Unit,
  onRight: () -> Unit,
  onUp: () -> Unit,
  onDownPress: (Boolean) -> Unit,
  onA: () -> Unit,
  onB: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier.background(Color(0xFFEEEEEE)).padding(12.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    // D-pad
    Box(
      modifier = Modifier
        .size(112.dp)
        .background(Color.Black)
        .border(2.dp, Color.White)
    ) {
      Canvas(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
          detectTapGestures(onTap = { offset ->
            val w = size.width; val h = size.height
            val cx = w / 2f; val cy = h / 2f
            val dx = offset.x - cx; val dy = offset.y - cy
            if (kotlin.math.abs(dx) > kotlin.math.abs(dy)) {
              if (dx < 0) onLeft() else onRight()
            } else {
              if (dy < 0) onUp() else onDownPress(true).also { onDownPress(false) }
            }
          })
        }
      ) {
        val w = size.width; val h = size.height
        val cx = w / 2f; val cy = h / 2f
        val arm = kotlin.math.min(w, h) * 0.35f
        val arrowW = arm * 0.35f
        val arrowH = arm * 0.55f
        val white = Color.White
        fun tri(center: Offset, dx: Float, dy: Float) {
          val base1 = Offset(center.x - dy * arrowW / arm, center.y + dx * arrowW / arm)
          val base2 = Offset(center.x + dy * arrowW / arm, center.y - dx * arrowW / arm)
          val tip = Offset(center.x + dx * arrowH / arm, center.y + dy * arrowH / arm)
          drawPath(androidx.compose.ui.graphics.Path().apply {
            moveTo(base1.x, base1.y); lineTo(base2.x, base2.y); lineTo(tip.x, tip.y); close()
          }, color = white)
        }
        tri(Offset(cx - arm, cy), -arm, 0f) // left
        tri(Offset(cx + arm, cy), arm, 0f) // right
        tri(Offset(cx, cy - arm), 0f, -arm) // up
        tri(Offset(cx, cy + arm), 0f, arm) // down
      }
    }

    // A/B buttons
    Row(horizontalArrangement = Arrangement.spacedBy(18.dp), verticalAlignment = Alignment.CenterVertically) {
      ActionButton(label = "A", onPress = onA)
      ActionButton(label = "B", onPress = onB)
    }
  }
}

@Composable
private fun ActionButton(label: String, onPress: () -> Unit) {
  Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier
      .size(72.dp)
      .background(Color(0xFFFF0000), CircleShape)
      .pointerInput(Unit) { detectTapGestures(onTap = { onPress() }) }
  ) {
    Text(label, color = Color.White, fontFamily = FontFamily.Monospace, fontSize = 20.sp)
  }
}
