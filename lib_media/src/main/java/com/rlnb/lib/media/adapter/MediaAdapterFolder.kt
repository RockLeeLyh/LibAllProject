package com.rlnb.lib.media.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.rlnb.lib.media.core.extendOnClickCallbackData
import com.rlnb.lib.media.databinding.LmediaFolderAdapterItemBinding
import com.rlnb.lib.media.img.ImgLoader
import com.rlnb.lib.media.select.bean.MediaFolderBean
import com.rlnb.lib.media.util.MediaLog

/**
 * 文件夹列表适配器
 * @author RL
 * @version 2021/9/29
 */
class MediaAdapterFolder : BaseRvAdapter<MediaFolderBean, LmediaFolderAdapterItemBinding>(),
    IRvOnBindLast<MediaFolderBean, LmediaFolderAdapterItemBinding> {

    /** 點擊回調方法 */
    var mClickCallback: ((folderBean: MediaFolderBean) -> Unit)? = null
    /** 當前選中的Item對象 */
    var mSelectBean: MediaFolderBean? = null

    override fun getVdb(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return LmediaFolderAdapterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun onBind(vdb: ViewDataBinding, bean: MediaFolderBean, position: Int) {
        if (vdb is LmediaFolderAdapterItemBinding) {
            vdb.tvName.text = bean.name
            vdb.tvTotal.text = " (${bean.total})"
            vdb.ivSelect.visibility = if (checkSameBean(bean)) View.VISIBLE else View.GONE
            bean.position = position
            vdb.vLine.visibility = View.VISIBLE
            ImgLoader.loadUriImg(bean.uri, vdb.ivCover)
            vdb.root.extendOnClickCallbackData(bean) { clickCallbackBean(it) }
        }
    }

    override fun onBindLast(vdb: LmediaFolderAdapterItemBinding, bean: MediaFolderBean) {
        vdb.vLine.visibility = View.GONE
        ImgLoader.loadUriImg(bean.uri, vdb.ivCover)
    }

    /**
     * 點擊Item處理方法
     * @param  bean 對應的數據對象
     */
    private fun clickCallbackBean(bean: MediaFolderBean?) {
        bean?.let {
            mClickCallback?.invoke(it)
            mSelectBean?.let { sBean ->
                notifyItemChanged(sBean.position)
            }
            this.mSelectBean = it
            notifyItemChanged(it.position)
        }
    }

    /**
     * 傳入數據對象判斷是否與當前選中對象是否一致
     * @param   bean    數據對象
     * @return  true - 一致
     */
    private fun checkSameBean(bean: MediaFolderBean): Boolean {
        if(mSelectBean == null) {
            mSelectBean =  getPosData(0)
            mSelectBean?.position = 0
        }
        if(mSelectBean == null) {
            return false
        }

        return bean.position == mSelectBean?.position
    }
}