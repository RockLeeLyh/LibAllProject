package com.rlnb.lib.media.app

import android.app.Application
import com.rlnb.lib.media.core.LoggerHelper

/**
 *
 * @author RL
 * @version 2021/9/29
 */
class NbApplication: Application() {


    override fun onCreate() {
        super.onCreate()
        LoggerHelper.initHelper()
    }
}