package com.clfsjkj.govcar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.clfsjkj.govcar.ItemAdapter.SelectedCarsAdapter;
import com.clfsjkj.govcar.base.BaseActivity;
import com.clfsjkj.govcar.bean.CarAndDriverBean;
import com.clfsjkj.govcar.customerview.SlideBackLayout;
import com.kongzue.dialog.listener.OnMenuItemClickListener;
import com.kongzue.dialog.v2.BottomMenu;
import com.kongzue.dialog.v2.SelectDialog;
import com.kongzue.dialog.v2.TipDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kongzue.dialog.v2.Notification.SHOW_TIME_SHORT;

public class DispatchCarsActivity extends BaseActivity {
    //需要派车页面

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ll_select)
    LinearLayout llSelect;
    @BindView(R.id.btn_reject)
    Button btnReject;
    @BindView(R.id.btn_pass)
    Button btnPass;
    @BindView(R.id.btn_status)
    LinearLayout btnStatus;
    @BindView(R.id.recyclerView_selected)
    RecyclerView recyclerViewSelected;
    private Context mContext;
    private boolean isShowBtnGroup;
    private String mTitle;
    private SelectedCarsAdapter adapter;
    private List<CarAndDriverBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_car);
        ButterKnife.bind(this);
        //创建侧滑关闭 Activity 控件
        SlideBackLayout mSlideBackLayout = new SlideBackLayout(this);
        //绑定 Activity
        mSlideBackLayout.bindActivity(this);
        mContext = this;
        mTitle = getIntent().getStringExtra("title");
        isShowBtnGroup = getIntent().getBooleanExtra("isShowBtnGroup", false);
        if (isShowBtnGroup) {
            btnStatus.setVisibility(View.VISIBLE);
        }
        initMyToolBar();
        setStatusBarFullTransparent();
        setFitSystemWindow(true);
        initWidget();
        initAdapter();
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

    private void initWidget() {
        btnStatus.setVisibility(View.VISIBLE);
        btnReject.setText("下发车队");
        btnPass.setText("派遣车辆");
        recyclerViewSelected.setLayoutManager(new GridLayoutManager(this, 2) {
            @Override
            public boolean canScrollVertically() {
                //解决ScrollView里存在多个RecyclerView时滑动卡顿的问题
                //如果你的RecyclerView是水平滑动的话可以重写canScrollHorizontally方法
                return false;
            }
        });
        //解决数据加载不完的问题
        recyclerViewSelected.setNestedScrollingEnabled(false);
        recyclerViewSelected.setHasFixedSize(true);

    }

    private void initAdapter() {
        data = new ArrayList<>();
        CarAndDriverBean bean;
        bean = new CarAndDriverBean();
        bean.setCarNum("云A00001");
        bean.setDriverName("张三");
        data.add(bean);
        bean = new CarAndDriverBean();
        bean.setCarNum("云A00002");
        bean.setDriverName("张三2");
        data.add(bean);
        bean = new CarAndDriverBean();
        bean.setCarNum("云A00003");
        bean.setDriverName("张三3");
        data.add(bean);
        bean = new CarAndDriverBean();
        bean.setCarNum("云A00004");
        bean.setDriverName("张三4");
        data.add(bean);
        bean = new CarAndDriverBean();
        bean.setCarNum("云A00005");
        bean.setDriverName("张三5");
        data.add(bean);
        bean = new CarAndDriverBean();
        bean.setCarNum("云A00006");
        bean.setDriverName("张三6");
        data.add(bean);
        adapter = new SelectedCarsAdapter(mContext, data, true);//是否可删除，是
        recyclerViewSelected.setAdapter(adapter);
    }


    @OnClick({R.id.ll_select, R.id.btn_reject, R.id.btn_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_select:
                //跳转到选择车辆页面
                Intent it = new Intent(DispatchCarsActivity.this, SelectCarActivity.class);
//                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(it);
                ;
                break;
            case R.id.btn_reject:
                //下发车队
                List<String> list = new ArrayList<>();
                list.add("车队1");
                list.add("车队2");
                list.add("车队3");
                BottomMenu.show((AppCompatActivity) mContext, list, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        Toast.makeText(mContext, "车队 " + text + " 被点击了", SHOW_TIME_SHORT).show();
                    }
                }, true);
                break;
            case R.id.btn_pass:
                //调接口传数据，派车成功
                SelectDialog.show(mContext, "确认派车吗?", "", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TipDialog.show(mContext, "完成", TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_FINISH);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                SystemClock.sleep(1000);
                                startActivity(new Intent(DispatchCarsActivity.this, MainActivity.class));
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
