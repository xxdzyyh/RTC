package com.tiilii.rtc.network;

/**
 * 服务器配置类
 *
 * @author fox
 * @since 2017/9/19
 */

public class HttpUtils {

    private HttpUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public final static String HOST = "http://59.175.213.88:8085/yunduo/";// 测试服务器
    /**
     * 获取学生做题的html页面
     */
    public final static String HOMEWORK_TOPIC = HOST + "homeworkStu/getTopicForAnswer.do";
}

