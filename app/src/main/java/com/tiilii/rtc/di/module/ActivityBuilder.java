package com.tiilii.rtc.di.module;

import com.tiilii.rtc.di.ActivityScoped;
import com.tiilii.rtc.ui.home.HomeActivity;
import com.tiilii.rtc.ui.home.HomeFragmentBuilder;
import com.tiilii.rtc.ui.home.HomeModule;
import com.tiilii.rtc.ui.learn.content.shot.ShotActivity;
import com.tiilii.rtc.ui.learn.content.shot.ShotModule;
import com.tiilii.rtc.ui.learn.content.write.WriteActivity;
import com.tiilii.rtc.ui.learn.content.write.WriteModule;
import com.tiilii.rtc.ui.practise.student.StudentActivity;
import com.tiilii.rtc.ui.practise.student.StudentModule;
import com.tiilii.rtc.ui.practise.teacher.detail.HomeworkDetailActivity;
import com.tiilii.rtc.ui.practise.teacher.detail.HomeworkDetailModule;
import com.tiilii.rtc.ui.practise.teacher.list.HomeworkListActivity;
import com.tiilii.rtc.ui.practise.teacher.list.HomeworkListModule;
import com.tiilii.rtc.ui.practise.teacher.statistic.HomeworkStatisticActivity;
import com.tiilii.rtc.ui.practise.teacher.statistic.HomeworkStatisticModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 配置activity
 *
 * @author fox
 * @since 2017/09/21
 */

@Module
public abstract class ActivityBuilder {

    /**
     * 主页
     */
    @ActivityScoped
    @ContributesAndroidInjector(modules = {HomeModule.class, HomeFragmentBuilder.class})
    abstract HomeActivity bindHomeActivity();

    /**
     * 老师练
     * 作业列表
     */
    @ActivityScoped
    @ContributesAndroidInjector(modules = HomeworkListModule.class)
    abstract HomeworkListActivity bindHomeworkListActivity();

    /**
     * 老师练
     * 作业统计
     */
    @ActivityScoped
    @ContributesAndroidInjector(modules = HomeworkStatisticModule.class)
    abstract HomeworkStatisticActivity bindHomeworkStatisticActivity();

    /**
     * 老师练
     * 作业详情
     */
    @ActivityScoped
    @ContributesAndroidInjector(modules = HomeworkDetailModule.class)
    abstract HomeworkDetailActivity bindHomeworkDetailActivity();

    /**
     * 屏幕上写字
     */
    @ActivityScoped
    @ContributesAndroidInjector(modules = WriteModule.class)
    abstract WriteActivity bindWriteActivity();

    /**
     * 写了字拍照
     */
    @ActivityScoped
    @ContributesAndroidInjector(modules = ShotModule.class)
    abstract ShotActivity bindShotActivity();

    /**
     * 自主练习
     */
    @ActivityScoped
    @ContributesAndroidInjector(modules = StudentModule.class)
    abstract StudentActivity bindStudentActivity();
}
