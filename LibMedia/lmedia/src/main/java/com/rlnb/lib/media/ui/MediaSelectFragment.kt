package com.rlnb.lib.media.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import com.rlnb.lib.media.R
import com.rlnb.lib.media.bean.MediaFileBean
import com.rlnb.lib.media.core.BaseFragment
import com.rlnb.lib.media.core.bindingLazy
import com.rlnb.lib.media.core.extendOnClickCallback
import com.rlnb.lib.media.databinding.LmediaSelectFragmentBinding
import com.rlnb.lib.media.model.MediaFileModel

class MediaSelectFragment(
    mediaFileModel: MediaFileModel
) : BaseFragment() {

    private val mMediaFileModel = mediaFileModel
    private val mMediaParamsBean by lazy {mediaFileModel.mMediaParamsBean}

    private val mVdb by bindingLazy<LmediaSelectFragmentBinding>(R.layout.lmedia_select_fragment)

    var mCancelCallback: (() -> Unit)? = null
    var mAlbumOpenCallback:(()->Unit)? = null
    var mCameraOpenCallback:(()->Unit)? = null

    /** 點擊確認按鈕按鈕回調方法,返回[MediaFileBean]格式 */
    var mClickConfirmBeanCallback: ((selectListData: List<MediaFileBean>) -> Unit)? = null

    /** 點擊確認按鈕按鈕回調方法,返回Uri格式 */
    var mClickConfirmUriCallback: ((selectListData: List<Uri>) -> Unit)? = null


    override fun getRootView(): View {
        return mVdb.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mVdb.btnCancel.extendOnClickCallback({ mCancelCallback?.invoke() })
        mVdb.btnMask.extendOnClickCallback({
            if (mMediaParamsBean.isSelectMaskClick) {
                mCancelCallback?.invoke()
            }
        })
        mVdb.btnAlbum.extendOnClickCallback({
            mAlbumOpenCallback?.invoke()
        })

        mVdb.btnCamera.extendOnClickCallback({
            mCameraOpenCallback?.invoke()
        })
    }

    /**
     * 照相成功回调方法
     * @param   uri 文件Uri
     */
    fun takeCameraSuccess(uri: Uri) {
        if (mClickConfirmUriCallback != null) {
            mClickConfirmUriCallback?.invoke(mutableListOf(uri))
        }
        if (mClickConfirmBeanCallback != null) {
            mMediaFileModel.getFileBeanForUri(uri)?.let {
                mClickConfirmBeanCallback?.invoke(mutableListOf(it))
            }
        }
    }



}