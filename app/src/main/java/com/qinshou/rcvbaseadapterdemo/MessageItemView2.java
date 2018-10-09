package com.qinshou.rcvbaseadapterdemo;

import android.content.Context;

import com.qinshou.rcvbaseadapterdemo.entity.MessageEntity;
import com.qinshou.rcvbaseadapterdemo.holder.BaseViewHolder;
import com.qinshou.rcvbaseadapterdemo.itemview.BaseItemView;

import demo.com.qinshou.rcvbaseadapterdemo.R;

/**
 * Description:
 * Created by 禽兽先生
 * Created on 2018/10/9
 */
public class MessageItemView2 extends BaseItemView<MessageEntity> {

    public MessageItemView2(Context context) {
        super(context, R.layout.item_rv_test_message2);
    }

    @Override
    public boolean isForViewType(MessageEntity item, int position) {
        if (item.getUserType() == 1) {
            return true;
        }
        return false;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, MessageEntity itemData, int position) {
        holder.setTvText(R.id.tv_test_message2, "接收的消息" + itemData.getMessage());
    }
}
