/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.clfsjkj.govcar.utils;

import android.app.Activity;
import android.content.Intent;

import com.clfsjkj.govcar.DemoNaviSettingActivity;

public class NormalUtils {

    public static void gotoSettings(Activity activity) {
        Intent it = new Intent(activity, DemoNaviSettingActivity.class);
        activity.startActivity(it);
    }

    public static String getTTSAppID() {
        return "14327542";
    }
}
