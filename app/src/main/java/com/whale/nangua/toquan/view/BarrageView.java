package com.whale.nangua.toquan.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.whale.nangua.toquan.R;
import com.whale.nangua.toquan.bean.Barrage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * 2秒一条
 * 屏幕上同时存在5条
 * Created by nangua on 2016/7/28.
 */
public class BarrageView extends FrameLayout {
    private static ArrayList<Barrage> date = new ArrayList<>(); //数据
    private int nowIndex = 0; //date的下标
    private Bitmap nowBitmap; //当前图片
    int width;    //控件宽
    int height;  //控件高
    float scale;    //像素密度
    FrameLayout frameLayout;
    FrameLayout.LayoutParams tvParams;

    static boolean IS_START = false;    //判断是否开始
    static boolean IS_RUNNING = false;    //判断是否运行弹幕

    long alltime; //视频总时长
    long starttime; //开始时间

    //    LinearLayout layout;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Barrage barrage = (Barrage) msg.getData().getSerializable("barrage");
            final LinearLayout layout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.barrageview_item, null);
            layout.setLayoutParams(tvParams);
            //随机获得Y值
            layout.setY(getRamdomY());
            layout.setX(width + layout.getWidth());

            //设置文字
            TextView textView = (TextView) layout.findViewById(R.id.barrageview_item_tv);
            textView.setText(barrage.getBarrageInfo());

            //设置图片
            NGNormalCircleImageView ngNormalCircleImageView = (NGNormalCircleImageView) layout.findViewById(R.id.barrageview_item_img);
            if (nowBitmap != null) {

                ngNormalCircleImageView.setImageBitmap(nowBitmap);
            }

            frameLayout.addView(layout);

            final ObjectAnimator anim = ObjectAnimator.ofFloat(layout, "translationX", -width);
            anim.setDuration(10000);


            //释放资源
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }
                @Override
                public void onAnimationEnd(Animator animation) {
                    anim.cancel();
                    layout.clearAnimation();
                    frameLayout.removeView(layout);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            anim.start();
        }
    };

    /**
     * 使用httprulconnection通过发送网络请求path获得bitmap
     * @param path
     * @return
     */
    public static Bitmap getBitmapFromUrl(String path) {
        try {
            //获得url
            URL url = new URL(path);
            //打开httprulconnection获得实例
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时时间
            conn.setConnectTimeout(5000);
            //设置Get
            conn.setRequestMethod("GET");
            //连接成功
            if (conn.getResponseCode() == 200) {
                //获得输入流
                InputStream inputStream = conn.getInputStream();
                //得到bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmap == null) {
                }
                //返回
                return bitmap;
            }
            //错误信息处理
        } catch (Exception e) {
            //打印错误信息
            e.printStackTrace();
        }
        return null;
    }

    int lastY;//上一次出现的Y值
    /**
     * 获得随机的Y轴的值
     *
     * @return
     */
    private float getRamdomY() {
        int tempY;
        int rY;
        int result = 0;
        // height * 2 / 4 - 25
        //首先随机选择一条道路
        int nowY = (int) (Math.random() * 3);
        switch (nowY) {
            case 0:
                nowY = avoidTheSameY(nowY,lastY);
                //第一条
                tempY = height / 4 - 25;
                rY = (int) (Math.random() * height / 4);
                if (rY >= height / 8) {
                    result = tempY + rY;
                } else {
                    result = tempY - rY + 50 ;
                }
                lastY = nowY;
                break;
            case 1:
                nowY = avoidTheSameY(nowY,lastY);
                //第二条
                tempY = height / 2 - 25;
                rY = (int) (Math.random() * height / 4);
                if (rY >= height / 8) {
                    result = tempY + rY;
                } else {
                    result = tempY - rY;
                }
                lastY = nowY;
                break;
            case 2:
                nowY = avoidTheSameY(nowY,lastY);
                //第三条
                tempY = height * 3 / 4 - 25;
                rY = (int) (Math.random() * height / 4);
                if (rY >= height / 8) {
                    result = tempY + rY -50;
                } else {
                    result = tempY - rY;
                }
                lastY = nowY;
                break;
        }
        return result;
    }

    /**
     * 避免Y重合的方法
     * @param lastY
     * @return
     */
    private int avoidTheSameY(int nowY,int lastY) {
        if (nowY == lastY) {
            nowY ++;
        }
        if (nowY == 4) {
            nowY = 0;
        }
        return nowY;
    }


    public BarrageView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth(); //宽度
        height = getHeight();   //高度
        init();
    }

    private void init() {
        setTime(600000);    //设置初始时长，改完记得删

        starttime = System.currentTimeMillis();

        scale = this.getResources().getDisplayMetrics().density;
        //获得自身实例
        frameLayout = (FrameLayout) findViewById(R.id.barrageview);
        tvParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, 50);

        if (IS_START) {
            //开始动画线程
            startBarrageView();
            IS_START = false;
        }
    }

    public void startBarrageView() {
        IS_RUNNING = true;//开启
        //开启线程发送弹幕
        new Thread() {
            @Override
            public void run() {

                while ((System.currentTimeMillis() - starttime < alltime)
                        && (nowIndex <= date.size() - 1) && (IS_RUNNING)
                        ){

                    try {
                        nowBitmap = getBitmapFromUrl(date.get(nowIndex).getBarrageUrl());
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("barrage",date.get(nowIndex));
                        nowIndex ++;
                        message.setData(bundle);
                        handler.sendMessage(message);
                        Thread.sleep((long) (Math.random() * 3000) + 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
        }.start();
    }

    /**
     * 停止弹幕
     */
    public void stopBarrageView() {
        IS_RUNNING = false;
    }

    /**
     * 重启弹幕
     */
    public void resumeBarrageView() {
        IS_RUNNING = true;
        startBarrageView();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    //设置数据
    public void setSentenceList(ArrayList<Barrage> date1) {
        date = date1;
        IS_START = true;
    }

    //获得视频总时长
    public void setTime(long time) {
        alltime = time;
    }

}
