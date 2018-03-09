package com.tiilii.rtc.ui.learn.mainpage;


import com.tiilii.rtc.di.FragmentScoped;

import dagger.Module;
import dagger.Provides;

/**
 * 习汉字
 *
 * @author fox
 * @since 2018/03/05
 */

@Module
public class LearnModule {

    @FragmentScoped
    @Provides
    LearnContract.Presenter providesPresenter(LearnPresenter learnPresenter) {
        return learnPresenter;
    }
}
