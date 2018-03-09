package com.tiilii.rtc.network;

import com.tiilii.rtc.model.BaseStatus;
import com.tiilii.rtc.model.HomeworkAnswer;
import com.tiilii.rtc.model.HomeworkList;
import com.tiilii.rtc.model.HomeworkNextQuestion;
import com.tiilii.rtc.model.HomeworkSchedule;
import com.tiilii.rtc.model.SystemTime;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * 网络请求API
 *
 * @author fox
 * @since 2018/02/22
 */

public interface CloudApi {

    /**
     * 获取系统时间
     */
    @GET("systemIni/getServerTimeValue.do")
    Single<SystemTime> getSystemTime();
    /**
     * 学生申诉题目
     */
    @GET("homeworkStu/complainTopicError.do")
    Single<BaseStatus> complainHomeworkQuestion(@QueryMap Map<String, String> params);
    /**
     * 获取作业列表
     */
    @GET("homeworkStu/getMyAllHomeworkList.do")
    Observable<HomeworkList> getHomeworkList(@QueryMap Map<String, String> options);
    /**
     * 获取作业进度
     */
    @GET("homeworkStu/getMyHomeworkSchedule.do")
    Observable<HomeworkSchedule> getHomeworkSchedule(@QueryMap Map<String, String> options);
    /**
     * 获取学生的作业题目列表
     */
    @GET("homeworkStu/getNextTopicForHomework.do")
    Observable<HomeworkNextQuestion> getNextTopicForHomework(@QueryMap Map<String, String> params);
    /**
     * 保存学生的答案
     */
    @GET()
    Observable<HomeworkAnswer> saveStuHomeworkAnswer(@Url String url);
}

