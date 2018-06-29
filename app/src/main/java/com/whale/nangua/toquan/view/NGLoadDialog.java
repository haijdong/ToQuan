package com.whale.nangua.toquan.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.whale.nangua.toquan.R;

/**
 * Created by nangua on 2016/8/10.
 */
public class NGLoadDialog extends RelativeLayout {
    float scale;    //像素密度
    float height;     //控件高度
    float with;       //控件宽度

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        height = this.getHeight();
        with = this.getWidth();
        CIRCLE_R = with / 3 / 3 / 2;
        float temp = CIRCLE_R/6;
        CIRCLE_R1 = CIRCLE_R;
        CIRCLE_R2 = CIRCLE_R - temp;
        CIRCLE_R3 = CIRCLE_R - 2*temp;
        CIRCLE_SPACE = 1 * scale;
    }

    public NGLoadDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.dialog_load, this, true);
        scale = context.getResources().getDisplayMetrics().density;       //获得像素密度
        init();

    }

    private void init() {
    }

    private float CIRCLE_R; //球半径
    private float CIRCLE_SPACE;//球间距
    private float CIRCLE_R1;    //球1半径
    private float CIRCLE_R2;    //球2半径
    private float CIRCLE_R3;    //球3半径
    private boolean CIRCLE_R1_STATE = true;    //球1状态，true缩小false增大
    private boolean CIRCLE_R2_STATE = true;    //球2状态，true缩小false增大
    private boolean CIRCLE_R3_STATE = true;    //球3状态，true缩小false增大

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(CIRCLE_R);
        paint.setStyle(Paint.Style.FILL);
        //画三个圆
        //第一个
        canvas.drawCircle(with / 2 - 2 * CIRCLE_R - CIRCLE_SPACE,
                height / 3,
                CIRCLE_R1,
                paint
        );
        if (CIRCLE_R1_STATE) {
            CIRCLE_R1 = CIRCLE_R1 - 0.5f;
            if (CIRCLE_R1 <= CIRCLE_R / 2) {
                CIRCLE_R1_STATE = false;
            }
        } else {
            CIRCLE_R1 = CIRCLE_R1 + 0.5f;
            if (CIRCLE_R1 >= CIRCLE_R) {
                CIRCLE_R1_STATE = true;
            }
        }
        //第二个
        canvas.drawCircle(with / 2,
                height / 3,
                CIRCLE_R2,
                paint
        );
        if (CIRCLE_R2_STATE) {
            CIRCLE_R2 = CIRCLE_R2 - 0.5f;
            if (CIRCLE_R2 <= CIRCLE_R / 2) {
                CIRCLE_R2_STATE = false;
            }
        } else {
            CIRCLE_R2 = CIRCLE_R2 + 0.5f;
            if (CIRCLE_R2 >= CIRCLE_R) {
                CIRCLE_R2_STATE = true;
            }
        }
        //第三个
        canvas.drawCircle(with / 2 + 2 * CIRCLE_R + CIRCLE_SPACE,
                height / 3,
                CIRCLE_R3,
                paint
        );
        if (CIRCLE_R3_STATE) {
            CIRCLE_R3 = CIRCLE_R3 - 0.5f;
            if (CIRCLE_R3 <= CIRCLE_R / 2) {
                CIRCLE_R3_STATE = false;
            }
        } else {
            CIRCLE_R3 = CIRCLE_R3 + 0.5f;
            if (CIRCLE_R3 >= CIRCLE_R) {
                CIRCLE_R3_STATE = true;
            }
        }
        postInvalidateDelayed(30);
    }

}
