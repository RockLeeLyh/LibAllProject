package com.rlnb.lib.media.delegate

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.rlnb.lib.media.R
import com.rlnb.lib.media.bean.MediaFileBean
import com.rlnb.lib.media.bean.MediaParamsBean
import com.rlnb.lib.media.model.MediaFileModel
import com.rlnb.lib.media.model.MediaFolderModel
import com.rlnb.lib.media.select.bean.MediaFolderBean
import com.rlnb.lib.media.ui.MediaFileFragment
import com.rlnb.lib.media.ui.MediaFolderFragment
import com.rlnb.lib.media.ui.MediaSelectFragment
import com.rlnb.lib.media.ui.vm.FileVm
import com.rlnb.lib.media.util.MediaLog
import com.rlnb.lib.media.util.TakePhotoUtils
import kotlinx.coroutines.launch

/**
 *
 * @author RL
 * @version 2021/9/29
 * @param   activity    当前页面上下文对象
 * @param   flId    Fragment容器Id
 * @param   mediaParamsBean 参数对象
 */
class MediaFragmentDelegate(activity: FragmentActivity, flId: Int, mediaParamsBean: MediaParamsBean) {
    private val mActivity = activity
    private val mFlId = flId
    private val mFm by lazy { activity.supportFragmentManager }

    /** 文件夾里列表數據模型 */
    private val mMediaFileModel by lazy { MediaFileModel(activity, null, mediaParamsBean) }

    /** 文件夾列表數據模型 */
    private val mMediaFolderModel by lazy { MediaFolderModel(activity, mediaParamsBean) }

    /** 設置參數對象 */
    private val mMediaParamsBean = mediaParamsBean

    /** 調用系統相冊 */
    private val mTakePhotoUtils by lazy {
        TakePhotoUtils(mActivity).apply {
            mTakePhotoSuccessCallback = { takeCameraSuccess(it) }
        }
    }

