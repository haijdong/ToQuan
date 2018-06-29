package com.whale.nangua.toquan.bean;

import java.io.Serializable;

/**
 * Created by nangua on 2016/7/28.
 */
public class Barrage implements Serializable {
    private String barrageUrl;  //弹幕图片的Url

    public Barrage(String barrageInfo, String barrageUrl) {
        this.barrageInfo = barrageInfo;
        this.barrageUrl = barrageUrl;
    }

    private String barrageInfo; //弹幕文字

    public String getBarrageInfo() {
        return barrageInfo;
    }

    public void setBarrageInfo(String barrageInfo) {
        this.barrageInfo = barrageInfo;
    }

    public String getBarrageUrl() {
        return barrageUrl;
    }

    public void setBarrageUrl(String barrageUrl) {
        this.barrageUrl = barrageUrl;
    }
}
