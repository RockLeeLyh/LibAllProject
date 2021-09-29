package com.rlnb.lib.media.model

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.core.content.ContentResolverCompat
import com.rlnb.lib.media.bean.MediaFileBean
import com.rlnb.lib.media.bean.MediaParamsBean
import com.rlnb.lib.media.select.bean.MediaFolderBean
import com.rlnb.lib.media.util.MediaLog
import com.rlnb.lib.media.util.MediaStaticVariable
import com.rlnb.lib.media.util.OsUtils
import java.lang.ref.WeakReference
import java.util.*


/**
 * 文件夾里列表數據模型
 * @author RL
 * @version 2021/9/18
 * @param context
 * @param mediaFolderBean    文件夾數據對象
 * @param mediaParamsBean   全局設置參數
 */
class MediaFileModel(
    context: Context,
    mediaFolderBean: MediaFolderBean?,
    mediaParamsBean: MediaParamsBean
) {
    private val mContext: WeakReference<Context> by lazy { WeakReference(context) }

    /** 全局設置參數 */
    val mMediaParamsBean = mediaParamsBean

    /** 文件夾數據對象 */
    private var mMediaFolderBean = mediaFolderBean

    /** 查詢行的數據下標對象 */
    private val mColumns by lazy { MediaColumnIndex() }

    /** 查詢字段 */
    private val mProjection by lazy {
        mutableListOf<String>().apply {
            add(MediaStaticVariable.ID_COLUMNS)
            add(MediaStaticVariable.SIZE_COLUMNS)
            add(MediaStaticVariable.DURATION_COLUMNS)
            add(MediaStaticVariable.MIME_TYPE_COLUMNS)
        }.toTypedArray()
    }

    /** 查詢條件表達式模版 */
    private var mSelection: String? = null

    /** 查詢條件表達式模版傳值 */
    private var mSelectionArgs: Array<String>? = null

    /**
     * Api 30 后支持的查詢條件Bundle對象
     * @return  返回查詢條件Bundle對象
     */
    private fun getBundle(): Bundle {
        return Bundle().apply {
            putString(ContentResolver.QUERY_ARG_SQL_SELECTION, mSelection)
            putStringArray(ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS, mSelectionArgs)
            putString(ContentResolver.QUERY_ARG_SQL_SORT_ORDER, MediaStaticVariable.SORT_ORDER)
//            putString(ContentResolver.QUERY_ARG_SQL_LIMIT, "10")
        }
    }

    /** 初始化查詢條件 */
    private fun initQuerySelection() {
        mSelection = mMediaParamsBean.getFileSelection(mMediaFolderBean?.bucketIdArray)
        mSelectionArgs = mMediaParamsBean.getFileSelectionArgs(mMediaFolderBean?.bucketIdArray)
    }

    /**
     * 查詢系統數據庫
     * @return  返回數據遊標對象
     */
    private fun getQuery(): Cursor? {
        MediaLog.i(this, "查詢Uri = ${MediaStaticVariable.QUERY_URI}")
        MediaLog.i(this, "文件夾Id = ${mMediaFolderBean?.bucketIdArray}")
        initQuerySelection()
        MediaLog.i(this, "查詢字段 = $mProjection")
        MediaLog.i(this, "查詢條件表達式模版 = $mSelection")
        MediaLog.i(this, "模版值 = ${Arrays.toString(mSelectionArgs)}")
        MediaLog.i(this, "排序 = ${MediaStaticVariable.SORT_ORDER}")
        return if (OsUtils.isVersionQFor10Api29After()) {
            MediaLog.i(this, "獲取查詢數據，R-30或之后=====================>")
            mContext.get()?.applicationContext?.contentResolver?.query(
                MediaStaticVariable.QUERY_URI,
                mProjection,
                getBundle(),
                null
            )
        } else {
            MediaLog.i(this, "獲取查詢數據，R-30之前=====================>")
            ContentResolverCompat.query(
                mContext.get()?.contentResolver,
                MediaStaticVariable.QUERY_URI,
                mProjection,
                mSelection,
                mSelectionArgs,
                MediaStaticVariable.SORT_ORDER,
//                "$mSortOrder LIMIT 10",
                null
            )
        }
    }

    /**
     * 查找傳入的文件夾下的文件列表
     * @param   mediaFolderBean 文件夾對象
     * @return  返回查找文件列表
     */
    fun queryFile(mediaFolderBean: MediaFolderBean): MutableList<MediaFileBean> {
        mMediaFolderBean = mediaFolderBean
        MediaLog.i(this, "解釋查詢數據,mBucketId = $mMediaFolderBean")
        var cursor: Cursor? = null
        try {
            cursor = getQuery()
            cursor?.let { return mapCursor(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return mutableListOf()
    }

    /**
     * 遁環數據遊標，格式化數據，
     * @param   cursor  數據遊標
     * @return  返回格式化數據列表
     */
    private fun mapCursor(cursor: Cursor): MutableList<MediaFileBean> {
        val dataList = getMutableFileBean()
        cursor.apply {
            mColumns.initFileColumn(cursor)
            while (cursor.moveToNext()) {
                val bean = mColumns.getMediaFileBean(cursor, mMediaFolderBean)
                dataList.add(bean)
            }
        }
        MediaLog.i(this, "解釋查詢數據,size = ${dataList.size}")
        return dataList
    }

    /**
     * 判斷是否增加照相機Item
     * @return  返回含有照相機列表
     */
    private fun getMutableFileBean(): MutableList<MediaFileBean> {
        val dataList = mutableListOf<MediaFileBean>()
        if (mMediaParamsBean.isShowTakeCamera) {
            dataList.add(MediaFileBean.getTakePhotoBean())
        }
        return dataList
    }


    /***
     * 根據傳入Uri查找對應數據庫里的數據，再組裝數據
     * @param   uri    查詢Uri
     * @return  返回文件信息對象
     */
    fun getFileBeanForUri(uri: Uri): MediaFileBean? {
        var cursor: Cursor? = null
        try {
            val id = uri.path?.split("/")?.last()
            MediaLog.i(this, "獲取Uri最后分隔數據，其實是對應數據里的文件id = $id")
            // 按Id查詢
            cursor = mContext.get()?.contentResolver?.query(
                MediaStaticVariable.QUERY_URI,
                mProjection,
                "${MediaStaticVariable.ID_COLUMNS}=?",
                arrayOf(id.toString()),
                null
            )
            cursor?.apply {
                moveToFirst()
                mColumns.initFileColumn(cursor)
                return mColumns.getMediaFileBean(cursor, mMediaFolderBean)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return null
    }

}