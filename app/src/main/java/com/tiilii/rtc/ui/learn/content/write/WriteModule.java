package com.tiilii.rtc.ui.learn.content.write;


import com.tiilii.rtc.di.ActivityScoped;

import dagger.Module;
import dagger.Provides;

/**
 * @author fox
 * @since 2017/11/20
 */

@Module
public class WriteModule {

    @ActivityScoped
    @Provides
    WriteContract.Presenter providesPresenter(WritePresenter writePresenter) {
        return writePresenter;
    }
}
