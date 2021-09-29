package com.rlnb.lib.media.delegate

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.rlnb.lib.media.R
import com.rlnb.lib.media.bean.MediaParamsBean
import com.rlnb.lib.media.model.MediaFileModel
import com.rlnb.lib.media.model.MediaFolderModel
import com.rlnb.lib.media.select.bean.MediaFolderBean
import com.rlnb.lib.media.ui.MediaFileFragment
import com.rlnb.lib.media.ui.MediaFolderFragment
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
class FragmentDelegate(activity: FragmentActivity, flId: Int, mediaParamsBean: MediaParamsBean) {
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
        TakePhotoUtils(mActivity).apply { mTakePhotoSuccessCallback = { takeCameraSuccess(it) } }
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

    /** 文件夾列表頁面 */
    private val mFolderFragment by lazy {
        MediaFolderFragment(mMediaFolderModel, mMediaParamsBean).apply {
            mClickFolderCallBack = { clickSelectFolderCallback(it) }
            mClickCloseCallBack = { showFolderFragment() }
        }
    }

    /** ViewModel数据对象 */
    private val mFileVm by lazy { FileVm() }

    /** 文件列表頁面 */
    private val mFileFragment by lazy {
        MediaFileFragment(mMediaFileModel, mFileVm).apply {
            mAdapterTakePhotoClickCallback =
                { mCheckCameraPerCallback?.invoke { openSysCamera() } }
            mClickFolderCallback = { showFolderFragment() }
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
        mTakePhotoUtils.openSysCamera()
    }

    /** 系统相机获取成功回调 */
    private fun takeCameraSuccess(uri: Uri) {
        mFileFragment.takeCameraSuccess(uri)
    }

    /**
     *  检查完可以权限后执行初始化
     */
    private fun checkReadExternalPerAndInit() {
        mActivity.lifecycleScope.launch {
            addFragment(mFileFragment)
            mFolderFragment.initData()
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
        if (fragment is MediaFolderFragment) {
            ft.setCustomAnimations(R.anim.lmedia_dialog_top_anim_enter, 0)
            mFileVm.isShowFolderMask.value = true
        }
        ft.add(mFlId, fragment, fragment::class.java.simpleName).commit()
        return false
    }

    /** 显示或隐藏文件夹列表Fragment页面 */
    private fun showFolderFragment() {
        if (addFragment(mFolderFragment)) {
            val ft = mFm.beginTransaction()
            if (mFolderFragment.isVisible) {
                ft.setCustomAnimations(0, R.anim.lmedia_dialog_top_anim_exit)
                mFileVm.isShowFolderMask.value = false
                ft.hide(mFolderFragment)
            } else {
                ft.setCustomAnimations(R.anim.lmedia_dialog_top_anim_enter, 0)
                mFileVm.isShowFolderMask.value = true
                ft.show(mFolderFragment)
            }
            ft.commit()
        }
    }

}