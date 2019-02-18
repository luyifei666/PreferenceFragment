package com.clfsjkj.govcar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.clfsjkj.govcar.base.BaseActivity;
import com.clfsjkj.govcar.customerview.SlideBackLayout;
import com.kongzue.dialog.listener.InputDialogOkButtonClickListener;
import com.kongzue.dialog.v2.InputDialog;
import com.kongzue.dialog.v2.SelectDialog;
import com.kongzue.dialog.v2.TipDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderChangeActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_car_path)
    TextView tvCarPath;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_back_time)
    TextView tvBackTime;
    @BindView(R.id.tv_real_car_path)
    TextView tvRealCarPath;
    @BindView(R.id.tv_real_car_start_time)
    TextView tvRealCarStartTime;
    @BindView(R.id.tv_real_car_back_time)
    TextView tvRealCarBackTime;
    @BindView(R.id.btn_reject)
    Button btnReject;
    @BindView(R.id.btn_pass)
    Button btnPass;
    @BindView(R.id.btn_status)
    LinearLayout btnStatus;

    public static final String REQUEST_CODE_PATH = "888";
    private String mSearchLat;//纬度
    private String mSearchLon;//经度
    private String addr;//地名
    private String addrDes;//地址
    private Context mContext;
    private TimePickerView pvTime;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_change);
        ButterKnife.bind(this);
        //创建侧滑关闭 Activity 控件
        SlideBackLayout mSlideBackLayout = new SlideBackLayout(this);
        //绑定 Activity
        mSlideBackLayout.bindActivity(this);
        //mSlideBackLayout与toolbar会冲突，这里解决
        mSlideBackLayout.addNotInterceptView(mToolbar);
        mContext = this;
        mTitle = getIntent().getStringExtra("title");
//        mTitle = getIntent().getStringExtra("title");
        btnStatus.setVisibility(View.VISIBLE);
        btnReject.setText("驳回");
        btnPass.setVisibility(View.VISIBLE);
        btnPass.setText("同意");
        initMyToolBar();
    }

    private void initMyToolBar() {
        initToolBar(mToolbar, mTitle, R.drawable.gank_ic_back_white);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回
            }
        });
    }

    private void initStartTime() {
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tvRealCarStartTime.setText(getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, true, true, true})
                .isDialog(true)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
        pvTime.show();
    }

    private void initEndTime() {
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tvRealCarBackTime.setText(getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, true, true, true})
                .isDialog(true)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("aaa)", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    @OnClick({R.id.tv_real_car_path, R.id.tv_real_car_start_time, R.id.tv_real_car_back_time, R.id.btn_reject, R.id.btn_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_real_car_path://途径地
                Intent it;
                it = new Intent(OrderChangeActivity.this, BaiduMapPoiActivity.class);
                it.putExtra("REQUEST_CODE", REQUEST_CODE_PATH);
                it.putExtra("TITLE", "请选择途径");
                startActivityForResult(it, 999);
                break;
            case R.id.tv_real_car_start_time://真实出车时间
                initStartTime();
                pvTime.show(); //弹出自定义时间选择器
                break;
            case R.id.tv_real_car_back_time://真实回车时间
                initEndTime();
                pvTime.show(); //弹出自定义时间选择器
                break;
            case R.id.btn_reject:
                InputDialog.show(mContext, "请输入驳回信息", "", "确定", new InputDialogOkButtonClickListener() {
                    @Override
                    public void onClick(Dialog dialog, String inputText) {
                        Toast.makeText(mContext, "您输入了：" + inputText, Toast.LENGTH_SHORT).show();
                    }
                }, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                break;
            case R.id.btn_pass:
                SelectDialog.show(mContext, "确认提交？", "", new DialogInterface.OnClickListener() {
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
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Integer.valueOf(REQUEST_CODE_PATH) && data != null) {
            //途径地
            mSearchLat = data.getStringExtra("lat");
            mSearchLon = data.getStringExtra("lon");
            addr = data.getStringExtra("addr");
            addrDes = data.getStringExtra("addrDes");
            if (null == addrDes) {
                tvRealCarPath.setText(addr);
            } else {
                tvRealCarPath.setText(addrDes + addr);
            }
        }
    }
}
