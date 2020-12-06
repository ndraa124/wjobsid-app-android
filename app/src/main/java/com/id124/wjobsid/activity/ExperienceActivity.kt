package com.id124.wjobsid.activity

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.id124.wjobsid.R
import kotlinx.android.synthetic.main.activity_experience.*
import java.text.SimpleDateFormat
import java.util.*


class ExperienceActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var myCalendar: Calendar
    private lateinit var date: OnDateSetListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experience)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = "Experience"

        myCalendar = Calendar.getInstance()
        date = OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val day = findViewById<TextView>(R.id.et_start)
            val myFormat = "dd-mm-yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            day.text = sdf.format(myCalendar.time)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.et_start -> {
                DatePickerDialog(
                    this@ExperienceActivity, date, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            R.id.btn_add_experience -> {

            }
            R.id.ln_back -> {
                this@ExperienceActivity.finish()
            }
        }
    }
}