package com.qinshou.rcvbaseadapterdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qinshou.rcvbaseadapterdemo.entity.MessageEntity;

import java.util.ArrayList;
import java.util.List;

import demo.com.qinshou.rcvbaseadapterdemo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvTest = (RecyclerView) findViewById(R.id.rv_test);
        TestAdapter testAdapter = new TestAdapter(this);
        rvTest.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvTest.setAdapter(testAdapter);
        testAdapter.setDataList(getList());
    }

    private List<MessageEntity> getList() {
        List<MessageEntity> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setMessage("你好");
            messageEntity.setUserType(i % 2);
            list.add(messageEntity);
        }
        return list;
    }
}
