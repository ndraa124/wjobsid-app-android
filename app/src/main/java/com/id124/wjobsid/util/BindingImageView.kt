package com.id124.wjobsid.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.id124.wjobsid.R

@BindingAdapter("loadImage")
fun loadImage(iv_image_profile: ImageView, url: String) {
    Glide.with(iv_image_profile.context)
        .asBitmap()
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.ic_account)
        .into(object : CustomTarget<Bitmap?>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                iv_image_profile.setImageBitmap(resource)
                iv_image_profile.buildLayer()
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
}