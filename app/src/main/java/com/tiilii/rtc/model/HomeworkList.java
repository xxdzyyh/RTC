package com.tiilii.rtc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author fox
 * @since 2018/01/12
 */

public class HomeworkList extends BaseStatus {

    @SerializedName("homeworkList")
    @Expose
    private List<HomeworkModel> homeworkList;
    @SerializedName("pageNum")
    @Expose
    private Integer pageNum;
    @SerializedName("pageSize")
    @Expose
    private Integer pageSize;
    @SerializedName("pageTotalCount")
    @Expose
    private Integer pageTotalCount;

    public List<HomeworkModel> getHomeworkList() {
        return homeworkList;
    }

    public void setHomeworkList(List<HomeworkModel> homeworkList) {
        this.homeworkList = homeworkList;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageTotalCount() {
        return pageTotalCount;
    }

    public void setPageTotalCount(Integer pageTotalCount) {
        this.pageTotalCount = pageTotalCount;
    }
}
