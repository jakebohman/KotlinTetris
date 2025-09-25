package com.example.kotlintetris.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
  val scoreStr = state.score.coerceAtLeast(0).toString().padStart(6, '0')
  val levelStr = state.level.coerceAtLeast(0).toString().padStart(2, '0')
  val linesStr = state.lines.coerceAtLeast(0).toString().padStart(3, '0')
  Column(
    modifier = modifier
      .background(Color.Black)
      .border(3.dp, Color.White)
      .padding(horizontal = 6.dp, vertical = 4.dp)
  ) {
    Text("SCORE  $scoreStr", color = Color.White, fontFamily = FontFamily.Monospace, fontSize = 14.sp)
    Text("LEVEL  $levelStr", color = Color.White, fontFamily = FontFamily.Monospace, fontSize = 14.sp)
    Text("LINES  $linesStr", color = Color.White, fontFamily = FontFamily.Monospace, fontSize = 14.sp)
  }
}
