package com.id124.wjobsid.base

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.id124.wjobsid.activity.login.LoginViewModel
import com.id124.wjobsid.remote.ApiClient
import com.id124.wjobsid.util.SharedPreference
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

abstract class BaseActivityCoroutine<ActivityBinding : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var bind: ActivityBinding
    protected lateinit var sharedPref: SharedPreference
    protected lateinit var userDetail: HashMap<String, String>
    protected lateinit var coroutineScope: CoroutineScope
    protected var setLayout: Int? = null
    protected var bitmap: Bitmap? = null
    protected var uri: Uri? = null

    companion object {
        const val REQUEST_IMAGE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this@BaseActivityCoroutine, setLayout!!)

        sharedPref = SharedPreference(this@BaseActivityCoroutine)
        userDetail = sharedPref.getAccountUser()
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    }

    protected inline fun <reified ClassActivity> intents(context: Context) {
        context.startActivity(Intent(context, ClassActivity::class.java))
    }

    protected inline fun <reified ApiService> createApi(context: Context): ApiService {
        return ApiClient.getApiClient(context).create(ApiService::class.java)
    }

    protected fun noticeToast(message: String) {
        Toast.makeText(this@BaseActivityCoroutine, message, Toast.LENGTH_SHORT).show()
    }

    protected fun selectImage() {
        Dexter.withActivity(this@BaseActivityCoroutine)
            .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        showImagePickerOptions()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()
    }

    private fun showImagePickerOptions() {
        ImagePickerActivity().showImagePickerOptions1(
            this@BaseActivityCoroutine,
            object : ImagePickerActivity.PickerOptionListener {
                override fun onTakeCameraSelected() {
                    launchCameraIntent()
                }

                override fun onChooseGallerySelected() {
                    launchGalleryIntent()
                }
            })
    }

    private fun launchCameraIntent() {
        val intent = Intent(this@BaseActivityCoroutine, ImagePickerActivity::class.java)
        intent.putExtra(
            ImagePickerActivity().INTENT_IMAGE_PICKER_OPTION,
            ImagePickerActivity().REQUEST_IMAGE_CAPTURE
        )

        intent.putExtra(ImagePickerActivity().INTENT_LOCK_ASPECT_RATIO, true)
        intent.putExtra(ImagePickerActivity().INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity().INTENT_ASPECT_RATIO_Y, 1)

        intent.putExtra(ImagePickerActivity().INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true)
        intent.putExtra(ImagePickerActivity().INTENT_BITMAP_MAX_WIDTH, 1080)
        intent.putExtra(ImagePickerActivity().INTENT_BITMAP_MAX_HEIGHT, 1080)
        startActivityForResult(intent, REQUEST_IMAGE)
    }

    private fun launchGalleryIntent() {
        val intent = Intent(this@BaseActivityCoroutine, ImagePickerActivity::class.java)
        intent.putExtra(
            ImagePickerActivity().INTENT_IMAGE_PICKER_OPTION,
            ImagePickerActivity().REQUEST_GALLERY_IMAGE
        )

        intent.putExtra(ImagePickerActivity().INTENT_LOCK_ASPECT_RATIO, true)
        intent.putExtra(ImagePickerActivity().INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity().INTENT_ASPECT_RATIO_Y, 1)

        intent.putExtra(ImagePickerActivity().INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true)
        intent.putExtra(ImagePickerActivity().INTENT_BITMAP_MAX_WIDTH, 1080)
        intent.putExtra(ImagePickerActivity().INTENT_BITMAP_MAX_HEIGHT, 1080)
        startActivityForResult(intent, REQUEST_IMAGE)
    }

    protected fun createPartFromString(descriptionString: String): RequestBody {
        return descriptionString.toRequestBody(MultipartBody.FORM)
    }

    protected fun createPartFromFile():  MultipartBody.Part {
        val file = File("${uri?.path}")
        val reqFile: RequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData("image", file.name, reqFile)
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}