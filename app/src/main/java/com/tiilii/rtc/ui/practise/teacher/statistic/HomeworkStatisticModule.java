package com.tiilii.rtc.ui.practise.teacher.statistic;


import com.tiilii.rtc.di.ActivityScoped;

import dagger.Module;
import dagger.Provides;

/**
 * 作业统计
 *
 * @author fox
 * @since 2018/01/12
 */

@Module
public class HomeworkStatisticModule {

    @ActivityScoped
    @Provides
    HomeworkStatisticContract.Presenter providesPresenter(HomeworkStatisticPresenter homeworkStatisticPresenter) {
        return homeworkStatisticPresenter;
    }
}
