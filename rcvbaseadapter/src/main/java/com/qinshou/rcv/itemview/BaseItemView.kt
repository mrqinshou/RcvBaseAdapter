package com.qinshou.rcv.itemview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.qinshou.rcv.adapter.RcvBaseAdapter
import com.qinshou.rcv.viewholder.BaseViewHolder
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Author: MrQinshou
 * Email:cqflqinhao@126.com
 * Date: 2021/4/14 13:47
 * Description:The basic of item view,if want to ono-to-many or many-to-many layout,you may use it.
 */
abstract class BaseItemView<T>(val context: Context, private val layoutId: Int, val rcvBaseAdapter: RcvBaseAdapter<Any>) {

    abstract fun bindViewHolder(holder: BaseViewHolder, itemData: T?, position: Int)

    fun onCreateViewHolder(parent: ViewGroup?): BaseViewHolder {
        return BaseViewHolder(context, LayoutInflater.from(context).inflate(layoutId, parent, false))
    }

    open fun isForViewType(item: Any?, position: Int): Boolean {
        if (item == null) {
            return false
        }
        val genericSuperclass = javaClass.genericSuperclass
        if (genericSuperclass !is ParameterizedType) {
            return false
        }
        if (genericSuperclass.actualTypeArguments.isEmpty()) {
            return false
        }
        val type: Type = genericSuperclass.actualTypeArguments[0]
        return item::class.java == type
    }
}