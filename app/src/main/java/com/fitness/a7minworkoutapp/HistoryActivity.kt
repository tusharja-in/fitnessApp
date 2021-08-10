package com.fitness.a7minworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val toolbar_history=findViewById<Toolbar>(R.id.toolbar_history)

        setSupportActionBar(toolbar_history)
        val actionBar=supportActionBar
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title="HISTORY"
        }
        toolbar_history.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}