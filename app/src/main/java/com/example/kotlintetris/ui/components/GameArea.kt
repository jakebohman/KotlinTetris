package com.example.kotlintetris.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.kotlintetris.model.GameState
import kotlin.math.abs

@Composable
fun GameArea(
  state: GameState,
  modifier: Modifier = Modifier,
  onTap: () -> Unit = {},
  onSwipeLeft: () -> Unit = {},
  onSwipeRight: () -> Unit = {},
  onSwipeDown: () -> Unit = {},
  onSwipeUp: () -> Unit = {},
  onLongPress: (Boolean) -> Unit = {}
) {
  val hudHeight = 96.dp
  var isLongPressing by remember { mutableStateOf(false) }

  Box(
    modifier = modifier
      .border(1.dp, Color(0xFFF5F5DC))
      .background(Color.Black)
      .pointerInput(Unit) {
        detectTapGestures(
          onTap = { onTap() },
          onLongPress = {
            isLongPressing = true
            onLongPress(true)
          }
        )
      }
      .pointerInput(Unit) {
        detectDragGestures(
          onDragStart = { },
          onDragEnd = {
            if (isLongPressing) {
              isLongPressing = false
              onLongPress(false)
            }
          }
        ) { change, dragAmount ->
          if (!isLongPressing) {
            val deltaX = dragAmount.x
            val deltaY = dragAmount.y

            // Determine swipe direction based on dominant axis
            if (abs(deltaX) > abs(deltaY)) {
              // Horizontal swipe
              if (abs(deltaX) > 10f) { // Minimum swipe distance
                if (deltaX > 0) onSwipeRight() else onSwipeLeft()
              }
            } else {
              // Vertical swipe
              if (abs(deltaY) > 10f) { // Minimum swipe distance
                if (deltaY > 0) onSwipeDown() else onSwipeUp()
              }
            }
          }
        }
      }
  ) {
    Column(Modifier.fillMaxWidth()) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Top
      ) {
        // Next box aligned to the right edge
        Box(Modifier.height(hudHeight).width(116.dp), contentAlignment = Alignment.TopStart) {
          NextPreview(next = state.nextQueue.firstOrNull(), modifier = Modifier.fillMaxSize())
        }
      }
      // Horizontal divider between HUD and board
      Box(Modifier.fillMaxWidth().height(4.dp).background(Color(0xFFF5F5DC)))
      // Board below the HUD; keep 10:20 aspect
      RetroBoard(state = state, modifier = Modifier.fillMaxWidth().aspectRatio(10f / 20f))
    }
  }
}
