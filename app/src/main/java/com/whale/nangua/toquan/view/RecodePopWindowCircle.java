package com.whale.nangua.toquan.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.whale.nangua.toquan.R;

/**
 * Created by nangua on 2016/7/29.
 */
public class RecodePopWindowCircle extends RelativeLayout {
    ProgressBar progressbar_register_recode;//进度条
    int width; //控件总高
    int height; //控件总宽
     boolean IS_SHOW_RECODING = true; //默认设置为true

    float scale = this.getResources().getDisplayMetrics().density; //获得像素

    public RecodePopWindowCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        //在构造函数中将Xml中定义的布局解析出来。
        LayoutInflater.from(context).inflate(R.layout.layout_recode_circlepopwindow, this, true);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        progressbar_register_recode = (ProgressBar) this.findViewById(R.id.progressbar_register_recode);
        width = getWidth();
        height = getHeight();
    }

    private void init() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(); //画笔
        if (IS_SHOW_RECODING) {
            progressbar_register_recode.setVisibility(View.VISIBLE);
            //画话筒下面的Y形,整体下移5个像素点5*scale
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(6);    //宽度
            paint.setAntiAlias(true);   //抗锯齿
            paint.setStyle(Paint.Style.STROKE); //设置空心
            RectF oval = new RectF();                     //RectF对象
            int xandy = width / 2;
            int r = (int) ( 8 * scale); //进度条底部半径
            int space = (int) (5 * scale); //圆弧与底部进度条的间隔
            oval.left = xandy - r - space;                              //左边
            oval.top = xandy - 2 * r - space + 5 * scale;                                   //上边
            oval.right = xandy + r + space;                             //右边
            oval.bottom = xandy + space + 5 * scale;
            //画弧形
            canvas.drawArc(oval, 20, 140, false, paint);
            //画线
            int lineLength = (int) (10 * scale);
            canvas.drawLine(xandy, xandy + space + 5 * scale, xandy, xandy + space + lineLength + 5 * scale, paint);
            //画弧形的圆点
            //因为位置计算没有精确化所以做了些微调
            int pointx = (int) (Math.cos(xandy) + r + space);
            paint.setStyle(Paint.Style.FILL); //设置实心
            //右边缘圆点
            canvas.drawCircle(xandy + pointx - 1 * scale, xandy + 2 * scale, 3, paint);
            //左边缘圆点
            canvas.drawCircle(xandy - pointx + 1 * scale, xandy + 2 * scale, 3, paint);
            //直线下边缘圆点
            canvas.drawCircle(xandy, xandy + space + lineLength + 5 * scale, 3, paint);
        }
        //否则显示垃圾桶界面
        else {
            progressbar_register_recode.setVisibility(View.INVISIBLE);
            Bitmap bitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.ic_trash);
            canvas.drawBitmap(bitmap,null,  new Rect((int) (width/2 - 20*scale),
                    (int) (height/2 - 25*scale),
                    (int) (width/2 + 20*scale),
                    (int) (height/2 + 25*scale)),null);
        }

        //画外围的蓝色录音圆弧
        paint.setColor(Color.parseColor("#0CA6D9"));
        paint.setStrokeWidth(2);    //宽度
        paint.setAntiAlias(true);   //抗锯齿
        paint.setStyle(Paint.Style.STROKE); //设置空心
        canvas.drawArc(new RectF(0 + 2, 0 + 2, width - 2, height - 2), -90, endArc, false, paint);
    }

    /**
     * 设置是否显示录音话筒在录音
     */
    public void setIsShowRecoding(boolean IS_SHOW_RECODING) {
        this.IS_SHOW_RECODING = IS_SHOW_RECODING;
        postInvalidate();
    }

    //设置时间圆弧终止角度
    public void setEndArc(int endArc) {
        this.endArc = endArc;
        postInvalidate();
    }

    //设置进度
    public void setProgress(int progress) {
        progressbar_register_recode.setProgress(progress);
    }


    int endArc = 0; //圆弧终止角度
}
