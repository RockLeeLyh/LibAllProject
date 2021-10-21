package com.rlnb.lib.project.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rlnb.lib.project.databinding.AdapterItemMainBinding
import com.rlnb.lib.project.ui.BaseRvViewHolder
import com.rlnb.lib.project.ui.bean.ActivityBean

/**
 * @author RL
 * @date 2021/10/21 16:00
 * @description
 */
class MainAdapter : RecyclerView.Adapter<BaseRvViewHolder>() {

    val mDataList = mutableListOf<ActivityBean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRvViewHolder {
        return BaseRvViewHolder(
            AdapterItemMainBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseRvViewHolder, position: Int) {
        val vdb = holder.vdb as AdapterItemMainBinding
        val bean = mDataList[position]
        vdb.root.setOnClickListener {
            val intent = Intent(it.context, bean.cls)
            it.context.startActivity(intent)
        }
        vdb.tvTitle.text = bean.name
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }
}