package com.qinshou.rcvbaseadapterdemo;

import android.content.Context;

import com.qinshou.rcvbaseadapterdemo.holder.BaseViewHolder;
import com.qinshou.rcvbaseadapterdemo.itemview.BaseItemView;

import demo.com.qinshou.rcvbaseadapterdemo.R;

/**
 * Description:
 * Created by 禽兽先生
 * Created on 2018/10/9
 */
public class IntegerItemView extends BaseItemView<Integer> {

    public IntegerItemView(Context context) {
        super(context, R.layout.item_rv_test_integer);
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, Integer itemData, int position) {
        holder.setTvText(R.id.tv_test_integer, "Integer 的 ItemView" + itemData);
    }
}
