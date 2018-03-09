package com.tiilii.rtc.base.mvp;

/**
 * MVP基础Presenter接口
 *
 * @author fox
 * @since 2017/9/19
 */

public interface BasePresenter<T> {

    /**
     * 绑定View接口
     */
    void bindView(T view);
}
