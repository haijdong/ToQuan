package com.whale.nangua.toquan.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.whale.nangua.toquan.R;

/**
 * Created by nangua on 2016/8/9.
 */
public class RelativelayoutOthersInfo extends RelativeLayout {
    private NGCircleImageView circleview_othersinfo_othersportrait;
    private ImageView circleview_othersinfo_playandpause;

    private int portraitCircleX;    //头像圆心X坐标
    private int portraitCircleY;    //头像圆心Y坐标
    private int playbtnCircleX;     //播放按钮X坐标
    private  int playbtnCircleY;    //播放按钮Y坐标
    private int portraitR;  //头像圆半径
    private int playbtnR;   //播放按钮半径

    public RelativelayoutOthersInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
        //在构造函数中将Xml中定义的布局解析出来。
        LayoutInflater.from(context).inflate(R.layout.relativelayout_othersinfo_infoshow, this, true);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        portraitCircleX = ( circleview_othersinfo_othersportrait.getLeft() +
                circleview_othersinfo_othersportrait.getRight() )/2;
        portraitCircleY = ( circleview_othersinfo_othersportrait.getTop() +
                circleview_othersinfo_othersportrait.getBottom() )/2;
        playbtnCircleX = ( circleview_othersinfo_playandpause.getLeft() +
                circleview_othersinfo_playandpause.getRight() )/2;
        playbtnCircleY = ( circleview_othersinfo_playandpause.getTop() +
                circleview_othersinfo_playandpause.getBottom() )/2;

        portraitR = ( circleview_othersinfo_othersportrait.getRight() -
                circleview_othersinfo_othersportrait.getLeft() )/2;
        playbtnR = ( circleview_othersinfo_playandpause.getRight() -
                circleview_othersinfo_playandpause.getLeft() )/2;
    }

    private void init() {
        circleview_othersinfo_othersportrait = (NGCircleImageView) this.findViewById(R.id.circleview_othersinfo_othersportrait);
        circleview_othersinfo_playandpause = (ImageView) this.findViewById(R.id.circleview_othersinfo_playandpause);
    }

    private int PLAYBTN_BORDER_WIDTH = 5;   //播放按钮边框宽

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //先画播放按钮圆框
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(PLAYBTN_BORDER_WIDTH);
        RectF oval = new RectF();
        oval.left = circleview_othersinfo_playandpause.getLeft() - PLAYBTN_BORDER_WIDTH;
        oval.right = circleview_othersinfo_playandpause.getRight()+PLAYBTN_BORDER_WIDTH;
        oval.top = circleview_othersinfo_playandpause.getTop() - PLAYBTN_BORDER_WIDTH;
        oval.bottom = circleview_othersinfo_playandpause.getBottom() + PLAYBTN_BORDER_WIDTH;
        canvas.drawArc(oval, 0, 360, false, paint);
        //再画连接两部分的长方形
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(playbtnR);
        canvas.drawRect(portraitCircleX,
                portraitCircleY -playbtnR - PLAYBTN_BORDER_WIDTH,
                playbtnCircleX ,
                playbtnCircleY + playbtnR + PLAYBTN_BORDER_WIDTH,
                paint
        );

    }
}
