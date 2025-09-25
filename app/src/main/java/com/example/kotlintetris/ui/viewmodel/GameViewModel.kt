package com.example.kotlintetris.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlintetris.logic.GameEngine
import kotlinx.coroutines.flow.StateFlow

class GameViewModel : ViewModel() {
  private val engine = GameEngine(viewModelScope)
  val state: StateFlow<com.example.kotlintetris.model.GameState> = engine.state

  init { engine.start() }

  fun left() = engine.move(-1)
  fun right() = engine.move(1)
  fun rotateCW() = engine.rotate(true)
  fun rotateCCW() = engine.rotate(false)
  fun hardDrop() = engine.hardDrop()
  fun hold() = engine.hold()
  fun softDrop(on: Boolean) = engine.setSoftDrop(on)
  fun togglePause() = engine.togglePause()
  fun restart() = engine.restart()
}
