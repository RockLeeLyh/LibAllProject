package com.rlnb.lib.project.media

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.rlnb.lib.media.ui.MediaMainActivity

/**
 * @author RL
 * @date 2021/10/21 16:17
 * @description
 */
class MediaOpenActivity : MediaMainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun confirmSuccess(uri: Uri) {
    }

    // ======================= 權限獲取 ==============================
    private var callbackReadExternalPer: (() -> Unit)? = null
    private val requestCodeReadExternalPer = 99

    override fun checkReadExternalPer(callback: () -> Unit) {
        callbackReadExternalPer = callback
        callbackReadExternalPer?.let {
            checkPer(requestCodeReadExternalPer, it, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private var callbackCameraPer: (() -> Unit)? = null
    private val requestCodeCameraPer = 999
    override fun checkCameraPer(callback: () -> Unit) {
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