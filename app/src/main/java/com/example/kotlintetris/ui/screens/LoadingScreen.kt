package com.example.kotlintetris.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(
  onLoadingComplete: () -> Unit,
  modifier: Modifier = Modifier
) {
  var loadingProgress by remember { mutableStateOf(0f) }

  // Animated tetromino pieces falling
  val infiniteTransition = rememberInfiniteTransition(label = "tetromino_animation")
  val animatedY by infiniteTransition.animateFloat(
    initialValue = -100f,
    targetValue = 800f,
    animationSpec = infiniteRepeatable(
      animation = tween(3000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "falling_pieces"
  )

  // Loading progress animation
  LaunchedEffect(Unit) {
    for (i in 0..100) {
      delay(30) // 3 second total loading time
      loadingProgress = i / 100f
    }
    onLoadingComplete()
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFF0F0F23)), // Dark blue background
    contentAlignment = Alignment.Center
  ) {
    // Animated falling tetromino pieces
    Canvas(modifier = Modifier.fillMaxSize()) {
      drawFallingTetrominoes(animatedY)
    }

    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      // Game title
      Text(
        text = "TETRIS",
        color = Color.White,
        fontSize = 48.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold
      )

      Spacer(modifier = Modifier.height(24.dp))

      // Subtitle
      Text(
        text = "Classic Puzzle Game",
        color = Color(0xFF888888),
        fontSize = 16.sp,
        fontFamily = FontFamily.Monospace
      )

      Spacer(modifier = Modifier.height(48.dp))

      // Loading bar background
      Box(
        modifier = Modifier
          .width(240.dp)
          .height(8.dp)
          .background(Color(0xFF333333))
      ) {
        // Loading bar progress
        Box(
          modifier = Modifier
            .fillMaxWidth(loadingProgress)
            .height(8.dp)
            .background(Color(0xFF00FF00))
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Loading text
      Text(
        text = "Loading... ${(loadingProgress * 100).toInt()}%",
        color = Color.White,
        fontSize = 14.sp,
        fontFamily = FontFamily.Monospace
      )
    }
  }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawFallingTetrominoes(animatedY: Float) {
  val blockSize = 24f
  val colors = listOf(
    Color(0xFF00F0F0), // I piece - cyan
    Color(0xFFF0F000), // O piece - yellow
    Color(0xFFA000F0), // T piece - purple
    Color(0xFF00F000), // S piece - green
    Color(0xFFF00000), // Z piece - red
    Color(0xFF0000F0), // J piece - blue
    Color(0xFFF0A000)  // L piece - orange
  )

  // Draw multiple falling pieces at different positions and speeds
  val pieces = listOf(
    Triple(size.width * 0.1f, animatedY * 0.8f, colors[0]),
    Triple(size.width * 0.25f, animatedY * 1.2f, colors[1]),
    Triple(size.width * 0.4f, animatedY, colors[2]),
    Triple(size.width * 0.55f, animatedY * 0.9f, colors[3]),
    Triple(size.width * 0.7f, animatedY * 1.1f, colors[4]),
    Triple(size.width * 0.85f, animatedY * 0.7f, colors[5])
  )

  pieces.forEach { (x, y, color) ->
    // Draw I-piece (4 blocks vertical)
    for (i in 0..3) {
      if (y + (i * blockSize) < size.height + blockSize) {
        drawRect(
          color = color.copy(alpha = 0.7f),
          topLeft = Offset(x, (y + (i * blockSize)) % (size.height + 200f)),
          size = Size(blockSize, blockSize)
        )
      }
    }
  }
}
