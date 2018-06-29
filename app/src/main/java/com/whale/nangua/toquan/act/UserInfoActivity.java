package com.whale.nangua.toquan.act;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whale.nangua.toquan.R;
import com.whale.nangua.toquan.view.NGHorizontalScrollView;
import com.whale.nangua.toquan.view.RecodePopWindowCircle;
import com.whale.nangua.toquan.voice.SoundMeter;

public class UserInfoActivity extends Activity implements
        SoundMeter.onStartRecoder, View.OnClickListener, View.OnTouchListener {

    public static LinearLayout linearlayout_userinfo_scrolllayout;  //底部可拖动的详细界面

    private RelativeLayout mainheadview;  //顶部个人资料视图
    private RelativeLayout mainactionbar; //顶部菜单栏
    private Button btn_userinfo_certification;  //申请认证按钮
    private Button btn_userinfo_cgrecord; //修改录音按钮
    private Button btn_userinfo_closerecode;    //关闭录音按钮
    private View layout_userinfo_popup; //录音框框
    private RelativeLayout relativelayout_userinfo_record; //录音界面
    private ImageButton imgbtn_userinfo_recode; //录音按钮
    private RecodePopWindowCircle circleview_userinfo_microphone; //录音视图显示圈圈
    private long startVoiceT; //开始录音的时间
    private String voiceName; //音频名

    //录音组件
    private SoundMeter mSensor;
    private Handler mHandler = new Handler();
    //得到音频图task
    private Runnable ampTask = new Runnable() {
        public void run() {
            double amp = mSensor.getAmplitude();
            updateDisplay(amp);
            mHandler.postDelayed(ampTask, POLL_INTERVAL);
        }
    };

    private int endArc = 0; //外围圆弧角度

    //更新时间圈圈task
    private Runnable updateArcTask = new Runnable() {
        @Override
        public void run() {
            endArc += 1;
            circleview_userinfo_microphone.setEndArc(endArc);
            mHandler.postDelayed(updateArcTask, UPDATE_ARC_INTERVAL);
        }
    };

    //更新时间圈圈终止task
    private Runnable mSleepTask = new Runnable() {
        public void run() {
            stop();
        }
    };

    //录音延迟
    private static final int POLL_INTERVAL = 300;
    //时间圆弧更新延迟,默认一秒
    private static final int UPDATE_ARC_INTERVAL = 200;
    //录音提示文字
    TextView tv_register_show;
    //是否取消发送
    private boolean IF_CANCLE_SEND = false;
    private LinearLayout linearlayout_userinfo_cgatr;   //修改个人签名的跳转Layout

    //两个横向相册
    NGHorizontalScrollView scrollview_userinfo_personlaphotos;
    NGHorizontalScrollView scrollview_userinfo_secretvideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        initView();
    }

    int Y;
    int position = 0;       //拖动Linearlayout的距离Y轴的距离
    int scrollviewdistancetotop = 0;   //headView的高
    int menubarHeight = 0;
    int chufaHeight = 0; //需要触发动画的高
    float scale;    //像素密度
    int headViewPosition = 0;

    ImageView userinfo_topbar;

    static boolean flag = true;

    static boolean topmenuflag = true;

    int screenHeight; //屏幕高度

    private void initView() {
        //初始化两个横向相册并设置数据
        scrollview_userinfo_personlaphotos = (NGHorizontalScrollView) findViewById(R.id.scrollview_userinfo_personlaphotos);
        scrollview_userinfo_secretvideo = (NGHorizontalScrollView) findViewById(R.id.scrollview_userinfo_secretvideo);
        scrollview_userinfo_personlaphotos.setImageUrls(Images.imageUrls);
        scrollview_userinfo_secretvideo.setImageUrls(new String[]{
                "http://www.people.com.cn/mediafile/pic/20151015/3/13819161782863104375.jpg",
                "http://www.people.com.cn/mediafile/pic/20151015/3/13819161782863104375.jpg",
                "http://www.people.com.cn/mediafile/pic/20151015/3/13819161782863104375.jpg",
                "http://www.people.com.cn/mediafile/pic/20151015/3/13819161782863104375.jpg",
                "http://www.people.com.cn/mediafile/pic/20151015/3/13819161782863104375.jpg"
        });


        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();    //屏幕高度
        scale = this.getResources().getDisplayMetrics().density;       //获得像素密度
        //初始化录音视图圈圈
        circleview_userinfo_microphone = (RecodePopWindowCircle) findViewById(R.id.circleview_register_microphone);
        //录音界面的relativelayout
        relativelayout_userinfo_record = (RelativeLayout) findViewById(R.id.relativelayout_userinfo_record);
        //初始化录音组件
        mSensor = new SoundMeter();
        mSensor.setonStartRecoderCallback(this);
        //初始化申请认证按钮
        btn_userinfo_certification = (Button) findViewById(R.id.btn_userinfo_certification);
        btn_userinfo_certification.setOnClickListener(this);
        //初始化修改录音按钮
        btn_userinfo_cgrecord = (Button) findViewById(R.id.btn_userinfo_cgrecord);
        btn_userinfo_cgrecord.setOnClickListener(this);
        //初始化录音按钮
        imgbtn_userinfo_recode = (ImageButton) findViewById(R.id.imgbtn_userinfo_recode);
        imgbtn_userinfo_recode.setOnClickListener(this);
        imgbtn_userinfo_recode.setOnTouchListener(this);
        //初始化录音显示文字
        tv_register_show = (TextView) findViewById(R.id.tv_register_show);

        //初始化关闭录音按钮
        btn_userinfo_closerecode = (Button) findViewById(R.id.btn_userinfo_closerecode);
        btn_userinfo_closerecode.setOnClickListener(this);
        //初始化录音框框
        layout_userinfo_popup = findViewById(R.id.layout_userinfo_popup);
        //初始化修改个人签名跳转layout
        linearlayout_userinfo_cgatr = (LinearLayout) findViewById(R.id.linearlayout_userinfo_cgatr);
        linearlayout_userinfo_cgatr.setOnClickListener(this);


        userinfo_topbar = (ImageView) findViewById(R.id.userinfo_topbar);

        mainheadview = (RelativeLayout) findViewById(R.id.mainheadview);
        mainactionbar = (RelativeLayout) findViewById(R.id.mainactionbar);

        menubarHeight = (int) (55 * scale);
        chufaHeight = (int) (110 * scale);

        scrollviewdistancetotop = (int) ((260) * scale);
        position = scrollviewdistancetotop;
        linearlayout_userinfo_scrolllayout = (LinearLayout) findViewById(R.id.linearlayout_userinfo_scrolllayout);
        linearlayout_userinfo_scrolllayout.setOnTouchListener(this);
        linearlayout_userinfo_scrolllayout.setY(scrollviewdistancetotop);    //要减去Absolote布局距离顶部的高度
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btn_userinfo_certification:
                i = new Intent(UserInfoActivity.this, CertificationActivity.class);
                startActivity(i);
                break;
            case R.id.btn_userinfo_cgrecord:
                if (!IS_SHOW_RECORD) {
                    linearlayout_userinfo_scrolllayout.setVisibility(View.INVISIBLE);
                    relativelayout_userinfo_record.setVisibility(View.VISIBLE);
                } else {
                    linearlayout_userinfo_scrolllayout.setVisibility(View.VISIBLE);
                    relativelayout_userinfo_record.setVisibility(View.GONE);
                }
                IS_SHOW_RECORD = !IS_SHOW_RECORD;
                break;
            case R.id.btn_userinfo_closerecode:
                //关闭录音
                linearlayout_userinfo_scrolllayout.setVisibility(View.VISIBLE);
                relativelayout_userinfo_record.setVisibility(View.INVISIBLE);
                IS_SHOW_RECORD = !IS_SHOW_RECORD;
                break;
            case R.id.linearlayout_userinfo_cgatr:
                //跳转到修改个人签名界面
                i = new Intent(UserInfoActivity.this, ChanggeAutographActivity.class);
                startActivity(i);
                break;
        }
    }

    private boolean IS_SHOW_RECORD = false; //是否显示录音界面

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.linearlayout_userinfo_scrolllayout:
                //滚动视图触摸事件判断
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //按下的Y的位置
                        Y = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int nowY = (int) linearlayout_userinfo_scrolllayout.getY();   //拖动界面的Y轴位置
                        float tempY = (int) (event.getRawY() - Y);    //手移动的偏移量
                        Y = (int) event.getRawY();
                        if ((nowY + tempY >= 0) && (nowY + tempY <= scrollviewdistancetotop)) {
                            if ((nowY + tempY <= menubarHeight) && (topmenuflag == true)) {
                                userinfo_topbar.setVisibility(View.VISIBLE);
                                topmenuflag = false;
                            } else if ((nowY + tempY > menubarHeight) && (topmenuflag == flag)) {
                                userinfo_topbar.setVisibility(View.INVISIBLE);
                                topmenuflag = true;
                            }
                            int temp = position += tempY;
                            linearlayout_userinfo_scrolllayout.setY(temp);


                            float headviewtemp = headViewPosition += (tempY / 3);
                            if (mainheadview.getY() <= 0) {
                                mainheadview.setY(headviewtemp);
                            } else {
                                if (headviewtemp < 0) {
                                    mainheadview.setY(headviewtemp);
                                }
                            }
                        }
                        //顶部的动画效果
                        if ((linearlayout_userinfo_scrolllayout.getY() <= chufaHeight) && (flag == true)) {
                            ObjectAnimator anim = ObjectAnimator.ofFloat(mainheadview, "alpha", 1, 0.0f);
                            anim.setDuration(500);
                            anim.start();
                            flag = false;
                        } else if ((linearlayout_userinfo_scrolllayout.getY() > chufaHeight + 40) && (flag == false)) {
                            ObjectAnimator anim = ObjectAnimator.ofFloat(mainheadview, "alpha", 0.0f, 1f);
                            anim.setDuration(500);
                            anim.start();
                            flag = true;
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                  /*      if ((linearlayout_userinfo_scrolllayout.getY() >= 210 * scale) &&
                                (linearlayout_userinfo_scrolllayout.getY() < 260 * scale)) {
                            ObjectAnimator anim = ObjectAnimator.ofFloat(linearlayout_userinfo_scrolllayout, "translationY", scrollviewdistancetotop);
                            anim.setDuration(100);
                            anim.start();
                        }*/
                        break;
                }
                break;
            case R.id.imgbtn_userinfo_recode:
                //录音拖动框框触摸事件判断
                int Y = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //TODO 播放录音
                        layout_userinfo_popup.setVisibility(View.VISIBLE);
                        circleview_userinfo_microphone.setEndArc(0);//初始化时间圆弧
                        startVoiceT = System.currentTimeMillis();
                        voiceName = startVoiceT + ".amr";
                        /**
                         * 开始录音方法，传入音频名字为事件+.amr
                         */
                        start(voiceName);
                        break;
                    case MotionEvent.ACTION_UP:
                        layout_userinfo_popup.setVisibility(View.GONE);
                        endArc = 0;
                        stop();
                        //如果取消发送
                        if (IF_CANCLE_SEND) {
                            //TODO 取消发送
                        }
                        //如果发送
                        else {
                            //TODO 发送
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //位置判断在方框范围以内
                        if (Y <= screenHeight / 2 + 90 * scale) {
                            tv_register_show.setText("手指松开，取消发送");
                            tv_register_show.setBackground(getResources().getDrawable(R.drawable.shape_register_recodetv));
                            circleview_userinfo_microphone.setIsShowRecoding(false);

                        } else {
                            tv_register_show.setText("手指上划，取消发送");
                            tv_register_show.setBackground(null);
                            circleview_userinfo_microphone.setIsShowRecoding(true);

                        }
                        break;
                }
                break;
        }
        return false;
    }

    /**
     * 更新显示音频高低图
     *
     * @param signalEMA
     */
    private void updateDisplay(double signalEMA) {
        int temp = 100 / 12;
        switch ((int) signalEMA) {
            case 0:
                circleview_userinfo_microphone.setProgress(temp);
                break;
            case 1:
                circleview_userinfo_microphone.setProgress(2 * temp);
                break;
            case 2:
                circleview_userinfo_microphone.setProgress(3 * temp);
                break;
            case 3:
                circleview_userinfo_microphone.setProgress(4 * temp);
                break;
            case 4:
                circleview_userinfo_microphone.setProgress(5 * temp);
                break;
            case 5:
                circleview_userinfo_microphone.setProgress(6 * temp);
                break;
            case 6:
                circleview_userinfo_microphone.setProgress(7 * temp);
                break;
            case 7:
                circleview_userinfo_microphone.setProgress(8 * temp);
                break;
            case 8:
                circleview_userinfo_microphone.setProgress(9 * temp);
                break;
            case 9:
                circleview_userinfo_microphone.setProgress(10 * temp);
                break;
            case 10:
                circleview_userinfo_microphone.setProgress(11 * temp);
                break;
            case 11:
                circleview_userinfo_microphone.setProgress(12 * temp);
                break;
            default:
                break;
        }
    }

    private static String SAVE_DIR = "/aiquan/userinfovoice";    //存储用户资料界面的录音文件夹

    /**
     * 开始录音
     *
     * @param name
     */
    private void start(String name) {
        mSensor.start(name, SAVE_DIR);
        mHandler.postDelayed(ampTask, POLL_INTERVAL);
        mHandler.postDelayed(updateArcTask, UPDATE_ARC_INTERVAL);
    }

    /**
     * 停止录音方法
     */
    private void stop() {
        mHandler.removeCallbacks(mSleepTask);
        mHandler.removeCallbacks(ampTask);
        mHandler.removeCallbacks(updateArcTask);
        mSensor.stop();
        circleview_userinfo_microphone.setProgress(0);
    }

    String soundFilePath;//录音文件路径

    @Override
    public void setVoicePath(String path) {
        soundFilePath = path;
    }
}
