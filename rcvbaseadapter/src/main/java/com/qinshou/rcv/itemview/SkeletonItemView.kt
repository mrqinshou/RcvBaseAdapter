package com.qinshou.rcv.itemview

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.qinshou.rcv.adapter.RcvBaseAdapter
import com.qinshou.rcv.adapter.RcvMultipleAdapter
import com.qinshou.rcv.viewholder.BaseViewHolder

/**
 * Author: MrQinshou
 * Email:cqflqinhao@126.com
 * Date: 2021/4/15 9:03
 * Description:The item view used to quickly implement skeleton layout.
 * It will iterate child view and set its background When bind UI.
 * If the default UI effect doesn't statisfied your needs,you can override bindViewHolder method
 * to implement your custom UI.
 */
open class SkeletonItemView(context: Context, layoutId: Int, rcvBaseAdapter: RcvBaseAdapter<Any>) : BaseItemView<Any>(context, layoutId, rcvBaseAdapter) {
    override fun isForViewType(item: Any?, position: Int): Boolean {
        // return true when the size of the collection of data equal 0 and skeletonItemViewCount
        // greater than 0.
        return (rcvBaseAdapter.dataList == null || rcvBaseAdapter.dataList?.size == 0)
                && rcvBaseAdapter is RcvMultipleAdapter
                && rcvBaseAdapter.skeletonItemViewCount > 0
    }

    override fun bindViewHolder(holder: BaseViewHolder, itemData: Any?, position: Int) {
        setSkeleton(holder.itemView)
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/15 9:53
     * Description:Iterate child view,if the view is ViewGroup,it will recursion call this method.
     */
    fun setSkeleton(view: View) {
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                setSkeleton(view.getChildAt(i))
            }
        } else {
            view.setBackgroundColor(Color.GRAY)
        }
    }
}