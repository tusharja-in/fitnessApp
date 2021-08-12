package com.fitness.a7minworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)
        val toolbar_finish_activity=findViewById<Toolbar>(R.id.toolbar_finish_activity)
        val btnFinish=findViewById<Button>(R.id.btnFinish)


        setSupportActionBar(toolbar_finish_activity)
        val actionBar=supportActionBar
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar_finish_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnFinish.setOnClickListener {
            finish()
        }

        addDateToDatabase()

    }

    private fun addDateToDatabase(){
        val calendar=Calendar.getInstance()
        val dateTime=calendar.time
        Log.i("DATE: ",""+dateTime)
        val sdf=SimpleDateFormat("dd MMM yyyy HH:mm:ss",Locale.getDefault())
        val date=sdf.format(dateTime)
        val dbHandler=SqliteOpenHelper(this,null)
        dbHandler.addDate(date)
        Log.i("Date: ","Success")

    }

}