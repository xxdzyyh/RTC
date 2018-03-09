package com.tiilii.rtc.ui.practise.teacher.statistic;

import android.content.Intent;

import com.tiilii.rtc.base.mvp.BasePresenter;
import com.tiilii.rtc.base.mvp.BaseView;
import com.tiilii.rtc.model.HomeworkScheduleModel;


/**
 * 作业统计
 *
 * @author fox
 * @since 2018/01/12
 */

interface HomeworkStatisticContract {

    interface View extends BaseView {

        /**
         * 更新视图
         */
        void updateViews(HomeworkScheduleModel model);
    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 获取作业进度
         */
        void getHomeworkSchedule();

        void setIntent(Intent intent);

        /**
         * 作业是否已结束
         */
        boolean isHomeworkFinished();
    }
}
