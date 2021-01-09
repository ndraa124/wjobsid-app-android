package com.id124.wjobsid.activity.portfolio

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseActivityCoroutine
import com.id124.wjobsid.databinding.ActivityPortfolioBinding
import com.id124.wjobsid.remote.ApiClient
import com.id124.wjobsid.remote.ApiClient.Companion.BASE_URL_IMAGE
import com.id124.wjobsid.util.form_validate.ValidatePortfolio.Companion.valAppName
import com.id124.wjobsid.util.form_validate.ValidatePortfolio.Companion.valDescription
import com.id124.wjobsid.util.form_validate.ValidatePortfolio.Companion.valLinkPub
import com.id124.wjobsid.util.form_validate.ValidatePortfolio.Companion.valLinkRepo
import com.id124.wjobsid.util.form_validate.ValidatePortfolio.Companion.valWorkPlace
import java.io.IOException
import java.util.*

class PortfolioActivity : BaseActivityCoroutine<ActivityPortfolioBinding>(), View.OnClickListener {
    private lateinit var viewModel: PortfolioViewModel
    private var typePortfolio: String? = null
    private var prId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_portfolio
        super.onCreate(savedInstanceState)
        prId = intent.getIntExtra("pr_id", 0)

        setToolbarActionBar()
        initTextWatcher()
        setDataFromIntent()
        setViewModel()
        subscribeLiveData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_choose_image -> {
                selectImage()
            }
            R.id.iv_image_view -> {
                selectImage()
            }
            R.id.btn_add_portfolio -> {
                when {
                    !valAppName(bind.inputLayoutApp, bind.etApp) -> {}
                    !valDescription(bind.inputLayoutDescription, bind.etDescription) -> {}
                    !valLinkPub(bind.inputLayoutPub, bind.etPubLink) -> {}
                    !valLinkRepo(bind.inputLayoutRepo, bind.etRepoLink) -> {}
                    !valWorkPlace(bind.inputLayoutWorkplace, bind.etWorkPlace) -> {}
                    else -> {
                        when (bind.rgPortfolioType.checkedRadioButtonId) {
                            bind.rbWeb.id -> {
                                typePortfolio = "aplikasi web"
                            }
                            bind.rbMobile.id -> {
                                typePortfolio = "aplikasi mobile"
                            }
                        }

                        if (prId != 0) {
                            if (bitmap == null) {
                                viewModel.serviceUpdateApi(
                                    prId = prId!!,
                                    prApp = createPartFromString(bind.etApp.text.toString()),
                                    prDescription = createPartFromString(bind.etDescription.text.toString()),
                                    prLinkPub = createPartFromString(bind.etPubLink.text.toString()),
                                    prLinkRepo = createPartFromString(bind.etRepoLink.text.toString()),
                                    prWorkPlace = createPartFromString(bind.etWorkPlace.text.toString()),
                                    prType = createPartFromString(typePortfolio!!)
                                )
                            } else {
                                viewModel.serviceUpdateApi(
                                    prId = prId!!,
                                    prApp = createPartFromString(bind.etApp.text.toString()),
                                    prDescription = createPartFromString(bind.etDescription.text.toString()),
                                    prLinkPub = createPartFromString(bind.etPubLink.text.toString()),
                                    prLinkRepo = createPartFromString(bind.etRepoLink.text.toString()),
                                    prWorkPlace = createPartFromString(bind.etWorkPlace.text.toString()),
                                    prType = createPartFromString(typePortfolio!!),
                                    image = createPartFromFile()
                                )
                            }
                        } else {
                            if (bitmap != null) {
                                viewModel.serviceCreateApi(
                                    enId = createPartFromString(sharedPref.getIdEngineer().toString()),
                                    prApp = createPartFromString(bind.etApp.text.toString()),
                                    prDescription = createPartFromString(bind.etDescription.text.toString()),
                                    prLinkPub = createPartFromString(bind.etPubLink.text.toString()),
                                    prLinkRepo = createPartFromString(bind.etRepoLink.text.toString()),
                                    prWorkPlace = createPartFromString(bind.etWorkPlace.text.toString()),
                                    prType = createPartFromString(typePortfolio!!),
                                    image = createPartFromFile()
                                )
                            } else {
                                noticeToast("Please select image!")
                            }
                        }
                    }
                }
            }
            R.id.btn_delete_portfolio -> {
                deleteConfirmation()
            }
            R.id.ln_back -> {
                this@PortfolioActivity.finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                uri = data?.getParcelableExtra("path")!!
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)

