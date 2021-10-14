package com.rlnb.lib.media.ui

import android.view.View
import com.rlnb.lib.media.R
import com.rlnb.lib.media.core.BaseFragment
import com.rlnb.lib.media.core.bindingLazy
import com.rlnb.lib.media.databinding.LmediaCustomCameraFragmentBinding

class MediaCustomCameraFragment : BaseFragment() {

    private val mVdb by bindingLazy<LmediaCustomCameraFragmentBinding>(R.layout.lmedia_custom_camera_fragment)
    override fun getRootView(): View {
        return mVdb.root
    }


}