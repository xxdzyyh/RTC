package com.tiilii.rtc.ui.practise.teacher.detail;

import android.content.Intent;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tiilii.rtc.base.BasePresenter;
import com.tiilii.rtc.base.RtcApplication;
import com.tiilii.rtc.data.CloudDataSource;
import com.tiilii.rtc.data.CloudRemoteDataSource;
import com.tiilii.rtc.di.ActivityScoped;
import com.tiilii.rtc.model.HomeworkAnswer;
import com.tiilii.rtc.model.HomeworkNextQuestion;
import com.tiilii.rtc.model.HomeworkQuestionModel;
import com.tiilii.rtc.network.HttpUtils;
import com.tiilii.rtc.ui.practise.teacher.statistic.HomeworkStatisticActivity;
import com.tiilii.rtc.utils.SharedPreferencesUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author fox
 * @since 2018/01/12
 */

@ActivityScoped
public class HomeworkDetailPresenter extends BasePresenter implements HomeworkDetailContract.Presenter {

    private HomeworkDetailContract.View mView;
    private Intent mIntent;

    private String mMyHomeworkUid;
    /**
     * 正在做的题目的uid
     */
    private String mQuestionUid;
    private String mQuestionUrl;
    /**
     * 当前显示题目的index
     */
    private int mCurrentQuestion = 0;
    /**
     * 当前显示题目的uid
     */
    private String mCurrentQuestionUid;
    private List<HomeworkQuestionModel> questionModelList;
    private boolean goNextQuestion = true;
    private String newTopicFlag = "0";
    /**
     * 是否到最后一题
     */
    private boolean isEof = false;

    @Inject
    @Named("homework_time")
    SharedPreferencesUtil mHomeworkTimeSp;
    @Inject
    CloudRemoteDataSource cloudRemoteDataSource;
    @Inject
    CloudRemoteDataSource mCloudRemoteDataSource;

    @Inject
    public HomeworkDetailPresenter() {
    }

    @Override
    public void bindView(HomeworkDetailContract.View view) {

        mView = view;
        getNextTopic();
        mMyHomeworkUid = mIntent.getStringExtra(HomeworkStatisticActivity.MY_HOMEWORK_UID);
    }

