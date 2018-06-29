package com.whale.nangua.toquan.act;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.whale.nangua.toquan.R;
import com.whale.nangua.toquan.frag.DiamondFragment;
import com.whale.nangua.toquan.frag.ExchangeFragment;
import com.whale.nangua.toquan.frag.IntegralFragment;
import com.whale.nangua.toquan.frag.OfficialFragment;
import com.whale.nangua.toquan.frag.VipFragment;

import java.util.ArrayList;

/**
 * Created by nangua on 2016/8/5.
 */
public class OfficialActivity extends Activity  {

    FragmentTransaction transaction;
    FragmentManager fm;
    //主frag
    OfficialFragment officialFragment;
    //四个分frag
    IntegralFragment integralFragment;
    DiamondFragment diamondFragment;
    VipFragment vipFragment;
    ExchangeFragment exchangeFragment;

    ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);
        initView();
    }

    private void initView() {
        fragments = new ArrayList<>();
        integralFragment = new IntegralFragment();
        diamondFragment = new DiamondFragment();
        vipFragment = new VipFragment();
        exchangeFragment = new ExchangeFragment();
        officialFragment = new OfficialFragment();
        fragments.add(officialFragment);
        fragments.add(integralFragment);
        fragments.add(diamondFragment);
        fragments.add(vipFragment);
        fragments.add(exchangeFragment);
        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        transaction.add(R.id.fragment_official, fragments.get(0))
                .add(R.id.fragment_official, fragments.get(1))
                .add(R.id.fragment_official, fragments.get(2))
                .add(R.id.fragment_official, fragments.get(3))
                .add(R.id.fragment_official, fragments.get(4))
                .show(fragments.get(0))
                .hide(fragments.get(1))
                .hide(fragments.get(2))
                .hide(fragments.get(3))
                .hide(fragments.get(4))
                .commit();
    }


    private int showPage = 0;   //0显示主页，1-4显示具体页

    /**
     * 根据传来的参数改变fragment
     *
     * @param num
     */
    public void trans(int num) {
        showPage = num;
        transaction = fm.beginTransaction();
        for (int i = 0;i<fragments.size();i++) {
            Fragment tempfrag = fragments.get(i);
            if (i==num) {
                transaction.show(tempfrag);
            } else {
                transaction.hide(tempfrag);
            }
        }
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        if (showPage!=0) {
            trans(0);
        } else {
            super.onBackPressed();
        }
    }
}
