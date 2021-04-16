package com.qinshou.rcv.listener

import com.qinshou.rcv.viewholder.BaseViewHolder


/**
 * Author: MrQinshou
 * Email:cqflqinhao@126.com
 * Date: 2021/4/14 11:17
 * Description:The listener of item click.
 */
interface OnItemClickListener<T> {
    fun onClick(holder: BaseViewHolder?, itemData: T?, position: Int)
}