package com.tiilii.rtc.ui.practise.teacher.list;


import com.tiilii.rtc.di.ActivityScoped;

import dagger.Module;
import dagger.Provides;

/**
 * 作业列表
 *
 * @author fox
 * @since 2018/01/12
 */

@Module
public class HomeworkListModule {

    @ActivityScoped
    @Provides
    HomeworkListContract.Presenter providesPresenter(HomeworkListPresenter homeworkListPresenter) {
        return homeworkListPresenter;
    }
}
