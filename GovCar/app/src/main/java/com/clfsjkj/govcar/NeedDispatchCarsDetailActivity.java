package com.clfsjkj.govcar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.clfsjkj.govcar.ItemAdapter.EnclosureAdapter;
import com.clfsjkj.govcar.ItemAdapter.TimeLineAdapter;
import com.clfsjkj.govcar.base.BaseActivity;
import com.clfsjkj.govcar.bean.EnclosureBean;
import com.clfsjkj.govcar.bean.TimeLineBean;
import com.clfsjkj.govcar.customerview.SlideBackLayout;
import com.clfsjkj.govcar.imageloader.GlideImageLoader;
import com.clfsjkj.govcar.utils.ToastUtils;
import com.kevin.photo_browse.ImageBrowseIntent;
import com.kongzue.dialog.listener.InputDialogOkButtonClickListener;
import com.kongzue.dialog.v2.InputDialog;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.clfsjkj.govcar.MainApplication.maxImgCount;

public class NeedDispatchCarsDetailActivity extends BaseActivity {

    private static final int PERMISSION_CALL_PHONE_CONTACT = 0x001;
    private static final int PERMISSION_CALL_PHONE_USERTEL = 0x002;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.order_num)
    TextView orderNum;
    @BindView(R.id.order_status)
    TextView orderStatus;
    @BindView(R.id.tv_car_use_contacts_tel)
    TextView tvCarUseContactsTel;
    @BindView(R.id.tv_car_user_tel)
    TextView tvCarUserTel;
    @BindView(R.id.btn_reject)
    Button btnReject;
    @BindView(R.id.btn_pass)
    Button btnPass;
    @BindView(R.id.btn_status)
    LinearLayout btnStatus;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView_time_line)
    RecyclerView recyclerViewTimeLine;
    private Context mContext;
    private ArrayList<EnclosureBean> mDataList;
    private ArrayList<TimeLineBean> mTimeLineList;
    private boolean isShowBtnGroup;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_dispatch_car_detail);
        ButterKnife.bind(this);
        //创建侧滑关闭 Activity 控件
        SlideBackLayout mSlideBackLayout = new SlideBackLayout(this);
        //绑定 Activity
        mSlideBackLayout.bindActivity(this);
        //mSlideBackLayout与toolbar会冲突，这里解决
        mSlideBackLayout.addNotInterceptView(mToolbar);
        mContext = this;
        mTitle = getIntent().getStringExtra("title");
        isShowBtnGroup = getIntent().getBooleanExtra("isShowBtnGroup", false);
        if (isShowBtnGroup) {
            btnStatus.setVisibility(View.VISIBLE);
        }
        initMyToolBar();
        setStatusBarFullTransparent();
        setFitSystemWindow(true);
        //最好放到 Application oncreate执行
        initImagePicker();
        initWidget();
        initData();
        initAdapter();
    }

    private void initData() {
        mDataList = new ArrayList<EnclosureBean>();
        EnclosureBean enclosureBean;
        enclosureBean = new EnclosureBean();
        enclosureBean.setPicName("One");
        enclosureBean.setPicUrl("https://ws1.sinaimg.cn/large/0065oQSqgy1fxd7vcz86nj30qo0ybqc1.jpg");
        mDataList.add(enclosureBean);
        enclosureBean = new EnclosureBean();
        enclosureBean.setPicName("Two");
        enclosureBean.setPicUrl("https://ws1.sinaimg.cn/large/0065oQSqgy1fwyf0wr8hhj30ie0nhq6p.jpg");
        mDataList.add(enclosureBean);
        enclosureBean = new EnclosureBean();
        enclosureBean.setPicName("Three");
        enclosureBean.setPicUrl("https://ws1.sinaimg.cn/large/0065oQSqgy1fwgzx8n1syj30sg15h7ew.jpg");
        mDataList.add(enclosureBean);
        enclosureBean = new EnclosureBean();
        enclosureBean.setPicName("Four");
        enclosureBean.setPicUrl("https://ws1.sinaimg.cn/large/0065oQSqly1fw8wzdua6rj30sg0yc7gp.jpg");
        mDataList.add(enclosureBean);

        mTimeLineList = new ArrayList<>();
        TimeLineBean timeLineBean;
        timeLineBean = new TimeLineBean();
        timeLineBean.setTime("2018-11-26 08:30");
        timeLineBean.setTitle("申请成功");
        mTimeLineList.add(timeLineBean);
        timeLineBean = new TimeLineBean();
        timeLineBean.setTime("2018-11-26 08:31");
        timeLineBean.setTitle("审批成功");
        mTimeLineList.add(timeLineBean);
        timeLineBean = new TimeLineBean();
        timeLineBean.setTime("2018-11-26 08:35");
        timeLineBean.setTitle("调度成功");
        mTimeLineList.add(timeLineBean);
        timeLineBean = new TimeLineBean();
        timeLineBean.setTime("2018-11-26 08:40");
        timeLineBean.setTitle("派车成功");
        mTimeLineList.add(timeLineBean);
        timeLineBean = new TimeLineBean();
        timeLineBean.setTime("2018-11-26 09:13");
        timeLineBean.setTitle("驾驶员接到乘客");
        mTimeLineList.add(timeLineBean);
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

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(false);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    private void initWidget() {
        btnReject.setText("拒绝派车");
        btnPass.setText("立即派车");
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                //解决ScrollView里存在多个RecyclerView时滑动卡顿的问题
                //如果你的RecyclerView是水平滑动的话可以重写canScrollHorizontally方法
                return false;
            }
        });
        //解决数据加载不完的问题
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
//        mRecyclerView.setFocusable(false);

        recyclerViewTimeLine.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                //解决ScrollView里存在多个RecyclerView时滑动卡顿的问题
                //如果你的RecyclerView是水平滑动的话可以重写canScrollHorizontally方法
                return false;
            }
        });

    }

    private void initAdapter() {
        final ArrayList<String> list = new ArrayList<>();
        for (EnclosureBean bean : mDataList) {
            list.add(bean.getPicUrl());
        }
        BaseQuickAdapter enclosureAdapter = new EnclosureAdapter(R.layout.enclosure_list_item, mDataList, NeedDispatchCarsDetailActivity.this);
        enclosureAdapter.openLoadAnimation();
        enclosureAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ImageBrowseIntent.showUrlImageBrowse(mContext, list, position);
            }
        });

        recyclerView.setAdapter(enclosureAdapter);

        BaseQuickAdapter timeLineAdapter = new TimeLineAdapter(R.layout.item_time_line, mTimeLineList);
        enclosureAdapter.openLoadAnimation();
        recyclerViewTimeLine.setAdapter(timeLineAdapter);
    }

    @OnClick({R.id.tv_car_use_contacts_tel, R.id.tv_car_user_tel, R.id.btn_reject, R.id.btn_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_car_use_contacts_tel:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    // 没有权限。
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                        // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
                        ToastUtils.showShortToast("您拒绝了打电话的权限，将无法拨打电话.");
                    } else {
                        // 申请授权。
                        ActivityCompat.requestPermissions(NeedDispatchCarsDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_CALL_PHONE_CONTACT);
                    }
                } else {
                    // 有权限了，去放肆吧。
                    callPhone(tvCarUseContactsTel.getText().toString());
                }
                break;
            case R.id.tv_car_user_tel:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    // 没有权限。
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                        // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
                        ToastUtils.showShortToast("您拒绝了打电话的权限，将无法拨打电话.");
                    } else {
                        // 申请授权。
                        ActivityCompat.requestPermissions(NeedDispatchCarsDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_CALL_PHONE_USERTEL);
                    }
                } else {
                    // 有权限了，去放肆吧。
                    callPhone(tvCarUserTel.getText().toString());
                }
                break;
            case R.id.btn_reject:
                InputDialog.show(mContext, "请输入驳回信息", "", "确定", new InputDialogOkButtonClickListener() {
                    @Override
                    public void onClick(Dialog dialog, String inputText) {
                        Toast.makeText(mContext, "您输入了：" + inputText, Toast.LENGTH_SHORT).show();
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

                    }
                });
                break;
            case R.id.btn_pass:
                Intent it = new Intent(NeedDispatchCarsDetailActivity.this, DispatchCarsActivity.class);
                it.putExtra("title", "派遣车辆");
                startActivity(it);
                break;
        }
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    @SuppressLint("MissingPermission")
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CALL_PHONE_CONTACT:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意，可以去放肆了。
                    callPhone(tvCarUseContactsTel.getText().toString());
                } else {
                    // 权限被用户拒绝了，洗洗睡吧。
                    ToastUtils.showShortToast("您拒绝了打电话的权限，将无法拨打电话.");

                }
                break;
            case PERMISSION_CALL_PHONE_USERTEL:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意，可以去放肆了。
                    callPhone(tvCarUserTel.getText().toString());
                } else {
                    // 权限被用户拒绝了，洗洗睡吧。
                    ToastUtils.showShortToast("您拒绝了打电话的权限，将无法拨打电话.");
                }
                break;

        }
    }


}
