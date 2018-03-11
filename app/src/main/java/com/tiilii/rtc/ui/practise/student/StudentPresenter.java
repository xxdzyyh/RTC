package com.tiilii.rtc.ui.practise.student;

import com.tiilii.rtc.base.BasePresenter;
import com.tiilii.rtc.di.ActivityScoped;

import javax.inject.Inject;

/**
 * @author fox
 * @since 2018/01/12
 */

@ActivityScoped
public class StudentPresenter extends BasePresenter implements StudentContract.Presenter {

    private StudentContract.View mView;

    @Inject
    public StudentPresenter() {
    }

    @Override
    public void bindView(StudentContract.View view) {

        mView = view;
    }

}
