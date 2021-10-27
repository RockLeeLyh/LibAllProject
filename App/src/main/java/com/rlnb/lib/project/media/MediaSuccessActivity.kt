package com.rlnb.lib.project.media

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rlnb.lib.project.R
import com.rlnb.lib.project.databinding.ActivityMediaSuccessBinding
import com.rlnb.lib.project.media.adapter.MediaSuccessAdapter

/**
 * @author RL
 * @date 2021/10/27 16:31
 * @description 选中图片展示页面
 */
class MediaSuccessActivity : AppCompatActivity() {

    private lateinit var mVdb: ActivityMediaSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mVdb = DataBindingUtil.setContentView(this, R.layout.activity_media_success)

        val uriList = intent.getParcelableArrayListExtra<Uri>("uriExtra")

        val adapter = MediaSuccessAdapter()
        uriList?.forEach { adapter.mDataList.add(it) }
        mVdb.rvImg.adapter = adapter
    }
}