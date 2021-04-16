package com.qinshou.rcv.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qinshou.rcv.listener.OnItemClickListener
import com.qinshou.rcv.listener.OnItemLongClickListener
import com.qinshou.rcv.viewholder.BaseViewHolder
import java.util.*

/**
 * Author: QinHao
 * Email:qinhao@jeejio.com
 * Date: 2021/4/14 11:14
 * Description:The base adapter of RecyclerView.If datum are the same type,please use
 * {@link RcvSimpleAdapter}.If datum are different type or datum are the same type but want to use
 * different layout,please use {@link RcvMultipleAdapter}.
 */
abstract class RcvBaseAdapter<T>(val context: Context, private val layoutId: Int) : RecyclerView.Adapter<BaseViewHolder>() {
    /**
     * Collection to save data
     */
    var dataList: MutableList<T>? = ArrayList()
        set(value) {
            if (value == null) {
                field = ArrayList()
            } else {
                field = value
            }
            notifyDataSetChanged()
        }

    /**
     * The Listener of item click
     */
    protected val onItemClickListener: OnItemClickListener<T>? = null

    /**
     * The Listener of item long click
     */
    protected val onItemLongClickListener: OnItemLongClickListener<T>? = null

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 17:36
     * Description:Do bind UI operate,children only needs to implement this method for complete the
     * adapter's tedious operate.
     */
    abstract fun bindViewHolder(holder: BaseViewHolder, itemData: T?, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(context, LayoutInflater.from(context).inflate(layoutId, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
//        if (dataList == null) {
//            return
//        }
//        if (dataList?.size == 0) {
//            return
//        }
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(View.OnClickListener {
                onItemClickListener.onClick(holder, dataList?.get(position), position)
            })
        }
        if (onItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(OnLongClickListener {
                onItemLongClickListener.onLongClick(holder, dataList?.get(position), position)
                true
            })
        }
        bindViewHolder(holder, dataList?.get(position), position)
    }

    override fun getItemCount(): Int {
        return if (dataList == null) 0 else dataList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 17:39
     * Description:Add datum to collection.
     * @param dataList The datum you want to add.
     * @param isRefresh If true adapter will refresh all,false only refresh the added section
     */
    fun addDataList(dataList: List<T>?, isRefresh: Boolean = false) {
        if (dataList == null || dataList.isEmpty()) {
            return
        }
        if (isRefresh) {
            this.dataList?.clear()
        }
        this.dataList?.addAll(dataList)
        if (isRefresh) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeInserted(this.dataList!!.size - dataList.size, this.dataList!!.size)
        }
    }
}