package com.example.kotlintetris.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.kotlintetris.model.GameState

/*
 * View to display the held Tetrimino
 */
@Composable
fun HoldView(state: GameState) {
  Column {
    Text("Hold")
    Text(state.hold?.name ?: "-")
  }
}

