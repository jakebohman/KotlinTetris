package com.example.kotlintetris.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kotlintetris.model.GameState

@Composable
fun NextQueueView(state: GameState) {
  Column {
    Text("Next")
    state.nextQueue.forEach { t ->
      Row(Modifier.height(24.dp)) { Text(t.name) }
    }
  }
}

