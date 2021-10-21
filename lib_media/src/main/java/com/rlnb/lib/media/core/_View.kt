package com.rlnb.lib.media.core

import android.view.View
import android.widget.Checkable
import com.rlnb.lib.media.R

/**
 *
 * @author RL
 * @version 2021/9/29
 */


/** 用于记录点击事件在设置时间内只能点击一次时间变量 */
var <T : View> T.extendOnClickCallbackTime: Long
    set(value) = setTag(R.id.lmedia_extend_click_time_id, value)
    get() = getTag(R.id.lmedia_extend_click_time_id) as? Long ?: 0

var View.extendOnClickCallbackData: Any?
    set(value) {
        setTag(R.id.lmedia_extend_click_data_id, value)
    }
    get() {
        return getTag(R.id.lmedia_extend_click_data_id)
    }

/**
 * 设置一定时间内点击事件（防止快速点击时产生的多次点击事件）
 * @param   callback    回调方法
 * @param   time    设置时间内防止点击变量（单位：毫秒）
 */
inline fun <T : View> T.extendOnClickCallback(crossinline callback: (T) -> Unit, time: Long = 800) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - extendOnClickCallbackTime > time) {
            extendOnClickCallbackTime = currentTimeMillis
            callback(this)
        }
    }
}

/**
 * 设置一定时间内点击事件（防止快速点击时产生的多次点击事件）
 * @param   callback    回调方法
 * @param   time    设置时间内防止点击变量（单位：毫秒）
 */
inline fun <T> View.extendOnClickCallbackData(
    data: T,
    time: Long = 800,
    crossinline callback: (T?) -> Unit
) {
    if (hasOnClickListeners()) {
        extendOnClickCallbackData = data
        return
    }
    extendOnClickCallbackData = data
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        val cTime = currentTimeMillis - extendOnClickCallbackTime
        if (cTime > time) {
            extendOnClickCallbackTime = currentTimeMillis
            val tagData = extendOnClickCallbackData
            callback(tagData as? T)
        }
    }
}
