package com.whale.nangua.toquan.act;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.whale.nangua.toquan.R;
import com.whale.nangua.toquan.view.NGOthersInfoAtyHorizotalScrollView;

/**
 * Created by nangua on 2016/8/8.
 */
public class OthersInfoActivity extends Activity implements View.OnClickListener {
    ScrollView myscrollview;
    float scale;
    float position = 0;       //拖动 的距离Y轴的距离
    float scrollviewdistancetotop = 0;   //headView的高
    private Button btn_othersinfo_guardta;  //守护TA按钮
    private Button btn_othersinfo_dialogsubmit; //dialog确定按钮
    private Button btn_othersinfo_dialogcancle; //diaolog取消按钮

    RelativeLayout relativelayout_othersinfo_top;   //顶部布局


    NGOthersInfoAtyHorizotalScrollView scrollview_othersinfo_photos;    //横向scrollview

    int ScreenHigh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_othersinfo);
        ScreenHigh = this.getWindowManager().getDefaultDisplay().getHeight();
        initView();
    }

    float lastY;
    float nowY;
    float tempY;
    int headViewPosition = 0;   //顶部布局位置
    static boolean flag = true; //判断向上或向下的flag
    float chufaHeight;    //触发顶部透明动画的高度

    private void initView() {
        scale = this.getResources().getDisplayMetrics().density;       //获得像素密度
        scrollview_othersinfo_photos = (NGOthersInfoAtyHorizotalScrollView) findViewById(R.id.scrollview_othersinfo_photos);
        scrollview_othersinfo_photos.setImageUrls(Images.imageUrls);

        btn_othersinfo_guardta = (Button) findViewById(R.id.btn_othersinfo_guardta);
        btn_othersinfo_guardta.setOnClickListener(this);

        relativelayout_othersinfo_top = (RelativeLayout) findViewById(R.id.relativelayout_othersinfo_top);

        myscrollview = (ScrollView) findViewById(R.id.myscrollview);
        myscrollview.setY(260 * scale);
        scrollviewdistancetotop = (260) * scale;
        position = (260 * scale);
        chufaHeight = 60 * scale;


        myscrollview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float y = event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //按下的Y的位置
                        lastY = y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //顶部的动画效果
                        if ((myscrollview.getY() <= chufaHeight) && (flag == true)) {
                            //向上滑
                            upanim = ObjectAnimator.ofFloat(relativelayout_othersinfo_top, "alpha", 1, 0.8f);
                            upanim.setDuration(100);
                            upanim.start();
                            flag = false;
                        } else if ((myscrollview.getY() > chufaHeight + 10) && (flag == false)) {
                            //向下滑
                            downanim = ObjectAnimator.ofFloat(relativelayout_othersinfo_top, "alpha", 0.8f, 1f);
                            downanim.setDuration(100);
                            downanim.start();
                            flag = true;
                        }

                        nowY = myscrollview.getY();   //拖动界面的Y轴位置
                        tempY = (event.getRawY() - lastY);    //手移动的偏移量
                        lastY = event.getRawY();
                        Log.d("xiaojingyu", tempY + "");
                        if ((nowY + tempY > 55 * scale) && (nowY + tempY <= scrollviewdistancetotop)) {
                            if (myscrollview.getScrollY() == 0) {
                                if ((int) myscrollview.getY() == 55 * scale) {
                                    if (tempY > 0) {
                                        myscrollview.setY(55 * scale + 1);
                                    }
                                }
                            }

                            if ((int) myscrollview.getY() > 55 * scale) {
                                float temp = position += tempY;
                                myscrollview.setY(temp);

                                float headviewtemp = headViewPosition += (tempY / 2);
                                if ((relativelayout_othersinfo_top.getY() < 0) && (headviewtemp <= 0)) {
                                    relativelayout_othersinfo_top.setY(headviewtemp);
                                } else {
                                    if (headviewtemp <= 0) {
                                        relativelayout_othersinfo_top.setY(headviewtemp);
                                    }
                                }
                                return true;
                            }

                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        tempY = 0;
                        lastY = 0;
                        break;
                }
                return false;
            }
        });
    }

    ObjectAnimator upanim;
    ObjectAnimator downanim;


    public int dpTopx(float dpValue) {
        return (int) (dpValue * scale + 0.5f);
    }

    Dialog dialog;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_othersinfo_guardta:
                LayoutInflater inflaterDl = LayoutInflater.from(this);
                LinearLayout layout = (LinearLayout) inflaterDl.inflate(R.layout.dialog_othersinfo_guardta, null);
                dialog = new AlertDialog.Builder(OthersInfoActivity.this).create();
                //设置对话框取消按钮
                btn_othersinfo_dialogcancle = (Button) layout.findViewById(R.id.btn_othersinfo_dialogcancle);
                btn_othersinfo_dialogcancle.setOnClickListener(this);
                //设置对话框确定按钮
                btn_othersinfo_dialogsubmit = (Button) layout.findViewById(R.id.btn_othersinfo_dialogsubmit);
                btn_othersinfo_dialogsubmit.setOnClickListener(this);
                //设置确定按钮

                dialog.show();
                dialog.getWindow().setContentView(layout);
                break;
            case R.id.btn_othersinfo_dialogcancle:
                dialog.cancel();
                break;
            case R.id.btn_othersinfo_dialogsubmit:
                dialog.cancel();
                break;
        }
    }

}
