package com.tiilii.rtc.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 基础Activity
 *
 * @author fox
 * @since 2017/9/19
 */

public class BaseActivity extends AppCompatActivity {

    /**
     * 前台订阅，activity退出前台后不再执行订阅事件。
     */
    public CompositeDisposable mForegroundDisposable;
    /**
     * 后台订阅，activity完全销毁后不再执行订阅事件。
     */
    public CompositeDisposable mBackgroundDisposable;
    /**
     * 上下文
     */
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        mForegroundDisposable = new CompositeDisposable();
        mBackgroundDisposable = new CompositeDisposable();

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mForegroundDisposable != null) {
            mForegroundDisposable.clear();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mForegroundDisposable != null && !mForegroundDisposable.isDisposed()) {
            mForegroundDisposable.dispose();
        }
        if (mBackgroundDisposable != null && !mBackgroundDisposable.isDisposed()) {
            mBackgroundDisposable.dispose();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}


