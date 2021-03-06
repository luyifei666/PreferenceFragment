package com.clfsjkj.govcar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.clfsjkj.govcar.base.BaseActivity;
import com.clfsjkj.govcar.customerview.SlideBackLayout;
import com.kongzue.dialog.v2.SelectDialog;
import com.kongzue.dialog.v2.TipDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserEvaluationActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.order_num)
    TextView orderNum;
    @BindView(R.id.order_status)
    TextView orderStatus;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.driverName)
    TextView driverName;
    @BindView(R.id.carNum)
    TextView carNum;
    @BindView(R.id.useTime)
    TextView useTime;
    @BindView(R.id.backTime)
    TextView backTime;
    @BindView(R.id.start)
    TextView start;
    @BindView(R.id.end)
    TextView end;
    @BindView(R.id.star)
    RatingBar star;
    @BindView(R.id.checkbox1)
    CheckBox checkbox1;
    @BindView(R.id.checkbox2)
    CheckBox checkbox2;
    @BindView(R.id.checkbox3)
    CheckBox checkbox3;
    @BindView(R.id.checkbox4)
    CheckBox checkbox4;
    @BindView(R.id.et_orther)
    EditText etOrther;
    @BindView(R.id.btn_car_apply)
    Button btnCarApply;
    //用车评价详情
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_evaluation);
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
        checkbox1.setOnCheckedChangeListener(this);
        checkbox2.setOnCheckedChangeListener(this);
        checkbox3.setOnCheckedChangeListener(this);
        checkbox4.setOnCheckedChangeListener(this);
        star.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.e("aaa", "rating = " + rating + ",fromUser = " + fromUser);
            }
        });
    }

    private void initMyToolBar() {
        initToolBar(mToolbar, "订单评价", R.drawable.gank_ic_back_white);
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.checkbox1:
                Log.e("aaa", "checkbox1 isChecked = " + isChecked);
                break;
            case R.id.checkbox2:
                Log.e("aaa", "checkbox21 isChecked = " + isChecked);
                break;
            case R.id.checkbox3:
                Log.e("aaa", "checkbox3 isChecked = " + isChecked);
                break;
            case R.id.checkbox4:
                Log.e("aaa", "checkbox4 isChecked = " + isChecked);
                break;
            default:
                break;
        }
    }
}
