package com.tiilii.rtc.base;

import android.app.Activity;
import android.app.Application;
import android.view.Gravity;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.tiilii.rtc.di.component.DaggerApplicationComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


/**
 * RTC入口
 *
 * @author fox
 * @since 2017/9/20
 */

public class RtcApplication extends Application implements HasActivityInjector {

    public static int userUid = 4012;

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);

        DaggerApplicationComponent.builder()
                .application(this)
                .build()
                .inject(this);

    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
