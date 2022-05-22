package com.example.agecalculatorapplication

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.util.*

class MainActivity : AppCompatActivity() {

    private var selectedDateText : TextView? = null
    private var ageInMinutes : TextView? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button : Button = findViewById(R.id.button)
        selectedDateText = findViewById(R.id.selectedDateText)
        ageInMinutes = findViewById(R.id.ageInMinutes)
        button.setOnClickListener {
            buttonPressed()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun buttonPressed(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this, { _, selectedYear, selectedMonth, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${selectedMonth+1}/$selectedYear"
            selectedDateText?.text = selectedDate
            val sdf = SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH)
            val theDate = sdf.parse(selectedDate)
            theDate?.let{
                val selectedDateInMinutes = theDate.time / 60000
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                currentDate?.let {
                    val currentDateInMinutes = currentDate.time / 60000
                    val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                    ageInMinutes?.text = differenceInMinutes.toString()
                }
            }
        }, year, month, day)
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }

}