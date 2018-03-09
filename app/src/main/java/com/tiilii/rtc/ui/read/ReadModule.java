package com.tiilii.rtc.ui.read;


import com.tiilii.rtc.di.FragmentScoped;

import dagger.Module;
import dagger.Provides;

/**
 * 主页
 *
 * @author fox
 * @since 2017/10/30
 */
@Module
public class ReadModule {

    @FragmentScoped
    @Provides
    ReadContract.Presenter providesPresenter(ReadPresenter readPresenter) {
        return readPresenter;
    }
}
