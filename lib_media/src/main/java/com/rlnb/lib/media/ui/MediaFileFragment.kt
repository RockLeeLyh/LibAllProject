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
import com.rlnb.lib.media.util.MediaLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

/**
 * 文件列表页面
 * @author RL
 * @version 2021/9/29
 */
class MediaFileFragment(mediaFileModel: MediaFileModel, fileVm: FileVm) : BaseFragment() {

    private val mVdb by bindingLazy<LmediaFileFragmentBinding>(R.layout.lmedia_file_fragment)
    private val mFileVm = fileVm
    private val mMediaParamsBean by lazy { mediaFileModel.mMediaParamsBean }
    private var mMediaFileModel = mediaFileModel
    private var mFolderFolderBean: MediaFolderBean? = null

    private var mLayoutManager: GridLayoutManager? = null
    private val mAdapter by lazy {
        MediaAdapterFile(mMediaParamsBean).apply {
            mClickSelectChangeFileCallback = { beanMap -> clickImageCallback(beanMap) }
            mAdapterTakePhotoClick = mAdapterTakePhotoClickCallback
        }
    }

    /** 點擊調用系統相機Item回調方法 */
    var mAdapterTakePhotoClickCallback: ((bean: MediaFileBean) -> Unit)? = null

    /** 點擊切換文件夾按鈕回調方法 */
    var mClickFolderCallback: (() -> Unit)? = null

    /** 關掉按鈕點擊事件回調方法 */
    var mClickCLoseCallback: (() -> Unit)? = null

    /** 點擊確認按鈕按鈕回調方法,返回[MediaFileBean]格式 */
    var mClickConfirmBeanCallback: ((selectListData: List<MediaFileBean>) -> Unit)? = null

    /** 點擊確認按鈕按鈕回調方法,返回Uri格式 */
    var mClickConfirmUriCallback: ((selectListData: List<Uri>) -> Unit)? = null

    override fun getRootView(): View {
        mVdb.vmFile = mFileVm
        return mVdb.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFileVm.isConfirmBtnShow.set(!mMediaParamsBean.isChooseToJumpNowTrue())
        setShowFolderMaskObserve()
        mFileVm.selectMaxFileSize.set(mMediaParamsBean.maxSelectable)
        switchChangeUi(0)
        mVdb.btnFolder.extendOnClickCallback({ mClickFolderCallback?.invoke() })
        mVdb.btnConfirm.extendOnClickCallback({ clickConfirm() })
        mVdb.ibClose.extendOnClickCallback({ mClickCLoseCallback?.invoke() })
    }

    override fun onResume() {
        super.onResume()
        initAdapter()
    }

    /** 初始化綁定適配器 */
    private fun initAdapter() {
        if (mLayoutManager == null) {
            orientationBasedUI(resources.configuration.orientation)
            mAdapter.apply {
                mVdb.rvFile.layoutManager = mLayoutManager
                mVdb.rvFile.adapter = this
            }
        }
    }

    /**
     * 初始化設置列表Item大小
     */
    private fun checkOrientionUi(width: Int, gridSpanCount: Int) {
        mAdapter.mSize = width
        if (mLayoutManager == null) {
            mLayoutManager = GridLayoutManager(context, gridSpanCount)
        }
    }


    /***
     * 傳入文件夾對象，刷新加載里面列表
     * @param   folderBean  文件夾對象
     */
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
                    delay(250)
                    mFileVm.isLoadingShow.set(false)
                    setAdapterData(dataList)
                }
            }
        }
    }

    /**
     * 判斷當前文件夾數據對象與傳入對象是否一致
     * @param   folderBean  文件夾數據對象
     * @return  true - 一致
     */
    private fun checkFolderSame(folderBean: MediaFolderBean): Boolean {
        return if (mFolderFolderBean == null) {
            return false
        } else {
            mFolderFolderBean?.position == folderBean.position
        }
    }

    /**
     * 設置列表數據
     * @param   dataList    數據
     */
    private fun setAdapterData(dataList: MutableList<MediaFileBean>?) {
        dataList?.let {
            mAdapter.apply {
                mListData = it
                notifyDataSetChanged()
            }
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

    /**
     * 點擊選擇或取消時，選中列表數據發生改變時回調方法
     * @param   beanMap 選中文件數據對象
     */
    private fun clickImageCallback(beanMap: MutableMap<Long, MediaFileBean>) {
        val size = beanMap.size
        switchChangeUi(size)

        if (mMediaParamsBean.isChooseToJumpNowTrue()) {
            MediaLog.i(this, "立即跳轉 =======>")
            clickConfirm()
        }

    }

    /**
     * 選擇文件變化時,更新UI,回調方法
     * @param   beanMap     選擇的列表
     * @param   size 選中個數
     */
    private fun switchChangeUi(size: Int) {
        mFileVm.isConfirmEnabled.set(size > 0)
        mFileVm.selectFileSize.set(size)
    }

    /** 點擊確認按鈕回調方法 */
    private fun clickConfirm() {
        if (mClickConfirmUriCallback != null) {
            val uriDataList = mAdapter.mSwitchBeanMap.map { it.value.uri }
            mClickConfirmUriCallback?.invoke(uriDataList)
        }
        if (mClickConfirmBeanCallback != null) {
            val beanDataList = mAdapter.mSwitchBeanMap.map { it.value }
            mClickConfirmBeanCallback?.invoke(beanDataList)
        }
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
        mFileVm.isShowFolderMask.observe(this) {
            setDropDownAnimator(it)
        }
    }

    /**
     * 顯示或隱藏下拉文件夾彈出框動畫
     * @param   isShow  true - 顯示
     */
    private fun setDropDownAnimator(isShow: Boolean) {
        setIsShowFolderMask(isShow)
        mVdb.ivDropDown.apply {
            if (isShow) {
                ObjectAnimator.ofFloat(this, View.ROTATION, 0F, 180F).setDuration(500).start()
            } else {
                ObjectAnimator.ofFloat(this, View.ROTATION, 180F, 360F).setDuration(500).start()
            }
        }
    }

    /**
     * 顯示或隱藏下拉文件夾時底部的蒙板彈出框動畫
     * @param   isShow  true - 顯示
     */
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