                    bind.ivImageView.visibility = View.GONE
                    bind.ibChooseImage.visibility = View.GONE
                    bind.ivImageLoad.visibility = View.VISIBLE
                    bind.ivImageLoad.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(bind.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Portfolio"
        bind.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initTextWatcher() {
        bind.etApp.addTextChangedListener(MyTextWatcher(bind.etApp))
        bind.etDescription.addTextChangedListener(MyTextWatcher(bind.etDescription))
        bind.etPubLink.addTextChangedListener(MyTextWatcher(bind.etPubLink))
        bind.etRepoLink.addTextChangedListener(MyTextWatcher(bind.etRepoLink))
        bind.etWorkPlace.addTextChangedListener(MyTextWatcher(bind.etWorkPlace))
    }

    private fun setDataFromIntent() {
        if (prId != 0) {
            bind.ibChooseImage.visibility = View.GONE
            bind.ivImageView.visibility = View.VISIBLE
            bind.btnDeletePortfolio.visibility = View.VISIBLE
            bind.tvAddPortfolio.text = getString(R.string.update_portfolio)
            bind.btnAddPortfolio.text = getString(R.string.update_portfolio)

            bind.etApp.setText(intent.getStringExtra("pr_app"))
            bind.etDescription.setText(intent.getStringExtra("pr_description"))
            bind.etPubLink.setText(intent.getStringExtra("pr_link_pub"))
            bind.etRepoLink.setText(intent.getStringExtra("pr_link_repo"))
            bind.etWorkPlace.setText(intent.getStringExtra("pr_work_place"))

            if (intent.getStringExtra("pr_type") == "aplikasi mobile") {
                bind.rbMobile.isChecked = true
            } else {
                bind.rbWeb.isChecked = true
            }

            if (intent.getStringExtra("pr_image") != null) {
                bind.imageUrl = BASE_URL_IMAGE + intent.getStringExtra("pr_image")
            } else {
                bind.imageUrl = ApiClient.BASE_URL_IMAGE_DEFAULT_BACKGROUND
            }

        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@PortfolioActivity).get(PortfolioViewModel::class.java)
        viewModel.setService(createApi(this@PortfolioActivity))
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@PortfolioActivity, {
            bind.btnAddPortfolio.visibility = View.GONE
            bind.btnDeletePortfolio.visibility = View.GONE
            bind.progressBar.visibility = View.VISIBLE
        })

        viewModel.onSuccessLiveData.observe(this@PortfolioActivity, {
            if (it) {
                setResult(RESULT_OK)
                this@PortfolioActivity.finish()

                bind.progressBar.visibility = View.GONE
                bind.btnAddPortfolio.visibility = View.VISIBLE
                bind.btnDeletePortfolio.visibility = View.VISIBLE
            } else {
                bind.progressBar.visibility = View.GONE
                bind.btnAddPortfolio.visibility = View.VISIBLE
                bind.btnDeletePortfolio.visibility = View.VISIBLE
            }
        })

        viewModel.onMessageLiveData.observe(this@PortfolioActivity, {
            noticeToast(it)
        })

        viewModel.onFailLiveData.observe(this@PortfolioActivity, {
            noticeToast(it)
        })
    }

    private fun deleteConfirmation() {
        val dialog = AlertDialog
            .Builder(this@PortfolioActivity)
            .setTitle("Notice!")
            .setMessage("Are you sure to delete this portfolio?")
            .setPositiveButton("OK") { _, _ ->
                viewModel.serviceDeleteApi(prId!!)
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
                R.id.et_app -> valAppName(bind.inputLayoutApp, bind.etApp)
                R.id.et_description -> valDescription(bind.inputLayoutDescription, bind.etDescription)
                R.id.et_pub_link -> valLinkPub(bind.inputLayoutPub, bind.etPubLink)
                R.id.et_repo_link -> valLinkRepo(bind.inputLayoutRepo, bind.etRepoLink)
                R.id.et_work_place -> valWorkPlace(bind.inputLayoutWorkplace, bind.etWorkPlace)
            }
        }
    }
}