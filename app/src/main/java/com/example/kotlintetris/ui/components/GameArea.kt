package com.example.kotlintetris.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kotlintetris.model.GameState

@Composable
fun GameArea(state: GameState, modifier: Modifier = Modifier) {
  val hudHeight = 96.dp
  Box(
    modifier = modifier
      .border(4.dp, Color.White)
      .background(Color.Black)
      .padding(6.dp)
  ) {
    Column(Modifier.fillMaxWidth()) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
      ) {
        // Score fills left side
        Box(Modifier.height(hudHeight).weight(1f)) {
          ScoreOverlay(state, modifier = Modifier.fillMaxSize())
        }
        // Vertical divider (shared border)
        Box(Modifier.width(4.dp).height(hudHeight).background(Color.White))
        // Next fixed width on right
        Box(Modifier.height(hudHeight).widthIn(min = 116.dp), contentAlignment = Alignment.TopStart) {
          NextPreview(next = state.nextQueue.firstOrNull(), modifier = Modifier.fillMaxSize())
        }
      }
      // Horizontal divider between HUD and board
      Box(Modifier.fillMaxWidth().height(4.dp).background(Color.White))
      // Board below the HUD; keep 10:20 aspect
      RetroBoard(state = state, modifier = Modifier.fillMaxWidth().aspectRatio(10f / 20f))
    }
  }
}
