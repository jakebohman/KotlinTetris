package com.example.kotlintetris.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlintetris.ui.components.*
import com.example.kotlintetris.ui.viewmodel.GameViewModel

@Composable
fun GameScreen(vm: GameViewModel = viewModel()) {
  val state by vm.state.collectAsState()

  val controllerHeight = 240.dp
  val overlap = 120.dp

  Box(Modifier.fillMaxSize().background(Color.Black)) {
    // Gray controller background anchored to bottom, behind the game area
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(controllerHeight)
        .align(Alignment.BottomCenter)
        .background(Color(0xFFEEEEEE))
    )

    // Controller content on top of gray background
    ControllerPanel(
      onLeft = { vm.left() },
      onRight = { vm.right() },
      onUp = { vm.rotateCW() },
      onDownPress = { on -> vm.softDrop(on) },
      onA = { vm.rotateCW() },
      onB = { vm.hardDrop() },
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.BottomCenter)
    )

    // Game area anchored near bottom, overlapping the top of the D-pad
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.BottomCenter)
        .padding(horizontal = 12.dp)
        .offset(y = -(controllerHeight - overlap)),
      contentAlignment = Alignment.Center
    ) {
      GameArea(state = state, modifier = Modifier.fillMaxWidth(0.9f))
    }

    // Pause button top-right
    Box(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
      Box(modifier = Modifier.align(Alignment.TopEnd)) {
        PauseButton(paused = state.paused, onClick = { vm.togglePause() })
      }
    }
  }
}
