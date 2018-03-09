package com.tiilii.rtc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author fox
 * @since 2018/01/15
 */

public class HomeworkQuestionModel {

    @SerializedName("@num:=0")
    @Expose
    private Integer num0;
    @SerializedName("topic_no")
    @Expose
    private Integer topicNo;
    @SerializedName("my_homework_topic_standard_score")
    @Expose
    private Object myHomeworkTopicStandardScore;
    @SerializedName("my_homework_uid")
    @Expose
    private Integer myHomeworkUid;
    @SerializedName("my_homework_topic_answer")
    @Expose
    private Object myHomeworkTopicAnswer;
    @SerializedName("has_complain")
    @Expose
    private String hasComplain;
    @SerializedName("study_topic_error_type_id")
    @Expose
    private Object studyTopicErrorTypeId;
    @SerializedName("topic_uid")
    @Expose
    private String topicUid;
    @SerializedName("is_right")
    @Expose
    private Object isRight;
    @SerializedName("my_homework_topic_duration_minutes")
    @Expose
    private Object myHomeworkTopicDurationMinutes;
    @SerializedName("my_homework_topic_score")
    @Expose
    private Object myHomeworkTopicScore;
    @SerializedName("my_homework_topic_commit_datetime")
    @Expose
    private Object myHomeworkTopicCommitDatetime;

    public Integer getNum0() {
        return num0;
    }

    public void setNum0(Integer num0) {
        this.num0 = num0;
    }

    public Integer getTopicNo() {
        return topicNo;
    }

    public void setTopicNo(Integer topicNo) {
        this.topicNo = topicNo;
    }

    public Object getMyHomeworkTopicStandardScore() {
        return myHomeworkTopicStandardScore;
    }

    public void setMyHomeworkTopicStandardScore(Object myHomeworkTopicStandardScore) {
        this.myHomeworkTopicStandardScore = myHomeworkTopicStandardScore;
    }

    public Integer getMyHomeworkUid() {
        return myHomeworkUid;
    }

    public void setMyHomeworkUid(Integer myHomeworkUid) {
        this.myHomeworkUid = myHomeworkUid;
    }

    public Object getMyHomeworkTopicAnswer() {
        return myHomeworkTopicAnswer;
    }

    public void setMyHomeworkTopicAnswer(Object myHomeworkTopicAnswer) {
        this.myHomeworkTopicAnswer = myHomeworkTopicAnswer;
    }

    public String getHasComplain() {
        return hasComplain;
    }

    public void setHasComplain(String hasComplain) {
        this.hasComplain = hasComplain;
    }

    public Object getStudyTopicErrorTypeId() {
        return studyTopicErrorTypeId;
    }

    public void setStudyTopicErrorTypeId(Object studyTopicErrorTypeId) {
        this.studyTopicErrorTypeId = studyTopicErrorTypeId;
    }

    public String getTopicUid() {
        return topicUid;
    }

    public void setTopicUid(String topicUid) {
        this.topicUid = topicUid;
    }

    public Object getIsRight() {
        return isRight;
    }

    public void setIsRight(Object isRight) {
        this.isRight = isRight;
    }

    public Object getMyHomeworkTopicDurationMinutes() {
        return myHomeworkTopicDurationMinutes;
    }

    public void setMyHomeworkTopicDurationMinutes(Object myHomeworkTopicDurationMinutes) {
        this.myHomeworkTopicDurationMinutes = myHomeworkTopicDurationMinutes;
    }

    public Object getMyHomeworkTopicScore() {
        return myHomeworkTopicScore;
    }

    public void setMyHomeworkTopicScore(Object myHomeworkTopicScore) {
        this.myHomeworkTopicScore = myHomeworkTopicScore;
    }

    public Object getMyHomeworkTopicCommitDatetime() {
        return myHomeworkTopicCommitDatetime;
    }

    public void setMyHomeworkTopicCommitDatetime(Object myHomeworkTopicCommitDatetime) {
        this.myHomeworkTopicCommitDatetime = myHomeworkTopicCommitDatetime;
    }
}
