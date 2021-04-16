package com.qinshou.demo

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qinshou.rcv.adapter.RcvSimpleAdapter
import com.qinshou.rcv.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.activity_one_to_one.*

/**
 * Author: QinHao
 * Email:qinhao@jeejio.com
 * Date: 2021/4/15 16:02
 * Description:
 */
class OneToOneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_to_one)
        val rcvOneToOneAdapter = RcvOneToOneAdapter(this)
        rcv_one_to_one.adapter = rcvOneToOneAdapter
        val list = ArrayList<String>()
        for (i in 0 until 100) {
            list.add("测试文字$i")
        }
        rcvOneToOneAdapter.dataList = list
    }

    private class RcvOneToOneAdapter(context: Context) : RcvSimpleAdapter<String>(context, R.layout.item_rcv_string) {

        override fun bindViewHolder(holder: BaseViewHolder, itemData: String?, position: Int) {
            holder.setTvText(R.id.text_view, itemData)
        }
    }
}