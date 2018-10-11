package com.qinshou.rcvbaseadapterdemo.adapter;

import android.content.Context;

/**
 * Description:item 为单布局的 RecyclerView 的适配器,只是简单继承了 RcvBaseAdapter<T>
 * 如果列表只是相同布局的 item,只需要继承该适配器,实现 public abstract void bindViewHolder(BaseViewHolder holder, T t) 这一个方法即可.
 * Created by 禽兽先生
 * Created on 2017/12/14
 */

public abstract class RcvSingleBaseAdapter<T> extends RcvBaseAdapter<T> {
    public RcvSingleBaseAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }
}
