package com.qinshou.demo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Author: QinHao
 * Email:qinhao@jeejio.com
 * Date: 2021/4/15 16:02
 * Description:
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_one_to_one.setOnClickListener(View.OnClickListener { startActivity(Intent(this, OneToOneActivity::class.java)) })
        btn_many_to_many.setOnClickListener(View.OnClickListener { startActivity(Intent(this, ManyToManyActivity::class.java)) })
        btn_one_to_many.setOnClickListener(View.OnClickListener { startActivity(Intent(this, OneToManyActivity::class.java)) })
        btn_special_item.setOnClickListener(View.OnClickListener { startActivity(Intent(this, SpecialItemActivity::class.java)) })
    }
}