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
public class StringItemView extends BaseItemView<String> {

    public StringItemView(Context context) {
        super(context, R.layout.item_rv_test_string);
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, String itemData, int position) {
        holder.setTvText(R.id.tv_test_string, "String 的 ItemView" + itemData);
    }
}
