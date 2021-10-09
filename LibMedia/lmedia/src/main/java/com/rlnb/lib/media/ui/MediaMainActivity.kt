package com.rlnb.lib.media.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rlnb.lib.media.R
import com.rlnb.lib.media.bean.MediaParamsBean
import com.rlnb.lib.media.databinding.LmediaMainActivityBinding
import com.rlnb.lib.media.delegate.MediaFragmentDelegate
import com.rlnb.lib.media.util.MediaLog

open abstract class MediaMainActivity : AppCompatActivity() {

    private lateinit var mVdb: LmediaMainActivityBinding

    private var mTakePhotoActivityResult: ((requestCode: Int, resultCode: Int, data: Intent?) -> Unit)? = null
    private lateinit var mMediaFragmentDelegate: MediaFragmentDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mVdb = DataBindingUtil.setContentView(this, R.layout.lmedia_main_activity)
        openMedia()
    }

    private fun openMedia() {
        mMediaFragmentDelegate = getMediaDelegate()
        mMediaFragmentDelegate?.init()
    }

    open fun getMediaParamsBean(): MediaParamsBean {
        return MediaParamsBean().apply {
            maxSelectable = 1
        }
    }

    open fun getMediaDelegate(): MediaFragmentDelegate {
        return MediaFragmentDelegate(
            this,
            mVdb.flMainApp.id,
            getMediaParamsBean()
        )
            .apply {
                mCheckCameraPerCallback = { checkCameraPer(it) }
                mCheckReadExternalPerCallback = { checkReadExternalPer(it) }
                mTakePhotoActivityResult = mTakePhotoActivityResultCallback
                mConfirmUriCallback = { confirmUri(it) }
                mCloseCallback = { closeMedia() }
            }
    }

    open fun closeMedia() {
        finish()
    }

    open abstract fun checkReadExternalPer(callback: () -> Unit)

    open abstract fun checkCameraPer(callback: () -> Unit)

    open abstract fun confirmSuccess(uri:Uri)

    private fun confirmUri(uriList: List<Uri>) {
        MediaLog.i(this, "獲取選中列表Uris = $uriList")
        if (uriList.isNotEmpty()) {
            confirmSuccess(uriList[0])
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mTakePhotoActivityResult?.invoke(requestCode, resultCode, data)
    }
}