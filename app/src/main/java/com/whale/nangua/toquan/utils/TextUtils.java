package com.whale.nangua.toquan.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.widget.TextView;

/**
 * 文本工具类
 * Created by nangua on 2016/7/8.
 */
public class TextUtils {

    //判断是否为空
    public static boolean isEmpty(String txt) {
        //判断引用对象是否为空，内容是否为空，长度是否为0
        if ((txt == null)||(txt.equals(null))||(txt.length()==0))
            return true;
        else
            return false;
    }

    public static void setMoreSpecialPollutantTypeNameWithOutAqi(TextView tv,TextView tv1,TextView tv2,TextView tv3,TextView tv4,TextView tv5, String polution_type) {

        String mystring = polution_type;

        int i = mystring.indexOf("2.5");
        if (i > 0) {
            mystring  = mystring.replace("2.5", "aaa");
        }
        Log.e("dhj1","  " + mystring);
        int i1 = mystring.indexOf("3");
        int i2 = mystring.indexOf("2");
        if (i2 > 0) {
            mystring  =  mystring.replace("2","a");
        }
        Log.e("dhj2","  " + mystring);
        int i3 = mystring.indexOf("2");
        int i4 = mystring.indexOf("10");




        int lSize = (int) tv.getTextSize();
        int sSize = (int) (tv.getTextSize() * 2f / 3f);
        Log.e("dhj3", i +"   " + i1 +"   " + i2+"  " + i3+"  " + i4);
        polution_type = polution_type.toUpperCase();
        SpannableString ss = new SpannableString(polution_type);
        if (i != -1) {
            ss.setSpan(new AbsoluteSizeSpan(lSize, false), 0, i, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new AbsoluteSizeSpan(sSize, false), i, i + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            Log.e("dhj","1111111111-------" + ss);
            tv1.setText(ss);
        }
        if (i1 != -1) {
            ss.setSpan(new AbsoluteSizeSpan(lSize, false), 0, i1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new AbsoluteSizeSpan(sSize, false), i1, i1 + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            Log.e("dhj","2222222222-------" + ss);
            tv2.setText(ss);
        }
        if (i2 != -1) {
            ss.setSpan(new AbsoluteSizeSpan(lSize, false), 0, i2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new AbsoluteSizeSpan(sSize, false), i, i2 + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            Log.e("dhj","4444444-------" + ss);
            tv3.setText(ss);
        }
        if (i3 != -1) {
            ss.setSpan(new AbsoluteSizeSpan(lSize, false), 0, i3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new AbsoluteSizeSpan(sSize, false), i, i3 + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            Log.e("dhj","666666666-------" + ss);
            tv4.setText(ss);
        }
        if (i4 != -1) {
            ss.setSpan(new AbsoluteSizeSpan(lSize, false), 0, i4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new AbsoluteSizeSpan(sSize, false), i, i4 + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            Log.e("dhj","8888888-------" + ss);
            tv5.setText(ss);
        }
        Log.e("dhj","sssssssssss-------" + ss);


    }
}
