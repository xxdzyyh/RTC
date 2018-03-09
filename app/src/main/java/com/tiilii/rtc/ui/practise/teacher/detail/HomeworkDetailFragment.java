package com.tiilii.rtc.ui.practise.teacher.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bovink.gradient.GradientTextView;
import com.tiilii.rtc.R;
import com.tiilii.rtc.base.BaseFragment;
import com.tiilii.rtc.model.HomeworkQuestionModel;
import com.tiilii.rtc.ui.practise.teacher.statistic.HomeworkStatisticActivity;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author fox
 * @since 2018/01/12
 */

public class HomeworkDetailFragment extends BaseFragment implements HomeworkDetailContract.View {

    private ActivityView activityView;
    private int questionNum;
    private long mHomeworkTime = 0;

    /**
     * 题目网页
     */
    @BindView(R.id.wv_homework)
    WebView homeworkWebView;
    /**
     * 加载题目的进度
     */
    @BindView(R.id.pb_progress)
    ProgressBar progressBar;

    //TODO 替换标题栏的返回图片
//    @BindColor(R.color.red)
//    int redColor;
//    @BindColor(R.color.green_1)
//    int greenColor;
//    @BindColor(R.color.gray_3)
//    int grayColor;

    @Inject
    HomeworkDetailContract.Presenter mPresenter;

    @Inject
    public HomeworkDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_homework_detail, container, false);
        ButterKnife.bind(this, root);
        // 绑定Activity的视图
        activityView = new ActivityView();
        ButterKnife.bind(activityView, getActivity());

        questionNum = getActivity().getIntent().getIntExtra(HomeworkStatisticActivity.QUESTION_COUNT, 0);

        homeworkWebView.getSettings().setJavaScriptEnabled(true);
        homeworkWebView.setWebViewClient(new HomeworkWebViewClient());
        homeworkWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setVisibility(View.VISIBLE);

                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                }

                super.onProgressChanged(view, newProgress);
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPresenter.bindView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        startCountdown();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.saveHomeworkTime(mHomeworkTime);
    }

    @Override
    public void showQuestion(String url) {

        startCountdown();
        homeworkWebView.loadUrl(url);
    }

    @Override
    public void showQuestionIndex(HomeworkQuestionModel left, HomeworkQuestionModel center, HomeworkQuestionModel right) {

        activityView.leftTextView.setVisibility(View.VISIBLE);
        activityView.rightTextView.setVisibility(View.VISIBLE);
        if (left == null) {

            activityView.leftTextView.setVisibility(View.INVISIBLE);
        } else {

            setQuestionIndexStatus(activityView.leftTextView, left);
        }

        if (right == null) {

            if (center.getTopicNo() < questionNum) {// 如果不是最后一题，则显示下一题题号

                activityView.rightTextView.setText(String.valueOf(center.getTopicNo() + 1));
//                activityView.rightTextView.setGradientStroke(3, grayColor);
            } else {

                activityView.rightTextView.setVisibility(View.INVISIBLE);
            }
        } else {

            setQuestionIndexStatus(activityView.rightTextView, right);
        }

        setQuestionIndexStatus(activityView.centerTextView, center);
    }

    @Override
    public void startCountdown() {
        mHomeworkTime = mPresenter.getHomeworkTime();
//        if (mHomeworkTime == -1) {
//            mHomeworkTime = 0;
//        }
        mBackgroundDisposable.clear();
        if (mPresenter.getTopicDone()) {

            return;
        }

        mBackgroundDisposable.add(Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {

                        mHomeworkTime += 1;
                        mPresenter.saveHomeworkTime(mHomeworkTime);
                    }
                }));
    }

    @Override
    public void reloadWebView() {
        homeworkWebView.reload();
    }

    @Override
    public void setQuestionComplained(boolean complained) {

        if (complained) {

            activityView.sideText.setText("题目已申诉");
            activityView.sideText.setEnabled(false);
        } else {

            activityView.sideText.setText("题目申诉");
            activityView.sideText.setEnabled(true);
        }
    }

    private void setQuestionIndexStatus(GradientTextView view, HomeworkQuestionModel model) {
//        view.setGradientStroke(3, grayColor);

        view.setText(String.valueOf(model.getTopicNo()));
        if (model.getIsRight() == null) {// 这题还没做

            return;
        }

        if (model.getIsRight().equals("1")) {


//            view.setGradientStroke(3, greenColor);
        } else {

//            view.setGradientStroke(3, redColor);
        }

    }

    @OnClick(R.id.tv_previous_question)
    void previousQuestion() {

        String url = mPresenter.getPreviousQuestion();
        if (StringUtils.isTrimEmpty(url)) {
            return;
        }
        showQuestion(url);
    }

    @OnClick(R.id.tv_next_question)
    void nextQuestion() {

        String url = mPresenter.getNextQuestion();
        if (StringUtils.isTrimEmpty(url)) {
            return;
        }
        showQuestion(url);
    }

    private class HomeworkWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            mPresenter.saveAnswer(url);
            return true;
        }
    }

    /**
     * Fragment连接的View
     */
    public class ActivityView {
        /**
         * 标题栏左边index
         */
        @BindView(R.id.tv_left)
        GradientTextView leftTextView;
        /**
         * 标题栏中间index
         */
        @BindView(R.id.tv_center)
        GradientTextView centerTextView;
        /**
         * 标题栏右边index
         */
        @BindView(R.id.tv_right)
        GradientTextView rightTextView;
        /**
         * 申诉按钮
         */
        @BindView(R.id.tv_side_text)
        TextView sideText;

        @OnClick(R.id.tv_side_text)
        void complainQuestion() {

            mPresenter.complainHomeworkQuestion();
        }
    }
}
