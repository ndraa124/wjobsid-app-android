package com.id124.wjobsid.activity.project

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseActivityCoroutine
import com.id124.wjobsid.databinding.ActivityProjectBinding
import com.id124.wjobsid.model.project.ProjectResponse
import com.id124.wjobsid.remote.ApiClient.Companion.BASE_URL_IMAGE
import com.id124.wjobsid.service.ProjectApiService
import com.id124.wjobsid.util.form_validate.ValidateProject.Companion.valDeadline
import com.id124.wjobsid.util.form_validate.ValidateProject.Companion.valDescription
import com.id124.wjobsid.util.form_validate.ValidateProject.Companion.valProjectName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class ProjectActivity : BaseActivityCoroutine<ActivityProjectBinding>(), View.OnClickListener {
    private lateinit var myCalendar: Calendar
    private lateinit var deadline: OnDateSetListener
    private var pjId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_project
        super.onCreate(savedInstanceState)
        pjId = intent.getIntExtra("pj_id", 0)

        setToolbarActionBar()
        initTextWatcher()
        setDataFromIntent()

        myCalendar = Calendar.getInstance()
        deadlineProject()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_choose_image -> {
                selectImage()
            }
            R.id.iv_image_view -> {
                selectImage()
            }
            R.id.et_deadline -> {
                DatePickerDialog(
                    this@ProjectActivity, deadline, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            R.id.btn_add_project -> {
                when {
                    !valProjectName(bind.inputLayoutProjectName, bind.etProjectName) -> {
                    }
                    !valDeadline(bind.inputLayoutDeadline, bind.etDeadline) -> {
                    }
                    !valDescription(bind.inputLayoutDescription, bind.etDescription) -> {
                    }
                    else -> {
                        if (pjId != 0) {
                            updateProject()
                        } else {
                            createProject()
                        }
                    }
                }
            }
            R.id.btn_delete_project -> {
                deleteConfirmation()
            }
            R.id.ln_back -> {
                this@ProjectActivity.finish()
            }
        }
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(bind.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Project"
        bind.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initTextWatcher() {
        bind.etProjectName.addTextChangedListener(MyTextWatcher(bind.etProjectName))
        bind.etDeadline.addTextChangedListener(MyTextWatcher(bind.etDeadline))
        bind.etDescription.addTextChangedListener(MyTextWatcher(bind.etDescription))
    }

    private fun setDataFromIntent() {
        if (pjId != 0) {
            bind.ibChooseImage.visibility = View.GONE
            bind.ivImageView.visibility = View.VISIBLE
            bind.btnDeleteProject.visibility = View.VISIBLE
            bind.tvAddProject.text = getString(R.string.update_project)
            bind.btnAddProject.text = getString(R.string.update_project)

            bind.etProjectName.setText(intent.getStringExtra("pj_project_name"))
            bind.etDeadline.setText(intent.getStringExtra("pj_deadline"))
            bind.etDescription.setText(intent.getStringExtra("pj_description"))

            bind.imageUrl = BASE_URL_IMAGE + intent.getStringExtra("pj_image")
        }
    }

    private fun deadlineProject() {
        deadline = OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val day = findViewById<TextView>(R.id.et_deadline)
            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            day.text = sdf.format(myCalendar.time)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                uri = data?.getParcelableExtra("path")!!
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)

                    bind.ivImageView.visibility = View.VISIBLE
                    bind.ibChooseImage.visibility = View.GONE
                    bind.ivImageView.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun createProject() {
        val service = createApi<ProjectApiService>(this@ProjectActivity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.createProject(
                        cnId = createPartFromString(sharedPref.getIdCompany().toString()),
                        pjProjectName = createPartFromString(bind.etProjectName.text.toString()),
                        pjDeadline = createPartFromString(bind.etDeadline.text.toString()),
                        pjDescription = createPartFromString(bind.etDescription.text.toString()),
                        image = createPartFromFile()
                    )
                } catch (t: Throwable) {
                    Log.e("msg", "${t.message}")
                }
            }

            if (response is ProjectResponse) {
                if (response.success) {
                    noticeToast(response.message)
                    this@ProjectActivity.finish()
                } else {
                    noticeToast(response.message)
                }
            }
        }
    }

    private fun updateProject() {
        val service = createApi<ProjectApiService>(this@ProjectActivity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.updateProject(
                        pjId = pjId!!,
                        pjProjectName = createPartFromString(bind.etProjectName.text.toString()),
                        pjDeadline = createPartFromString(bind.etDeadline.text.toString()),
                        pjDescription = createPartFromString(bind.etDescription.text.toString()),
                        image = createPartFromFile()
                    )
                } catch (t: Throwable) {
                    Log.e("msg", "${t.message}")
                }
            }

            if (response is ProjectResponse) {
                if (response.success) {
                    noticeToast(response.message)
                    this@ProjectActivity.finish()
                } else {
                    noticeToast(response.message)
                }
            }
        }
    }

    private fun deleteProject() {
        val service = createApi<ProjectApiService>(this@ProjectActivity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.deleteProject(
                        pjId = pjId!!
                    )
                } catch (t: Throwable) {
                    Log.e("msg", "${t.message}")
                }
            }

            if (response is ProjectResponse) {
                if (response.success) {
                    noticeToast(response.message)
                    this@ProjectActivity.finish()
                } else {
                    noticeToast(response.message)
                }
            }
        }
    }

    private fun deleteConfirmation() {
        val dialog = AlertDialog
            .Builder(this@ProjectActivity)
            .setTitle("Notice!")
            .setMessage("Are you sure to delete this project?")
            .setPositiveButton("OK") { _, _ ->
                deleteProject()
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
                R.id.et_project_name -> valProjectName(
                    bind.inputLayoutProjectName,
                    bind.etProjectName
                )
                R.id.et_deadline -> valDeadline(bind.inputLayoutDeadline, bind.etDeadline)
                R.id.et_description -> valDescription(
                    bind.inputLayoutDescription,
                    bind.etDescription
                )
            }
        }
    }
}