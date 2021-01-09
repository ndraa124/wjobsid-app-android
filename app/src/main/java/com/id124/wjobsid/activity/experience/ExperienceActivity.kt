package com.id124.wjobsid.activity.experience

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseActivityCoroutine
import com.id124.wjobsid.databinding.ActivityExperienceBinding
import com.id124.wjobsid.util.form_validate.ValidateExperience.Companion.valCompany
import com.id124.wjobsid.util.form_validate.ValidateExperience.Companion.valDescription
import com.id124.wjobsid.util.form_validate.ValidateExperience.Companion.valEnd
import com.id124.wjobsid.util.form_validate.ValidateExperience.Companion.valPosition
import com.id124.wjobsid.util.form_validate.ValidateExperience.Companion.valStart
import kotlinx.android.synthetic.main.activity_experience.*
import java.text.SimpleDateFormat
import java.util.*

class ExperienceActivity : BaseActivityCoroutine<ActivityExperienceBinding>(), View.OnClickListener {
    private lateinit var viewModel: ExperienceViewModel
    private lateinit var myCalendar: Calendar
    private lateinit var dateStart: OnDateSetListener
    private lateinit var dateEnd: OnDateSetListener
    private var exId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_experience
        super.onCreate(savedInstanceState)
        exId = intent.getIntExtra("ex_id", 0)

        setToolbarActionBar()
        initTextWatcher()
        setDataFromIntent()

        myCalendar = Calendar.getInstance()
        dateStart()
        dateEnd()

        setViewModel()
        subscribeLiveData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.et_start -> {
                DatePickerDialog(
                    this@ExperienceActivity, dateStart, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            R.id.et_end -> {
                DatePickerDialog(
                    this@ExperienceActivity, dateEnd, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            R.id.btn_add_experience -> {
                when {
                    !valPosition(bind.inputLayoutPosition, bind.etPosition) -> {}
                    !valCompany(bind.inputLayoutCompany, bind.etCompany) -> {}
                    !valStart(bind.inputLayoutDateStart, bind.etStart) -> {}
                    !valEnd(bind.inputLayoutDateEnd, bind.etEnd) -> {}
                    !valDescription(bind.inputLayoutDescription, bind.etDescription) -> {}
                    else -> {
                        if (exId != 0) {
                            viewModel.serviceUpdateApi(
                                exId = exId!!,
                                exPosition = bind.etPosition.text.toString(),
                                exCompany = bind.etCompany.text.toString(),
                                exStart = bind.etStart.text.toString(),
                                exEnd = bind.etEnd.text.toString(),
                                exDescription = bind.etDescription.text.toString()
                            )
                        } else {
                            viewModel.serviceCreateApi(
                                enId = sharedPref.getIdEngineer(),
                                exPosition = bind.etPosition.text.toString(),
                                exCompany = bind.etCompany.text.toString(),
                                exStart = bind.etStart.text.toString(),
                                exEnd = bind.etEnd.text.toString(),
                                exDescription = bind.etDescription.text.toString()
                            )
                        }
                    }
                }
            }
            R.id.btn_delete_experience -> {
                deleteConfirmation()
            }
            R.id.ln_back -> {
                this@ExperienceActivity.finish()
            }
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

    private fun initTextWatcher() {
        bind.etPosition.addTextChangedListener(MyTextWatcher(bind.etPosition))
        bind.etCompany.addTextChangedListener(MyTextWatcher(bind.etCompany))
        bind.etStart.addTextChangedListener(MyTextWatcher(bind.etStart))
        bind.etEnd.addTextChangedListener(MyTextWatcher(bind.etEnd))
        bind.etDescription.addTextChangedListener(MyTextWatcher(bind.etDescription))
    }

    private fun setDataFromIntent() {
        if (exId != 0) {
            bind.etPosition.setText(intent.getStringExtra("ex_position"))
            bind.etCompany.setText(intent.getStringExtra("ex_company"))
            bind.etStart.setText(intent.getStringExtra("ex_start"))
            bind.etEnd.setText(intent.getStringExtra("ex_end"))
            bind.etDescription.setText(intent.getStringExtra("ex_description"))

            bind.btnDeleteExperience.visibility = View.VISIBLE
            bind.tvAddExperience.text = getString(R.string.update_experience)
            bind.btnAddExperience.text = getString(R.string.update_experience)
        }
    }

    private fun dateStart() {
        dateStart = OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val day = findViewById<TextView>(R.id.et_start)
            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            day.text = sdf.format(myCalendar.time)
        }
    }

    private fun dateEnd() {
        dateEnd = OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val day = findViewById<TextView>(R.id.et_end)
            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            day.text = sdf.format(myCalendar.time)
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@ExperienceActivity).get(ExperienceViewModel::class.java)
        viewModel.setService(createApi(this@ExperienceActivity))
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@ExperienceActivity, {
            bind.btnAddExperience.visibility = View.GONE
            bind.btnDeleteExperience.visibility = View.GONE
            bind.progressBar.visibility = View.VISIBLE
        })

        viewModel.onSuccessLiveData.observe(this@ExperienceActivity, {
            if (it) {
                setResult(RESULT_OK)
                this@ExperienceActivity.finish()

                bind.progressBar.visibility = View.GONE
                bind.btnAddExperience.visibility = View.VISIBLE
                bind.btnDeleteExperience.visibility = View.VISIBLE
            } else {
                bind.progressBar.visibility = View.GONE
                bind.btnAddExperience.visibility = View.VISIBLE
                bind.btnDeleteExperience.visibility = View.VISIBLE
            }
        })

        viewModel.onMessageLiveData.observe(this@ExperienceActivity, {
            noticeToast(it)
        })

        viewModel.onFailLiveData.observe(this@ExperienceActivity, {
            noticeToast(it)
        })
    }

    private fun deleteConfirmation() {
        val dialog = AlertDialog
            .Builder(this@ExperienceActivity)
            .setTitle("Notice!")
            .setMessage("Are you sure to delete this experience?")
            .setPositiveButton("OK") { _, _ ->
                viewModel.serviceDeleteApi(
                    exId = exId!!
                )
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        dialog?.show()
    }

    inner class MyTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                R.id.et_position -> valPosition(bind.inputLayoutPosition, bind.etPosition)
                R.id.et_company -> valCompany(bind.inputLayoutCompany, bind.etCompany)
                R.id.et_start -> valStart(bind.inputLayoutDateStart, bind.etStart)
                R.id.et_end -> valEnd(bind.inputLayoutDateEnd, bind.etEnd)
                R.id.et_description -> valDescription(bind.inputLayoutDescription, bind.etDescription)
            }
        }
    }
}