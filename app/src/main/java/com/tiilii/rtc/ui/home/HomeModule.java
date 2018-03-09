package com.tiilii.rtc.ui.home;


import com.tiilii.rtc.di.ActivityScoped;

import dagger.Module;
import dagger.Provides;

/**
 * 首页
 *
 * @author fox
 * @since 2017/09/22
 */

@Module
public class HomeModule {

    @ActivityScoped
    @Provides
    HomeContract.Presenter providesPresenter(HomePresenter homePresenter) {
        return homePresenter;
    }

}
