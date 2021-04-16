package com.qinshou.rcv.itemview

import android.content.Context
import com.qinshou.rcv.adapter.RcvBaseAdapter
import com.qinshou.rcv.adapter.RcvMultipleAdapter
import com.qinshou.rcv.viewholder.BaseViewHolder

/**
 * Author: MrQinshou
 * Email:cqflqinhao@126.com
 * Date: 2021/4/14 15:33
 * Description:The item view used to quickly implement footer layout.
 */
open class FooterItemView(context: Context, layoutId: Int, rcvBaseAdapter: RcvBaseAdapter<Any>) : BaseItemView<Any>(context, layoutId, rcvBaseAdapter) {
    override fun isForViewType(item: Any?, position: Int): Boolean {
        val list = ArrayList<FooterItemView>()
        // record the count of header view.
        var headerViewCount = 0
        // calculate the count of footer view.
        for (i in 0 until (rcvBaseAdapter as RcvMultipleAdapter).itemViewSparseArray.size()) {
            if (rcvBaseAdapter.itemViewSparseArray.valueAt(i) is FooterItemView) {
                list.add(rcvBaseAdapter.itemViewSparseArray.valueAt(i) as FooterItemView)
            }
            if (rcvBaseAdapter.itemViewSparseArray.valueAt(i) is HeaderItemView) {
                headerViewCount++
            }
        }
        // return false when position is not the bottom.
        if (position < rcvBaseAdapter.dataList!!.size + headerViewCount) {
            return false
        }
        // 判断当前 FooterView 是不是集合中按照添加顺序对应的那一个
        return this == list[position - (rcvBaseAdapter.dataList!!.size + headerViewCount)]
    }

    override fun bindViewHolder(holder: BaseViewHolder, itemData: Any?, position: Int) {
    }
}