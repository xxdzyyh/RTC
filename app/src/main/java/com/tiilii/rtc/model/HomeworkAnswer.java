package com.tiilii.rtc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author fox
 * @since 2018/01/16
 */

public class HomeworkAnswer extends BaseStatus {

    @SerializedName("isRight")
    @Expose
    private String isRight;

    public String getIsRight() {
        return isRight;
    }

    public void setIsRight(String isRight) {
        this.isRight = isRight;
    }
}
