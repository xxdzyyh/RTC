package com.tiilii.rtc.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import com.blankj.utilcode.util.ToastUtils;
import com.tiilii.rtc.R;
import com.tiilii.rtc.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * 首页
 *
 * @author fox
 * @since 2017/09/22
 */

public class HomeActivity extends BaseActivity implements HasSupportFragmentInjector {
    /**
     * 上一次按返回键的时间
     */
    private long mLastPressTime = 0;

    /**
     * 退出提示语
     */
    @BindString(R.string.toast_exit_app)
    String mExitAppToast;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    HomeFragment mHomeFragment;
    @Inject
    HomePresenter mHomePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_home_content, mHomeFragment)
                .commit();

    }


    @Override
    public void onBackPressed() {// 按两下返回键退出APP

        // 当前时间
        long pressTime = System.currentTimeMillis();

        if (pressTime - mLastPressTime > 3000) {
            mLastPressTime = pressTime;
            ToastUtils.showShort(mExitAppToast);
        } else {
            ActivityCompat.finishAffinity(this);
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
