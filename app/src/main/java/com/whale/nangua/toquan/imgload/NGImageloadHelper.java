package com.whale.nangua.toquan.imgload;

import android.widget.ImageView;

import com.whale.nangua.toquan.R;
import com.whale.nangua.toquan.view.NGNormalCircleImageView;


/**
 * 图片加载框架使用帮助类
 * Created by nangua on 2016/7/8.
 */
public class NGImageloadHelper {
    /**
     * 处理图片
     * @param view
     * @param url
     */
    public static void displayImage(NGNormalCircleImageView view,
                                    String url) {
        //添加任务
        NGDownloadImage.getInstance().addTask(url,
                view);
        //执行任务
        NGDownloadImage.getInstance().doTask();
    }

    /**
     * 带回调实例的加载图片
     * @param view
     * @param url
     */
    public static void displayImage(NGDownloadImage.NGImageCallback callback,ImageView view,
                                    String url) {
        view.setImageResource(R.drawable.pumpkin);
        //添加任务
        NGDownloadImage.getInstance().addTask( url,
                view,callback);
        //执行任务
        NGDownloadImage.getInstance().doTask();
    }

}
