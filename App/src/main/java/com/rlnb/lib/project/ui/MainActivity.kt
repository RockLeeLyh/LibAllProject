package com.rlnb.lib.project.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rlnb.lib.project.R
import com.rlnb.lib.project.databinding.ActivityMainBinding
import com.rlnb.lib.project.media.MediaOpenActivity
import com.rlnb.lib.project.media.MediaOpenCustomCameraActivity
import com.rlnb.lib.project.ui.adapter.MainAdapter
import com.rlnb.lib.project.ui.bean.ActivityBean

class MainActivity : AppCompatActivity() {

    private lateinit var mVdb: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mVdb = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val adapter = MainAdapter()
        adapter.mDataList.add(ActivityBean("媒体选择框", MediaOpenActivity::class.java))
        adapter.mDataList.add(ActivityBean("自定义相机", MediaOpenCustomCameraActivity::class.java))
        mVdb.rvActivity.adapter = adapter

    }

}