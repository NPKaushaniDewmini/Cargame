package com.example.cargame

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GameEnd : AppCompatActivity() {
    private lateinit var finalScoreTextView: TextView
    private lateinit var restartButton: Button
    private lateinit var highScoreTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_end)

        finalScoreTextView = findViewById(R.id.textView)
        restartButton = findViewById(R.id.restart)
        highScoreTextView = findViewById(R.id.highscore)

        sharedPreferences = getSharedPreferences("game_data", Context.MODE_PRIVATE)

        val finalScore = intent.getIntExtra("finalScore", 0)

        // Set final score
        finalScoreTextView.text = "Final Score: $finalScore"

        // Save high score
        saveHighScore(finalScore)

        // Display high score
        displayHighScore()

        restartButton.setOnClickListener {
            // Restart the game by starting the MainActivity
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(mainActivityIntent)
            finish() // Close the GameOverActivity
        }
    }

    private fun saveHighScore(score: Int) {
        val editor = sharedPreferences.edit()
        val currentHighScore = sharedPreferences.getInt("high_score", 0)
        if (score > currentHighScore) {
            editor.putInt("high_score", score)
            editor.apply()
        }
    }

    private fun displayHighScore() {
        val highScore = sharedPreferences.getInt("high_score", 0)
        highScoreTextView.text = "High Score: $highScore"
    }
}