package com.rlnb.lib.media.util

import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore

/**
 *
 * @author RL
 * @version 2021/9/29
 */
object MediaUtils {
    fun isImageType(type:String?):Boolean {
        return type?.startsWith(MediaStaticVariable.IMAGE_PREFIX, true) == true
    }
    fun isVideoType(type:String?):Boolean {
        return type?.startsWith(MediaStaticVariable.VIDEO_PREFIX, true) == true
    }
    fun getUri(id: Long, type: String?): Uri {
        val contentUri = when {
            isImageType(type) -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            isVideoType(type) -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            else -> MediaStore.Files.getContentUri("external")
        }
        return ContentUris.withAppendedId(contentUri, id)
    }
}