package com.rlnb.lib.core.logger

import com.orhanobut.logger.Logger

/**
 * @author RL
 * @date 2021/10/19 15:23
 * @description 打印日志工具类
 */
abstract class LCoreLog {

    /** 返回模塊 Tag 字符串，不同模塊會重寫這方法返回 */
    open fun getTagValue(): String {
        return "RlLog"
    }

    fun getMsg(objClass: Any?, msg: String): String {
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