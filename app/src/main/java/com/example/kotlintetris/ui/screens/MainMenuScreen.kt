package com.example.kotlintetris.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlintetris.ui.components.RetroBackground
import com.example.kotlintetris.data.HighScoreManager

/*
 * Main Menu Screen for the Tetris game.
 */
@Composable
fun MainMenuScreen(
  onPlayGame: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val highScoreManager = remember { HighScoreManager(context) }
  val highScore by remember { mutableStateOf(highScoreManager.getHighScore()) }

  Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    // Retro background
    RetroBackground()

    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier.padding(32.dp)
    ) {
      // Game title
      Text(
        text = "TETRIS",
        color = Color.White,
        fontSize = 56.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Subtitle
      Text(
        text = "Classic Puzzle Game",
        color = Color(0xFF888888),
        fontSize = 18.sp,
        fontFamily = FontFamily.Monospace
      )

      Spacer(modifier = Modifier.height(16.dp))

      // High Score display
      Text(
        text = "HIGH SCORE: $highScore",
        color = Color.Yellow,
        fontSize = 20.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold
      )

      Spacer(modifier = Modifier.height(64.dp))

      // Decorative tetromino pieces around the title
      Canvas(
        modifier = Modifier
          .size(200.dp, 60.dp)
      ) {
        drawMenuDecorations()
      }

      Spacer(modifier = Modifier.height(48.dp))

      // Play Game button
      Button(
        onClick = onPlayGame,
        modifier = Modifier
          .width(200.dp)
          .height(56.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = Color(0xFF4CAF50),
          contentColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp)
      ) {
        Text(
          text = "PLAY GAME",
          fontSize = 20.sp,
          fontFamily = FontFamily.Monospace,
          fontWeight = FontWeight.Bold
        )
      }

      Spacer(modifier = Modifier.height(32.dp))

      // Instructions
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
      ) {
        Text(
          text = "HOW TO PLAY:",
          color = Color.White,
          fontSize = 16.sp,
          fontFamily = FontFamily.Monospace,
          fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Touch controls
        Text(
          text = "TOUCH CONTROLS:",
          color = Color(0xFF888888),
          fontSize = 12.sp,
          fontFamily = FontFamily.Monospace,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "• Tap to rotate pieces",
          color = Color(0xFFCCCCCC),
          fontSize = 14.sp,
          fontFamily = FontFamily.Monospace
        )
        Text(
          text = "• Swipe left/right to move",
          color = Color(0xFFCCCCCC),
          fontSize = 14.sp,
          fontFamily = FontFamily.Monospace
        )
        Text(
          text = "• Swipe down to drop",
          color = Color(0xFFCCCCCC),
          fontSize = 14.sp,
          fontFamily = FontFamily.Monospace
        )
        Text(
          text = "• Long press for soft drop",
          color = Color(0xFFCCCCCC),
          fontSize = 14.sp,
          fontFamily = FontFamily.Monospace
        )

        Spacer(modifier = Modifier.height(8.dp))

        // D-pad and button controls
        Text(
          text = "CONTROLLER:",
          color = Color(0xFF888888),
          fontSize = 12.sp,
          fontFamily = FontFamily.Monospace,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "• D-pad left/right to move",
          color = Color(0xFFCCCCCC),
          fontSize = 14.sp,
          fontFamily = FontFamily.Monospace
        )
        Text(
          text = "• D-pad up to rotate",
          color = Color(0xFFCCCCCC),
          fontSize = 14.sp,
          fontFamily = FontFamily.Monospace
        )
        Text(
          text = "• D-pad down for hard drop",
          color = Color(0xFFCCCCCC),
          fontSize = 14.sp,
          fontFamily = FontFamily.Monospace
        )
        Text(
          text = "• A button to rotate CCW",
          color = Color(0xFFCCCCCC),
          fontSize = 14.sp,
          fontFamily = FontFamily.Monospace
        )
        Text(
          text = "• B button to rotate CW",
          color = Color(0xFFCCCCCC),
          fontSize = 14.sp,
          fontFamily = FontFamily.Monospace
        )
      }

      Spacer(modifier = Modifier.height(48.dp))
    }
  }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawMenuDecorations() {
  val blockSize = 16f
  val colors = listOf(
    Color(0xFF00F0F0), // Cyan
    Color(0xFFF0F000), // Yellow
    Color(0xFFA000F0), // Purple
    Color(0xFF00F000), // Green
    Color(0xFFF00000), // Red
    Color(0xFF0000F0), // Blue
    Color(0xFFF0A000)  // Orange
  )

  // Left decorative pieces
  val leftX = 20f
  val leftY = size.height / 2f - blockSize
  for (i in 0..2) {
    drawRect(
      color = colors[i % colors.size],
      topLeft = Offset(leftX + i * blockSize, leftY),
      size = Size(blockSize, blockSize)
    )
  }

  // Right decorative pieces
  val rightX = size.width - 60f
  val rightY = size.height / 2f - blockSize
  for (i in 0..2) {
    drawRect(
      color = colors[(i + 3) % colors.size],
      topLeft = Offset(rightX + i * blockSize, rightY),
      size = Size(blockSize, blockSize)
    )
  }
}
