package com.example.kotlintetris.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.kotlintetris.model.GameState

@Composable
fun ScorePanel(state: GameState) {
  Column {
    Text("Score: ${state.score}")
    Text("Lines: ${state.lines}")
    Text("Level: ${state.level}")
    if (state.gameOver) Text("GAME OVER")
  }
}

