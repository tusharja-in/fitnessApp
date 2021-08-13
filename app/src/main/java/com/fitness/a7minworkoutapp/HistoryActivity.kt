package com.fitness.a7minworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val toolbar_history=findViewById<Toolbar>(R.id.toolbar_history)

        setSupportActionBar(toolbar_history)
        val actionBar=supportActionBar
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title="EXERCISE HISTORY"
        }
        toolbar_history.setNavigationOnClickListener {
            onBackPressed()
        }
        getAllCompletedDates()
    }

    private fun getAllCompletedDates(){

        val rvDatabaseDate=findViewById<RecyclerView>(R.id.rvDatabaseDate)
        val tvNoDataAvailable=findViewById<TextView>(R.id.tvNoDataAvailable)

        val dbHandler=SqliteOpenHelper(this,null)
        val allCompletedExerciseDatesList=dbHandler.getAllCompletedDatesList()
        if(allCompletedExerciseDatesList.size>0){
            tvNoDataAvailable.visibility= View.GONE
            rvDatabaseDate.visibility=View.VISIBLE
            rvDatabaseDate.layoutManager=LinearLayoutManager(this)
            val historyAdapter=HistoryAdapter(this,allCompletedExerciseDatesList)
            rvDatabaseDate.adapter=historyAdapter
        }
        else{
            tvNoDataAvailable.visibility= View.VISIBLE
            rvDatabaseDate.visibility=View.GONE
        }
    }
}