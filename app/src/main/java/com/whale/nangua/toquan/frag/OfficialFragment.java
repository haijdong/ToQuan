package com.whale.nangua.toquan.frag;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.whale.nangua.toquan.act.OfficialActivity;
import com.whale.nangua.toquan.R;

/**
 * Created by nangua on 2016/8/5.
 */
public class OfficialFragment extends Fragment implements View.OnClickListener {
    ImageButton imgbtn_official_integral;   //积分说明
    ImageButton imgbtn_official_diamond;    //钻石说明
    ImageButton imgbtn_official_vip;    //Vip说明
    ImageButton imgbtn_official_exchange;   //积分兑换
    OfficialActivity officialActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_official, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        imgbtn_official_integral = (ImageButton) v.findViewById(R.id.imgbtn_official_integral);
        imgbtn_official_diamond = (ImageButton) v.findViewById(R.id.imgbtn_official_diamond);
        imgbtn_official_vip = (ImageButton) v.findViewById(R.id.imgbtn_official_vip);
        imgbtn_official_exchange = (ImageButton) v.findViewById(R.id.imgbtn_official_exchange);
        imgbtn_official_integral.setOnClickListener(this);
        imgbtn_official_diamond.setOnClickListener(this);
        imgbtn_official_vip.setOnClickListener(this);
        imgbtn_official_exchange.setOnClickListener(this);
        officialActivity = (OfficialActivity) getActivity();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgbtn_official_integral:
                officialActivity.trans(1);
                break;
            case R.id.imgbtn_official_diamond:
                officialActivity.trans(2);
                break;
            case R.id.imgbtn_official_vip:
                officialActivity.trans(3);
                break;
            case R.id.imgbtn_official_exchange:
                officialActivity.trans(4);
                break;
        }
    }
}
