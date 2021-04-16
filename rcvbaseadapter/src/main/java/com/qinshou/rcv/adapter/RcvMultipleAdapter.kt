package com.qinshou.rcv.adapter

import android.content.Context
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import com.qinshou.rcv.itemview.*
import com.qinshou.rcv.viewholder.BaseViewHolder

/**
 * Author: QinHao
 * Email:qinhao@jeejio.com
 * Date: 2021/4/14 13:38
 * Description:The multiple implement of RcvBaseAdapter,used for one-to-many or many-to-many.
 * It's only a manager of item view,The actual bind UI operate is done by the implementation
 * of item view.
 */
open class RcvMultipleAdapter(context: Context) : RcvBaseAdapter<Any>(context, 0) {
    /**
     * Use SparseArray to save item view
     */
    internal val itemViewSparseArray = SparseArray<BaseItemView<*>>()
    var skeletonItemViewCount = 0
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return itemViewSparseArray.get(viewType).onCreateViewHolder(parent)
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 17:45
     * Description:Override this method because the adapter conclude header and footer,must recalculate
     * the position of data.
     */
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
//        if (dataList == null) {
//            return
//        }
//        if (dataList?.size == 0) {
//            return
//        }
        val itemData = getItemData(position)
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(View.OnClickListener {
                onItemClickListener.onClick(holder, itemData, position)
            })
        }
        if (onItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(View.OnLongClickListener {
                onItemLongClickListener.onLongClick(holder, itemData, position)
                true
            })
        }
        bindViewHolder(holder, itemData, position)
    }

    override fun getItemViewType(position: Int): Int {
        for (i in 0 until itemViewSparseArray.size()) {
            val baseItemView: BaseItemView<*> = itemViewSparseArray.valueAt(i)
            // 显示空布局
            if (showEmptyItemView()) {
                return if (baseItemView is EmptyItemView && baseItemView.isForViewType(null, position)) {
                    itemViewSparseArray.keyAt(i)
                } else {
                    continue
                }
            }
            if (showSkeletonItemView()) {
                return if (baseItemView is SkeletonItemView && baseItemView.isForViewType(null, position)) {
                    itemViewSparseArray.keyAt(i)
                } else {
                    continue
                }
            }
            // 如果头布局数量大于 1,则判断该 position 是否是头布局
            val headerItemViewCount = getHeaderItemViewCount()
            if (position < headerItemViewCount) {
                return if (baseItemView is HeaderItemView && baseItemView.isForViewType(null, position)) {
                    itemViewSparseArray.keyAt(i)
                } else {
                    continue
                }
            }
            // 如果脚布局数量大于 1,则判断该 position 是否是脚布局
            if (position >= dataList!!.size + headerItemViewCount) {
                return if (baseItemView is FooterItemView && baseItemView.isForViewType(null, position)) {
                    itemViewSparseArray.keyAt(i)
                } else {
                    continue
                }
            }
            // 正常布局
            if (baseItemView.isForViewType(dataList?.get(position - headerItemViewCount), position)) {
                return itemViewSparseArray.keyAt(i)
            }
        }
        throw IllegalArgumentException("No ItemView added that matches position=$position in data source")
    }

    override fun getItemCount(): Int {
        if (showSkeletonItemView()) {
            return skeletonItemViewCount
        }
        // 如果需要显示空布局,则返回固定数量 1
        if (showEmptyItemView()) {
            return 1
        }
        // 要加上头布局和脚布局数量
        return super.getItemCount() + getHeaderItemViewCount() + getFooterItemViewCount()
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 17:47
     * Description:Iterate through the collection of BaseItemView and delegate the binding UI operate to
     * corresponding item view
     */
    override fun bindViewHolder(holder: BaseViewHolder, itemData: Any?, position: Int) {
        for (i in 0 until itemViewSparseArray.size()) {
            val baseItemView: BaseItemView<Any> = itemViewSparseArray.valueAt(i) as BaseItemView<Any>
            if (baseItemView.isForViewType(itemData, position)) {
                baseItemView.bindViewHolder(holder, itemData, position)
                break
            }
        }
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 17:50
     * Description:Return true When the size of collection of data equal 0
     * and added empty view.
     * @return true if is's time to show empty view.
     */
    private fun showEmptyItemView(): Boolean {
        if (dataList != null && dataList!!.size > 0) {
            return false
        }
        if (skeletonItemViewCount > 0) {
            return false
        }
        for (i in 0 until itemViewSparseArray.size()) {
            if (itemViewSparseArray.valueAt(i) is EmptyItemView) {
                return true
            }
        }
        return false
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/15 10:31
     * Description:Return true When the size of collection of data equal 0
     * and added skeleton view and skeletonItemViewCount greater than 0.
     * @return true if is's time to show empty view.
     */
    private fun showSkeletonItemView(): Boolean {
        if (dataList != null && dataList!!.size > 0) {
            return false
        }
        if (skeletonItemViewCount <= 0) {
            return false
        }
        for (i in 0 until itemViewSparseArray.size()) {
            if (itemViewSparseArray.valueAt(i) is SkeletonItemView) {
                return true
            }
        }
        return false
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 17:50
     * Description:Iterate the collection of BaseItemView to get the count of header views.
     * @return The count of header views.
     */
    private fun getHeaderItemViewCount(): Int {
        var headerItemViewCount = 0
        for (i in 0 until itemViewSparseArray.size()) {
            if (itemViewSparseArray.valueAt(i) is HeaderItemView) {
                headerItemViewCount++
            }
        }
        return headerItemViewCount
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 17:50
     * Description:Iterate the collection of BaseItemView to get the count of footer views.
     * @return The count of footer views.
     */
    private fun getFooterItemViewCount(): Int {
        var footerItemViewCount = 0
        for (i in 0 until itemViewSparseArray.size()) {
            if (itemViewSparseArray.valueAt(i) is FooterItemView) {
                footerItemViewCount++
            }
        }
        return footerItemViewCount
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 17:50
     * Description:Use position minus the count of header views to get the corresponding data.
     * @return The corresponding data.
     */
    private fun getItemData(position: Int): Any? {
        if (dataList == null) {
            return null
        }
        val headerItemViewCount = getHeaderItemViewCount()
//        val footerItemViewCount: Int = getFooterItemViewCount()
        // 头布局
        if (position < headerItemViewCount) {
            return null
        }
        // 脚布局
        if (position >= dataList!!.size + headerItemViewCount) {
            return null
        }
        return dataList!![position - headerItemViewCount]
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 18:00
     * Description:Add itemView
     */
    fun addItemView(baseItemView: BaseItemView<*>) {
        // index 只是为了设置不同的 ViewType,所以自增即可
        itemViewSparseArray.put(itemViewSparseArray.size(), baseItemView)
    }
}