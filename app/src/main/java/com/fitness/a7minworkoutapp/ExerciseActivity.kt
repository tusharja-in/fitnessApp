package com.fitness.a7minworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition=-1
    private var restTimer:CountDownTimer?=null
    private var restProgress=0
    private var tts:TextToSpeech?=null

    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgress=0

    //no of seconds we want to set on timer for exercise
    private var exerciseTimerDuration:Long=2

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

        tts= TextToSpeech(this,this)
        exerciseList=Constants.defaultExerciseList()
        setUpRestView()

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
                if (currentExercisePosition < exerciseList!!.size-1){
                    setUpRestView()
                }
                else{
                    Toast.makeText(this@ExerciseActivity,"kuch bhi kro ab",Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    private fun setUpExerciseView(){
        val llRestView=findViewById<LinearLayout>(R.id.llRestView)
        val ivImage=findViewById<ImageView>(R.id.ivImage)
        val tvExerciseName=findViewById<TextView>(R.id.tvExerciseName)
        llRestView.visibility=View.GONE
        val llExerciseView=findViewById<LinearLayout>(R.id.llExerciseView)
        llExerciseView.visibility=View.VISIBLE

        if (exerciseTimer!=null){
            exerciseProgress=0
            exerciseTimer!!.cancel()
        }

        speakOut("Perform ${exerciseList!![currentExercisePosition].getName()} for 30 seconds.")
        startExerciseProgressBar()
        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text=(exerciseList!![currentExercisePosition].getName())
    }

    private fun setUpRestView(){

        val tvUpcomingExercise=findViewById<TextView>(R.id.tvUpcomingExercise)

        val llRestView=findViewById<LinearLayout>(R.id.llRestView)
        llRestView.visibility=View.VISIBLE
        val llExerciseView=findViewById<LinearLayout>(R.id.llExerciseView)
        llExerciseView.visibility=View.GONE

        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }
        currentExercisePosition++
        tvUpcomingExercise.text=exerciseList!![currentExercisePosition].getName()
        startRestProgressBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }

        if(exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgress=0
        }

        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }

    }

    override fun onInit(status: Int) {
        if (status==TextToSpeech.SUCCESS){
            val result=tts!!.setLanguage(Locale.US)
            if (result==TextToSpeech.LANG_NOT_SUPPORTED || result==TextToSpeech.LANG_MISSING_DATA)
            {
                Log.e("tts","lang missing")
            }
        }
        else{
            Log.e("tts","error")
        }
    }

    private fun speakOut(text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }
}