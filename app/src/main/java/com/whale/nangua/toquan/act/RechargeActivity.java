package com.whale.nangua.toquan.act;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.whale.nangua.toquan.R;

/**
 * Created by nangua on 2016/7/14.
 */
public class RechargeActivity extends Activity implements View.OnClickListener{
    Button recharge_rechargebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initView();
    }

    private void initView() {
        recharge_rechargebtn = (Button) findViewById(R.id.recharge_rechargebtn);
        recharge_rechargebtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        LayoutInflater inflaterDl = LayoutInflater.from(this);
        LinearLayout layout = (LinearLayout)inflaterDl.inflate(R.layout.recharge_dialog, null );
        final Dialog dialog = new AlertDialog.Builder(RechargeActivity.this).create();
        dialog.show();
        dialog.getWindow().setContentView(layout);


    }
}
