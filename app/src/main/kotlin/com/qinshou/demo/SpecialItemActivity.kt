package com.qinshou.demo

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.qinshou.demo.bean.NewsBean
import com.qinshou.rcv.adapter.RcvBaseAdapter
import com.qinshou.rcv.adapter.RcvMultipleAdapter
import com.qinshou.rcv.itemview.*
import com.qinshou.rcv.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.activity_one_to_one.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * Author: QinHao
 * Email:qinhao@jeejio.com
 * Date: 2021/4/15 16:02
 * Description:
 */
class SpecialItemActivity : AppCompatActivity() {
    private val rcvNewsAdapter: RcvNewsAdapter by lazy {
        return@lazy RcvNewsAdapter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_to_one)
        rcv_one_to_one.adapter = rcvNewsAdapter
    }

    private class RcvNewsAdapter(context: Context, specialItemActivity: SpecialItemActivity) : RcvMultipleAdapter(context) {

        init {
            // EmptyItemView,需要自定义 UI,声明成员变量再重写里面的方法,否则如果有多个匿名内部类的话,kotlin 会报错
            val emptyItemView: EmptyItemView = object : EmptyItemView(context, R.layout.layout_empty_view_of_rcv_one_to_one, this) {
                override fun bindViewHolder(holder: BaseViewHolder, itemData: Any?, position: Int) {
                    super.bindViewHolder(holder, itemData, position)
                    holder.setOnClickListener(R.id.btn_refresh, View.OnClickListener {
                        // 刷新数据,具体怎么发送请求,可以根据自己情况来,可以传一个 v 层或 p 层的实例进来,也
                        // 可以通过 Handler 发送消息通知
                        specialItemActivity.refresh()
                    })
                }
            }
            addItemView(emptyItemView)
            // 骨架屏 item
            val skeletonItemView: SkeletonItemView = object : SkeletonItemView(context, R.layout.item_rcv_news, this) {
                override fun bindViewHolder(holder: BaseViewHolder, itemData: Any?, position: Int) {
                    // 默认效果会遍历 ItemView 中的所有子 View,将背景设置为灰色,如果需要修改 UI,可以重写
                    // bindViewHolder 方法,不调用 super.bindViewHolder(holder, itemData, position),然
                    // 后实现自己的 UI 逻辑
//                    super.bindViewHolder(holder, itemData, position)
                    val tvTime: View? = holder.findViewById(R.id.tv_time)
                    val layoutParams = tvTime?.layoutParams
                    layoutParams?.width = 200
                    tvTime?.layoutParams = layoutParams
                    setSkeleton(holder.itemView)
                }
            }
            addItemView(skeletonItemView)
            // HeaderItemView,头布局仅做展示,没有逻辑操作的话,不需要重写 bindViewHolder 方法
            addItemView(HeaderItemView(context, R.layout.layout_header_view_of_rcv_one_to_one, this))
            // HeaderItemView,需要自定义 UI,声明成员变量再重写 bindViewHolder 方法,否则如果有多个匿名内部类的话,kotlin 会报错
            val headerItemView2: HeaderItemView = object : HeaderItemView(context, R.layout.layout_header_view_of_rcv_one_to_one, this) {
                override fun bindViewHolder(holder: BaseViewHolder, itemData: Any?, position: Int) {
                    super.bindViewHolder(holder, itemData, position)
                    holder.setTvText(R.id.text_view, "Header View 2")
                }
            }
            addItemView(headerItemView2)
            // FooterItemView,脚布局仅做展示,没有逻辑操作的话,不需要重写 bindViewHolder 方法
            addItemView(FooterItemView(context, R.layout.layout_footer_view_of_rcv_one_to_one, this))
            // FooterItemView,需要自定义 UI,声明成员变量再重写 bindViewHolder 方法,否则如果有多个匿名内部类的话,kotlin 会报错
            val footerItemView2: FooterItemView = object : FooterItemView(context, R.layout.layout_footer_view_of_rcv_one_to_one, this) {
                override fun bindViewHolder(holder: BaseViewHolder, itemData: Any?, position: Int) {
                    super.bindViewHolder(holder, itemData, position)
                    holder.setTvText(R.id.text_view, "Footer View 2")
                }
            }
            addItemView(footerItemView2)
            // 真正要显示的数据
            addItemView(NewsItemView(context, this))
        }
    }

    private class NewsItemView(context: Context, rcvBaseAdapter: RcvBaseAdapter<Any>) : BaseItemView<NewsBean?>(context, R.layout.item_rcv_news, rcvBaseAdapter) {
        private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)

        override fun bindViewHolder(holder: BaseViewHolder, itemData: NewsBean?, position: Int) {
            holder.setTvText(R.id.tv_title, itemData?.titleChinese)
            holder.setTvText(R.id.tv_source, "作者：" + itemData?.sourceChinese)
            holder.setTvText(R.id.tv_translator, "翻译者：" + itemData?.translatorAndEditor)
            if (itemData?.publishTime != null) {
                holder.setTvText(R.id.tv_time, "翻译者：" + simpleDateFormat.format(Date(itemData.publishTime!!)))
            }
        }
    }

    private fun refresh() {
        GlobalScope.launch(Dispatchers.Main) {
            // 加载数据前设置骨架屏数量
            rcvNewsAdapter.skeletonItemViewCount = 10
            delay(3000)
            val list = ArrayList<Any>()
            for (i in 0 until 100) {
                val newsBean = NewsBean()
                newsBean.titleChinese = "测试标题测试标题测试标题测试标题"
                newsBean.sourceChinese = "测试来源"
                newsBean.translatorAndEditor = "测试翻译者"
                newsBean.publishTime = System.currentTimeMillis()
                list.add(newsBean)
            }
            // 数据加载成功后,将骨架屏数量改为 0
            rcvNewsAdapter.skeletonItemViewCount = 0
            rcvNewsAdapter.dataList = list
        }
    }
}