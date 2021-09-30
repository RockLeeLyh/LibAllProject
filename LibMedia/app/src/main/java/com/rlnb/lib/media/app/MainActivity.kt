package com.rlnb.lib.media.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.rlnb.lib.media.app.databinding.MainLayoutBinding
import com.rlnb.lib.media.bean.MediaFileBean
import com.rlnb.lib.media.bean.MediaParamsBean
import com.rlnb.lib.media.delegate.MediaFragmentDelegate
import com.rlnb.lib.media.util.MediaLog
import com.yalantis.ucrop.UCrop

/**
 *
 * @author RL
 * @version 2021/9/29
 */
class MainActivity : AppCompatActivity() {

    private var mTakePhotoActivityResult: ((requestCode: Int, resultCode: Int, data: Intent?) -> Unit)? = null

    private lateinit var mMediaFragmentDelegate:MediaFragmentDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vdb: MainLayoutBinding = DataBindingUtil.setContentView(this, R.layout.main_layout)

        mMediaFragmentDelegate = MediaFragmentDelegate(
            this,
            vdb.flMainApp.id,
            MediaParamsBean().apply {
                maxSelectable = 1
            }
        )
            .apply {
                mCheckCameraPerCallback = { checkCameraPer(it) }
                mCheckReadExternalPerCallback = { checkReadExternalPer(it) }
                mTakePhotoActivityResult = mTakePhotoActivityResultCallback
                mConfirmUriCallback = {confirmUri(it)}
                mConfirmBeanCallback = {confirmBean(it)}
                init()
            }
    }

    private fun confirmUri(uriList:List<Uri>) {
        MediaLog.i(this,"獲取選中列表Uris = ${uriList}")
        if(uriList.isNotEmpty()) {
            crop(uriList[0])
        }
    }

    private fun confirmBean(beanList:List<MediaFileBean>) {
        MediaLog.i(this,"獲取選中列表Beans = ${beanList}")

    }
    var destinationUri:Uri? = null
    private fun crop(sourceUri:Uri) {
        destinationUri = mMediaFragmentDelegate.createImageUri()
        MediaLog.i(this,"UCrop 裁剪，uri = $destinationUri")

        UCrop.of(sourceUri, destinationUri!!)
//                .withAspectRatio(16F, 9)
//                .withMaxResultSize(maxWidth, maxHeight)
            .start(this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mTakePhotoActivityResult?.invoke(requestCode, resultCode, data)

        if (requestCode == UCrop.REQUEST_CROP) {
            if(resultCode == RESULT_OK) {
                val uri = data?.let { UCrop.getOutput(it) }
                MediaLog.i(this, "UCrop 裁剪成功，uri = $uri")
            }else {
                MediaLog.i(this, "UCrop 裁剪取消，destinationUri = $destinationUri")
                mMediaFragmentDelegate.delUri(destinationUri)
            }
        }
        else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = data?.let { UCrop.getError(it) }
            mMediaFragmentDelegate.delUri(destinationUri)
            MediaLog.i(this,"UCrop 裁剪失敗，cropError = $cropError")
        }
    }

    // ======================= 權限獲取 ==============================
    private var callbackReadExternalPer: (() -> Unit)? = null
    private val requestCodeReadExternalPer = 99

    fun checkReadExternalPer(callback: () -> Unit) {
        callbackReadExternalPer = callback
        callbackReadExternalPer?.let {
            checkPer(requestCodeReadExternalPer, it, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private var callbackCameraPer: (() -> Unit)? = null
    private val requestCodeCameraPer = 999
    fun checkCameraPer(callback: () -> Unit) {
        callbackCameraPer = callback
        callbackCameraPer?.let {
            checkPer(
                requestCodeCameraPer,
                it,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    private fun checkPer(requestCode: Int, callback: () -> Unit, vararg pers: String) {
        var isPer = false
        pers.forEach {
            if (ContextCompat.checkSelfPermission(this, it) !== PackageManager.PERMISSION_GRANTED) {
                isPer = true
            }
        }
        if (isPer) {
            requestPermission(requestCode, pers.asList().toTypedArray())
        } else {
            callback?.invoke()
        }
    }

    private fun requestPermission(requestCode: Int, requiredPermissions: Array<String>) {
        ActivityCompat.requestPermissions(
            this,
            requiredPermissions,
            requestCode
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            requestCodeReadExternalPer -> {
                Toast.makeText(this, "獲取ReadExternal權限成功", Toast.LENGTH_SHORT).show()
                callbackReadExternalPer?.invoke()
            }
            requestCodeCameraPer -> {
                Toast.makeText(this, "獲取Camera權限成功", Toast.LENGTH_SHORT).show()
                callbackCameraPer?.invoke()

            }
        }

    }
}