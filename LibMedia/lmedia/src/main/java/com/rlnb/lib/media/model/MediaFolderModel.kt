package com.rlnb.lib.media.model

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import androidx.core.content.ContentResolverCompat
import com.rlnb.lib.media.bean.MediaParamsBean
import com.rlnb.lib.media.select.bean.MediaFolderBean
import com.rlnb.lib.media.util.MediaLog
import com.rlnb.lib.media.util.MediaStaticVariable
import com.rlnb.lib.media.util.OsUtils
import java.lang.ref.WeakReference
import java.util.*

/**
 * 文件夾列表數據模型
 * @author RL
 * @version 2021/9/29
 */
class MediaFolderModel(context: Context, mediaParamsBean: MediaParamsBean) {
    private val mContext: WeakReference<Context> by lazy { WeakReference(context) }

    /** 查詢行的數據下標對象 */
    private val mColumns by lazy { MediaColumnIndex() }

    /** 查詢字段 */
    private val mProjection by lazy {
        mutableListOf<String>().apply {
            when {
                OsUtils.isVersionQFor10Api29Before() -> add("COUNT(${MediaStaticVariable.BUCKET_ID_COLUMNS}) AS ${MediaStaticVariable.COUNT_COLUMNS}")
                OsUtils.isVersionQFor10Api29After() -> add("COUNT(${MediaStaticVariable.BUCKET_ID_COLUMNS})")
            }
            add(MediaStaticVariable.ID_COLUMNS)
            add(MediaStaticVariable.BUCKET_ID_COLUMNS)
            add(MediaStaticVariable.BUCKET_DISPLAY_NAME_COLUMNS)
            add(MediaStaticVariable.SIZE_COLUMNS)
            add(MediaStaticVariable.MIME_TYPE_COLUMNS)
        }.toTypedArray()
    }

    /** 查詢條件表達式模版 */
    private val mSelection by lazy { mediaParamsBean.getFolderSelection() }

    /** 查詢條件表達式模版傳值 */
    private val mSelectionArgs by lazy { mediaParamsBean.getFolderSelectionArgs() }

    /**
     * Api 30 后支持的查詢條件Bundle對象
     * @return  返回查詢條件Bundle對象
     */
    private val mBundle by lazy {
        Bundle().apply {
            putString(ContentResolver.QUERY_ARG_SQL_SELECTION, mSelection)
            putStringArray(ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS, mSelectionArgs)
            if (OsUtils.isVersionQFor10Api29After()) {
                putString(ContentResolver.QUERY_ARG_SQL_GROUP_BY, MediaStaticVariable.BUCKET_ID_COLUMNS)
            }
            putString(ContentResolver.QUERY_ARG_SQL_SORT_ORDER, MediaStaticVariable.SORT_ORDER)
        }
    }

    /**
     * 查詢系統數據庫
     * @return  返回數據遊標對象
     */
    private fun getQuery(): Cursor? {
        MediaLog.i(this, "查詢Uri = ${MediaStaticVariable.QUERY_URI}")
        MediaLog.i(this, "查詢字段 = $mProjection")
        MediaLog.i(this, "查詢條件表達式模版 = $mSelection")
        MediaLog.i(this, "模版值 = ${Arrays.toString(mSelectionArgs)}")
        MediaLog.i(this, "排序 = ${MediaStaticVariable.SORT_ORDER}")
        return if (OsUtils.isVersionQFor10Api29After()) {
            MediaLog.i(this, "獲取查詢數據，R-30或之后=====================>")
            mContext.get()?.applicationContext?.contentResolver?.query(
                MediaStaticVariable.QUERY_URI,
                mProjection,
                mBundle,
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
                null
            )
        }
    }

