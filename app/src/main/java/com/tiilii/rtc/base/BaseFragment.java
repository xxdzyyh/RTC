package com.tiilii.rtc.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tiilii.rtc.base.mvp.BaseView;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 基础Fragment
 *
 * @author fox
 * @since 2017/9/19
 */

public class BaseFragment extends Fragment implements BaseView {

    /**
     * 进度条
     */
    private KProgressHUD mKProgressHUD;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getContext();
        mForegroundDisposable = new CompositeDisposable();
        mBackgroundDisposable = new CompositeDisposable();

        mKProgressHUD = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mForegroundDisposable != null) {
            mForegroundDisposable.clear();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mForegroundDisposable != null && !mForegroundDisposable.isDisposed()) {
            mForegroundDisposable.dispose();
        }
        if (mBackgroundDisposable != null && !mBackgroundDisposable.isDisposed()) {
            mBackgroundDisposable.dispose();
        }
    }


    @Override
    public void showProgress(String label) {

        mKProgressHUD
                .setLabel(label)
                .show();
    }

    @Override
    public void hideProgress() {

        mKProgressHUD.dismiss();
    }

}
