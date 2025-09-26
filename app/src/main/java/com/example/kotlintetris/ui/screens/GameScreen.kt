package com.example.kotlintetris.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlintetris.ui.components.*
import com.example.kotlintetris.ui.viewmodel.GameViewModel

/*
 * Main game screen composable, integrating game area, controller panel, pause button, and game over overlay.
 * Interacts with GameViewModel to manage game state and user inputs.
 */
@Composable
fun GameScreen(onReturnToMenu: () -> Unit = {}) {
  val context = LocalContext.current
  val vm: GameViewModel = viewModel { GameViewModel(context) }
  val state by vm.state.collectAsState()

  val controllerHeight = 220.dp
  val overlap = 80.dp

  Box(Modifier.fillMaxSize()) {
    // Retro Tetris background
    RetroBackground()

    // Controller background anchored to bottom; restore fixed height
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(controllerHeight)
        .align(Alignment.BottomCenter)
        .background(Color(0xFFB2B2BF))
    ) {
      // top highlight strip
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(4.dp)
          .align(Alignment.TopCenter)
          .background(Color(0xFFD9D9E6))
      )
      // bottom shadow strip
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(4.dp)
          .align(Alignment.BottomCenter)
          .background(Color(0xFF66666F))
      )
      // Nintendo branding
      Box(modifier = Modifier.fillMaxSize().padding(start = 12.dp, top = 12.dp, end = 4.dp)) {
        Text(
          text = "Nintendo",
          color = Color.Red,
          fontFamily = FontFamily.SansSerif,
          fontWeight = FontWeight.Black,
          fontSize = 18.sp,
          modifier = Modifier.align(Alignment.TopEnd)
        )
      }
    }

    // Controller content on top of gray background
    ControllerPanel(
      onLeft = { vm.left() },
      onRight = { vm.right() },
      onUp = { vm.rotateCW() },
      onDownPress = { on -> if (on) vm.hardDrop() }, // Down button: Hard drop
      onA = { vm.rotateCCW() },  // A Button: Rotate clockwise
      onB = { vm.rotateCW() }, // B Button: Rotate counter-clockwise
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.BottomCenter)
        .offset(y = (-16).dp)
    )

    // Game area anchored near bottom, overlapping the top of the D-pad
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.BottomCenter)
        .padding(horizontal = 20.dp)
        .offset(y = -(controllerHeight - overlap)),
      contentAlignment = Alignment.Center
    ) {
      GameArea(
        state = state,
        modifier = Modifier.fillMaxWidth(0.85f),
        onTap = { vm.rotateCW() },           // Tap anywhere: Rotate clockwise
        onSwipeLeft = { vm.left() },         // Swipe left: Move piece left
        onSwipeRight = { vm.right() },       // Swipe right: Move piece right
        onSwipeDown = { vm.singleSoftDrop() }, // Swipe down: Single soft drop (one row down)
        onSwipeUp = { vm.rotateCW() },       // Swipe up: Rotate piece (alternative)
        onLongPress = { pressed -> vm.softDrop(pressed) } // Long press: Continuous soft drop
      )
    }

    // Pause button top-right
    Box(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
      Box(modifier = Modifier.align(Alignment.TopEnd)) {
        PauseButton(paused = state.paused, onClick = { vm.togglePause() })
      }
    }

    // Game Over overlay with tap to restart
    if (state.gameOver) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(Color(0xAA000000)),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text("GAME OVER", color = Color.Red, fontSize = 36.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold)
          Spacer(Modifier.height(8.dp))
          Text("FINAL SCORE: ${state.score}", color = Color.White, fontSize = 20.sp, fontFamily = FontFamily.SansSerif)
          Spacer(Modifier.height(8.dp))
          Text("HIGH SCORE: ${state.highScore}", color = Color.Yellow, fontSize = 18.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold)
          Spacer(Modifier.height(16.dp))
          Text("LINES CLEARED: ${state.lines}", color = Color.White, fontSize = 16.sp, fontFamily = FontFamily.SansSerif)
          Spacer(Modifier.height(8.dp))
          Text("LEVEL REACHED: ${state.level}", color = Color.White, fontSize = 16.sp, fontFamily = FontFamily.SansSerif)

          // Show "NEW HIGH SCORE!" if current score equals high score
          if (state.score == state.highScore && state.score > 0) {
            Spacer(Modifier.height(16.dp))
            Text("NEW HIGH SCORE!", color = Color.Green, fontSize = 18.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
          }

          Spacer(Modifier.height(32.dp))

          // Restart button
          Box(
            modifier = Modifier
              .width(200.dp)
              .height(48.dp)
              .background(Color(0xFF4CAF50), shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
              .clickable { vm.restart() },
            contentAlignment = Alignment.Center
          ) {
            Text("PLAY AGAIN", color = Color.White, fontSize = 18.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
          }

          Spacer(Modifier.height(16.dp))

          // Return to menu button
          Box(
            modifier = Modifier
              .width(200.dp)
              .height(48.dp)
              .background(Color(0xFF2196F3), shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
              .clickable { onReturnToMenu() },
            contentAlignment = Alignment.Center
          ) {
            Text("MAIN MENU", color = Color.White, fontSize = 18.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
