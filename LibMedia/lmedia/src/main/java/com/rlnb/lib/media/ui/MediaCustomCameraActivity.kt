package com.rlnb.lib.media.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rlnb.lib.media.R
import com.rlnb.lib.media.databinding.LmediaMainActivityBinding

abstract class MediaCustomCameraActivity : AppCompatActivity() {

    private lateinit var mVdb: LmediaMainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mVdb = DataBindingUtil.setContentView(this, R.layout.lmedia_main_activity)


        val ft = supportFragmentManager.beginTransaction()
        ft.add( mVdb.flMainApp.id, MediaCustomCameraFragment(), MediaCustomCameraFragment::class.java.simpleName).commit()
    }
}