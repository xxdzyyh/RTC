package com.tiilii.rtc.ui.practise.student;


import com.tiilii.rtc.di.ActivityScoped;

import dagger.Module;
import dagger.Provides;

/**
 * @author fox
 * @since 2018/01/12
 */

@Module
public class StudentModule {

    @ActivityScoped
    @Provides
    StudentContract.Presenter providesPresenter(StudentPresenter studentPresenter) {
        return studentPresenter;
    }
}
