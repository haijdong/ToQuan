package com.whale.nangua.toquan.act;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.whale.nangua.toquan.R;
import com.whale.nangua.toquan.bean.Barrage;
import com.whale.nangua.toquan.view.BarrageView;

import java.util.ArrayList;

/**
 * 测试弹幕的TestAty
 * Created by nangua on 2016/7/18.
 */
public class TestAty extends Activity implements View.OnClickListener {
    BarrageView barrageview;
    ArrayList<Barrage> date;
    Button barrageview_begin;
    Button barrageview_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barrageview_test);
        initView();
    }

    private void initView() {
        barrageview_begin = (Button) findViewById(R.id.barrageview_begin);
        barrageview_stop = (Button) findViewById(R.id.barrageview_stop);
        barrageview_begin.setOnClickListener(this);
        barrageview_stop.setOnClickListener(this);
        date = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            date.add(new Barrage(
                    "测试弹幕" + i, "http://pic.818today.com/imgsy/image/2016/0215/6359114592207963687677523.jpg"));
        }

        barrageview = (BarrageView) findViewById(R.id.barrageview);
        barrageview.setSentenceList(date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.barrageview_begin:
                barrageview.resumeBarrageView();
                break;
            case R.id.barrageview_stop:
                barrageview.stopBarrageView();
                break;

        }
    }
}
