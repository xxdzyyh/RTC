package com.tiilii.rtc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author fox
 * @since 2018/01/12
 */

public class HomeworkSchedule extends BaseStatus {

    @SerializedName("myHomework")
    @Expose
    private HomeworkScheduleModel homeworkSchedule;

    public HomeworkScheduleModel getHomeworkSchedule() {
        return homeworkSchedule;
    }

    public void setHomeworkSchedule(HomeworkScheduleModel homeworkSchedule) {
        this.homeworkSchedule = homeworkSchedule;
    }
}
