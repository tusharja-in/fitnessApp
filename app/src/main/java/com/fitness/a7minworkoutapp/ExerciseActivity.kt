package com.fitness.a7minworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class ExerciseActivity : AppCompatActivity() {

    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition=-1
    private var restTimer:CountDownTimer?=null
    private var restProgress=0

    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgress=0

    private var exerciseTimerDuration:Long=30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        val toolbar_exercise_activity=findViewById<Toolbar>(R.id.toolbar_exercise_activity)

        setSupportActionBar(toolbar_exercise_activity)
        val actionbar= supportActionBar
        if(actionbar!=null){
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar_exercise_activity.setNavigationOnClickListener {
            onBackPressed()
        }
        setUpRestView()
        exerciseList=Constants.defaultExerciseList()

    }

    private fun startRestProgressBar(){
        val progressBar=findViewById<ProgressBar>(R.id.progressBar)
        val tvTimer=findViewById<TextView>(R.id.tvTimer)
        progressBar.progress=restProgress
        restTimer=object:CountDownTimer(10000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress=10-restProgress
                tvTimer.text=(10-restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                setUpExerciseView()
            }
        }.start()
    }

    private fun startExerciseProgressBar(){
        val progressExerciseBar=findViewById<ProgressBar>(R.id.progressExerciseBar)
        val tvExerciseTimer=findViewById<TextView>(R.id.tvExerciseTimer)
        progressExerciseBar.progress=exerciseProgress

        exerciseTimer=object :CountDownTimer(exerciseTimerDuration*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressExerciseBar.progress=exerciseTimerDuration.toInt()-exerciseProgress
                tvExerciseTimer.text=(exerciseTimerDuration.toInt()-exerciseProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,"kuch bhi kro ab",Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun setUpExerciseView(){
        val llRestView=findViewById<LinearLayout>(R.id.llRestView)
        llRestView.visibility=View.GONE
        val llExerciseView=findViewById<LinearLayout>(R.id.llExerciseView)
        llExerciseView.visibility=View.VISIBLE

        if (exerciseTimer!=null){
            exerciseProgress=0
            exerciseTimer!!.cancel()
        }
        startExerciseProgressBar()
    }

    private fun setUpRestView(){

        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }
        startRestProgressBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }
    }

}