package com.example.kotlintetris

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.kotlintetris.ui.screens.GameScreen
import com.example.kotlintetris.ui.theme.TetrisTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      TetrisTheme { GameScreen() }
    }
  }
}

