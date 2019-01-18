package com.clfsjkj.govcar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.clfsjkj.govcar.base.BaseActivity;
import com.kongzue.dialog.v2.SelectDialog;
import com.kongzue.dialog.v2.TipDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarBackActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ck_suixing)
    Button mGPS;
    @BindView(R.id.btn_reject)
    Button btnReject;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_back);
        ButterKnife.bind(this);
        mContext = this;
//        mTitle = getIntent().getStringExtra("title");
        initMyToolBar();
    }

    private void initMyToolBar() {
        initToolBar(mToolbar, "归队详情", R.drawable.gank_ic_back_white);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回
            }
        });
    }

    @OnClick({R.id.ck_suixing, R.id.btn_reject})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ck_suixing://GPS
                showProgressDialog("载入中");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        dissmissProgressDialog();
                    }
                }).start();
                break;
            case R.id.btn_reject:
                SelectDialog.show(mContext, "确认提交吗?", "", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TipDialog.show(mContext, "完成", TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_FINISH);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                SystemClock.sleep(1000);
                                finish();
                            }
                        }).start();
                    }
                }, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(mContext, "您点击了取消按钮", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