    /** 调用系统相机拍照后回调 onActivityResult  */
    var mTakePhotoActivityResultCallback = { requestCode: Int, resultCode: Int, data: Intent? ->
        mTakePhotoUtils.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * 检查相机权限是否有授权回调方法
     * 1.先调用 [perCallback] 回调方法，检查授权
     * 2.检查成功后再回调 [openCameraCallback] 方法
     */
    var mCheckCameraPerCallback: ((openCameraCallback: () -> Unit) -> Unit)? = null

    /**
     * 检查读取本地文件权限是否有授权回调方法
     * 1.先调用 [perCallback] 回调方法，检查授权
     * 2.检查成功后再回调 [readExternalCallback] 方法
     */
    var mCheckReadExternalPerCallback: ((readExternalCallback: () -> Unit) -> Unit)? = null

    /**
     * 點擊確認按鈕選中文件回調方法
     * @param   [selectListData]    選中文件列表
     */
    var mConfirmBeanCallback: ((selectListData: List<MediaFileBean>) -> Unit)? = null
    var mConfirmUriCallback: ((selectListData: List<Uri>) -> Unit)? = null

    /** 关闭回调方法 */
    var mCloseCallback: (() -> Unit)? = null

    /** 選擇類型頁面 */
    private val mSelectFragment by lazy {
        MediaSelectFragment(mMediaFileModel).apply {
            mCancelCallback = { selectCancelCallback() }
            mAlbumOpenCallback = { selectAlbumOpenCallback() }
            mCameraOpenCallback = { selectCameraOpenCallback() }
            mClickConfirmUriCallback = { confirmUriCallback(it) }
            mClickConfirmBeanCallback = { confirmBeanCallback(it) }
        }
    }

    /** 文件夾列表頁面 */
    private val mFolderFragment by lazy {
        MediaFolderFragment(mMediaFolderModel).apply {
            mClickFolderCallBack = { clickSelectFolderCallback(it) }
            mClickCloseCallBack = { showFolderFragment() }
        }
    }

    /** ViewModel数据对象 */
    private val mFileVm by lazy { FileVm() }

    private var isInitFileFragment = false

    /** 文件列表頁面 */
    private val mFileFragment by lazy {
        MediaFileFragment(mMediaFileModel, mFileVm).apply {
            mAdapterTakePhotoClickCallback = { openSysCamera() }
            mClickFolderCallback = { showFolderFragment() }
            mClickConfirmUriCallback = { confirmUriCallback(it) }
            mClickConfirmBeanCallback = { confirmBeanCallback(it) }
            mClickCLoseCallback = { clickCloseFileCallback() }
            isInitFileFragment = true
        }
    }


    /** 初始化方法 */
    fun init() {
        mCheckReadExternalPerCallback?.invoke {
            checkReadExternalPerAndInit()
        }
    }


    /** 打开系统相机 */
    private fun openSysCamera() {
        mCheckCameraPerCallback?.invoke { mTakePhotoUtils.openSysCamera() }
    }

    /** 系统相机获取成功回调 */
    private fun takeCameraSuccess(uri: Uri) {
        if (isInitFileFragment && mFileFragment.isVisible) {
            mFileFragment.takeCameraSuccess(uri)
        } else {
            mSelectFragment.takeCameraSuccess(uri)
        }
    }

    /**
     * 點擊確認按鈕回調方法,Uri格式
     * @param   selectListData  選中文件列表
     */
    private fun confirmUriCallback(selectListData: List<Uri>) {
        mConfirmUriCallback?.invoke(selectListData)

        checkConfirmBeforeClose()
    }

    /**
     * 點擊確認按鈕回調方法,Bean格式
     * @param   selectListData  選中文件列表
     */
    private fun confirmBeanCallback(selectListData: List<MediaFileBean>) {
        mConfirmBeanCallback?.invoke(selectListData)
        checkConfirmBeforeClose()
    }

    private fun checkConfirmBeforeClose() {
        hideFragment(mFolderFragment)
        hideFragment(mFileFragment)
        hideFragment(mSelectFragment)
        if (mMediaParamsBean.isConfirmBeforeClose) {
            mCloseCallback?.invoke()
        }
    }

    /**
     *  检查完可以权限后执行初始化
     */
    private fun checkReadExternalPerAndInit() {
        mActivity.lifecycleScope.launch {
            if (mMediaParamsBean.isSelectFirstShowType) {
                showOrHide(mSelectFragment)
            } else {
                selectAlbumOpenCallback()
            }
        }
    }

    /**
     * 文件夹列表Item点击回调方法
     * @param   folderBean  文件夹信息对象
     */
    private fun clickSelectFolderCallback(folderBean: MediaFolderBean) {
        MediaLog.i(this, "點擊目錄 = $folderBean")
        mFileVm.selectBtnValue.set(folderBean.name)
        mFileFragment.refresh(folderBean)
        if (isAddFolderFragment()) {
            showFolderFragment()
        }
    }


    /**
     * 判断是否已经添加文件夹列表Fragment页面
     * @return  true - 未添加
     */
    private fun isAddFolderFragment(): Boolean {
        return mFm.findFragmentByTag(mFolderFragment::class.java.simpleName) != null
    }

    /** 类型选择弹出框取消按钮回调 */
    private fun selectCancelCallback() {
        showOrHide(mSelectFragment)
        mCloseCallback?.invoke()
    }

    private fun showFolderFragment() {
        showOrHide(mFolderFragment)
    }

    private fun showOrHide(fragment: Fragment) {
        showOrHide(fragment, fragment.isVisible)
    }

    /** 显示或隐藏文件夹列表Fragment页面 */
    private fun showOrHide(fragment: Fragment, isVisible: Boolean) {
        if (addFragment(fragment)) {
            val ft = mFm.beginTransaction()
            when (fragment) {
                is MediaSelectFragment -> getSelectAnimResId(ft, isVisible)
                is MediaFolderFragment -> {
                    getFolderAnimResId(ft, isVisible)
                    mFileVm.isShowFolderMask.value = !isVisible
                }
                is MediaFileFragment -> getFileAnimResId(ft, isVisible)
                else -> 0
            }
            if (isVisible) {
                ft.hide(fragment)
            } else {
                ft.show(fragment)
            }
            ft.commit()
        }
    }

    private fun hideFragment(fragment: Fragment) {
        if (!fragment.isVisible) {
            return
        }
        showOrHide(fragment, true)
    }

    /** 打开相册按钮回调方法 */
    private fun selectAlbumOpenCallback() {
        showOrHide(mFileFragment)
        mFolderFragment.initData()
    }

    /** 打开相机按钮回调方法 */
    private fun selectCameraOpenCallback() {
        openSysCamera()
    }

    /**
     * 添加传入Fragment页面
     * @param   fragment    文件夹或文件列表Fragment页面
     * @return  true - 已添加过，false - 未添加过
     */
    private fun addFragment(fragment: Fragment): Boolean {
        val ft = mFm.beginTransaction()
        if (mFm.findFragmentByTag(fragment::class.java.simpleName) != null) {
            return true
        }
        when (fragment) {
            is MediaSelectFragment -> ft.setCustomAnimations(R.anim.lmedia_dialog_bottom_anim_enter, 0)
            is MediaFolderFragment -> {
                ft.setCustomAnimations(R.anim.lmedia_dialog_top_anim_enter, 0)
                mFileVm.isShowFolderMask.value = true
            }
            is MediaFileFragment -> ft.setCustomAnimations(R.anim.lmedia_dialog_center_anim_enter, 0)
        }

        ft.add(mFlId, fragment, fragment::class.java.simpleName).commit()
        return false
    }


    private fun clickCloseFileCallback() {
        showOrHide(mFileFragment)
        hideFragment(mFolderFragment)
        if (!mMediaParamsBean.isSelectFirstShowType) {
            mCloseCallback?.invoke()
        }
    }


    private fun getSelectAnimResId(ft: FragmentTransaction, isVisible: Boolean) {
        if (isVisible) {
            ft.setCustomAnimations(0, R.anim.lmedia_dialog_bottom_anim_exit)
        } else {
            ft.setCustomAnimations(R.anim.lmedia_dialog_bottom_anim_enter, 0)
        }
    }

    private fun getFolderAnimResId(ft: FragmentTransaction, isVisible: Boolean) {
        if (isVisible) {
            ft.setCustomAnimations(0, R.anim.lmedia_dialog_top_anim_exit)
        } else {
            ft.setCustomAnimations(R.anim.lmedia_dialog_top_anim_enter, 0)
        }
    }

    private fun getFileAnimResId(ft: FragmentTransaction, isVisible: Boolean) {
        if (isVisible) {
            ft.setCustomAnimations(0, R.anim.lmedia_dialog_center_anim_exit)
        } else {
            ft.setCustomAnimations(R.anim.lmedia_dialog_center_anim_enter, 0)
        }
    }

    /**
     * @return  返回新建并插入数据的文件Uri
     */
    fun createImageUri(): Uri? {
        return mTakePhotoUtils.createImageUri()
    }

    /**
     * 删除传入的Uri文件
     * @param   uri
     */
    fun delUri(uri: Uri?) {
        mTakePhotoUtils.delUri(uri)
    }
}