package com.example.real_age_calculator_b422041_punit

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.lang.Math.log
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val radioGroup1 = findViewById<RadioGroup>(R.id.rbOptions1)
        val radioGroup2 = findViewById<RadioGroup>(R.id.rbOptions2)
        val pickText = findViewById<TextView>(R.id.editText)
        val bDatePicker = findViewById<Button>(R.id.bDatePicker)
        val calc = findViewById<Button>(R.id.btCalculate)
        val viewAGE = findViewById<TextView>(R.id.viewAge)
        var selDate:String? = null;

        //Calendar instances
        val c = Calendar.getInstance()
        val year = c[Calendar.YEAR]
        val month = c[Calendar.MONTH]
        val day = c[Calendar.DAY_OF_MONTH]

        //Options
        val optionYears = findViewById<RadioButton>(R.id.rYears)
        val optionMonths = findViewById<RadioButton>(R.id.rMonths)
        val optionDays = findViewById<RadioButton>(R.id.rDays)
        val optionHours = findViewById<RadioButton>(R.id.rHours)
        val optionMinutes = findViewById<RadioButton>(R.id.rMinutes)
        val optionSeconds = findViewById<RadioButton>(R.id.rSeconds)

        //Code to deselect other options when one option is selected
        radioGroup1.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                // Clear selection of radioGroup2
                radioGroup2.clearCheck()
                radioGroup1.check(checkedId)
            }
        }
        radioGroup2.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                // Clear selection of radioGroup1
                radioGroup1.clearCheck()
                radioGroup2.check(checkedId)
            }
        }

        //Views the calendar
        bDatePicker.setOnClickListener { // Get the current date

            // Create the date picker dialog
            val datePickerDialog = DatePickerDialog(
                this@MainActivity,
                { view, yy, mm, dd ->
                    // Do something with the selected date
                    if(mm<9 && dd>9) {
                        pickText.text = "Date picked : $dd/0${mm + 1}/$yy"
                        selDate = "$dd/0${mm + 1}/$yy"
                    }
                    else if(mm<9 && dd<10){
                        pickText.text = "Date picked : 0$dd/0${mm + 1}/$yy"
                        selDate = "0$dd/0${mm + 1}/$yy"
                    }
                    else {
                        pickText.text = "Date picked : $dd/${mm + 1}/$yy"
                        selDate = "$dd/${mm + 1}/$yy"
                    }
                }, year, month, day
            )

            // Show the date picker dialog
            datePickerDialog.show()
        }

            calc.setOnClickListener {

                val dobs: String? = selDate

                if(selDate == null){
                    Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val birthDate = LocalDate.parse(dobs, formatter)
                val now = LocalDate.now()

                val age = Period.between(birthDate, now)
                val years = age.years
                val months = age.months
                val days = ChronoUnit.DAYS.between(birthDate, now)

                val hours = LocalDateTime.now().hour
                val minutes = LocalDateTime.now().minute
                val seconds = LocalDateTime.now().second

                if (optionDays.isChecked) {
                    viewAGE.text = "Your age is\n$days days."
                } else if (optionMonths.isChecked) {
                    viewAGE.text = "Your age is\n${years*12 +months} months."
                } else if (optionYears.isChecked) {
                    viewAGE.text = "Your age is\n$years years."
                } else if (optionHours.isChecked) {
                    viewAGE.text = "Your age is\n${days * 24 + hours} hours."
                } else if (optionMinutes.isChecked) {
                    viewAGE.text = "Your age is\n${(days * 24 + hours) * 60 + minutes} minutes."
                } else if (optionSeconds.isChecked) {
                    viewAGE.text =
                        "Your age is\n${((days * 24 + hours) * 60 + minutes) * 60 + seconds} seconds."
                }
            }
        }
    }