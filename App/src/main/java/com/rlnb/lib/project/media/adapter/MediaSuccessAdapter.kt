package com.rlnb.lib.project.media.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rlnb.lib.media.img.ImgLoader
import com.rlnb.lib.project.databinding.AdapterItemMainBinding
import com.rlnb.lib.project.databinding.AdapterItemMediaSuccessBinding
import com.rlnb.lib.project.ui.BaseRvViewHolder

/**
 * @author RL
 * @date 2021/10/27 16:37
 * @description 选中图片展示页面适配器
 */
class MediaSuccessAdapter: RecyclerView.Adapter<BaseRvViewHolder>() {
    val mDataList = mutableListOf<Uri>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRvViewHolder {
        return BaseRvViewHolder(
            AdapterItemMediaSuccessBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseRvViewHolder, position: Int) {
        val vdb = holder.vdb as AdapterItemMediaSuccessBinding
        val bean = mDataList[position]
        ImgLoader.loadUriImg(bean, vdb.ivImg)
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }
}