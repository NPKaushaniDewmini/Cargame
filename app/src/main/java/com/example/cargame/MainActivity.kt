package com.example.cargame

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.WindowCompat

class MainActivity : AppCompatActivity(), GameTask {
    lateinit var rootLayout: ConstraintLayout // Change the type to ConstraintLayout
    lateinit var startBtn: Button
    lateinit var mGameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)

        mGameView = GameView(this, this)
        startBtn.setOnClickListener {
            mGameView.setBackgroundResource(R.drawable.road)
            rootLayout.addView(mGameView, ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)
            startBtn.visibility = View.GONE
        }
    }

    override fun closeGame(mScore: Int) {
        rootLayout.removeView(mGameView)
        startBtn.visibility = View.VISIBLE
    }
}
