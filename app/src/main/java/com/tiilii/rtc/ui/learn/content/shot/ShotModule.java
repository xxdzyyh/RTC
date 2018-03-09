package com.tiilii.rtc.ui.learn.content.shot;


import com.tiilii.rtc.di.ActivityScoped;

import dagger.Module;
import dagger.Provides;

/**
 * @author fox
 * @since 2017/11/20
 */

@Module
public class ShotModule {

    @ActivityScoped
    @Provides
    ShotContract.Presenter providesPresenter(ShotPresenter shotPresenter) {
        return shotPresenter;
    }
}
