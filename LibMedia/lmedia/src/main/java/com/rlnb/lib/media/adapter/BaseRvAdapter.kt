package com.rlnb.lib.media.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @author RL
 * @version 2021/9/29
 */
abstract class BaseRvAdapter<T : Any, D : ViewDataBinding> :
    RecyclerView.Adapter<BaseRvViewHolder>() {

    var mListData: MutableList<T> = mutableListOf()

    abstract fun getVdb(parent: ViewGroup, viewType: Int): ViewDataBinding

    abstract fun onBind(vdb: ViewDataBinding, bean: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRvViewHolder {
        return BaseRvViewHolder(getVdb(parent, viewType))
    }

    override fun onBindViewHolder(holder: BaseRvViewHolder, position: Int) {
        val vdb = holder.vdb
        val bean = mListData[position]
        onBind(vdb, bean, position)

        if(this is IRvOnBindFirst<*,*> && isPosFirstItem(position)) {
            (this as IRvOnBindFirst<T, ViewDataBinding>)?.onBindFirst(vdb, bean)
        }

        if(this is IRvOnBindLast<*,*> && isPosLastItem(position)) {
            (this as IRvOnBindLast<T, ViewDataBinding>)?.onBindLast(vdb, bean)
        }
    }

    override fun getItemCount(): Int {
        return mListData?.size
    }


    fun getPosData(position: Int): T {
        return mListData[position]
    }

    /**
     * 傳入下標判斷是否為最底的Item
     * @return  true - 最底
     */
    private fun isPosLastItem(position: Int): Boolean {
        val lastPos = itemCount - 1
        return position == lastPos
    }

    /**
     * 傳入下標判斷是否為最底的Item
     * @return  true - 最頂
     */
    private fun isPosFirstItem(position: Int): Boolean {
        return position == 0
    }

    fun isDataEmpty(): Boolean {
        return !(mListData.isNullOrEmpty())
    }
}