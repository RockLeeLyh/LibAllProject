package com.rlnb.lib.media.ui

import android.animation.ObjectAnimator
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.rlnb.lib.media.R
import com.rlnb.lib.media.adapter.MediaAdapterFile
import com.rlnb.lib.media.bean.MediaFileBean
import com.rlnb.lib.media.bean.MediaParamsBean
import com.rlnb.lib.media.core.BaseFragment
import com.rlnb.lib.media.core.DisplayUtil
import com.rlnb.lib.media.core.bindingLazy
import com.rlnb.lib.media.core.extendOnClickCallback
import com.rlnb.lib.media.databinding.LmediaFileFragmentBinding
import com.rlnb.lib.media.model.MediaFileModel
import com.rlnb.lib.media.select.bean.MediaFolderBean
import com.rlnb.lib.media.ui.vm.FileVm
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

/**
 * 文件列表页面
 * @author RL
 * @version 2021/9/29
 */
class MediaFileFragment(mediaFileModel: MediaFileModel,fileVm:FileVm) : BaseFragment() {

    private val mVdb by bindingLazy<LmediaFileFragmentBinding>(R.layout.lmedia_file_fragment)
    private val mFileVm = fileVm
    private val mMediaParamsBean by lazy {mediaFileModel.mMediaParamsBean}
    private var mMediaFileModel = mediaFileModel
    private var mFolderFolderBean: MediaFolderBean? = null

    private var mLayoutManager: GridLayoutManager? = null
    private val mAdapter by lazy {
        MediaAdapterFile(mMediaParamsBean).apply {
            mClickSelectChangeFileCallback = { beanMap -> clickImageCallback(beanMap) }
            mAdapterTakePhotoClick = mAdapterTakePhotoClickCallback
        }
    }

    var mAdapterTakePhotoClickCallback: ((bean: MediaFileBean) -> Unit)? = null

    /** 點擊切換文件夾按鈕回調方法 */
    var mClickFolderCallback: (() -> Unit)? = null

    /** 點擊確認按鈕按鈕回調方法 */
    var mClickConfirmCallback: (() -> Unit)? = null

    override fun getRootView(): View {
        mVdb.vmFile = mFileVm
        return mVdb.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setShowFolderMaskObserve()
        mVdb.btnFolder.extendOnClickCallback({ mClickFolderCallback?.invoke() })
        mVdb.btnConfirm.extendOnClickCallback({ mClickConfirmCallback?.invoke() })
        setSwitchSizeValue(0)
    }

    fun refresh(folderBean: MediaFolderBean) {
        if (checkFolderSame(folderBean)) {
            return
        }
        mFolderFolderBean = folderBean
        lifecycleScope.launch {
            mAdapter.apply {
                mFileVm.isLoadingShow.set(true)
                if (isDataEmpty()) {
                    mListData?.clear()
                    notifyDataSetChanged()
                }
                mFolderFolderBean?.let {
                    val dataList = mMediaFileModel.queryFile(it)
                    delay(500)
                    mFileVm.isLoadingShow.set(false)
                    setAdapterData(dataList)
                }
            }
        }
    }


    private fun setAdapterData(dataList: MutableList<MediaFileBean>?) {
        dataList?.let {
            mAdapter.apply {
                mListData = it
                notifyDataSetChanged()
            }

        }
    }

    override fun onResume() {
        super.onResume()
        initAdapter()
    }

    private fun initAdapter() {
        if(mLayoutManager == null) {
            orientationBasedUI(resources.configuration.orientation)
            mAdapter.apply {
                mVdb.rvFile.layoutManager = mLayoutManager
                mVdb.rvFile.adapter = this
            }
        }
    }

    private fun checkOrientionUi(width: Int, gridSpanCount: Int) {
        mAdapter.mSize = width
        if (mLayoutManager == null) {
            mLayoutManager = GridLayoutManager(context, gridSpanCount)
        }
    }

    override fun orientationBasedUI(orientation: Int) {
        super.orientationBasedUI(orientation)
        val widthPixels = DisplayUtil.getDisplayWidthPixels(context)
        val gridSpanCountW = mMediaParamsBean.gridSpanCount
        val gridSpanCountH = mMediaParamsBean.gridSpanCount + 2
        val width =
            if (orientation == Configuration.ORIENTATION_PORTRAIT) widthPixels / gridSpanCountW else widthPixels / gridSpanCountH
        val spanCount = if (orientation == Configuration.ORIENTATION_PORTRAIT) gridSpanCountW else gridSpanCountH
        checkOrientionUi(width, spanCount)
    }

    private fun clickImageCallback(beanMap: MutableMap<Long, MediaFileBean>) {
        val size = beanMap.size
        switchChangeCallback(beanMap, size)
        setSwitchSizeValue(size)
    }


    private fun checkFolderSame(folderBean: MediaFolderBean): Boolean {
        return if (mFolderFolderBean == null) {
            return false
        } else {
            mFolderFolderBean?.position == folderBean.position
        }
    }


    /**
     * 選擇文件變化時回調方法
     * @param   beanMap     選擇的列表
     * @param   size 選中個數
     */
    private fun switchChangeCallback(beanMap: MutableMap<Long, MediaFileBean>, size: Int) {
        mVdb.btnConfirm.isEnabled = size > 0
    }

    fun getSelectBean(): List<MediaFileBean> {
        val selectBean = mutableListOf<MediaFileBean>()
        mAdapter.mSwitchBeanMap.forEach {
            selectBean.add(it.value)
        }
        return selectBean.toList()
    }

    private fun setSwitchSizeValue(size: Int) {
        mVdb.btnSwitchSize.text = getString(R.string.lmedia_switch_btn, size, mMediaParamsBean.maxSelectable)
    }

    /**
     * 照相成功回调方法
     * @param   uri 文件Uri
     */
    fun takeCameraSuccess(uri: Uri) {
        mMediaFileModel.getFileBeanForUri(uri)?.let {
            mAdapter?.addTakePhotoBean(it)
        }
    }

    /** 监听是否弹出文件夹页面 */
    private fun setShowFolderMaskObserve() {
        mFileVm.isShowFolderMask.observe(this){
            setDropDownAnimator(it)
        }
    }


    private fun setDropDownAnimator(isShow: Boolean) {
        setIsShowFolderMask(isShow)
        if (isShow) {
            ObjectAnimator.ofFloat(mVdb.ivDropDown, View.ROTATION, 0F, 180F).setDuration(500).start()
        } else {
            ObjectAnimator.ofFloat(mVdb.ivDropDown, View.ROTATION, 180F, 360F).setDuration(500).start()
        }
    }

    private fun setIsShowFolderMask(isShow: Boolean) {
        mVdb.vFolderMask?.let {
            it.visibility = if (isShow) View.VISIBLE else View.GONE
            if (isShow) {
                ObjectAnimator.ofFloat(it, View.ALPHA, 0F, 1F).setDuration(500).start()
            } else {
                ObjectAnimator.ofFloat(it, View.ALPHA, 1F, 0F).setDuration(500).start()
            }
        }
    }
}