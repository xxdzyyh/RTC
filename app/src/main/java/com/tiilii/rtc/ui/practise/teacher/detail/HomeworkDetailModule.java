package com.tiilii.rtc.ui.practise.teacher.detail;


import com.tiilii.rtc.di.ActivityScoped;

import dagger.Module;
import dagger.Provides;

/**
 * @author fox
 * @since 2018/01/12
 */

@Module
public class HomeworkDetailModule {

    @ActivityScoped
    @Provides
    HomeworkDetailContract.Presenter providesPresenter(HomeworkDetailPresenter homeworkDetailPresenter) {
        return homeworkDetailPresenter;
    }
}
