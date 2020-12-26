package com.id124.wjobsid.activity.experience

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseActivity
import com.id124.wjobsid.databinding.ActivityExperienceBinding
import kotlinx.android.synthetic.main.activity_experience.*
import java.text.SimpleDateFormat
import java.util.*

class ExperienceActivity : BaseActivity<ActivityExperienceBinding>(), View.OnClickListener {
    private lateinit var myCalendar: Calendar
    private lateinit var date: OnDateSetListener

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_experience
        super.onCreate(savedInstanceState)

        setToolbarActionBar()

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

    private fun setToolbarActionBar() {
        setSupportActionBar(bind.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Experience"
        bind.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

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