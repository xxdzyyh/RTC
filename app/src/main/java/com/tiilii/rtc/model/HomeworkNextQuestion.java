package com.tiilii.rtc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author fox
 * @since 2018/01/15
 */

public class HomeworkNextQuestion extends BaseStatus {

    @SerializedName("myTopicList")
    @Expose
    private List<HomeworkQuestionModel> homeworkQuestionList;
    @SerializedName("isEOF")
    @Expose
    private String isEOF;
    @SerializedName("showTopicNo")
    @Expose
    private String showQuestionNo;
    @SerializedName("showTopicUid")
    @Expose
    private String showQuestionUid;

    public List<HomeworkQuestionModel> getHomeworkQuestionList() {
        return homeworkQuestionList;
    }

    public void setHomeworkQuestionList(List<HomeworkQuestionModel> homeworkQuestionList) {
        this.homeworkQuestionList = homeworkQuestionList;
    }

    public String getIsEOF() {
        return isEOF;
    }

    public void setIsEOF(String isEOF) {
        this.isEOF = isEOF;
    }

    public String getShowQuestionNo() {
        return showQuestionNo;
    }

    public void setShowQuestionNo(String showQuestionNo) {
        this.showQuestionNo = showQuestionNo;
    }

    public String getShowQuestionUid() {
        return showQuestionUid;
    }

    public void setShowQuestionUid(String showQuestionUid) {
        this.showQuestionUid = showQuestionUid;
    }
}
