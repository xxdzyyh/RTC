package com.tiilii.rtc.ui.home;


import com.tiilii.rtc.di.FragmentScoped;
import com.tiilii.rtc.ui.learn.mainpage.LearnFragment;
import com.tiilii.rtc.ui.learn.mainpage.LearnModule;
import com.tiilii.rtc.ui.practise.mainpage.PractiseFragment;
import com.tiilii.rtc.ui.practise.mainpage.PractiseModule;
import com.tiilii.rtc.ui.read.ReadFragment;
import com.tiilii.rtc.ui.read.ReadModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 首页
 *
 * @author fox
 * @since 2017/09/22
 */

@Module
public abstract class HomeFragmentBuilder {

    /**
     * 诵经典
     */
    @FragmentScoped
    @ContributesAndroidInjector(modules = ReadModule.class)
    abstract ReadFragment bindReadFragment();

    /**
     * 习汉字
     */
    @FragmentScoped
    @ContributesAndroidInjector(modules = LearnModule.class)
    abstract LearnFragment bindLearnFragment();

    /**
     * 练运算
     */
    @FragmentScoped
    @ContributesAndroidInjector(modules = PractiseModule.class)
    abstract PractiseFragment bindPractiseFragment();
}
