package com.ckc.diceapp

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    var again = 0
    var totalScore = 0
    lateinit var sharedPreferences: SharedPreferences
    lateinit var topScore :TextView
    lateinit var button :Button
    lateinit var restart :ImageView
    lateinit var image :ImageView
    lateinit var score :TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button = findViewById<Button>(R.id.button)
        topScore = findViewById<TextView>(R.id.textViewTopScore)
        restart = findViewById<ImageView>(R.id.restart)
        image = findViewById<ImageView>(R.id.imageView)
        score = findViewById<TextView>(R.id.score)
        button.setOnClickListener { click() }

        sharedPreferences = getSharedPreferences("com.ckc.diceapp", MODE_PRIVATE)
        var data = sharedPreferences.getInt("data",0)
        topScore.text = "Top Score : $data"

        restart.setOnClickListener {
            val alert = android.app.AlertDialog.Builder(this)
            alert.setTitle("!!!!")
            alert.setMessage("Top Score restart !!")
            alert.setNegativeButton("No"){_,a->
                 Toast.makeText(this@MainActivity,"No restart",Toast.LENGTH_LONG).show()

            }
            alert.setPositiveButton("Yes"){_,a->
                sharedPreferences.edit().putInt("data",0).apply()
                topScore.text = "Top Score : 0"
            }.show()
        }
    }

    fun click(){
        val random = Random(6)
        var randomNumber = random.randomGet()

        var getData = sharedPreferences.getInt("data",0)

        if (getData!=0){ topScore.text = "Top Score : $getData" }
        again++

        if(again<6){
            totalScore += randomNumber
            val drawableSource =  when(randomNumber){
                1->R.drawable.dice_1
                2->R.drawable.dice_2
                3->R.drawable.dice_3
                4->R.drawable.dice_4
                5->R.drawable.dice_5
                else->R.drawable.dice_6
            }
            image.setImageResource(drawableSource)
            score.text = "Score : $randomNumber \n Top Score : $totalScore"

        }else {
            if (getData!=0 && totalScore>getData){
                 sharedPreferences.edit().putInt("data",totalScore).apply()
                val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Congratulations")
                alert.setMessage("New Top Score : $totalScore")
                alert.setIcon(R.drawable.dice_1)
                alert.setNegativeButton("Exit"){ _, a-> finish() }
                alert.setPositiveButton("Restart"){ _,a->
                    again=0
                    totalScore=0
                    score.text = "Score :  \n Top Score : " }.show()

            }else{
                if (getData==0){ sharedPreferences.edit().putInt("data",totalScore).apply() }
                val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Hello")
                alert.setMessage(" Top Score : $totalScore")
                alert.setIcon(R.drawable.dice_1)
                alert.setNegativeButton("Exit"){ _, a-> finish() }
                alert.setPositiveButton("Restart"){ _,a->
                    again=0
                    totalScore=0
                    score.text = "Score :  \n Top Score : " }.show()
            }
        }
    }


}