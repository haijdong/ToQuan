package com.whale.nangua.toquan.voice;

import java.io.File;
import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import com.whale.nangua.toquan.bean.CreateFiles;

public  class SoundMeter {
	static final private double EMA_FILTER = 0.6;
	
	private static String SAVE_DIR = "/aiquanvoice";    //默认存储录音的文件夹名
	private MediaRecorder mRecorder = null;
	
	private double mEMA = 0.0;


    /**
     * 开始录音方法
     * 这里应该在每次开始录音之前清楚以前录音的文件,确保只有一个录音文件
     * @param name
     */
	public void start(String name,String savedir) {
        if (mRecorder == null) {
            File dir = new File(Environment.getExternalStorageDirectory()
                    ,savedir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            //删除完之前所有内容
           File[] files = dir.listFiles();

            for(File file:files) {
                file.delete();
            }

            File soundFile = new File(dir,name) ;
            if (!soundFile.exists()) {
                try {
                    soundFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            callback.setVoicePath(soundFile.getAbsolutePath());
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
            mRecorder.setOutputFile(soundFile.getAbsolutePath());

            try {
                mRecorder.prepare();
                mRecorder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

    public void setonStartRecoderCallback(onStartRecoder callback) {
        this.callback = callback;
    }

    private onStartRecoder callback;

    public interface onStartRecoder{
        void setVoicePath(String name);
    }

	public void stop() {
		if (mRecorder != null) {
			try{
				mRecorder.stop();//抓异常时因为start方法和stop方法调用时间太短（小于1秒），会有异常。
			}
			catch(Exception ex){}
			mRecorder.release();
			mRecorder = null;
		}
	}

	public void start() {
		if (mRecorder != null) {
			mRecorder.start();
		}
	}

	public double getAmplitude() {
		if (mRecorder != null)
			return (mRecorder.getMaxAmplitude() / 2700.0);
		else
			return 0;
	}

	public double getAmplitudeEMA() {
		double amp = getAmplitude();
		mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;
		return mEMA;
	}
}
