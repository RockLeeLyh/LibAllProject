package com.rlnb.lib.media.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.rlnb.lib.media.R
import com.rlnb.lib.media.adapter.MediaAdapterFolder
import com.rlnb.lib.media.bean.MediaParamsBean
import com.rlnb.lib.media.core.BaseFragment
import com.rlnb.lib.media.core.bindingLazy
import com.rlnb.lib.media.core.extendOnClickCallback
import com.rlnb.lib.media.databinding.LmediaFolderFragmentBinding
import com.rlnb.lib.media.model.MediaFolderModel
import com.rlnb.lib.media.select.bean.MediaFolderBean

/**
 *
 * @author RL
 * @version 2021/9/29
 */
class MediaFolderFragment(mediaFolderModel: MediaFolderModel) :
    BaseFragment() {
    private val mVdb by bindingLazy<LmediaFolderFragmentBinding>(R.layout.lmedia_folder_fragment)

    private val mMediaFolderModel = mediaFolderModel

    private val mAdapter by lazy {
        MediaAdapterFolder().apply {
            mClickCallback = { bean -> mClickFolderCallBack?.invoke(bean) }
        }
    }

    /** 點擊空白區域回調方法 */
    var mClickCloseCallBack: (() -> Unit)? = null

    /** 獲取數據為空時回調方法 */
    val mGetDataEmptyCallback: (() -> Unit)? = null

    /** 點擊列表回調方法 */
    var mClickFolderCallBack: ((folderBean: MediaFolderBean) -> Unit)? = null

    private var isFirstInitData = false

    override fun getRootView(): View {
        return mVdb.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mVdb.btnClose.extendOnClickCallback({ mClickCloseCallBack?.invoke() })
    }


    override fun onResume() {
        super.onResume()
        setAdapter()
    }

    private fun setAdapter() {
        if (mVdb.rvFolder.adapter == null) {
            mVdb.rvFolder.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = mAdapter
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    /** 初始化目录文件夹 */
    fun initData() {
        if(isFirstInitData) {
            return
        }
        isFirstInitData = true
        val dataList = mMediaFolderModel.queryFolder()
        if (dataList.isNullOrEmpty()) {
            mGetDataEmptyCallback?.invoke()
        } else {
            mClickFolderCallBack?.invoke(dataList[0])
            mAdapter.mListData = dataList
        }
    }


}