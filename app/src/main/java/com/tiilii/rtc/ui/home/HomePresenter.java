package com.tiilii.rtc.ui.home;

import com.tiilii.rtc.base.BasePresenter;
import com.tiilii.rtc.di.ActivityScoped;

import javax.inject.Inject;

/**
 * 首页
 *
 * @author fox
 * @since 2017/09/22
 */

@ActivityScoped
public class HomePresenter extends BasePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;

    @Inject
    public HomePresenter() {

    }

    @Override
    public void bindView(HomeContract.View view) {

        mView = view;
    }


}
