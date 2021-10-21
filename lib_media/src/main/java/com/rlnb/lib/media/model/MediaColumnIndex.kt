package com.rlnb.lib.media.model

import android.database.Cursor
import com.rlnb.lib.media.bean.MediaFileBean
import com.rlnb.lib.media.select.bean.MediaFolderBean
import com.rlnb.lib.media.util.MediaStaticVariable
import com.rlnb.lib.media.util.MediaUtils

/**
 * 查詢行的數據下標對象
 * @author RL
 * @version 2021/9/29
 */
data class MediaColumnIndex(
    /** 數據行數 Id 列對應下標 */
    var mIdColumn: Int = 0,
    /** 文件夾 Id 對應下標 */
    var mBucketIdColumn: Int = 0,
    /** 文件夾名稱對應下標 */
    var mBucketDisplayColumn: Int = 0,
    /** 數據行數類型列對應下標 */
    var mTypeColumn: Int = 0,
    /** 數據行數size列對應下標 */
    var mSizeColumn: Int = 0,
    /** 數據行數時間列對應下標 */
    var mDurationColumn: Int = 0,
    /** 分組查詢時，分組條數對應下標 */
    var mCountColumn: Int = 0
) {
    /**
     * 初始化文件夾數據庫行的列下標
     * @param   cursor 數據遊標
     * @param   isCountColumn   區分獲取分組總和時的方式，
     *                          之前時 true：使用 count 下標
     *                          之后false：使用固定下標 0 獲取
     */
    fun initFolderColumn(cursor: Cursor?, isCountColumn: Boolean) {
        cursor?.apply {
            mIdColumn = getColumnIndexOrThrow(MediaStaticVariable.ID_COLUMNS)
            mBucketIdColumn = getColumnIndexOrThrow(MediaStaticVariable.BUCKET_ID_COLUMNS)
            mBucketDisplayColumn = getColumnIndexOrThrow(MediaStaticVariable.BUCKET_DISPLAY_NAME_COLUMNS)
            mTypeColumn = getColumnIndexOrThrow(MediaStaticVariable.MIME_TYPE_COLUMNS)
            mCountColumn = if (isCountColumn) {
                getColumnIndexOrThrow(MediaStaticVariable.COUNT_COLUMNS)
            }else {
                0
            }
        }
    }

    /**
     * 文件夾可以使用分組查詢的數據解釋，Api 29 之前或之后
     * @param   cursor 數據遊標
     */
    fun getMediaFolderGroupByBean(cursor: Cursor): MediaFolderBean {
        cursor.apply {
            val bean = getMediaFolderBean(cursor)
            val total = getInt(mCountColumn)
            bean.total = total
            return bean
        }
    }

    /**
     * 文件夾信息組裝對象，默認總數為 1
     * @param   cursor 數據遊標
     * @return  返回文件夾信息對象
     */
    fun getMediaFolderBean(cursor: Cursor): MediaFolderBean {
        cursor.apply {
            val id = getLong(mIdColumn)
            val bucketId = getLong(mBucketIdColumn)
            val bucketDisplay = getString(mBucketDisplayColumn) ?: "#"
            val type = getString(mTypeColumn)
            val uri = MediaUtils.getUri(id, type)
            return MediaFolderBean(
                bucketId,
                bucketDisplay,
                type,
                uri,
                1
            )
        }
    }

    /** 初始化文件數據庫行的列下標 */
    fun initFileColumn(cursor: Cursor?) {
        cursor?.apply {
            mIdColumn = getColumnIndexOrThrow(MediaStaticVariable.ID_COLUMNS)
            mSizeColumn = getColumnIndexOrThrow(MediaStaticVariable.SIZE_COLUMNS)
            mTypeColumn = getColumnIndexOrThrow(MediaStaticVariable.MIME_TYPE_COLUMNS)
            mDurationColumn = getColumnIndexOrThrow(MediaStaticVariable.DURATION_COLUMNS)
        }
    }

    /**
     * 組裝文件信息對象
     * @return  返回文件信息對象
     */
    fun getMediaFileBean(cursor: Cursor, folderBean: MediaFolderBean?): MediaFileBean {
        cursor.apply {
            val id = getLong(mIdColumn)
            val size = getInt(mSizeColumn)
            val type = getString(mTypeColumn)
            val duration = getLong(mDurationColumn)
            val uri = MediaUtils.getUri(id, type)
            return MediaFileBean(id, folderBean?.name ?: "#", size, duration, type, uri)
        }
    }
}