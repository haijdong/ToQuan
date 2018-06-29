package com.whale.nangua.toquan.act;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.whale.nangua.toquan.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by nangua on 2016/7/30.
 */
public class TestMediaRecoder extends Activity implements View.OnClickListener {

    Button luzhi;
    Button stopluzhi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testmediarecode_layout);
        initView();
    }

    private void initView() {
        luzhi = (Button) findViewById(R.id.luzhi);
        stopluzhi = (Button) findViewById(R.id.stopluzhi);
        luzhi.setOnClickListener(this);
        stopluzhi.setOnClickListener(this);
    }

    private MediaRecorder mp = null;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.luzhi:
                startRecord();
                break;
            case R.id.stopluzhi:
                stopRecord();
                break;
        }
    }

    private void stopRecord() {
        if (mp!=null) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }

    private void startRecord() {
        if (mp == null) {
            File dir = new File(Environment.getExternalStorageDirectory()
                    ,"nanguasounds");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            //删除完所有内容
            File[] files = dir.listFiles();
            for(File file:files) {
                file.delete();
            }

            File soundFile = new File(dir,System.currentTimeMillis()+".amr") ;
            if (!soundFile.exists()) {
                try {
                    soundFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            mp = new MediaRecorder();
            mp.setAudioSource(MediaRecorder.AudioSource.MIC);
            mp.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
            mp.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
            mp.setOutputFile(soundFile.getAbsolutePath());

            try {
                mp.prepare();
                mp.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
