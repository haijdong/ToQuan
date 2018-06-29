package com.whale.nangua.toquan.act;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.whale.nangua.toquan.R;

public class Guide2Activity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;

    private ViewFlipper viewFlipper;
    private GestureDetector gestureDetector = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide2);
        viewFlipper = (ViewFlipper) findViewById(R.id.id_viewPager);
        gestureDetector = new GestureDetector(this);

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);


//        startAnimation();
    }


    private void startAnimation() {
        ObjectAnimator anim1 = new ObjectAnimator().ofFloat(imageView1, "alpha", 1f, 0f).setDuration(5000);
        ObjectAnimator anim2 = new ObjectAnimator().ofFloat(imageView2, "alpha", 0f, 1f).setDuration(5000);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(anim1, anim2);

        ObjectAnimator anim3 = new ObjectAnimator().ofFloat(imageView2, "alpha", 1f, 0f).setDuration(5000);
        ObjectAnimator anim4 = new ObjectAnimator().ofFloat(imageView3, "alpha", 0f, 1f).setDuration(5000);
        AnimatorSet set1 = new AnimatorSet();
        set1.playTogether(anim3, anim4);

        ObjectAnimator anim5 = new ObjectAnimator().ofFloat(imageView3, "alpha", 1f, 0f).setDuration(5000);
        ObjectAnimator anim6 = new ObjectAnimator().ofFloat(imageView1, "alpha", 0f, 1f).setDuration(5000);
        AnimatorSet set2 = new AnimatorSet();
        set2.playTogether(anim5, anim6);

        AnimatorSet set3 = new AnimatorSet();
        set3.playSequentially(set, set1, set2);
        set3.addListener(new AnimatorListenerAdapter() {

            private boolean mCanceled;

            @Override
            public void onAnimationStart(Animator animation) {
                mCanceled = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCanceled = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!mCanceled) {
                    animation.start();
                }
            }

        });
        set3.start();
    }

    public boolean onDown(MotionEvent arg0) {
        return false;
    }

    public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
                           float arg3) {

        if (arg0.getX() - arg1.getX() > 120) {
            this.viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                    R.anim.push_left_in));
            this.viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                    R.anim.push_left_out));
            this.viewFlipper.showNext();
            return true;
        } else if (arg0.getX() - arg1.getX() < -120) {
            this.viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                    R.anim.push_right_in));
            this.viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                    R.anim.push_right_out));
            this.viewFlipper.showPrevious();
            return true;
        }
        return true;
    }

    public void onLongPress(MotionEvent e) {
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return false;
    }

    public void onShowPress(MotionEvent e) {
    }

    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.gestureDetector.onTouchEvent(event);
    }
}
