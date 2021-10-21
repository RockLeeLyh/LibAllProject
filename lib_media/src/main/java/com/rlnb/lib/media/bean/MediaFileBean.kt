package com.rlnb.lib.media.bean

import android.net.Uri
import com.rlnb.lib.media.util.MediaStaticVariable

/**
 * 文件信息對象
 * @author RL
 * @version 2021/9/16
 */
data class MediaFileBean(
    // ========================= 數據庫里數據 =================================
    /** Id */
    val id: Long,
    /** 所屬文件夾的Id */
    var bucketId: String,
    /** 文件大小，單位: B */
    val size: Int,
    /** 視頻文件時，時長 */
    val duration: Long,
    /** 類型,例如：image/jpeg，video/mp4 */
    val type: String,
    // ========================= 列表使用的一些保存數據 =================================
    /** 文件所對應的Uri */
    val uri: Uri,
    /** 列表下標 */
    var position: Int = -1,
    /** 選中文件后的顯示文件排序數 */
    var switchIndex: Int = 0,
    /** 是否為照相機對象，true - 是 */
    var isTakePhoto:Boolean = false
) {

    companion object {
        /** @return  返回一個照相的文件數據對象 */
        fun getTakePhotoBean(): MediaFileBean {
            return MediaFileBean(
                0,
                MediaStaticVariable.TAKE_PHOTO_BUCKET_ID,
                0,
                0,
                MediaStaticVariable.TAKE_PHOTO_BUCKET_ID,
                Uri.parse(""),
                isTakePhoto = true
            )
        }
    }
}