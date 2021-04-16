package com.qinshou.demo

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qinshou.rcv.adapter.RcvBaseAdapter
import com.qinshou.rcv.adapter.RcvMultipleAdapter
import com.qinshou.rcv.itemview.BaseItemView
import com.qinshou.rcv.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.activity_many_to_many.*

/**
 * Author: QinHao
 * Email:qinhao@jeejio.com
 * Date: 2021/4/15 16:02
 * Description:
 */
class ManyToManyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_many_to_many)
        val rcvManyToManyAdapter = RcvManyToManyAdapter(this)
        rcv_many_to_many.adapter = rcvManyToManyAdapter
        val list = ArrayList<Any>()
        for (i in 0 until 100) {
            if (i % 2 == 0) {
                list.add(i)
            } else {
                list.add("测试文字$i")
            }
        }
        rcvManyToManyAdapter.dataList = list
    }

    private class RcvManyToManyAdapter(context: Context) : RcvMultipleAdapter(context) {
        init {
            addItemView(IntegerItemView(context, this))
            addItemView(StringItemView(context, this))
        }
    }

    private class IntegerItemView(context: Context, rcvBaseAdapter: RcvBaseAdapter<Any>) : BaseItemView<Int?>(context, R.layout.item_rcv_integer, rcvBaseAdapter) {

        override fun bindViewHolder(holder: BaseViewHolder, itemData: Int?, position: Int) {
            holder.setTvText(R.id.text_view, "" + itemData)
        }
    }

    private class StringItemView(context: Context, rcvBaseAdapter: RcvBaseAdapter<Any>) : BaseItemView<String?>(context, R.layout.item_rcv_string, rcvBaseAdapter) {

        override fun bindViewHolder(holder: BaseViewHolder, itemData: String?, position: Int) {
            holder.setTvText(R.id.text_view, itemData)
        }
    }
}