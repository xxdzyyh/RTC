package com.tiilii.rtc.ui.practise.mainpage;

import android.content.Context;

import com.tiilii.rtc.di.FragmentScoped;
import com.tiilii.rtc.network.CloudApi;

import javax.inject.Inject;

/**
 * 习汉字
 *
 * @author fox
 * @since 2018/03/05
 */

@FragmentScoped
public class PractisePresenter implements PractiseContract.Presenter {

    private PractiseContract.View mView;

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
    public PractisePresenter() {

    }

    @Override
    public void bindView(PractiseContract.View view) {

        mView = view;
    }


}
