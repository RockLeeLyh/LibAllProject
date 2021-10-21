package com.rlnb.lib.media.core

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 *
 * @author RL
 * @version 2021/9/29
 */
abstract class BaseFragment: Fragment() {

    abstract fun getRootView(): View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getRootView()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        orientationBasedUI(newConfig.orientation)
    }

    open fun orientationBasedUI(orientation: Int) {}

}


inline fun <reified T : ViewDataBinding> Fragment.bindingLazy(@LayoutRes resId: Int): Lazy<T> =
    lazy { DataBindingUtil.inflate<T>(layoutInflater, resId, null, false) }