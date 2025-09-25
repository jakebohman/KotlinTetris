package com.example.kotlintetris.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlintetris.model.GameState

@Composable
fun ScoreOverlay(state: GameState, modifier: Modifier = Modifier) {
  val levelStr = state.level.coerceAtLeast(0).toString().padStart(2, '0')
  Column(
    modifier = modifier
      .background(Color.Black)
      .padding(horizontal = 6.dp, vertical = 4.dp)
  ) {
    Text("SCORE: ${state.score.coerceAtLeast(0)}", color = Color.White, fontFamily = FontFamily.Monospace, fontSize = 14.sp)
    Text("LEVEL: $levelStr", color = Color.White, fontFamily = FontFamily.Monospace, fontSize = 14.sp)
    Text("LINES: ${state.lines.coerceAtLeast(0)}", color = Color.White, fontFamily = FontFamily.Monospace, fontSize = 14.sp)
  }
}
