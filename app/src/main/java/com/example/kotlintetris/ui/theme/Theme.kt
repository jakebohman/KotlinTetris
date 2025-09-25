package com.example.kotlintetris.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

@Composable
fun TetrisTheme(content: @Composable () -> Unit) {
  MaterialTheme(
    colorScheme = darkColorScheme(),
    content = content
  )
}

