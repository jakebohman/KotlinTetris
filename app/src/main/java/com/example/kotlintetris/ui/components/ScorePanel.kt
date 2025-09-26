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

/*
 * A panel that displays the current score, lines cleared, level, and high score.
 */
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
        fontSize = 16.sp, // Increased from 12.sp to match numbers
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "${state.score}",
        color = Color.White,
        fontSize = 16.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold
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
        fontSize = 16.sp, // Increased from 12.sp to match numbers
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "${state.lines}",
        color = Color.White,
        fontSize = 16.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold
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
        fontSize = 16.sp, // Increased from 12.sp to match numbers
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "${state.level}",
        color = Color.White, // Changed from Yellow to White
        fontSize = 16.sp, // Increased from 12.sp to 16.sp
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold // Added bold weight
      )
    }

    // High score label and value on same line
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(
        text = "HIGH",
        color = Color.White,
        fontSize = 16.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "${state.highScore}",
        color = Color.White,
        fontSize = 16.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold
      )
    }
  }
}
