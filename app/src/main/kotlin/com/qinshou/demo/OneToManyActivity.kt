package com.qinshou.demo

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qinshou.rcv.adapter.RcvBaseAdapter
import com.qinshou.rcv.adapter.RcvMultipleAdapter
import com.qinshou.rcv.itemview.BaseItemView
import com.qinshou.rcv.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.activity_one_to_many.*

/**
 * Author: QinHao
 * Email:qinhao@jeejio.com
 * Date: 2021/4/15 16:02
 * Description:一对多布局
 */
class OneToManyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_to_many)
        val rcvOneToManyAdapter = RcvOneToManyAdapter(this)
        rcv_one_to_many.adapter = rcvOneToManyAdapter
        val list = ArrayList<Any>()
        for (i in 0 until 100) {
            list.add("测试文字$i")
        }
        rcvOneToManyAdapter.dataList = list
    }

private class RcvOneToManyAdapter(context: Context) : RcvMultipleAdapter(context) {
    init {
        addItemView(OneToManyItemView1(context, this))
        addItemView(OneToManyItemView2(context, this))
    }
}

    private class OneToManyItemView1(context: Context, rcvBaseAdapter: RcvBaseAdapter<Any>) : BaseItemView<String?>(context, R.layout.item_rcv_one_to_many_1, rcvBaseAdapter) {

        // 一对多时,重写 isForViewType,根据自己的业务需求判断 ItemView 显示的时机
        override fun isForViewType(item: Any?, position: Int): Boolean {
            return position % 2 == 0
        }

        override fun bindViewHolder(holder: BaseViewHolder, itemData: String?, position: Int) {
            holder.setTvText(R.id.text_view, itemData)
        }
    }

    private class OneToManyItemView2(context: Context, rcvBaseAdapter: RcvBaseAdapter<Any>) : BaseItemView<String?>(context, R.layout.item_rcv_one_to_many_2, rcvBaseAdapter) {

        // 一对多时,重写 isForViewType,根据自己的业务需求判断 ItemView 显示的时机
        override fun isForViewType(item: Any?, position: Int): Boolean {
            return position % 2 != 0
        }

        override fun bindViewHolder(holder: BaseViewHolder, itemData: String?, position: Int) {
            holder.setTvText(R.id.text_view, itemData)
        }
    }
}