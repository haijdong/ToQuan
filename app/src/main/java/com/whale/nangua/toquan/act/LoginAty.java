package com.whale.nangua.toquan.act;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.whale.nangua.toquan.R;

/**
 * Created by nangua on 2016/7/26.
 */
public class LoginAty extends Activity implements View.OnClickListener {
    Button phonelogin_btn;
    Button weixinlogin_btn;
    ImageView aiquan_init_logo; //初始要消失的图标
    ImageView aiquan_logo;  //爱圈图标
    TextView login_slogan; //标语

    float scale; //屏幕像素密度

    Handler animationHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            aiquan_logo.setVisibility(View.VISIBLE);
            weixinlogin_btn.setVisibility(View.VISIBLE);
            phonelogin_btn.setVisibility(View.VISIBLE);
            //启动第二波动画
            logoHolder(aiquan_logo);    //图标出现
            btnHolder(weixinlogin_btn); //微信登录按钮出现
            btnHolder(phonelogin_btn); //手机登录按钮出现
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initView();
    }

    private void initView() {
        scale = this.getResources().getDisplayMetrics().density;
        phonelogin_btn = (Button) findViewById(R.id.phonelogin_btn);
        phonelogin_btn.setOnClickListener(this);
        weixinlogin_btn = (Button) findViewById(R.id.weixinlogin_btn);
        weixinlogin_btn.setOnClickListener(this);
        login_slogan = (TextView) findViewById(R.id.login_slogan);
        aiquan_logo = (ImageView) findViewById(R.id.aiquan_logo);
        aiquan_init_logo = (ImageView) findViewById(R.id.aiquan_init_logo);
        //启动第一波动画
        startAnimation();
    }

    private void startAnimation() {
        initlogoHolder(aiquan_init_logo);   //初始圈圈消失
        sloganHolder(login_slogan);   //文字上浮
        new Thread() {
            @Override
            public void run() {
                animationHandler.sendEmptyMessageDelayed(1,1000);
            }
        }.start();
    }

    /**
     * 初始文字上浮
     * @param login_slogan  文字
     */
    private void sloganHolder(View login_slogan) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 0.1f,
                1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 0.1f,
                1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0.1f,
                1f);
        PropertyValuesHolder trsX = PropertyValuesHolder.ofFloat("translationY", 180 * scale,0);
        ObjectAnimator.ofPropertyValuesHolder(login_slogan, pvhX, pvhY, pvhZ, trsX).setDuration(1500).start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startAnimation();
    }

    /**
     * 初始圈圈消失
     * @param view 初始圈圈
     */
    public void initlogoHolder(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                0f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                0f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                0f);
        PropertyValuesHolder trsX = PropertyValuesHolder.ofFloat("translationY", -20 * scale);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ, trsX).setDuration(2000).start();
    }

    /**
     * 图标圈圈出现
     * @param view  图标圈圈
     */
    public void logoHolder(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 0f,
                1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 0f,
                1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0f,
                1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(1000).start();
    }

    /**
     * 两个按钮出现
     * @param view  传入两个按钮
     */
    public void btnHolder(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 0f,
                1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 0.7f,
                1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0.7f,
                1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(1000).start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phonelogin_btn:
                Intent i = new Intent(LoginAty.this, PhoneLoginAty.class);
                startActivity(i);
                break;
            case R.id.weixinlogin_btn:
                //TODO 跳转微信登录
                break;
        }
    }
}