    @Override
    public void getNextTopic() {

        Map<String, String> params = new HashMap<>();
        params.put("userUid", String.valueOf(RtcApplication.userUid));
        params.put("myHomeworkUid", mIntent.getStringExtra(HomeworkStatisticActivity.MY_HOMEWORK_UID));
        params.put("newTopicFlag", newTopicFlag);

        mCloudRemoteDataSource.getNextTopicForHomework(params,
                new CloudDataSource.GetDataCallback<HomeworkNextQuestion>() {
                    @Override
                    public void onLoadSuccess(HomeworkNextQuestion homeworkNextQuestion) {

                        questionModelList = homeworkNextQuestion.getHomeworkQuestionList();
                        mQuestionUrl = HttpUtils.HOMEWORK_TOPIC + "?userUid=" + RtcApplication.userUid
                                + "&myHomeworkUid=" + mIntent.getStringExtra(HomeworkStatisticActivity.MY_HOMEWORK_UID) + "&topicUid=";
                        // 下一题的uid
                        mQuestionUid = homeworkNextQuestion.getShowQuestionUid();

                        if (goNextQuestion) {
                            // 第一次直接显示未做的题目

                            mCurrentQuestion = Integer.valueOf(homeworkNextQuestion.getShowQuestionNo()) - 1;
                            mCurrentQuestionUid = mQuestionUid;

                        }
                        mView.showQuestion(mQuestionUrl + mCurrentQuestionUid);
                        if (homeworkNextQuestion.getFlag().equals("1")) {

                            updateQuestionIndex();
                        } else {

                            // 判断是否到最后一题
                            if (homeworkNextQuestion.getIsEOF().equals("1")) {

                                isEof = true;
                                updateQuestionIndex();
                                ToastUtils.showShort("已经到了最后一题");
                            } else {

                                ToastUtils.showShort(homeworkNextQuestion.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onLoadError() {

                    }

                    @Override
                    public void onLoadComplete() {

                    }
                });
    }

    @Override
    public void saveAnswer(String url) {

        url = url + "&duration=" + getHomeworkTime();

        mCloudRemoteDataSource.saveStuHomeworkAnswer(url,
                new CloudDataSource.GetDataCallback<HomeworkAnswer>() {
                    @Override
                    public void onLoadSuccess(HomeworkAnswer homeworkAnswer) {

                        if (homeworkAnswer.getFlag().equals("1")) {

                            saveHomeworkTime(1);
                            questionModelList.get(mCurrentQuestion).setIsRight(homeworkAnswer.getIsRight());
                            if (homeworkAnswer.getIsRight().equals("1")) {

                                goNextQuestion = true;
                                String url = getNextQuestion();
                                if (StringUtils.isTrimEmpty(url)) {
                                    return;
                                }
                                mView.showQuestion(url);
                            } else {

                                goNextQuestion = false;
                                mView.reloadWebView();
                                updateQuestionIndex();
                            }
//                            getNextTopic();
                        } else {

                            ToastUtils.showShort(homeworkAnswer.getMsg());
                        }
                    }

                    @Override
                    public void onLoadError() {

                    }

                    @Override
                    public void onLoadComplete() {

                    }
                });
    }

    @Override
    public void complainHomeworkQuestion() {

        Map<String, String> params = new HashMap<>();
        params.put("userUid", String.valueOf(RtcApplication.userUid));
        params.put("myHomeworkUid", mIntent.getStringExtra(HomeworkStatisticActivity.MY_HOMEWORK_UID));
        params.put("topicUid", String.valueOf(mCurrentQuestionUid));

        cloudRemoteDataSource.complainHomeworkQuestion(params,
                new CloudDataSource.DataLoadFinishCallback() {
                    @Override
                    public void onDataAvailable() {

                        HomeworkQuestionModel center = questionModelList.get(mCurrentQuestion);
                        center.setHasComplain("1");
                        mView.setQuestionComplained(true);
                    }

                    @Override
                    public void onDataNotAvailable() {

                    }
                });
    }

    @Override
    public String getPreviousQuestion() {
        if (mCurrentQuestion == 0) {
            ToastUtils.showShort("已经是第一题");
            return "";
        }
        // 上一题index
        int index = mCurrentQuestion - 1;
        // 上一题uid
        String id = questionModelList.get(index).getTopicUid();

        mCurrentQuestion = index;
        mCurrentQuestionUid = id;

        updateQuestionIndex();

        return mQuestionUrl + id;
    }

    @Override
    public String getNextQuestion() {

        if (mCurrentQuestion == questionModelList.size() - 1) {

            if (isEof) {

                ToastUtils.showShort("已经到了最后一题");
                return "";
            }
            newTopicFlag = "1";
            goNextQuestion = true;
            getNextTopic();
            return "";
        }
//        if (questionModelList.get(mCurrentQuestion).getIsRight() == null) {
//
//            ToastUtils.showShort("请先答题");
//            return "";
//        }
//        if (mCurrentQuestion == questionModelList.size() - 1) {
//
//            return "";
//        }
        // 下一题index
        int index = mCurrentQuestion + 1;
        // 下一题uid
        String id = questionModelList.get(index).getTopicUid();

        mCurrentQuestion = index;
        mCurrentQuestionUid = id;

        updateQuestionIndex();

        return mQuestionUrl + id;
    }

    @Override
    public void setIntent(Intent intent) {
        mIntent = intent;
    }

    @Override
    public void saveHomeworkTime(long time) {

        mHomeworkTimeSp.put(mCurrentQuestionUid, time);
    }

    @Override
    public long getHomeworkTime() {

        return mHomeworkTimeSp.getLong(mCurrentQuestionUid);
    }

    @Override
    public boolean getIsWorking() {
        if (mCurrentQuestionUid == null || mQuestionUid == null) {
            return false;
        }
        return mCurrentQuestionUid.equals(mQuestionUid);
    }

    @Override
    public boolean getTopicDone() {
        if (questionModelList == null) {// 还没有获取服务器数据
            return true;
        }
        Object done = questionModelList.get(mCurrentQuestion).getIsRight();

        if (done == null) {// 题目未做

            return false;
        } else {// 题目已做

            return true;
        }
    }

    private void updateQuestionIndex() {

        HomeworkQuestionModel center;
        HomeworkQuestionModel left = null;
        HomeworkQuestionModel right = null;

        center = questionModelList.get(mCurrentQuestion);

        if (mCurrentQuestion != 0) {
            left = questionModelList.get(mCurrentQuestion - 1);
        }

        if (mCurrentQuestion != questionModelList.size() - 1) {

            right = questionModelList.get(mCurrentQuestion + 1);
        }

        mView.showQuestionIndex(left, center, right);

        if (center.getHasComplain() == null) {

            mView.setQuestionComplained(false);
        } else {

            mView.setQuestionComplained(true);
        }
    }
}
