package com.tiilii.rtc.data;

import com.tiilii.rtc.model.HomeworkAnswer;
import com.tiilii.rtc.model.HomeworkList;
import com.tiilii.rtc.model.HomeworkNextQuestion;
import com.tiilii.rtc.model.HomeworkSchedule;

import java.util.Map;

/**
 * 网络请求接口
 *
 * @author fox
 * @since 2018/02/05
 */

public interface CloudDataSource {

    /**
     * 公用回调方法
     */
    interface GetDataCallback<T> {

        void onLoadSuccess(T t);

        void onLoadError();

        void onLoadComplete();
    }

    interface GetSystemTimeCallback {

        void onSystemTimeLoaded(long time);
    }

    interface DataLoadFinishCallback {

        void onDataAvailable();

        void onDataNotAvailable();
    }

    /**
     * 获取系统时间
     */
    void getSystemTime(GetSystemTimeCallback callback);

    /**
     * 申诉题目
     */
    void complainHomeworkQuestion(Map<String, String> params, DataLoadFinishCallback callback);

    /**
     * 获取作业列表
     */
    void getHomeworkList(Map<String, String> params, GetDataCallback<HomeworkList> callback);

    /**
     * 获取作业进度
     */
    void getHomeworkSchedule(Map<String, String> params, GetDataCallback<HomeworkSchedule> callback);
    /**
     * 获取学生的作业题目列表
     */
    void getNextTopicForHomework(Map<String, String> params, GetDataCallback<HomeworkNextQuestion> callback);
    /**
     * 保存学生的答案
     */
    void saveStuHomeworkAnswer(String url, GetDataCallback<HomeworkAnswer> callback);
}
