package com.tiilii.rtc.ui.read;

import android.content.Context;

import com.tiilii.rtc.di.FragmentScoped;
import com.tiilii.rtc.network.CloudApi;

import javax.inject.Inject;

/**
 * 主页
 *
 * @author fox
 * @since 2017/10/30
 */
@FragmentScoped
public class ReadPresenter implements ReadContract.Presenter {

    private ReadContract.View mView;

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
    public ReadPresenter() {

    }

    @Override
    public void bindView(ReadContract.View view) {

        mView = view;
    }


}
