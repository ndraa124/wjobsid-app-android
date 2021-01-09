package com.id124.wjobsid.activity.image_profile.engineer

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

class ImageProfileEngineerActivity : BaseActivityCoroutine<ActivityImageProfileBinding>(), View.OnClickListener {
    private lateinit var viewModel: ImageProfileEngineerViewModel
    private var enId: Int? = null
    private var enProfile: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_image_profile
        super.onCreate(savedInstanceState)

        enId = intent.getIntExtra("en_id", 0)
        enProfile = intent.getStringExtra("en_profile")

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
                if (enId != 0) {
                    if (bitmap == null) {
                        setResult(RESULT_OK)
                        this@ImageProfileEngineerActivity.finish()
                    } else {
                        viewModel.serviceUpdateImageEngineer(
                            enId = enId!!,
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
        if (enId != 0) {
            bind.ibChooseImage.visibility = View.GONE
            bind.ivImageView.visibility = View.VISIBLE

            if (enProfile != null) {
                bind.imageUrl = ApiClient.BASE_URL_IMAGE + enProfile
            } else {
                bind.imageUrl = ApiClient.BASE_URL_IMAGE_DEFAULT_PROFILE_2
            }
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@ImageProfileEngineerActivity).get(ImageProfileEngineerViewModel::class.java)
        viewModel.setService(createApi(this@ImageProfileEngineerActivity))
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@ImageProfileEngineerActivity, {
            bind.btnUpdateImage.visibility = View.GONE
            bind.progressBar.visibility = View.VISIBLE
        })

        viewModel.onSuccessLiveData.observe(this@ImageProfileEngineerActivity, {
            if (it) {
                setResult(RESULT_OK)
                this@ImageProfileEngineerActivity.finish()

                bind.progressBar.visibility = View.GONE
                bind.btnUpdateImage.visibility = View.VISIBLE
            } else {
                bind.progressBar.visibility = View.GONE
                bind.btnUpdateImage.visibility = View.VISIBLE
            }
        })

        viewModel.onMessageLiveData.observe(this@ImageProfileEngineerActivity, {
            noticeToast(it)
        })

        viewModel.onFailLiveData.observe(this@ImageProfileEngineerActivity, {
            noticeToast(it)
        })
    }
}