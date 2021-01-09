package com.id124.wjobsid.activity.image_profile.company

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseActivityCoroutine
import com.id124.wjobsid.databinding.ActivityImageProfileBinding
import com.id124.wjobsid.remote.ApiClient
import java.io.IOException

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
                selectImage()
            }
            R.id.iv_image_view -> {
                selectImage()
            }
            R.id.btn_update_image -> {
                if (cnId != 0) {
                    if (bitmap == null) {
                        setResult(RESULT_OK)
                        this@ImageProfileCompanyActivity.finish()
                    } else {
                        viewModel.serviceUpdateImageEngineer(
                            cnId = cnId!!,
                            image = createPartFromFile()
                        )
                    }
                } else {
                    noticeToast("Please login again!")
                }
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
        viewModel = ViewModelProvider(this@ImageProfileCompanyActivity).get(ImageProfileCompanyViewModel::class.java)
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