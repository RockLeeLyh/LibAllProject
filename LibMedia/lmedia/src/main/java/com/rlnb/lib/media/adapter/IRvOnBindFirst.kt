package com.rlnb.lib.media.adapter

import androidx.databinding.ViewDataBinding

/**
 *
 * @author RL
 * @version 2021/9/29
 */
interface IRvOnBindFirst<T : Any, D : ViewDataBinding> {
    fun onBindFirst(vdb: D, bean: T)
}