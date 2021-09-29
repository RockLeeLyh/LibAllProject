package com.rlnb.lib.media.util

import android.os.Build

/**
 *
 * @author RL
 * @version 2021/9/29
 */
object OsUtils {

    /**
     * 判斷當前系統版本，代號 = Q ,版本 = 10， Api = 29，為之前的版本
     * @return  true - 當前版本比 Q 小
     */
    fun isVersionQFor10Api29Before(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.Q
    }

    /**
     * 判斷當前系統版本，代號 = Q ,版本 = 10， Api = 29，為當前
     * @return  true - 當前版本與 Q 一樣
     */
    fun isVersionQFor10Api29Same(): Boolean {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.Q
    }

    /**
     * 判斷當前系統版本，代號 = Q ,版本 = 10， Api = 29，為之后的版本
     * @return  true - 當前版本比 Q 大
     */
    fun isVersionQFor10Api29After(): Boolean {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.Q
    }

    fun isVersionNFor7Api24Before():Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.N
    }
}