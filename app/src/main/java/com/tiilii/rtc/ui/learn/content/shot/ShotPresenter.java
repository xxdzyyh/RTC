package com.tiilii.rtc.ui.learn.content.shot;

import com.tiilii.rtc.di.ActivityScoped;

import javax.inject.Inject;

/**
 * @author fox
 * @since 2017/11/20
 */

@ActivityScoped
public class ShotPresenter implements ShotContract.Presenter {

    private ShotContract.View mView;

    @Inject
    public ShotPresenter() {
    }

    @Override
    public void bindView(ShotContract.View view) {

        mView = view;
    }
}
