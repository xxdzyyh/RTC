package com.tiilii.rtc.ui.learn.content.write;

import com.tiilii.rtc.di.ActivityScoped;

import javax.inject.Inject;

/**
 * @author fox
 * @since 2017/11/20
 */

@ActivityScoped
public class WritePresenter implements WriteContract.Presenter {

    private WriteContract.View mView;

    @Inject
    public WritePresenter() {
    }

    @Override
    public void bindView(WriteContract.View view) {

        mView = view;
    }
}
