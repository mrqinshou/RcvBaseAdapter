package com.qinshou.rcv.listener

import com.qinshou.rcv.viewholder.BaseViewHolder


/**
 * Author: MrQinshou
 * Email:cqflqinhao@126.com
 * Date: 2021/4/14 11:18
 * Description:The listener of item long click.
 */
interface OnItemLongClickListener<T> {
    fun onLongClick(holder: BaseViewHolder?, itemData: T?, position: Int)
}