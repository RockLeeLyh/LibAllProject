package com.rlnb.lib.media.ui

import android.view.View
import com.rlnb.lib.media.R
import com.rlnb.lib.media.core.BaseFragment
import com.rlnb.lib.media.core.bindingLazy
import com.rlnb.lib.media.databinding.LmediaPreviewFragmentBinding

class MediaPreviewFragment: BaseFragment() {

    private val mVdb by bindingLazy<LmediaPreviewFragmentBinding>(R.layout.lmedia_preview_fragment)

    override fun getRootView(): View {
        return mVdb.root
    }
}