package com.rlnb.lib.media.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.rlnb.lib.media.app.databinding.MainLayoutBinding
import com.rlnb.lib.media.core.extendOnClickCallback
import com.rlnb.lib.media.ui.MediaMainActivity

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
            startActivity(Intent(this,OpenMediaActivity::class.java))
        })
    }


}