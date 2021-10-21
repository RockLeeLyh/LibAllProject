package com.rlnb.lib.media.select.bean

import android.net.Uri
import com.rlnb.lib.media.util.MediaStaticVariable

/**
 * 文件夾信息對象
 * @author RL
 * @version 2021/9/15
 */
data class MediaFolderBean(
    // ========================= 數據庫里數據 =================================
    /** 第一個文件夾 Id */
    var bucketId: Long,
    /** 文件夾名稱 */
    var name: String,
    /** 文件夾里第一個文件類型 */
    var type: String,
    // ========================= 列表使用的一些保存數據 =================================
    /** 文件夾里第一個文件Uri */
    var uri: Uri,
    /** 文件夾里包含的文件個數 */
    var total: Int = 0,
    /** 是否為全部文件夾，true - 是 */
    var isAll: Boolean = false,
    /** 合并同名稱的文件夾后，所有合并的文件夾Id列表 */
    var bucketIdArray: MutableList<Long> = mutableListOf(),
    /** 文件夾下標 */
    var position: Int = -1
) {
    fun plusTotal() {
        total++
    }

    companion object {
        /**
         * 組裝一個全部的文件夾信息對象
         * @param   uri 文件夾第一個文件Uri
         * @param   total 文件夾里文件總個數
         * @return   返回一個全部的文件夾對象
         */
        fun getAllFolderBean(uri: Uri, total: Int): MediaFolderBean {
            return MediaFolderBean(
                0, MediaStaticVariable.ALL_BUCKET_NAME,
                MediaStaticVariable.ALL_BUCKET_NAME,
                uri,
                total,
                true
            )
        }
    }
}
