package com.whale.nangua.toquan.frag;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whale.nangua.toquan.R;

/**
 * Created by nangua on 2016/8/5.
 */
public class VipFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vip,container,false);
        return v;
    }
}
