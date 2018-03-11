package com.tiilii.rtc.ui.practise.student;

import android.content.Intent;

import com.tiilii.rtc.base.mvp.BasePresenter;
import com.tiilii.rtc.base.mvp.BaseView;
import com.tiilii.rtc.model.HomeworkQuestionModel;


/**
 * @author fox
 * @since 2018/01/12
 */

interface StudentContract {

    interface View extends BaseView {

        void showQuestion(String url);

        void showQuestionIndex(HomeworkQuestionModel left, HomeworkQuestionModel center, HomeworkQuestionModel right);

        void startCountdown();

        void reloadWebView();

        void setQuestionComplained(boolean complained);
    }

    interface Presenter extends BasePresenter<View> {

        void getNextTopic();

        void saveAnswer(String url);

        void complainHomeworkQuestion();

        String getPreviousQuestion();

        String getNextQuestion();

        void setIntent(Intent intent);

        void saveHomeworkTime(long time);

        long getHomeworkTime();

        boolean getIsWorking();

        /**
         * 判断题目是否已经做了
         */
        boolean getTopicDone();
    }
}
