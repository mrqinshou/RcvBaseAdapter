package com.qinshou.rcv.adapter

import android.content.Context

/**
 * Author: QinHao
 * Email:qinhao@jeejio.com
 * Date: 2021/4/14 13:29
 * Description:The simple implement of RcvBaseAdapter,used for one-to-one.
 */
abstract class RcvSimpleAdapter<T>(context: Context, layoutId: Int) : RcvBaseAdapter<T>(context, layoutId) {
}