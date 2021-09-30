package com.rlnb.lib.media.util

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.EnvironmentCompat
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 调用系统照相机工具类
 * @author RL
 * @version 2021/9/29
 */
class TakePhotoUtils(activity: Activity) {
    private val mActivity = activity

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }

    lateinit var currentPhotoUri: Uri

    /** 拍照成功回调方法 */
    var mTakePhotoSuccessCallback: ((uri: Uri) -> Unit)? = null

    /** 拍照失败回调方法 */
    var mTakePhotoFailCallback: (() -> Unit)? = null

    /** 打开系统照相机 */
    fun openSysCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            val uri = try {
//                if (OsUtils.isVersionQFor10Api29Before())
//                    createImageFileUri()
//                else
                createImageUri()
            } catch (e: Exception) {
                null
            }
            uri?.let {
                currentPhotoUri = it
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri)
                takePictureIntent.resolveActivity(mActivity.packageManager)?.also {
                    mActivity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    /**
     * 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
     * @return  true - 有
     */
    private fun isMediaMounted(): Boolean {
        val status = Environment.getExternalStorageState()
        return status == Environment.MEDIA_MOUNTED
    }

    /**
     * @return 获取图片保存数据库Uri
     */
    private fun getMediaUri(): Uri {
        return if (isMediaMounted()) {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        } else {
            MediaStore.Images.Media.INTERNAL_CONTENT_URI
        }
    }

    /**
     * 向系统数据库插入一条图片文件
     * @return  返回插入的文件的URI
     */
    fun createImageUri(): Uri? {
        return mActivity.contentResolver.insert(getMediaUri(), ContentValues())
    }

    /**
     * 删除插入文件
     */
    private fun delImageUri() {
        delUri(currentPhotoUri)
    }

    fun delUri(uri:Uri?) {
        try {
            uri?.let {
                val id = it.path?.split("/")?.last()
                val delUri = getMediaUri()
                val delCount = mActivity.contentResolver.delete(
                    delUri,
                    "${MediaStaticVariable.ID_COLUMNS}=?",
                    arrayOf(id.toString())
                )
                MediaLog.i(this,"删除插入照相图片，delCount = $delCount")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != REQUEST_IMAGE_CAPTURE) {
            return
        }
        if (resultCode == AppCompatActivity.RESULT_OK) {
            MediaLog.i(this, "照相成功，currentPhotoUri = $currentPhotoUri")
            mTakePhotoSuccessCallback?.invoke(currentPhotoUri)
        } else {
            MediaLog.i(this, "照相失败，currentPhotoUri = $currentPhotoUri")
            delImageUri()
            mTakePhotoFailCallback?.invoke()
        }
    }


    private fun createImageFileUri(): Uri? {
        createImageFile()?.let {
            return if (OsUtils.isVersionNFor7Api24Before()) {
                Uri.fromFile(it)
            } else {
                //适配Android 7.0文件权限，通过FileProvider创建一个content类型的Uri
                MediaProviderUtils.getFileProviderUri(mActivity, it)
            }
        }
        return null
    }

    /**
     * 创建保存图片的文件
     */
    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.apply {
            if (!exists()) {
                mkdir()
            }
        }
        val prefix = "Jpeg_${timeStamp}_"
        val tempFile = File.createTempFile(prefix, ".jpg", storageDir)
        return if (Environment.MEDIA_MOUNTED != EnvironmentCompat.getStorageState(tempFile)) {
            null
        } else tempFile
    }


}