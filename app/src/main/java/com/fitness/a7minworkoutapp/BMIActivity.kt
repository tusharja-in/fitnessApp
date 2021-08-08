package com.fitness.a7minworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class BMIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)
        val tb_bmi=findViewById<Toolbar>(R.id.tb_bmi)

        setSupportActionBar(tb_bmi)
        val actionBar=supportActionBar
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title="Calculate BMI"
        }
        tb_bmi.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}