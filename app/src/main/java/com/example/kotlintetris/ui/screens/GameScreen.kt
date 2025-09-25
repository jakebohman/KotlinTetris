package com.example.kotlintetris.ui.screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlintetris.ui.components.*
import com.example.kotlintetris.ui.viewmodel.GameViewModel

@Composable
fun GameScreen(vm: GameViewModel = viewModel()) {
  val state by vm.state.collectAsState()

  Column(Modifier.fillMaxSize().padding(12.dp)) {
    // Top bar with pause button aligned right
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
      PauseButton(paused = state.paused, onClick = { vm.togglePause() })
    }

    // Center area: board centered and large
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f),
      contentAlignment = Alignment.Center
    ) {
      Box(Modifier.fillMaxWidth(0.9f)) {
        RetroBoard(state, modifier = Modifier.fillMaxWidth())
        // Overlays inside board corners
        Box(Modifier.align(Alignment.TopStart).padding(4.dp)) {
          ScoreOverlay(state)
        }
        Box(Modifier.align(Alignment.TopEnd).padding(4.dp)) {
          NextPreview(next = state.nextQueue.firstOrNull())
        }
      }
    }

    // Controller at bottom
    ControllerPanel(
      onLeft = { vm.left() },
      onRight = { vm.right() },
      onUp = { vm.rotateCW() },
      onDownPress = { on -> vm.softDrop(on) },
      onA = { vm.rotateCW() },
      onB = { vm.hardDrop() },
      modifier = Modifier
        .fillMaxWidth()
        .height(168.dp)
        .padding(top = 8.dp)
    )
  }
}
