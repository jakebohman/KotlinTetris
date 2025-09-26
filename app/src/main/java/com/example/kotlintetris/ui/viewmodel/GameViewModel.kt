package com.example.kotlintetris.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlintetris.logic.GameEngine
import com.example.kotlintetris.data.HighScoreManager
import kotlinx.coroutines.flow.StateFlow

class GameViewModel(context: Context) : ViewModel() {
  private val highScoreManager = HighScoreManager(context)
  private val engine = GameEngine(viewModelScope, highScoreManager)
  val state: StateFlow<com.example.kotlintetris.model.GameState> = engine.state

  init { engine.start() }

  fun left() { if (!state.value.paused) engine.move(-1) }
  fun right() { if (!state.value.paused) engine.move(1) }
  fun rotateCW() { if (!state.value.paused) engine.rotate(true) }
  fun rotateCCW() { if (!state.value.paused) engine.rotate(false) }
  fun hardDrop() { if (!state.value.paused) engine.hardDrop() }
  fun singleSoftDrop() { if (!state.value.paused) engine.singleSoftDrop() }
  fun hold() { if (!state.value.paused) engine.hold() }
  fun softDrop(on: Boolean) { if (!state.value.paused) engine.setSoftDrop(on) }
  fun togglePause() = engine.togglePause()
  fun restart() = engine.restart()
}
