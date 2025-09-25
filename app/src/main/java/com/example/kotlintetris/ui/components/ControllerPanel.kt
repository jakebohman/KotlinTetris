package com.example.kotlintetris.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.min
import kotlin.math.sqrt

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
    modifier = modifier.padding(start = 12.dp, end = 12.dp, top = 36.dp, bottom = 0.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    // D-pad: 5 black squares forming a cross
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(160.dp)) {
      Canvas(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
          detectTapGestures(
            onPress = { offset ->
              val w = size.width; val h = size.height
              val cx = w / 2f; val cy = h / 2f
              val dx = offset.x - cx; val dy = offset.y - cy
              val horizontal = kotlin.math.abs(dx) > kotlin.math.abs(dy)
              if (horizontal) {
                if (dx < 0) onLeft() else onRight()
                // Wait for release to avoid retrigger onPress re-entry
                try {
                  awaitRelease()
                } catch (_: Throwable) {}
              } else {
                if (dy < 0) {
                  onUp()
                  try { awaitRelease() } catch (_: Throwable) {}
                } else {
                  // Soft drop hold while pressed
                  onDownPress(true)
                  try { awaitRelease() } catch (_: Throwable) {}
                  onDownPress(false)
                }
              }
            }
          )
        }
      ) {
        val w = size.width; val h = size.height
        val s = min(w, h) / 3f // square size so total cross fits in 3s x 3s
        val cx = w / 2f; val cy = h / 2f
        val black = Color.Black
        fun drawSquare(centerX: Float, centerY: Float) {
          drawRect(
            color = black,
            topLeft = Offset(centerX - s / 2f, centerY - s / 2f),
            size = Size(s, s)
          )
        }
        // center + arms
        drawSquare(cx, cy)
        drawSquare(cx - s, cy) // left
        drawSquare(cx + s, cy) // right
        drawSquare(cx, cy - s) // up
        drawSquare(cx, cy + s) // down

        // Equilateral white arrows centered in arm squares
        val side = s * 0.6f
        val alt = (sqrt(3.0) / 2.0 * side).toFloat()
        fun tri(center: Offset, dir: Offset) {
          val dirLen = kotlin.math.hypot(dir.x, dir.y)
          val ux = dir.x / dirLen; val uy = dir.y / dirLen
          val px = -uy; val py = ux // perpendicular unit
          val base1 = Offset(center.x - px * (side / 2f), center.y - py * (side / 2f))
          val base2 = Offset(center.x + px * (side / 2f), center.y + py * (side / 2f))
          val tip = Offset(center.x + ux * alt, center.y + uy * alt)
          val p = Path().apply {
            moveTo(base1.x, base1.y); lineTo(base2.x, base2.y); lineTo(tip.x, tip.y); close()
          }
          drawPath(p, color = Color.White)
        }
        tri(Offset(cx - s*3/4, cy), Offset(-1f, 0f)) // left
        tri(Offset(cx + s*3/4, cy), Offset(1f, 0f))  // right
        tri(Offset(cx, cy - s*3/4), Offset(0f, -1f)) // up
        tri(Offset(cx, cy + s*3/4), Offset(0f, 1f))  // down
      }
    }

    // A/B buttons panel (moved down slightly)
    Row(
      horizontalArrangement = Arrangement.spacedBy(24.dp),
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(top = 64.dp)
    ) {
      FramedActionButton(label = "B", onPress = onB)
      FramedActionButton(label = "A", onPress = onA)
    }
  }
}

@Composable
private fun FramedActionButton(label: String, onPress: () -> Unit) {
  // Outer column: square on top, label below aligned to bottom-right of square
  Column(horizontalAlignment = Alignment.End, modifier = Modifier.width(84.dp)) {
    // White rounded square frame with thin black border
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier
        .size(84.dp)
        .background(Color.White, RoundedCornerShape(8.dp))
        //.border(1.dp, Color.Black, RoundedCornerShape(8.dp))
    ) {
      // Red circular button with thin black border
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .size(62.dp)
          .background(Color(0xFFFF0000), CircleShape)
          .border(1.dp, Color.Black, CircleShape)
          .pointerInput(Unit) { detectTapGestures(onPress = {
            onPress()
            try { awaitRelease() } catch (_: Throwable) {}
          }) }
      ) {
        // Remove centered letter; letter appears below square instead
      }
    }
    // Retro label below square at bottom-right
    Text(
      label,
      color = Color.Red,
      fontFamily = FontFamily.Monospace, // retro-styled monospace
      fontWeight = FontWeight.Bold,
      fontSize = 18.sp,
      modifier = Modifier.padding(top = 4.dp)
    )
  }
}
