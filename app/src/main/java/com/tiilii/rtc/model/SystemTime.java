package com.tiilii.rtc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author fox
 * @since 2018/02/05
 */

public class SystemTime extends BaseStatus {

    @SerializedName("serverTimeValue")
    @Expose
    private Long serverTimeValue;

    public Long getServerTimeValue() {
        return serverTimeValue;
    }

    public void setServerTimeValue(Long serverTimeValue) {
        this.serverTimeValue = serverTimeValue;
    }
}
