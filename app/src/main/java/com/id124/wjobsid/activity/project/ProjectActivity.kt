package com.id124.wjobsid.activity.project

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseActivityCoroutine
import com.id124.wjobsid.databinding.ActivityProjectBinding
import com.id124.wjobsid.remote.ApiClient.Companion.BASE_URL_IMAGE
import com.id124.wjobsid.remote.ApiClient.Companion.BASE_URL_IMAGE_DEFAULT_BACKGROUND
import com.id124.wjobsid.util.FileHelper
import com.id124.wjobsid.util.FileHelper.Companion.createPartFromFile
import com.id124.wjobsid.util.FileHelper.Companion.createPartFromString
import com.id124.wjobsid.util.form_validate.ValidateProject.Companion.valDeadline
import com.id124.wjobsid.util.form_validate.ValidateProject.Companion.valDescription
import com.id124.wjobsid.util.form_validate.ValidateProject.Companion.valProjectName
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class ProjectActivity : BaseActivityCoroutine<ActivityProjectBinding>(), View.OnClickListener {
    private lateinit var viewModel: ProjectViewModel
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

        setViewModel()
        subscribeLiveData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_choose_image -> {
                pickImageFromGallery()
            }
            R.id.iv_image_view -> {
                pickImageFromGallery()
            }
            R.id.iv_image_load -> {
                pickImageFromGallery()
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
                    !valProjectName(bind.inputLayoutProjectName, bind.etProjectName) -> return
                    !valDeadline(bind.inputLayoutDeadline, bind.etDeadline) -> return
                    !valDescription(bind.inputLayoutDescription, bind.etDescription) -> return
                    else -> {
                        if (pjId != 0) {
                            if (pathImage == null) {
                                viewModel.serviceUpdateApi(
                                    pjId = pjId!!,
                                    pjProjectName = createPartFromString(bind.etProjectName.text.toString()),
                                    pjDeadline = createPartFromString(bind.etDeadline.text.toString()),
                                    pjDescription = createPartFromString(bind.etDescription.text.toString())
                                )
                            } else {
                                viewModel.serviceUpdateApi(
                                    pjId = pjId!!,
                                    pjProjectName = createPartFromString(bind.etProjectName.text.toString()),
                                    pjDeadline = createPartFromString(bind.etDeadline.text.toString()),
                                    pjDescription = createPartFromString(bind.etDescription.text.toString()),
                                    image = createPartFromFile(pathImage!!)
                                )
                            }
                        } else {
                            if (pathImage != null) {
                                viewModel.serviceCreateApi(
                                    cnId = createPartFromString(sharedPref.getIdCompany().toString()),
                                    pjProjectName = createPartFromString(bind.etProjectName.text.toString()),
                                    pjDeadline = createPartFromString(bind.etDeadline.text.toString()),
                                    pjDescription = createPartFromString(bind.etDescription.text.toString()),
                                    image = createPartFromFile(pathImage!!)
                                )
                            } else {
                                noticeToast("Please select image!")
                            }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            bind.ibChooseImage.visibility = View.GONE
            bind.ivImageView.visibility = View.GONE
            bind.ivImageLoad.visibility = View.VISIBLE
            bind.ivImageLoad.setImageURI(data?.data)

            pathImage = FileHelper.getPathFromURI(this@ProjectActivity, data?.data!!)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.serviceIsHireApi(pjId)
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

            if (intent.getStringExtra("pj_image") != null) {
                bind.imageUrl = BASE_URL_IMAGE + intent.getStringExtra("pj_image")
            } else {
                bind.imageUrl = BASE_URL_IMAGE_DEFAULT_BACKGROUND
            }
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

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@ProjectActivity).get(ProjectViewModel::class.java)
        viewModel.setServiceProject(createApi(this@ProjectActivity))
        viewModel.setServiceHire(createApi(this@ProjectActivity))
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@ProjectActivity, {
            bind.btnAddProject.visibility = View.GONE
            bind.btnDeleteProject.visibility = View.GONE
            bind.progressBar.visibility = View.VISIBLE
        })

        viewModel.onSuccessLiveData.observe(this@ProjectActivity, {
            if (it) {
                setResult(RESULT_OK)
                this@ProjectActivity.finish()

                bind.progressBar.visibility = View.GONE
                bind.btnAddProject.visibility = View.VISIBLE
                bind.btnDeleteProject.visibility = View.VISIBLE
            } else {
                bind.progressBar.visibility = View.GONE
                bind.btnAddProject.visibility = View.VISIBLE
                bind.btnDeleteProject.visibility = View.VISIBLE
            }
        })

        viewModel.onSuccessHireLiveData.observe(this, {
            if (it) {
                bind.btnAddProject.visibility = View.GONE
                bind.btnDeleteProject.visibility = View.GONE
            }
        })

        viewModel.onMessageLiveData.observe(this@ProjectActivity, {
            noticeToast(it)
        })

        viewModel.onFailLiveData.observe(this@ProjectActivity, {
            noticeToast(it)
        })

        viewModel.onFailHireLiveData.observe(this@ProjectActivity, {
            Log.d("msg", it)
            bind.btnAddProject.visibility = View.VISIBLE
            bind.btnDeleteProject.visibility = View.VISIBLE
        })
    }

    private fun deleteConfirmation() {
        val dialog = AlertDialog
            .Builder(this@ProjectActivity)
            .setTitle("Notice!")
            .setMessage("Are you sure to delete this project?")
            .setPositiveButton("OK") { _, _ ->
                viewModel.serviceDeleteApi(pjId = pjId!!)
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