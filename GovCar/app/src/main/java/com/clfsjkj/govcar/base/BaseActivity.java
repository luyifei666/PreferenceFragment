package com.clfsjkj.govcar.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.bumptech.glide.Glide;
import com.clfsjkj.govcar.MainApplication;
import com.clfsjkj.govcar.R;
import com.clfsjkj.govcar.alltextsize.MessageSocket;
import com.clfsjkj.govcar.alltextsize.RxBus;
import com.clfsjkj.govcar.customerview.SlideBackLayout;
import com.clfsjkj.govcar.utils.RxActivityTool;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.lzy.imagepicker.util.Utils.getStatusHeight;

/**
 * Created by maning on 16/3/2.
 * <p/>
 * 父类
 */
public class BaseActivity extends AppCompatActivity {

    private SVProgressHUD mSVProgressHUD;
    public Context mContext;
    public Observable observable;//用于全局字体大小设置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
//        //因为LoginActivity和MainActivity是不需要这个功能的，但是放在BaseActivity里无法取消这两个页面，所以只能单独放到每个Activity里了
//        //创建侧滑关闭 Activity 控件
//        SlideBackLayout mSlideBackLayout = new SlideBackLayout(this);
//        //绑定 Activity
//        mSlideBackLayout.bindActivity(this);
        //解除绑定 Activity
//        mSlideBackLayout.unbindActivity();
        RxActivityTool.addActivity(this);//将Activity全部加入进去，当退出APP时，结束所有的Activity
        observable = RxBus.getInstance().register(this.getClass().getSimpleName(), MessageSocket.class);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<MessageSocket>() {

            @Override
            public void accept(MessageSocket message) throws Exception {
                rxBusCall(message);
            }
        });
        initDialog();
    }

    private void initDialog() {
        mSVProgressHUD = new SVProgressHUD(this);
    }

    public void showProgressDialog() {
        dissmissProgressDialog();
        mSVProgressHUD.showWithStatus("加载中...", SVProgressHUD.SVProgressHUDMaskType.Black);
    }

    public void showProgressDialog(String message) {
        if (TextUtils.isEmpty(message)) {
            showProgressDialog();
        } else {
            dissmissProgressDialog();
            mSVProgressHUD.showWithStatus(message, SVProgressHUD.SVProgressHUDMaskType.Black);
        }
    }

    public void showProgressSuccess(String message) {
        dissmissProgressDialog();
        mSVProgressHUD.showSuccessWithStatus(message, SVProgressHUD.SVProgressHUDMaskType.Black);
    }

    public void showProgressError(String message) {
        dissmissProgressDialog();
        mSVProgressHUD.showErrorWithStatus(message, SVProgressHUD.SVProgressHUDMaskType.Black);
    }

    public void dissmissProgressDialog() {
        if (mSVProgressHUD.isShowing()) {
            mSVProgressHUD.dismiss();
        }
    }

    public void initToolBar(Toolbar toolbar, String title, int icon) {
        toolbar.setTitle(title);// 标题的文字需在setSupportActionBar之前，不然会无效
        toolbar.setNavigationIcon(icon);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.gank_text1_color));
        toolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }

    public void initToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);// 标题的文字需在setSupportActionBar之前，不然会无效
        setSupportActionBar(toolbar);
    }

    public void rxBusCall(MessageSocket message) {
    }


    public int getColorById(int resId) {
        return ContextCompat.getColor(this, resId);
    }


    public void goActivity(Class<?> activity) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), activity);
        startActivity(intent);
    }

    //重写字体缩放比例 api<25
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            Configuration config = res.getConfiguration();
            config.fontScale = MainApplication.getMyInstance().getFontScale();//1 设置正常字体大小的倍数
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return res;
    }

    //重写字体缩放比例  api>25
    @Override
    protected void attachBaseContext(Context newBase) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            final Resources res = newBase.getResources();
            final Configuration config = res.getConfiguration();
            config.fontScale = MainApplication.getMyInstance().getFontScale();//1 设置正常字体大小的倍数
            final Context newContext = newBase.createConfigurationContext(config);
            super.attachBaseContext(newContext);
        } else {
            super.attachBaseContext(newBase);
        }
    }

    /**
     * 全透状态栏
     */
    protected void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 半透明状态栏
     */
    protected void setHalfTransparent() {

        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 如果需要内容紧贴着StatusBar
     * 应该在对应的xml布局文件中，设置根布局fitsSystemWindows=true。
     */
    private View contentViewGroup;

    protected void setFitSystemWindow(boolean fitSystemWindow) {
        if (contentViewGroup == null) {
            contentViewGroup = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        }
        contentViewGroup.setFitsSystemWindows(fitSystemWindow);
    }

    /**
     * 为了兼容4.4的抽屉布局->透明状态栏
     */
    protected void setDrawerLayoutFitSystemWindow() {
        if (Build.VERSION.SDK_INT == 19) {//19表示4.4
            int statusBarHeight = getStatusHeight(this);
            if (contentViewGroup == null) {
                contentViewGroup = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
            }
            if (contentViewGroup instanceof DrawerLayout) {
                DrawerLayout drawerLayout = (DrawerLayout) contentViewGroup;
                drawerLayout.setClipToPadding(true);
                drawerLayout.setFitsSystemWindows(false);
                for (int i = 0; i < drawerLayout.getChildCount(); i++) {
                    View child = drawerLayout.getChildAt(i);
                    child.setFitsSystemWindows(false);
                    child.setPadding(0, statusBarHeight, 0, 0);
                }

            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mSVProgressHUD.isShowing()) {
                mSVProgressHUD.dismiss();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onDestroy() {
        dissmissProgressDialog();
        Glide.with(this.getApplicationContext()).pauseRequests();
        RxBus.getInstance().unregister(this.getClass().getSimpleName(), observable);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
