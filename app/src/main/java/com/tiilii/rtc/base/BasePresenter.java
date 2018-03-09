package com.tiilii.rtc.base;


import android.content.Context;

import com.tiilii.rtc.data.CloudRemoteDataSource;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 基础Presenter
 *
 * @author fox
 * @since 2017/9/19
 */

public class BasePresenter {

    /**
     * 前台订阅，activity退出前台后不再执行订阅事件。
     */
    public CompositeDisposable mForegroundDisposable;
    /**
     * 后台订阅，activity完全销毁后不再执行订阅事件。
     */
    public CompositeDisposable mBackgroundDisposable;

    /**
     * 网络请求实现类
     */
    @Inject
    public CloudRemoteDataSource mCloudRemoteDataSource;
    /**
     * 上下文
     */
    @Inject
    public Context mContext;

    public BasePresenter() {

        mForegroundDisposable = new CompositeDisposable();
        mBackgroundDisposable = new CompositeDisposable();
    }
}
