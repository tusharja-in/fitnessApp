package com.fitness.a7minworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputLayout
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    val METRIC_VIEW="METRIC_VIEW"
    val US_VIEW="US_VIEW"
    var currentUnitView=METRIC_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)
        val tb_bmi=findViewById<Toolbar>(R.id.tb_bmi)
        val btnCalculateUnits=findViewById<Button>(R.id.btnCalculateUnits)
        val rgUnits=findViewById<RadioGroup>(R.id.rgUnits)
//        val rbMetricUnit=findViewById<RadioButton>(R.id.rbMetricUnit)
//        val rbUSUnit=findViewById<RadioButton>(R.id.rbUSUnit)
//        val tilWeightInKG=findViewById<TextInputLayout>(R.id.tilWeightInKG)
//        val tilHeightInCM=findViewById<TextInputLayout>(R.id.tilHeightInCM)
//        val tilWeightInLBS=findViewById<TextInputLayout>(R.id.tilWeightInLBS)
//        val llFeetInch=findViewById<LinearLayout>(R.id.llFeetInch)

        setSupportActionBar(tb_bmi)
        val actionBar=supportActionBar
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title="Calculate BMI"
        }

        rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId==R.id.rbMetricUnit){
                makeMetricUnitViewVisible()
            }
            else
            {
                makeUsUnitViewVisible()
            }
        }

        tb_bmi.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculateUnits.setOnClickListener {
            val etWeightInKG=findViewById<AppCompatEditText>(R.id.etWeightInKG)
            val etHeightInCM=findViewById<AppCompatEditText>(R.id.etHeightInCM)
            val etHeightInFeet=findViewById<AppCompatEditText>(R.id.etHeightInFeet)
            val etHeightInInch=findViewById<AppCompatEditText>(R.id.etHeightInInch)
            val etWeightInLBS=findViewById<AppCompatEditText>(R.id.etWeightInLBS)

            if (currentUnitView==METRIC_VIEW){
                if (validateMetricInputData()){
                    val wKG=etWeightInKG.text.toString().toFloat()
                    val hCM=etHeightInCM.text.toString().toFloat()/100
                    val bmi=wKG/(hCM*hCM)
                    displayBMIResult(bmi)
                }
                else
                    Toast.makeText(this,"Enter valid data",Toast.LENGTH_SHORT).show()
            }
            else{
                if (validateUsInputData()){
                    val wLbs=etWeightInLBS.text.toString().toFloat()
                    val hFeet=etHeightInFeet.text.toString().toFloat()
                    val hInch=etHeightInInch.text.toString().toFloat()
                    val hTotal=(hFeet*12)+hInch
                    val bmi=703*wLbs/(hTotal*hTotal)
                    displayBMIResult(bmi)
                }
                else
                    Toast.makeText(this,"Enter valid data",Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun validateMetricInputData():Boolean{
        var dataValid=true
        val etWeightInKG=findViewById<AppCompatEditText>(R.id.etWeightInKG)
        val etHeightInCM=findViewById<AppCompatEditText>(R.id.etHeightInCM)
        if(etHeightInCM.text.toString().isEmpty()||etWeightInKG.text.toString().isEmpty()){
            dataValid=false
        }
        return dataValid
    }

    private fun validateUsInputData():Boolean{
        var dataValid=true
        val etHeightInFeet=findViewById<AppCompatEditText>(R.id.etHeightInFeet)
        val etHeightInInch=findViewById<AppCompatEditText>(R.id.etHeightInInch)
        val etWeightInLBS=findViewById<AppCompatEditText>(R.id.etWeightInLBS)

        if(etHeightInFeet.text.toString().isEmpty()||etHeightInInch.text.toString().isEmpty()||etWeightInLBS.text.toString().isEmpty()){
            dataValid=false
        }
        return dataValid
    }

    private fun displayBMIResult(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String
        val tvBMIDescription=findViewById<TextView>(R.id.tvBMIDescription)
        val tvYourBMI=findViewById<TextView>(R.id.tvYourBMI)
        val tvBMIValue=findViewById<TextView>(R.id.tvBMIValue)
        val tvBMIType=findViewById<TextView>(R.id.tvBMIType)
        val llDisplayBMIResult=findViewById<LinearLayout>(R.id.llDisplayBMIResult)

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        llDisplayBMIResult.visibility=View.VISIBLE

        // This is used to round the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        tvBMIValue.text = bmiValue
        tvBMIType.text = bmiLabel
        tvBMIDescription.text = bmiDescription
    }

    private fun makeMetricUnitViewVisible(){
        val tilWeightInKG=findViewById<TextInputLayout>(R.id.tilWeightInKG)
        val tilHeightInCM=findViewById<TextInputLayout>(R.id.tilHeightInCM)
        val tilWeightInLBS=findViewById<TextInputLayout>(R.id.tilWeightInLBS)
        val llFeetInch=findViewById<LinearLayout>(R.id.llFeetInch)
        val llDisplayBMIResult=findViewById<LinearLayout>(R.id.llDisplayBMIResult)
        val llUsUnit=findViewById<LinearLayout>(R.id.llUsUnit)
        val etWeightInKG=findViewById<AppCompatEditText>(R.id.etWeightInKG)
        val etHeightInCM=findViewById<AppCompatEditText>(R.id.etHeightInCM)

        currentUnitView=METRIC_VIEW
        etHeightInCM.text!!.clear()
        etWeightInKG.text!!.clear()
        tilHeightInCM.visibility=View.VISIBLE
        tilWeightInKG.visibility=View.VISIBLE
        tilWeightInLBS.visibility=View.GONE
        llFeetInch.visibility=View.GONE
        llDisplayBMIResult.visibility=View.GONE
        llUsUnit.visibility=View.GONE
    }

    private fun makeUsUnitViewVisible(){
        val tilWeightInKG=findViewById<TextInputLayout>(R.id.tilWeightInKG)
        val tilHeightInCM=findViewById<TextInputLayout>(R.id.tilHeightInCM)
        val tilWeightInLBS=findViewById<TextInputLayout>(R.id.tilWeightInLBS)
        val llFeetInch=findViewById<LinearLayout>(R.id.llFeetInch)
        val llDisplayBMIResult=findViewById<LinearLayout>(R.id.llDisplayBMIResult)
        val llUsUnit=findViewById<LinearLayout>(R.id.llUsUnit)
        val etHeightInFeet=findViewById<AppCompatEditText>(R.id.etHeightInFeet)
        val etHeightInInch=findViewById<AppCompatEditText>(R.id.etHeightInInch)
        val etWeightInLBS=findViewById<AppCompatEditText>(R.id.etWeightInLBS)

        currentUnitView=US_VIEW
        tilHeightInCM.visibility=View.GONE
        tilWeightInKG.visibility=View.GONE
        etHeightInFeet.text!!.clear()
        etHeightInInch.text!!.clear()
        etWeightInLBS.text!!.clear()
        tilWeightInLBS.visibility=View.VISIBLE
        llUsUnit.visibility=View.VISIBLE
        llFeetInch.visibility=View.VISIBLE
        llDisplayBMIResult.visibility=View.GONE
    }

}