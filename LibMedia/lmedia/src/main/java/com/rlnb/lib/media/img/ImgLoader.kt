package com.rlnb.lib.media.img

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide

/**
 *
 * @author RL
 * @version 2021/9/29
 */
object ImgLoader {
    fun loadNetImg(activity: FragmentActivity, url: String, iv: ImageView) {
        Glide.with(activity).load(url).into(iv)
    }

    fun loadUriImg(uri: Uri, iv: ImageView) {
        iv.context?.let {
            Glide.with(it)
                .asBitmap()
                .load(uri).into(iv)
        }
    }

    fun loadUriImg(context: Context, uri: Uri, iv: ImageView) {
        Glide.with(context)
            .asBitmap()
            .load(uri).into(iv)
    }

    fun loadResImg(context: Context, resId: Int, iv: ImageView) {
        Glide.with(context).load(resId).into(iv)
    }
}