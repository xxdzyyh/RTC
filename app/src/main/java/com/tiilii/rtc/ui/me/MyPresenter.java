package com.tiilii.rtc.ui.me;

import android.content.Context;

import com.tiilii.rtc.di.FragmentScoped;
import com.tiilii.rtc.network.CloudApi;


import javax.inject.Inject;

/**
 * Created by wangxuefeng on 2018/6/8.
 */


@FragmentScoped
public class MyPresenter implements MyContract.Presenter {

    private MyContract.View mView;

    /**
     * 环境，由Application提供
     */
    @Inject
    Context mContext;
    /**
     * 云朵API
     */
    @Inject
    CloudApi cloudApi;

    @Inject
    public MyPresenter() {

    }

    @Override
    public void bindView(MyContract.View view) {

        mView = view;
    }

}
