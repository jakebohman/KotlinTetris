package com.example.kotlintetris.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlintetris.model.GameState

@Composable
fun ScorePanel(state: GameState, modifier: Modifier = Modifier) {
  Column(
    modifier = modifier
      .background(Color.Black)
      .padding(8.dp),
    verticalArrangement = Arrangement.spacedBy(4.dp)
  ) {
    // Score label and value on same line
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(
        text = "SCORE",
        color = Color.White,
        fontSize = 12.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "${state.score}",
        color = Color.Yellow,
        fontSize = 12.sp,
        fontFamily = FontFamily.Monospace
      )
    }

    // Lines label and value on same line
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(
        text = "LINES",
        color = Color.White,
        fontSize = 12.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "${state.lines}",
        color = Color.Yellow,
        fontSize = 12.sp,
        fontFamily = FontFamily.Monospace
      )
    }

    // Level label and value on same line
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(
        text = "LEVEL",
        color = Color.White,
        fontSize = 12.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "${state.level}",
        color = Color.Yellow,
        fontSize = 12.sp,
        fontFamily = FontFamily.Monospace
      )
    }
  }
}
