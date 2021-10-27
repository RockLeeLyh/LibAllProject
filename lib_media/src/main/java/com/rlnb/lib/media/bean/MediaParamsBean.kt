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
    /** 是否首次顯示選擇類型上拉框 */
    var isSelectFirstShowType:Boolean = true,
    /** 类型选择弹出框空白区域是否可点击 */
    var isSelectMaskClick:Boolean = false,
    /** 是否顯示照相機 */
    var isShowTakeCamera: Boolean = true,
    /** 最大選擇文件數量 */
    var maxSelectable: Int = 9,
    /** 顯示圖片列表列數 */
    var gridSpanCount: Int = 4,
    /** 選擇圖片是否立即跳轉，只有設置最大數里為1時，才會有效，默認不跳轉，需要點擊確認按鈕 */
    var isChooseToJumpNow: Boolean = false,
    /** 确认选中后是否自动关闭页面，true - 关闭 */
    var isConfirmBeforeClose:Boolean = true,
) {
    /**
     * 拼接文件夾查詢條件表達式模版
     * @return  返回拼接好字符串
     */
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

    /**
     * 組裝文件夾查詢條件表達式模版傳值
     * @return  返回組裝的模板值列表
     */
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


    /**
     * 拼接文件夾里面文件列表查詢條件表達式模版
     * @param   sb   組裝字符串對象
     * @param   bucketIdArray   文件夾對象Id列表
     */
    private fun appendFileBucketId(sb: StringBuilder, bucketIdArray: MutableList<Long>?) {
        if (bucketIdArray.isNullOrEmpty()) {
            return
        }
        sb.apply {
            // 文件夾
            append("(")
            var isFirst = true
            bucketIdArray.forEach { _ ->
                if (isFirst) {
                    isFirst = false
                } else {
                    append(" OR ")
                }
                append("${MediaStore.Files.FileColumns.BUCKET_ID} = ?")
            }
            append(")")
            append(" AND ")
        }
    }

    /**
     * 拼接文件夾里面文件列表查詢條件表達式模版
     * @param   bucketIdArray   文件夾對象Id列表
     * @return  返回拼接好字符串
     */
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
                    appendFileBucketId(this, bucketIdArray)
                }
                1, 2 -> {
                    append("${MediaStore.Files.FileColumns.MEDIA_TYPE}=?")
                    append(" AND ")
                    appendFileBucketId(this, bucketIdArray)
                }
            }
            append("${MediaStore.Files.FileColumns.SIZE}>0")
        }.toString()
    }

    /**
     * 組裝文件夾查詢條件表達式模版傳值
     * @return  返回組裝的模板值列表
     */
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
            it.forEach { bId ->
                args.add(bId.toString())
            }
        }
        return args.toTypedArray()

    }

    /**
     * 判斷是否立即跳轉，條件有：
     * 1.最大選擇圖片數量為 [maxSelectable] = 1
     * 2.[isChooseToJumpNow] = true
     * 都成功時，才會立即跳轉
     * @return  true - 立即跳轉
     */
    fun isChooseToJumpNowTrue():Boolean {
        return isChooseToJumpNow && isMaxSelectableForOne()
    }

    /***
     * 判斷最大可選文件數為1
     * @return  true - 是
     */
    fun isMaxSelectableForOne():Boolean {
        return maxSelectable == 1
    }

}