package com.rlnb.lib.media.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rlnb.lib.media.app.databinding.MainLayoutBinding
import com.rlnb.lib.media.core.extendOnClickCallback

/**
 *
 * @author RL
 * @version 2021/9/29
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vdb: MainLayoutBinding = DataBindingUtil.setContentView(this, R.layout.main_layout)
        vdb.btnOpen.extendOnClickCallback({
            startActivity(Intent(this, OpenMediaActivity::class.java))
        })

        vdb.btnCustomCamera.extendOnClickCallback({
            startActivity(Intent(this, OpenCustomCameraActivity::class.java))
        })
    }


}