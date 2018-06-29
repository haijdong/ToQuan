package com.whale.nangua.toquan.act;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.whale.nangua.toquan.R;
import com.whale.nangua.toquan.utils.TextUtils;

import java.util.List;

public class NestedScrollViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroll_view);


       /* TextUtils.setMoreSpecialPollutantTypeNameWithOutAqi(
                (TextView) findViewById(R.id.tv1),
                (TextView) findViewById(R.id.tv2),
                (TextView) findViewById(R.id.tv3),
                (TextView) findViewById(R.id.tv4),
                (TextView) findViewById(R.id.tv5),
                (TextView) findViewById(R.id.tv6),
                "pm2.5,pm10,SO2"
                );*/
    }

}
