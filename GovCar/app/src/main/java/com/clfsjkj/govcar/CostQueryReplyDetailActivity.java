package com.clfsjkj.govcar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.clfsjkj.govcar.base.BaseActivity;
import com.clfsjkj.govcar.customerview.SlideBackLayout;
import com.kongzue.dialog.v2.SelectDialog;
import com.kongzue.dialog.v2.TipDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CostQueryReplyDetailActivity extends BaseActivity {
    //费用质疑回复详情
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.btn_car_apply)
    Button btnCarApply;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_query_reply_detail);
        ButterKnife.bind(this);
        //创建侧滑关闭 Activity 控件
        SlideBackLayout mSlideBackLayout = new SlideBackLayout(this);
        //绑定 Activity
        mSlideBackLayout.bindActivity(this);
        //mSlideBackLayout与toolbar会冲突，这里解决
        mSlideBackLayout.addNotInterceptView(mToolbar);
        mContext = this;
        initMyToolBar();
        setStatusBarFullTransparent();
        setFitSystemWindow(true);
    }

    private void initMyToolBar() {
        initToolBar(mToolbar, "质疑回复", R.drawable.gank_ic_back_white);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回
            }
        });
    }

    @OnClick(R.id.btn_car_apply)
    public void onViewClicked() {
        SelectDialog.show(mContext, "提示", "确认提交？", new DialogInterface.OnClickListener() {
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
        });
    }
}
