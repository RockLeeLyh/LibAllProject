package com.rlnb.lib.media.util

import android.app.Activity
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import kotlin.jvm.Throws

/**
 * Android 7
 * @author RL
 * @version 2021/9/29
 */
object MediaProviderUtils {

    private fun getFileProviderPn(): String {
        return "fileprovidermedia"
    }

    private fun getFileProviderAuthority(activity: Activity): String {
        return "${activity.application.packageName}.${getFileProviderPn()}"
    }

    /**
     * 适配Android 7.0文件权限，通过FileProvider创建一个content类型的Uri
     */
    @Throws(IllegalArgumentException::class)
    fun getFileProviderUri(activity: Activity, file: File): Uri {
        return FileProvider.getUriForFile(
            activity,
            getFileProviderAuthority(activity),
            file
        )
    }
}