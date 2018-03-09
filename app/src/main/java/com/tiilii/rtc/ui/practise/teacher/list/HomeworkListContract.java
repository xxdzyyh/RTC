package com.tiilii.rtc.ui.practise.teacher.list;

import android.content.Intent;

import com.tiilii.rtc.base.mvp.BasePresenter;
import com.tiilii.rtc.base.mvp.BaseView;
import com.tiilii.rtc.model.HomeworkModel;

import java.util.List;
import java.util.Map;

/**
 * 作业列表
 *
 * @author fox
 * @since 2018/01/12
 */

interface HomeworkListContract {

    interface View extends BaseView {

        /**
         * 显示空作业列表
         */
        void showEmptyView();

        /**
         * 加载作业列表数据
         */
        void updateHomeworkList(List<HomeworkModel> homeworkModelList);

        /**
         * 清除作业列表的数据
         */
        void clearHomeworkList();

        void updateAdapter(Map<Integer, String> countdown);
    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 获取作业列表
         */
        void getHomeworkList();

        /**
         * 获取系统时间
         */
        void getSystemTime();

        /**
         * 开始倒计时
         */
        void startCountdown();

        /**
         * 取消订阅Disposable
         */
        void unSubscribe();

        /**
         * 清空订阅的Disposable
         */
        void clearSubscribe();

        /**
         * 刷新作业列表
         */
        void refreshHomeworkList();

        /**
         * 加载作业列表更多数据
         */
        void loadMoreHomeworkList();

        /**
         * 判断作业列表是否有更多数据
         */
        boolean hasMoreHomeworkList();

        /**
         * 传递数据
         */
        void setIntent(Intent intent);
    }
}
