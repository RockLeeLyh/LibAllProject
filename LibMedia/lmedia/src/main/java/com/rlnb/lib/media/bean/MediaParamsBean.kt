package com.rlnb.lib.media.bean

import android.provider.MediaStore
import com.rlnb.lib.media.util.OsUtils

/**
 * 設置參數對象
 * @author RL
 * @version 2021/9/26
 */
data class MediaParamsBean(
    /**
     * 相冊查詢類型
     * 0：所有類型（圖片，視頻）
     * 1：圖片
     * 2：視頻
     *  */
    var mediaType: Int = 1,
    /** 是否顯示照相機 */
    var isShowTakeCamera: Boolean = true,
    /** 最大選擇文件數量 */
    var maxSelectable: Int = 9,
    /** 顯示圖片列表列數 */
    var gridSpanCount: Int = 4
) {

    fun getFolderSelection(): String {
        return StringBuilder().apply {
            when (mediaType) {
                0 -> {
                    append("(")
                    append("${MediaStore.Files.FileColumns.MEDIA_TYPE}=?")
                    append(" OR ")
                    append("${MediaStore.Files.FileColumns.MEDIA_TYPE}=?")
                    append(")")
                    append(" AND ")
                }
                1, 2 -> {
                    append("${MediaStore.Files.FileColumns.MEDIA_TYPE}=?")
                    append(" AND ")
                }
            }
            append("${MediaStore.Files.FileColumns.SIZE}>0")
            when {
                OsUtils.isVersionQFor10Api29Before() -> {
                    append(")")
                    append(" GROUP BY (${MediaStore.Files.FileColumns.BUCKET_ID}")
                }
            }
        }.toString()
    }

    fun getFolderSelectionArgs(): Array<String>? {
        return when (mediaType) {
            0 -> arrayOf(
                MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(),
                MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()
            )
            1 -> arrayOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString())
            2 -> arrayOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString())
            else -> null
        }
    }

    private fun appendFileBucketId(sb:StringBuilder,bucketIdArray: MutableList<Long>?) {
        if(bucketIdArray.isNullOrEmpty()) {
            return
        }
        sb.apply {
            // 文件夾
            append("(")
            var isFirst = true
            bucketIdArray.forEach{ _ ->
                if(isFirst) {
                    isFirst = false
                }else {
                    append(" OR ")
                }
                append("${MediaStore.Files.FileColumns.BUCKET_ID} = ?")
            }
            append(")")
            append(" AND ")
        }
    }

    fun getFileSelection(bucketIdArray: MutableList<Long>?): String {
        return StringBuilder().apply {
            when (mediaType) {
                0 -> {
                    // 類型
                    append("(")
                    append("${MediaStore.Files.FileColumns.MEDIA_TYPE}=?")
                    append(" OR ")
                    append("${MediaStore.Files.FileColumns.MEDIA_TYPE}=?")
                    append(")")
                    append(" AND ")
                    appendFileBucketId(this,bucketIdArray)
                }
                1, 2 -> {
                    append("${MediaStore.Files.FileColumns.MEDIA_TYPE}=?")
                    append(" AND ")
                    appendFileBucketId(this,bucketIdArray)
                }
            }
            append("${MediaStore.Files.FileColumns.SIZE}>0")
        }.toString()
    }

    fun getFileSelectionArgs(bucketIdArray: MutableList<Long>?): Array<String>? {
        val args = mutableListOf<String>()
         when (mediaType) {
            0 -> {
                args.add(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString())
                args.add(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString())
            }
            1 -> args.add(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString())
            2 -> args.add(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString())
        }
        bucketIdArray?.let {
            it.forEach{ bId ->
                args.add(bId.toString())
            }
        }
        return args.toTypedArray()

    }

}