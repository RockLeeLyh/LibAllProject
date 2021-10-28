package com.rlnb.lib.media.util

import com.orhanobut.logger.Logger

/**
 *
 * @author RL
 * @version 2021/9/29
 */
object MediaLog{

    /** 返回模塊 Tag 字符串，不同模塊會重寫這方法返回 */
    fun getTagValue(): String {
        return "MediaLog"
    }

    private fun getMsg(objClass: Any?, msg: String): String {
        return "rl - ${getTagValue()} - ${objClass?.javaClass?.simpleName} ==> $msg"
    }

    fun i(objClass: Any?, msg: String, vararg value: Any?) {
        Logger.i(getMsg(objClass, msg), *value)
    }

    fun e(objClass: Any?, msg: String, vararg value: Any?) {
        Logger.e(getMsg(objClass, msg), *value)
    }

    fun e(objClass: Any?, throwable: Throwable, msg: String, vararg value: Any?) {
        Logger.e(throwable, getMsg(objClass, msg), *value)
    }
}