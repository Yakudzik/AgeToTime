package com.example.agetotime

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var currentLocal: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentLocal = getCurrentLanguage() //запрашиваем язык

        if (currentLocal=="English") { //устанавливаем картинку языка
            button_localisation.setImageResource(R.drawable.russia)
        }else{
            button_localisation.setImageResource(R.drawable.united_states)
        }

        buttonSelectBirthDay.setOnClickListener { view -> //кнопка выбора даты
            clickDate(view)
        }

        button_localisation.setOnClickListener { //кнопка смены языка
            changeLanguage()
        }
    }


    private fun clickDate(view: View) {

        val clickedDate = Calendar.getInstance()
        val year = clickedDate.get(Calendar.YEAR)
        val month = clickedDate.get(Calendar.MONTH)
        val dayOfMonth = clickedDate.get(Calendar.DAY_OF_MONTH)
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->

                val clickedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"

                text_selectedDate.text = clickedDate

                val theDate = simpleDateFormat.parse(clickedDate)

                val selectedDayInMinute = theDate!!.time / 60000

                val currentDate =
                    simpleDateFormat.parse(simpleDateFormat.format(System.currentTimeMillis()))

                val currentDateInMinutes = currentDate!!.time / 60000

                val differenceInTime = currentDateInMinutes - selectedDayInMinute

                text_ageMinutes.text = "$differenceInTime " + getString(R.string.resultMinutes)
                text_ageYears.text = "${differenceInTime / 525600} " +getString(R.string.resultYears)
                text_ageDays.text = "${differenceInTime / 1400} "+getString(R.string.resultDays)
                text_ageSeconds.text = "${differenceInTime * 60} "+getString(R.string.resultSeconds)

            },
            year,
            month,
            dayOfMonth
        )

        dpd.datePicker.maxDate = Date().time - 86400000
        dpd.show()
    }

    private fun changeLanguage() {
        if (currentLocal == "English") {
            LocalisationManger.setNewLocalisation(this, "ru")
             recreate()

        } else if (currentLocal != "English") {
            LocalisationManger.setNewLocalisation(this, "en")
             recreate()
        }
    }

    private fun getCurrentLanguage(): String {
        return Locale.getDefault().displayLanguage
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocalisationManger.setLocalisation(newBase!!))
    }
}


