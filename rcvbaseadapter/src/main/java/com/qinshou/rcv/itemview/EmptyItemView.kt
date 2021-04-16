package com.qinshou.rcv.itemview

import android.content.Context
import com.qinshou.rcv.adapter.RcvBaseAdapter
import com.qinshou.rcv.adapter.RcvMultipleAdapter
import com.qinshou.rcv.viewholder.BaseViewHolder

/**
 * Author: MrQinshou
 * Email:cqflqinhao@126.com
 * Date: 2021/4/14 15:09
 * Description:The item view used to quickly implement empty layout.
 */
open class EmptyItemView(context: Context, layoutId: Int, rcvBaseAdapter: RcvBaseAdapter<Any>) : BaseItemView<Any>(context, layoutId, rcvBaseAdapter) {
    override fun isForViewType(item: Any?, position: Int): Boolean {
        // return true when the size of the collection of data equal 0 and skeletonItemViewCount
        // less than or equal to 0.
        return (rcvBaseAdapter.dataList == null || rcvBaseAdapter.dataList?.size == 0)
                && rcvBaseAdapter is RcvMultipleAdapter
                && rcvBaseAdapter.skeletonItemViewCount <= 0
    }

    override fun bindViewHolder(holder: BaseViewHolder, itemData: Any?, position: Int) {

    }
}