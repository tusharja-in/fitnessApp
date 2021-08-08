package com.fitness.a7minworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)
        val tb_bmi=findViewById<Toolbar>(R.id.tb_bmi)
        val btnCalculateUnits=findViewById<Button>(R.id.btnCalculateUnits)

        setSupportActionBar(tb_bmi)
        val actionBar=supportActionBar
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title="Calculate BMI"
        }
        tb_bmi.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculateUnits.setOnClickListener {
            val etWeightInKG=findViewById<AppCompatEditText>(R.id.etWeightInKG)
            val etHeightInCM=findViewById<AppCompatEditText>(R.id.etHeightInCM)
            if (validateInputData()){
                var wKG=etWeightInKG.text.toString().toFloat()
                var hCM=etHeightInCM.text.toString().toFloat()/100
                var bmi=wKG/(hCM*hCM)
                displayBMIResult(bmi)
            }
            else
                Toast.makeText(this,"Enter valid data",Toast.LENGTH_SHORT).show()
        }

    }

    private fun validateInputData():Boolean{
        var dataValid=true
        val etWeightInKG=findViewById<AppCompatEditText>(R.id.etWeightInKG)
        val etHeightInCM=findViewById<AppCompatEditText>(R.id.etHeightInCM)
        if(etHeightInCM.text.toString().isEmpty()||etWeightInKG.text.toString().isEmpty()){
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

        tvYourBMI.visibility = View.VISIBLE
        tvBMIValue.visibility = View.VISIBLE
        tvBMIType.visibility = View.VISIBLE
        tvBMIDescription.visibility = View.VISIBLE

        // This is used to round the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        tvBMIValue.text = bmiValue
        tvBMIType.text = bmiLabel
        tvBMIDescription.text = bmiDescription
    }

}