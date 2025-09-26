package com.example.kotlintetris

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.kotlintetris.ui.screens.GameScreen
import com.example.kotlintetris.ui.screens.LoadingScreen
import com.example.kotlintetris.ui.screens.MainMenuScreen
import com.example.kotlintetris.ui.theme.TetrisTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      TetrisTheme {
        TetrisApp()
      }
    }
  }
}

@Composable
fun TetrisApp() {
  var isLoading by remember { mutableStateOf(true) }
  var showMainMenu by remember { mutableStateOf(false) }
  var showGame by remember { mutableStateOf(false) }

  when {
    isLoading -> {
      LoadingScreen(
        onLoadingComplete = {
          isLoading = false
          showMainMenu = true
        }
      )
    }
    showMainMenu -> {
      MainMenuScreen(
        onPlayGame = {
          showMainMenu = false
          showGame = true
        }
      )
    }
    showGame -> {
      GameScreen(
        onReturnToMenu = {
          showGame = false
          showMainMenu = true
        }
      )
    }
  }
}
