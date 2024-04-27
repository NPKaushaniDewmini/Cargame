package com.example.cargame

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View


class GameView(var c: Context, var gameTask: GameTask) : View(c) {
    private var myPaint: Paint? = null
    private var speed = 1
    private var time = 0
    private var score = 0
    private var myCarposition = 1
    private val otherCars = ArrayList<HashMap<String, Any>>()

    var viewWidth = 0
    var viewHeight = 0

    init {
        myPaint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        if (time % 700 < 10 + speed) {
            val map = HashMap<String, Any>()
            map["lane"] = (0..2).random()
            map["startTime"] = time
            otherCars.add(map)
        }
            time += 10 + speed
            val carWidth = viewWidth / 5
            val carHeight = carWidth + 10
            myPaint!!.style = Paint.Style.FILL
            val d = resources.getDrawable(R.drawable.car_red, null)
            d.setBounds(myCarposition * viewWidth / 3 + viewWidth / 15 + 25, viewHeight - 2 - carHeight, myCarposition * viewWidth / 3 + viewWidth / 15 + carWidth - 25, viewHeight - 2)
             d.draw(canvas)
            myPaint!!.color= Color.GREEN
            var highScore = 0// Draw the drawable on canvas

            for(i in otherCars.indices){
                 try {
                     val carX = otherCars[i]["lane"] as Int * viewWidth / 3 + viewWidth / 15
                     val carY = time - otherCars[i]["startTime"] as Int
                     val d2 = resources.getDrawable(R.drawable.car_yellow, null)

                     d2.setBounds(carX + 25, carY - carHeight, carX + carWidth - 25, carY)

                     d2.draw(canvas)
                     if(otherCars[i]["lane"] as Int == myCarposition){
                         if(carY> viewHeight - 2- carHeight && carY < viewHeight-2)
                             closeGame(score);
                     }
                     if(carY > viewHeight + carHeight) {
                         otherCars.removeAt(i)
                         score++
                         speed = 1 + Math.abs(score / 10)
                         if (score > highScore) {
                             highScore = score
                         }
                     }
                 }
                 catch(e:Exception){
                     e.printStackTrace()

                 }                 }
            myPaint!!.color=Color.WHITE
            myPaint!!.textSize = 40f
         canvas.drawText("Score: $score",80f,80f,myPaint!!)
        canvas.drawText("Speed: $speed",380f,80f,myPaint!!)
        invalidate()
        }

    fun closeGame(score: Int) {
        val gameOverIntent = Intent(c, GameEnd::class.java)
        gameOverIntent.putExtra("finalScore", score)
        c.startActivity(gameOverIntent)
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_DOWN -> {
                val x1 = event.x
                if (x1 < viewWidth / 2) {
                    if (myCarposition > 0) {
                        myCarposition--
                    }
                }
                if (x1 > viewWidth / 2) {
                    if (myCarposition < 2) {
                        myCarposition++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP->{}

        }
        return true
    }
    }

