package com.rlnb.lib.media.adapter

import androidx.databinding.ViewDataBinding

/**
 *
 * @author RL
 * @version 2021/9/29
 */
interface IRvOnBindLast<T : Any, D : ViewDataBinding> {
    fun onBindLast(vdb: D, bean: T)
}