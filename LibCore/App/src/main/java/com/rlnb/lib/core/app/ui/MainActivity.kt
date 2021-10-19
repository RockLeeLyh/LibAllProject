package com.rlnb.lib.core.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rlnb.lib.core.app.R
import com.rlnb.lib.core.app.databinding.MainLayoutBinding

/**
 * @author RL
 * @date 2021/10/19 15:25
 * @description 主页
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vdb: MainLayoutBinding = DataBindingUtil.setContentView(this, R.layout.main_layout)

    }
}