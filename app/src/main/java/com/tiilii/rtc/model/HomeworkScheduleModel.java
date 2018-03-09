package com.tiilii.rtc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author fox
 * @since 2018/01/12
 */

public class HomeworkScheduleModel {

    @SerializedName("same_topic")
    @Expose
    private String sameTopic;
    @SerializedName("homework_type_name")
    @Expose
    private String homeworkTypeName;
    @SerializedName("homework_status")
    @Expose
    private Integer homeworkStatus;
    @SerializedName("homework_finish_ratio_stu")
    @Expose
    private Float homeworkFinishRatioStu;
    @SerializedName("homework_count_topic")
    @Expose
    private Integer homeworkCountTopic;
    @SerializedName("enable_id")
    @Expose
    private String enableId;
    @SerializedName("homework_end_datetime")
    @Expose
    private String homeworkEndDatetime;
    @SerializedName("homework_finish_ratio_topic")
    @Expose
    private Float homeworkFinishRatioTopic;
    @SerializedName("homework_sum_minutes_stu")
    @Expose
    private Integer homeworkSumMinutesStu;
    @SerializedName("suggest_finish")
    @Expose
    private Integer suggestFinish;
    @SerializedName("grade_id")
    @Expose
    private String gradeId;
    @SerializedName("homework_begin_datetime")
    @Expose
    private String homeworkBeginDatetime;
    @SerializedName("homework_during")
    @Expose
    private Integer homeworkDuring;
    @SerializedName("creator_uid")
    @Expose
    private Integer creatorUid;
    @SerializedName("homework_right_ratio")
    @Expose
    private Float homeworkRightRatio;
    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("homework_name")
    @Expose
    private String homeworkName;
    @SerializedName("homework_min_one_day")
    @Expose
    private Integer homeworkMinOneDay;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("my_homework_uid")
    @Expose
    private Integer myHomeworkUid;
    @SerializedName("homework_create_datetime")
    @Expose
    private String homeworkCreateDatetime;
    @SerializedName("countdown")
    @Expose
    private Integer countdown;
    @SerializedName("dept_name")
    @Expose
    private String deptName;
    @SerializedName("today_finish_topic")
    @Expose
    private Integer todayFinishTopic;
    @SerializedName("classes_name")
    @Expose
    private String classesName;
    @SerializedName("homework_difficulty")
    @Expose
    private Integer homeworkDifficulty;
    @SerializedName("homework_uid")
    @Expose
    private Integer homeworkUid;
    @SerializedName("class_uid")
    @Expose
    private Integer classUid;
    @SerializedName("homework_type_id")
    @Expose
    private String homeworkTypeId;
    @SerializedName("creator_name")
    @Expose
    private String creatorName;
    @SerializedName("finish_num")
    @Expose
    private Integer finishNum;
    @SerializedName("homework_count_stu")
    @Expose
    private Integer homeworkCountStu;
    @SerializedName("all_finish_topic")
    @Expose
    private Integer allFinishTopic;

    public String getSameTopic() {
        return sameTopic;
    }

    public void setSameTopic(String sameTopic) {
        this.sameTopic = sameTopic;
    }

    public String getHomeworkTypeName() {
        return homeworkTypeName;
    }

    public void setHomeworkTypeName(String homeworkTypeName) {
        this.homeworkTypeName = homeworkTypeName;
    }

    public Integer getHomeworkStatus() {
        return homeworkStatus;
    }

    public void setHomeworkStatus(Integer homeworkStatus) {
        this.homeworkStatus = homeworkStatus;
    }

    public Float getHomeworkFinishRatioStu() {
        return homeworkFinishRatioStu;
    }

    public void setHomeworkFinishRatioStu(Float homeworkFinishRatioStu) {
        this.homeworkFinishRatioStu = homeworkFinishRatioStu;
    }

    public Integer getHomeworkCountTopic() {
        return homeworkCountTopic;
    }

    public void setHomeworkCountTopic(Integer homeworkCountTopic) {
        this.homeworkCountTopic = homeworkCountTopic;
    }

    public String getEnableId() {
        return enableId;
    }

    public void setEnableId(String enableId) {
        this.enableId = enableId;
    }

    public String getHomeworkEndDatetime() {
        return homeworkEndDatetime;
    }

