package com.qinshou.rcvbaseadapterdemo.listener;


import com.qinshou.rcvbaseadapterdemo.holder.BaseViewHolder;

/**
 * Description:
 * Created by 禽兽先生
 * Created on 2018/3/8
 */

public interface OnItemClickListener<T> {
    void onItemClick(BaseViewHolder holder, T t, int position);
}
