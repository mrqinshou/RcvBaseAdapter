package com.qinshou.rcvbaseadapterdemo;

import android.content.Context;

import com.qinshou.rcvbaseadapterdemo.adapter.RcvMultipleBaseAdapter;

public class TestAdapter extends RcvMultipleBaseAdapter {

    public TestAdapter(Context context) {
        super(context);
        addItemView(new MessageItemView1(context));
        addItemView(new MessageItemView2(context));
    }
}
