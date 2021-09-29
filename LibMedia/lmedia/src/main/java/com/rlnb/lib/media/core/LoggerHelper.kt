package com.rlnb.lib.media.core

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.rlnb.lib.media.BuildConfig

/**
 *
 * @author RL
 * @version 2021/9/29
 */
object LoggerHelper {

    fun initHelper(tag: String = "RlLog") {
        //一般Logcat輸出設置
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder() //是否显示线程信息。 默认值为true
            .showThreadInfo(true) //（可选）要显示的方法行数。 默认2
            .methodCount(0) //（可选）隐藏内部方法调用到偏移量。 默认5
            .methodOffset(0) //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
            .tag(tag).build()
        //根据上面的格式设置logger相应的适配器
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}