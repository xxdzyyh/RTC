package com.tiilii.rtc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * com.tiilii.jhcloud.model
 *
 * @author bovink
 * @since 2017/9/19
 */
public class BaseStatus {

    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("msg")
    @Expose
    private String msg;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
