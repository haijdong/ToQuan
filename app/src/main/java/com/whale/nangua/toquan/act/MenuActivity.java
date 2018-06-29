package com.whale.nangua.toquan.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.whale.nangua.toquan.R;

public class MenuActivity extends Activity implements View.OnClickListener {

    private TextView guide;
    private TextView Guide2Activity;
    private TextView LoginAty;
    private TextView OfficialActivity;
    private TextView OthersInfoActivity;
    private TextView PersonalActivity;
    private TextView TestAty;
    private TextView PopActivity;
    private TextView CarrousellayoutActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        guide = (TextView) findViewById(R.id.guide);
        Guide2Activity = (TextView) findViewById(R.id.Guide2Activity);
        LoginAty = (TextView) findViewById(R.id.LoginAty);
        OfficialActivity = (TextView) findViewById(R.id.OfficialActivity);
        OthersInfoActivity = (TextView) findViewById(R.id.OthersInfoActivity);
        PersonalActivity = (TextView) findViewById(R.id.PersonalActivity);
        TestAty = (TextView) findViewById(R.id.TestAty);
        PopActivity = (TextView) findViewById(R.id.PopActivity);
        CarrousellayoutActivity = (TextView) findViewById(R.id.CarrousellayoutActivity);


        guide.setOnClickListener(this);
        Guide2Activity.setOnClickListener(this);
        LoginAty.setOnClickListener(this);
        OfficialActivity.setOnClickListener(this);
        OthersInfoActivity.setOnClickListener(this);
        PersonalActivity.setOnClickListener(this);
        TestAty.setOnClickListener(this);
        PopActivity.setOnClickListener(this);
        CarrousellayoutActivity.setOnClickListener(this);
        findViewById(R.id.NestedScrollViewActivity).setOnClickListener(this);
        findViewById(R.id.TextHomeActivity).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guide:
                goToActivity(GuideActivity.class);
                break;
            case R.id.Guide2Activity:
                goToActivity(Guide2Activity.class);
                break;
            case R.id.LoginAty: //圆圈变化
                goToActivity(LoginAty.class);
                break;
            case R.id.OfficialActivity: //菜单
                goToActivity(OfficialActivity.class);
                break;
            case R.id.OthersInfoActivity://QQ 个人中心
                goToActivity(OthersInfoActivity.class);
                break;
            case R.id.PersonalActivity:// 个人中心
                goToActivity(PersonalActivity.class);
                break;
            case R.id.TestAty://弹幕
                goToActivity(TestAty.class);
                break;
            case R.id.PopActivity:
                goToActivity(PopActivity.class);
                break;
            case R.id.CarrousellayoutActivity://旋转木马
                goToActivity(CarrousellayoutActivity.class);
                break;
            case R.id.NestedScrollViewActivity:
                goToActivity(NestedScrollViewActivity.class);
                break;
            case R.id.TextHomeActivity: //沉浸式状态栏
                goToActivity(TextHomeActivity.class);
                break;


        }
    }

    public void goToActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
