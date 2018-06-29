package com.whale.nangua.toquan.act;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.whale.nangua.toquan.R;
import com.whale.nangua.toquan.frag.GuardFragment;
import com.whale.nangua.toquan.frag.HotFragment;
import com.whale.nangua.toquan.frag.NewestFragment;
import com.whale.nangua.toquan.view.NGHorizontalScrollCursor;

import java.util.ArrayList;

/**
 * Created by nangua on 2016/8/5.
 */
public class FindActivity extends FragmentActivity implements
        NGHorizontalScrollCursor.onViewPagerChanggedListner, View.OnClickListener {
    ViewPager vp_find;
    ArrayList<Fragment> frags_find_lists;
    int[] rbtns_find_ids = {
            R.id.rbtn_find_guard, R.id.rbtn_find_hot, R.id.rbtn_find_newsest
    };
    ArrayList<RadioButton> rbtns_find;
    ImageButton imgbtn_find_addvideo;
    NGHorizontalScrollCursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_find);
        initView();
    }

    private void initView() {
        imgbtn_find_addvideo = (ImageButton) findViewById(R.id.imgbtn_find_addvideo);
        imgbtn_find_addvideo.setOnClickListener(this);
        //初始化radiobutton
        rbtns_find = new ArrayList<>();
        for (int i = 0; i < rbtns_find_ids.length; i++) {
            rbtns_find.add((RadioButton) findViewById(rbtns_find_ids[i]));
            rbtns_find.get(i).setOnClickListener(this);
        }

        frags_find_lists = new ArrayList<>();
        GuardFragment guardFragment = new GuardFragment();
        HotFragment hotFragment = new HotFragment();
        NewestFragment newestFragment = new NewestFragment();
        frags_find_lists.add(guardFragment);
        frags_find_lists.add(hotFragment);
        frags_find_lists.add(newestFragment);
        vp_find = (ViewPager) findViewById(R.id.vp_find);

        vp_find.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Fragment getItem(int arg0) {
                return frags_find_lists.get(arg0);
            }
        });
        vp_find.setCurrentItem(1);
        cursor = (NGHorizontalScrollCursor) findViewById(R.id.sc_find);
        cursor.setcallback(this);
        cursor.setViewPager(vp_find);   //回调传参

    }

    @Override
    public void CheckPage(int position) {
        rbtns_find.get(position).setTextColor(Color.WHITE);
        for (int i = 0; i < rbtns_find.size(); i++) {
            if (i != position) {
                rbtns_find.get(i).setTextColor(Color.parseColor("#BCBECA"));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgbtn_find_addvideo:
                Intent i = new Intent(FindActivity.this, PrivateVideoActivity.class);
                startActivity(i);
                break;
            case R.id.rbtn_find_guard:
                vp_find.setCurrentItem(0);
                break;
            case R.id.rbtn_find_hot:
                vp_find.setCurrentItem(1);
                break;
            case R.id.rbtn_find_newsest:
                vp_find.setCurrentItem(2);
                break;
        }
    }
}
