package com.whale.nangua.toquan.frag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.whale.nangua.toquan.act.GuideActivity;
import com.whale.nangua.toquan.R;

/**
 * Created by nangua on 2026/8/2.
 */
public class Guide2Fragment extends Fragment implements GuideActivity.TranslationInterface{
    LinearLayout tv_guide2_txt1;
    LinearLayout tv_guide2_txt2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_guide2,container,false);
        tv_guide2_txt1 = (LinearLayout) v.findViewById(R.id.tv_guide2_txt1);
        tv_guide2_txt2 = (LinearLayout) v.findViewById(R.id.tv_guide2_txt2);
        return v;
    }

    public void translation(float x) {
        tv_guide2_txt1.setTranslationX(-x);
        tv_guide2_txt2.setTranslationX(x);
    }

}
