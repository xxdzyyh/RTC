package com.tiilii.rtc.base.mvp;

/**
 * MVP基础View接口
 *
 * @author fox
 * @since 2017/9/19
 */

public interface BaseView {


    /**
     * 显示进度条
     */
    void showProgress(String label);

    /**
     * 隐藏进度条
     */
    void hideProgress();
}
