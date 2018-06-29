package com.whale.nangua.toquan.act;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.whale.nangua.toquan.R;

/**
 * Created by nangua on 2016/8/4.
 */
public class PersonalActivity extends Activity implements View.OnClickListener{
    Button btn_personal_userinfo;   //用户详细信息
    Button btn_personal_recharge;   //充值提现
    Button btn_personal_setup;  //设置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initView();
    }

    private void initView() {
        btn_personal_userinfo = (Button) findViewById(R.id.btn_personal_userinfo);
        btn_personal_userinfo.setOnClickListener(this);
        btn_personal_setup = (Button) findViewById(R.id.btn_personal_setup);
        btn_personal_setup.setOnClickListener(this);
        btn_personal_recharge = (Button) findViewById(R.id.btn_personal_recharge);
        btn_personal_recharge.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btn_personal_userinfo:
                  i = new Intent(PersonalActivity.this,UserInfoActivity.class);
                startActivity(i);
                break;

            case R.id.btn_personal_recharge:
                  i = new Intent(PersonalActivity.this,RechargeActivity.class);
                startActivity(i);
                break;
            case R.id.btn_personal_setup:
                  i = new Intent(PersonalActivity.this,SetupActivity.class);
                startActivity(i);
                break;
        }
    }
}