    /** @return  返回查找文件列表 */
    fun queryFolder(): MutableList<MediaFolderBean> {
        var cursor: Cursor? = null
        try {
            cursor = getQuery()
            cursor?.let { it ->
                //
                val mapName = when {
                    OsUtils.isVersionQFor10Api29Before() -> mapCursorVersionQFor10Api29Before(it)
                    OsUtils.isVersionQFor10Api29Same() -> mapCursorVersionQFor10Api29Same(it)
                    else -> mapCursorVersionQFor10Api29After(it)
                }.groupBy { it.name }

                return filterSameAndMergeFolder(mapName)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return mutableListOf()
    }

    /**
     * Api 29之前的查詢數據解釋組裝
     * 因為可以使用 GROUP UP 查詢，所以解釋方式與 Api29之前一樣
     * @param   cursor 數據遊標
     * @return  返回解釋后的文件夾列表
     */
    private fun mapCursorVersionQFor10Api29Before(cursor: Cursor): MutableList<MediaFolderBean> {
        MediaLog.i(this, "解釋查詢數據，Q-29之前=====================>")
        return mapCursorForGroupBy(cursor, true)
    }

    /**
     * Api 29之后的查詢數據解釋組裝
     * 因為可以使用 GROUP UP 查詢，所以解釋方式與 Api29之后一樣
     * @param   cursor 數據遊標
     * @return  返回解釋后的文件夾列表
     */
    private fun mapCursorVersionQFor10Api29After(cursor: Cursor): MutableList<MediaFolderBean> {
        MediaLog.i(this, "解釋查詢數據，Q-29之后=====================>")
        return mapCursorForGroupBy(cursor, false)
    }

    /**
     * Api 29之前或之后的查詢數據解釋
     * @param   cursor  數據遊標
     * @param   isCountColumn   區分獲取分組總和時的方式，
     *                          之前時 true：使用 count 下標
     *                          之后時 false：使用固定下標 0 獲取
     */
    private fun mapCursorForGroupBy(cursor: Cursor, isCountColumn: Boolean): MutableList<MediaFolderBean> {
        val dataList = mutableListOf<MediaFolderBean>()
        cursor.apply {
            mColumns.initFolderColumn(cursor, isCountColumn)
            while (cursor.moveToNext()) {
                val bean = mColumns.getMediaFolderGroupByBean(cursor)
                dataList.add(bean)
            }
        }
        return dataList
    }

    /**
     * 過濾合并文件夾名稱相同的的列表
     * @param   mapData 名稱作為Key的列表數據
     * @return  返回合并后的數據列表
     */
    private fun filterSameAndMergeFolder(mapData: Map<String, List<MediaFolderBean>>): MutableList<MediaFolderBean> {
        val dataList = mutableListOf<MediaFolderBean>()
        var totalAll = 0
        mapData.forEach { keyList ->
            val keyBeanList = keyList.value
            val firstBean = keyBeanList[0]
            var size = 0
            keyBeanList.forEach { bean ->
                firstBean.bucketIdArray.add(bean.bucketId)
                size += bean.total
            }
            firstBean.total = size
            totalAll += size
            dataList.add(firstBean)
        }
        if (dataList.isNotEmpty()) {
            dataList.add(0, MediaFolderBean.getAllFolderBean(dataList[0].uri, totalAll))
        }
        return dataList
    }

    /**
     * 當前 Api 29 的查詢數據解釋組裝
     * 因為不可以使用 GROUP UP 查詢，所以需要最底效率來組裝
     * @param   cursor 數據遊標
     * @return  返回解釋后的文件夾列表
     */
    private fun mapCursorVersionQFor10Api29Same(cursor: Cursor): MutableList<MediaFolderBean> {
        MediaLog.i(this, "解釋查詢數據，當前為Q-29=====================>")
        val dataList = mutableListOf<MediaFolderBean>()
        cursor.apply {
            mColumns.initFolderColumn(cursor, false)
            val beanMap = mutableMapOf<Long, MediaFolderBean>()
            while (cursor.moveToNext()) {
                val bucketId = getLong(mColumns.mBucketIdColumn)
                if (!beanMap.containsKey(bucketId)) {
                    val bean = mColumns.getMediaFolderBean(cursor)
                    beanMap[bucketId] = bean
                } else {
                    beanMap[bucketId]?.plusTotal()
                }
            }
            beanMap.forEach { dataList.add(it.value) }
        }

        return dataList
    }

}