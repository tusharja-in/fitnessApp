package com.fitness.a7minworkoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition=-1
    private var restTimer:CountDownTimer?=null
    private var restProgress=0
    private var tts:TextToSpeech?=null
    private var player:MediaPlayer?=null
    private var exerciseAdapter:ExerciseStatusAdapter?=null
    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgress=0

    //no of seconds we want to set on timer for exercise
    private var exerciseTimerDuration:Long=30
    private var restTimerDuration:Long=10

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
            customDialogForBackButton()
        }

        tts= TextToSpeech(this,this)
        exerciseList=Constants.defaultExerciseList()
        setUpRestView()
        setupExerciseStatusRecyclerView()

    }

    private fun startRestProgressBar(){
        val progressBar=findViewById<ProgressBar>(R.id.progressBar)
        val tvTimer=findViewById<TextView>(R.id.tvTimer)
        progressBar.progress=restProgress
        restTimer=object:CountDownTimer(restTimerDuration*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress=restTimerDuration.toInt()-restProgress
                tvTimer.text=(restTimerDuration-restProgress).toString()
            }

            override fun onFinish() {
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
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
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setUpRestView()
                }
                else{
                    finish()
                    val intent=Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
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
        try {
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false
            player!!.start()
        }catch (e:Exception){e.printStackTrace()}

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

        if(player!=null){
            player!!.stop()
        }

    }

    override fun onInit(status: Int) {
        if (status==TextToSpeech.SUCCESS){
            val result=tts!!.setLanguage(Locale.US)
            if (result==TextToSpeech.LANG_NOT_SUPPORTED || result==TextToSpeech.LANG_MISSING_DATA)
            {
                Log.e("tts","language missing")
            }
        }
        else{
            Log.e("tts","error")
        }
    }

    private fun speakOut(text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    private fun setupExerciseStatusRecyclerView(){
        val rvExerciseStatus=findViewById<RecyclerView>(R.id.rvExerciseStatus)

        rvExerciseStatus.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter= ExerciseStatusAdapter(exerciseList!!,this)
        rvExerciseStatus.adapter=exerciseAdapter
    }

    private fun customDialogForBackButton(){

        val customDialog=Dialog(this)
        customDialog.setContentView(R.layout.exercise_back_confirmation)
        customDialog.findViewById<Button>(R.id.btnYES).setOnClickListener {
            finish()
            customDialog.dismiss()
        }

        customDialog.findViewById<Button>(R.id.btnNO).setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

}