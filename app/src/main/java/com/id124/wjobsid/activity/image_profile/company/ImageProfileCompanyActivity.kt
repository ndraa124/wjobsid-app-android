package com.id124.wjobsid.activity.image_profile.company

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseActivityCoroutine
import com.id124.wjobsid.databinding.ActivityImageProfileBinding
import com.id124.wjobsid.remote.ApiClient
import com.id124.wjobsid.util.FileHelper.Companion.createPartFromFile
import com.id124.wjobsid.util.FileHelper.Companion.getPathFromURI
import java.util.*

class ImageProfileCompanyActivity : BaseActivityCoroutine<ActivityImageProfileBinding>(), View.OnClickListener {
    private lateinit var viewModel: ImageProfileCompanyViewModel
    private var cnId: Int? = null
    private var cnProfile: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_image_profile
        super.onCreate(savedInstanceState)

        cnId = intent.getIntExtra("cn_id", 0)
        cnProfile = intent.getStringExtra("cn_profile")

        setToolbarActionBar()
        setDataFromIntent()
        setViewModel()
        subscribeLiveData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_choose_image -> {
                pickImageFromGallery()
            }
            R.id.iv_image_view -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                        requestPermissions(permissions, PERMISSION_CODE)
                    } else {
                        pickImageFromGallery()
                    }
                } else {
                    pickImageFromGallery()
                }
            }
            R.id.iv_image_load -> {
                pickImageFromGallery()
            }
            R.id.btn_update_image -> {
                if (cnId != 0) {
                    if (pathImage == null) {
                        setResult(RESULT_OK)
                        this@ImageProfileCompanyActivity.finish()
                    } else {
                        viewModel.serviceUpdateImageEngineer(
                            cnId = cnId!!,
                            image = createPartFromFile(pathImage!!)
                        )
                    }
                } else {
                    noticeToast("Please login again!")
                }
            }
            R.id.ln_back -> {
                this@ImageProfileCompanyActivity.finish()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    noticeToast("Permission denied...!")
                }
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

            pathImage = getPathFromURI(this@ImageProfileCompanyActivity, data?.data!!)
        }
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(bind.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile"
        bind.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setDataFromIntent() {
        if (cnId != 0) {
            bind.ibChooseImage.visibility = View.GONE
            bind.ivImageView.visibility = View.VISIBLE

            if (cnProfile != null) {
                bind.imageUrl = ApiClient.BASE_URL_IMAGE + cnProfile
            } else {
                bind.imageUrl = ApiClient.BASE_URL_IMAGE_DEFAULT_PROFILE_2
            }
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@ImageProfileCompanyActivity).get(
            ImageProfileCompanyViewModel::class.java
        )
        viewModel.setService(createApi(this@ImageProfileCompanyActivity))
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@ImageProfileCompanyActivity, {
            bind.btnUpdateImage.visibility = View.GONE
            bind.progressBar.visibility = View.VISIBLE
        })

        viewModel.onSuccessLiveData.observe(this@ImageProfileCompanyActivity, {
            if (it) {
                setResult(RESULT_OK)
                this@ImageProfileCompanyActivity.finish()

                bind.progressBar.visibility = View.GONE
                bind.btnUpdateImage.visibility = View.VISIBLE
            } else {
                bind.progressBar.visibility = View.GONE
                bind.btnUpdateImage.visibility = View.VISIBLE
            }
        })

        viewModel.onMessageLiveData.observe(this@ImageProfileCompanyActivity, {
            noticeToast(it)
        })

        viewModel.onFailLiveData.observe(this@ImageProfileCompanyActivity, {
            noticeToast(it)
        })
    }
}