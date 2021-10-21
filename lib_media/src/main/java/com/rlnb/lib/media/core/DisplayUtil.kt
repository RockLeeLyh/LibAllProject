package com.rlnb.lib.media.core

import android.content.Context

/**
 *
 * @author RL
 * @version 2021/9/29
 */

object DisplayUtil {

    fun getDisplayWidthPixels(context: Context?): Int {
        return context?.resources?.displayMetrics?.widthPixels ?: 0
    }
}