    public void setHomeworkEndDatetime(String homeworkEndDatetime) {
        this.homeworkEndDatetime = homeworkEndDatetime;
    }

    public Float getHomeworkFinishRatioTopic() {
        return homeworkFinishRatioTopic;
    }

    public void setHomeworkFinishRatioTopic(Float homeworkFinishRatioTopic) {
        this.homeworkFinishRatioTopic = homeworkFinishRatioTopic;
    }

    public Integer getHomeworkSumMinutesStu() {
        return homeworkSumMinutesStu;
    }

    public void setHomeworkSumMinutesStu(Integer homeworkSumMinutesStu) {
        this.homeworkSumMinutesStu = homeworkSumMinutesStu;
    }

    public Integer getSuggestFinish() {
        return suggestFinish;
    }

    public void setSuggestFinish(Integer suggestFinish) {
        this.suggestFinish = suggestFinish;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getHomeworkBeginDatetime() {
        return homeworkBeginDatetime;
    }

    public void setHomeworkBeginDatetime(String homeworkBeginDatetime) {
        this.homeworkBeginDatetime = homeworkBeginDatetime;
    }

    public Integer getHomeworkDuring() {
        return homeworkDuring;
    }

    public void setHomeworkDuring(Integer homeworkDuring) {
        this.homeworkDuring = homeworkDuring;
    }

    public Integer getCreatorUid() {
        return creatorUid;
    }

    public void setCreatorUid(Integer creatorUid) {
        this.creatorUid = creatorUid;
    }

    public Float getHomeworkRightRatio() {
        return homeworkRightRatio;
    }

    public void setHomeworkRightRatio(Float homeworkRightRatio) {
        this.homeworkRightRatio = homeworkRightRatio;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getHomeworkName() {
        return homeworkName;
    }

    public void setHomeworkName(String homeworkName) {
        this.homeworkName = homeworkName;
    }

    public Integer getHomeworkMinOneDay() {
        return homeworkMinOneDay;
    }

    public void setHomeworkMinOneDay(Integer homeworkMinOneDay) {
        this.homeworkMinOneDay = homeworkMinOneDay;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getMyHomeworkUid() {
        return myHomeworkUid;
    }

    public void setMyHomeworkUid(Integer myHomeworkUid) {
        this.myHomeworkUid = myHomeworkUid;
    }

    public String getHomeworkCreateDatetime() {
        return homeworkCreateDatetime;
    }

    public void setHomeworkCreateDatetime(String homeworkCreateDatetime) {
        this.homeworkCreateDatetime = homeworkCreateDatetime;
    }

    public Integer getCountdown() {
        return countdown;
    }

    public void setCountdown(Integer countdown) {
        this.countdown = countdown;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getTodayFinishTopic() {
        return todayFinishTopic;
    }

    public void setTodayFinishTopic(Integer todayFinishTopic) {
        this.todayFinishTopic = todayFinishTopic;
    }

    public String getClassesName() {
        return classesName;
    }

    public void setClassesName(String classesName) {
        this.classesName = classesName;
    }

    public Integer getHomeworkDifficulty() {
        return homeworkDifficulty;
    }

    public void setHomeworkDifficulty(Integer homeworkDifficulty) {
        this.homeworkDifficulty = homeworkDifficulty;
    }

    public Integer getHomeworkUid() {
        return homeworkUid;
    }

    public void setHomeworkUid(Integer homeworkUid) {
        this.homeworkUid = homeworkUid;
    }

    public Integer getClassUid() {
        return classUid;
    }

    public void setClassUid(Integer classUid) {
        this.classUid = classUid;
    }

    public String getHomeworkTypeId() {
        return homeworkTypeId;
    }

    public void setHomeworkTypeId(String homeworkTypeId) {
        this.homeworkTypeId = homeworkTypeId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Integer getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(Integer finishNum) {
        this.finishNum = finishNum;
    }

    public Integer getHomeworkCountStu() {
        return homeworkCountStu;
    }

    public void setHomeworkCountStu(Integer homeworkCountStu) {
        this.homeworkCountStu = homeworkCountStu;
    }

    public Integer getAllFinishTopic() {
        return allFinishTopic;
    }

    public void setAllFinishTopic(Integer allFinishTopic) {
        this.allFinishTopic = allFinishTopic;
    }
}
