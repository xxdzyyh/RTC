package com.tiilii.rtc.ui.practise.mainpage;


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
public class PractiseModule {

    @FragmentScoped
    @Provides
    PractiseContract.Presenter providesPresenter(PractisePresenter practisePresenter) {
        return practisePresenter;
    }
}
