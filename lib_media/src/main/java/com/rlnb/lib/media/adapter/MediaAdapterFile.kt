package com.rlnb.lib.media.adapter

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import com.rlnb.lib.media.R
import com.rlnb.lib.media.bean.MediaFileBean
import com.rlnb.lib.media.bean.MediaParamsBean
import com.rlnb.lib.media.core.extendOnClickCallbackData
import com.rlnb.lib.media.databinding.LmediaFileImgAdapterItemBinding
import com.rlnb.lib.media.databinding.LmediaFileOpenCameraAdapterItemBinding
import com.rlnb.lib.media.databinding.LmediaFileVideoAdapterItemBinding
import com.rlnb.lib.media.img.ImgLoader
import com.rlnb.lib.media.util.MediaLog
import com.rlnb.lib.media.util.MediaUtils

/**
 * 文件列表适配器
 * @author RL
 * @version 2021/9/29
 * @param   mediaParamsBean 参数对象
 */

class MediaAdapterFile(mediaParamsBean: MediaParamsBean) :
    BaseRvAdapter<MediaFileBean, ViewDataBinding>(),
    IRvOnBindFirst<MediaFileBean, ViewDataBinding> {

    private val mMediaParamsBean = mediaParamsBean

    /** 選中的文件 */
    val mSwitchBeanMap by lazy { mutableMapOf<Long, MediaFileBean>() }

    /** Item 大小 */
    var mSize = 0

    /**
     * 选择文件改变后回调方法
     * @param   beanMap 选中的文件集合
     */
    var mClickSelectChangeFileCallback: ((beanMap: MutableMap<Long, MediaFileBean>) -> Unit)? = null

    /** 调用系统相机回调方法 */
    var mAdapterTakePhotoClick: ((bean: MediaFileBean) -> Unit)? = null

    override fun getItemViewType(position: Int): Int {
        val bean = getPosData(position)
        return if (bean.isTakePhoto) {
            R.layout.lmedia_file_open_camera_adapter_item
        } else {
            val type = bean.type
            when {
                MediaUtils.isImageType(type) -> R.layout.lmedia_file_img_adapter_item
                MediaUtils.isVideoType(type) -> R.layout.lmedia_file_video_adapter_item
                else -> R.layout.lmedia_file_img_adapter_item
            }
        }
    }

    override fun getVdb(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return when (viewType) {
            R.layout.lmedia_file_img_adapter_item -> {
                LmediaFileImgAdapterItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
            R.layout.lmedia_file_video_adapter_item -> {
                LmediaFileVideoAdapterItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
            else -> {
                LmediaFileOpenCameraAdapterItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
        }
    }

    override fun onBindFirst(vdb: ViewDataBinding, bean: MediaFileBean) {
    }

    override fun onBind(vdb: ViewDataBinding, bean: MediaFileBean, position: Int) {
        vdb.root.layoutParams.width = mSize
        vdb.root.layoutParams.height = mSize
        bean.position = position
        initClickCallback(vdb.root, bean)
        if (bean.isTakePhoto) {
            MediaLog.i(this, "照相機，直接返回")
            return
        }
        when (vdb) {
            is LmediaFileImgAdapterItemBinding -> {
                val size = String.format("%.2f", (bean.size / 1024F / 1024F))
                vdb.tvSize.text = "${size}M"
                setVdbValueItem(vdb.ivCover, vdb.tvSwitch, vdb.vSwitchMask, bean, position)
            }
            is LmediaFileVideoAdapterItemBinding -> {
                val time = DateUtils.formatElapsedTime(bean.duration / 1000)
                vdb.tvTime.text = "$time"
                setVdbValueItem(vdb.ivCover, vdb.tvSwitch, vdb.vSwitchMask, bean, position)
            }
        }
    }

    /**
     * 设置Item控件绑定数据
     * @param   ivCover 封面图片控件
     * @param   tvSwitch    选中的排序下标显示控件
     * @param   vSwitchMask    选中的㵂照层控件
     * @param   bean    数据对象
     * @param   position    Item下标
     */
    private fun setVdbValueItem(
        ivCover: ImageView,
        tvSwitch: TextView,
        vSwitchMask: View,
        bean: MediaFileBean,
        position: Int
    ) {

        ImgLoader.loadUriImg(bean.uri, ivCover)
        val id = bean.id
        val isSwitch = mSwitchBeanMap.containsKey(id)
        if (isSwitch) {
            mSwitchBeanMap[id]?.bucketId = bean.bucketId
            mSwitchBeanMap[id]?.position = position
        }
        vSwitchMask.visibility = if (isSwitch) View.VISIBLE else View.GONE
        tvSwitch.isSelected = isSwitch
        tvSwitch.text = if (isSwitch) {
            mSwitchBeanMap[id]?.switchIndex.toString()
        } else {
            ""
        }
    }

    /**
     * 列表Item设置点击事件
     * @param   rootView    Item控件对象
     * @param   bean    Item数据对象
     */
    private fun initClickCallback(rootView: View, bean: MediaFileBean) {
        rootView.extendOnClickCallbackData(bean) {
            it?.let {
                if (bean.isTakePhoto) {
                    mAdapterTakePhotoClick?.invoke(it)
                } else {
                    clickFileItem(it)
                }
            }
        }
    }

    /**
     * 点击文件Item事件方法
     * @param   bean    数据对象
     */
    private fun clickFileItem(bean: MediaFileBean) {
       if(mMediaParamsBean.isMaxSelectableForOne()) {
           clickFileOneItem(bean)
       }else {
           clickFileMoreItem(bean)
       }
    }

    /**
     * 点击文件Item事件方法,單圖選擇時
     * @param   bean    数据对象
     */
    private fun clickFileOneItem(bean:MediaFileBean){
        mSwitchBeanMap.forEach{notifyItemChanged(it.value.position)}
        mSwitchBeanMap.clear()
        clickFileMoreItem(bean)
    }

    /**
     * 点击文件Item事件方法,多圖選擇時
     * @param   bean    数据对象
     */
    private fun clickFileMoreItem(bean:MediaFileBean){
        val sizeMap = mSwitchBeanMap.size
        val id = bean.id
        if (mSwitchBeanMap.containsKey(id)) {
            val delBean = mSwitchBeanMap[id]
            mSwitchBeanMap.remove(id)
            val index = delBean?.switchIndex ?: Int.MAX_VALUE
            MediaLog.i(this, "删除选中id为: $id 的文件，排序号为：$index, 删除前选中个数 = $sizeMap")
            notifyItemChanged(bean.position)

            if (index < sizeMap) {
                MediaLog.i(this,"删除选中id为: $id 的文件，需要更新删除后的排序号")
                mSwitchBeanMap.filterValues { it.switchIndex > index }
                    .forEach{
                        val fIndex = it.value.switchIndex
                        val newIndex = fIndex - 1
                        it.value.switchIndex = newIndex
                        if (it.value.bucketId == bean.bucketId) {
                            notifyItemChanged(it.value.position)
                        }
                        MediaLog.i(this,"原排序下标 = $fIndex, 新排序下标 = $newIndex")
                    }
            }
        } else {
            if (sizeMap >= mMediaParamsBean.maxSelectable) {
                MediaLog.i(this,"已超过可选图片大小 $sizeMap >= ${mMediaParamsBean.maxSelectable}")
                return
            }
            val index = sizeMap.plus(1)
            bean.switchIndex = index
            mSwitchBeanMap[id] = bean
            notifyItemChanged(bean.position)
            MediaLog.i(this,"添加新选中的文件 = $bean ")
        }
        mClickSelectChangeFileCallback?.invoke(mSwitchBeanMap)
    }


    /** 照相后添加進行列表 */
    fun addTakePhotoBean(bean: MediaFileBean) {
        val index = if (mMediaParamsBean.isShowTakeCamera) 1 else 0
        bean.position = index
        mListData?.add(index, bean)
        clickFileItem(bean)
        notifyItemInserted(index)

    }


}