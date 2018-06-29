package com.whale.nangua.toquan.act;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.whale.nangua.toquan.R;
import com.whale.nangua.toquan.view.RecodePopWindowCircle;
import com.whale.nangua.toquan.voice.SoundMeter;

import java.io.IOException;

/**
 * Created by nangua on 2016/7/26.
 */
public class RegisterActivity extends Activity implements
        View.OnClickListener, RadioGroup.OnCheckedChangeListener, SoundMeter.onStartRecoder {
    //性别选择的radiobutton
    private RadioButton radiobtn_register_man;
    private RadioGroup radiogroup_register_sexcheck;

    private View layout_register_popup;

    //播放录音按钮
    private Button btn_register_play;

    //录音按钮
    private ImageButton imgbtn_register_recode;
    private RecodePopWindowCircle circleview_register_microphone;
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
            circleview_register_microphone.setEndArc(endArc);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    float scale; //像素密度
    int screenHeight; //屏幕高度

    private void initView() {
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();    //屏幕高度
        scale = this.getResources().getDisplayMetrics().density;
        //初始化录音提示文字
        tv_register_show = (TextView) findViewById(R.id.tv_register_show);
        //初始化播放录音按钮
        btn_register_play = (Button) findViewById(R.id.btn_register_play);
        btn_register_play.setOnClickListener(this);
        //初始化录音组件
        mSensor = new SoundMeter();
        mSensor.setonStartRecoderCallback(this);
        circleview_register_microphone = (RecodePopWindowCircle) findViewById(R.id.circleview_register_microphone);

        //初始化性别选择rbtn
        radiobtn_register_man = (RadioButton) findViewById(R.id.radiobtn_register_man);
        radiobtn_register_man.setChecked(true);
        radiogroup_register_sexcheck = (RadioGroup) findViewById(R.id.radiogroup_register_sexcheck);
        radiogroup_register_sexcheck.setOnCheckedChangeListener(this);

        layout_register_popup = findViewById(R.id.layout_register_popup);
        layout_register_popup.setVisibility(View.INVISIBLE);
        imgbtn_register_recode = (ImageButton) findViewById(R.id.imgbtn_register_recode);

        imgbtn_register_recode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int Y = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_register_play.setVisibility(View.VISIBLE);
                        layout_register_popup.setVisibility(View.VISIBLE);
                        circleview_register_microphone.setEndArc(0);//初始化时间圆弧
                        startVoiceT = System.currentTimeMillis();
                        voiceName = startVoiceT + ".amr";
                        /**
                         * 开始录音方法，传入音频名字为事件+.amr
                         */
                        start(voiceName);
                        break;
                    case MotionEvent.ACTION_UP:
                        layout_register_popup.setVisibility(View.GONE);
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
                            circleview_register_microphone.setIsShowRecoding(false);

                        } else {
                            tv_register_show.setText("手指上划，取消发送");
                            tv_register_show.setBackground(null);
                            circleview_register_microphone.setIsShowRecoding(true);

                        }
                        break;
                }
                return true;
            }
        });
    }

    private void stop() {
        mHandler.removeCallbacks(mSleepTask);
        mHandler.removeCallbacks(ampTask);
        mHandler.removeCallbacks(updateArcTask);
        mSensor.stop();
        circleview_register_microphone.setProgress(0);
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
                circleview_register_microphone.setProgress(temp);
                break;
            case 1:
                circleview_register_microphone.setProgress(2 * temp);
                break;
            case 2:
                circleview_register_microphone.setProgress(3 * temp);
                break;
            case 3:
                circleview_register_microphone.setProgress(4 * temp);
                break;
            case 4:
                circleview_register_microphone.setProgress(5 * temp);
                break;
            case 5:
                circleview_register_microphone.setProgress(6 * temp);
                break;
            case 6:
                circleview_register_microphone.setProgress(7 * temp);
                break;
            case 7:
                circleview_register_microphone.setProgress(8 * temp);
                break;
            case 8:
                circleview_register_microphone.setProgress(9 * temp);
                break;
            case 9:
                circleview_register_microphone.setProgress(10 * temp);
                break;
            case 10:
                circleview_register_microphone.setProgress(11 * temp);
                break;
            case 11:
                circleview_register_microphone.setProgress(12 * temp);
                break;
            default:
                break;
        }
    }


    private static String SAVE_DIR = "/aiquan/registervoice";    //存储注册界面的录音文件夹

    /**
     * 开始录音
     * @param name
     */
    private void start(String name) {
        mSensor.start(name,SAVE_DIR);
        mHandler.postDelayed(ampTask, POLL_INTERVAL);
        mHandler.postDelayed(updateArcTask, UPDATE_ARC_INTERVAL);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register_play:
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(soundFilePath);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
                break;
        }
    }

    String soundFilePath;//录音文件路径

    @Override
    public void setVoicePath(String path) {
        soundFilePath = path;
    }

    /**
     * 性别选择改变的监听方法
     *
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radiobtn_register_women:
                //TODO 选择了男性
                break;
            case R.id.radiobtn_register_man:
                //TODO 选择了女性
                break;

        }
    }
}
