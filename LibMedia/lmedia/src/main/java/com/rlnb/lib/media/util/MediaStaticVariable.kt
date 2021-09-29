package com.rlnb.lib.media.util

import android.net.Uri
import android.provider.MediaStore

/**
 *
 * @author RL
 * @version 2021/9/29
 */
object MediaStaticVariable {
    val QUERY_URI: Uri = MediaStore.Files.getContentUri("external")

    const val TAKE_PHOTO_BUCKET_ID = "takePhotoBucketId"
    const val ALL_BUCKET_NAME = "ALL"

    const val IMAGE_PREFIX = "image"
    const val VIDEO_PREFIX = "video"

    const val ID_COLUMNS = MediaStore.Files.FileColumns._ID
    const val BUCKET_ID_COLUMNS = MediaStore.Files.FileColumns.BUCKET_ID
    const val BUCKET_DISPLAY_NAME_COLUMNS = MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME
    const val SIZE_COLUMNS = MediaStore.Files.FileColumns.SIZE
    const val DURATION_COLUMNS = MediaStore.Files.FileColumns.DURATION
    const val MIME_TYPE_COLUMNS = MediaStore.Files.FileColumns.MIME_TYPE
    const val COUNT_COLUMNS = "count"

    val SORT_ORDER = "${MediaStore.Files.FileColumns.DATE_ADDED} DESC"

}