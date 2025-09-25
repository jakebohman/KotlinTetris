package com.example.kotlintetris.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.example.kotlintetris.model.GameState
import com.example.kotlintetris.model.Point
import com.example.kotlintetris.model.TetrominoType

@Composable
fun BoardView(state: GameState, modifier: Modifier = Modifier) {
  val w = state.width
  val h = state.height
  Canvas(modifier = modifier.aspectRatio(w / h.toFloat())) {
    val cw = size.width / w
    val ch = size.height / h
    fun drawCell(x: Int, y: Int, color: Long, alpha: Float = 1f) {
      drawRect(
        color = Color(color).copy(alpha = alpha),
        topLeft = Offset(x * cw, y * ch),
        size = androidx.compose.ui.geometry.Size(cw - 1, ch - 1)
      )
    }
    state.board.forEachIndexed { index, t ->
      t?.let {
        val x = index % w
        val y = index / w
        drawCell(x,y,it.color)
      }
    }
    state.ghost.forEach { p: Point ->
      drawCell(p.x, p.y, 0xFFFFFFFF, 0.25f)
    }
    state.active?.cells()?.forEach { p ->
      drawCell(p.x, p.y, state.active.type.color)
    }
  }
}
