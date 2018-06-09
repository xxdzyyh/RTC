package com.tiilii.rtc.ui.me;

import com.tiilii.rtc.di.FragmentScoped;


import dagger.Module;
import dagger.Provides;

/**
 * Created by wangxuefeng on 2018/6/8.
 */


@Module
public class MyModule {


    @FragmentScoped
    @Provides
    MyContract.Presenter providesPresenter(MyPresenter myPresenter) {
        return myPresenter;
    }
}
