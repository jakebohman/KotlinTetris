package com.example.kotlintetris.data

import android.content.Context
import android.content.SharedPreferences

/*
 *  Manages high score storage and retrieval using SharedPreferences.
 */
class HighScoreManager(context: Context) {
  private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

  companion object {
    private const val PREFS_NAME = "tetris_prefs"
    private const val HIGH_SCORE_KEY = "high_score"
  }

    // Retrieve the current high score, defaulting to 0 if not set.
  fun getHighScore(): Int {
    return prefs.getInt(HIGH_SCORE_KEY, 0)
  }

    // Update the high score.
    // Returns true if a new high score was set, false otherwise.
  fun updateHighScore(score: Int): Boolean {
    val currentHighScore = getHighScore()
    if (score > currentHighScore) {
      prefs.edit().putInt(HIGH_SCORE_KEY, score).apply()
      return true // New high score achieved
    }
    return false // No new high score
  }

    // Directly set the high score (use with caution).
  fun setHighScore(score: Int) {
    prefs.edit().putInt(HIGH_SCORE_KEY, score).apply()
  }
}
