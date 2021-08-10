package com.fitness.a7minworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ll_start_btn=findViewById<LinearLayout>(R.id.ll_start_btn)
        val ll_BMI_calculate=findViewById<LinearLayout>(R.id.ll_BMI_calculate)
        val llHistoryButton=findViewById<LinearLayout>(R.id.llHistoryButton)

        ll_start_btn.setOnClickListener {
            val intent = Intent(this,ExerciseActivity::class.java)
            startActivity(intent)
        }

        ll_BMI_calculate.setOnClickListener {
            val intent=Intent(this,BMIActivity::class.java)
            startActivity(intent)
        }

        llHistoryButton.setOnClickListener {
            val intent=Intent(this,HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}