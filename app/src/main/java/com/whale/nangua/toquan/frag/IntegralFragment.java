package com.whale.nangua.toquan.frag;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.whale.nangua.toquan.R;

/**
 * Created by nangua on 2016/8/5.
 */
public class IntegralFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_integral,container,false);
        return v;
    }

}
