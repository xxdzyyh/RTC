package com.tiilii.rtc.ui.practise.teacher.statistic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bovink.gradient.GradientTextView;
import com.bovink.gradient.GradientView;
import com.tiilii.rtc.R;
import com.tiilii.rtc.base.BaseFragment;
import com.tiilii.rtc.model.HomeworkScheduleModel;
import com.tiilii.rtc.ui.practise.teacher.detail.HomeworkDetailActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作业统计
 *
 * @author fox
 * @since 2018/01/12
 */

public class HomeworkStatisticFragment extends BaseFragment implements HomeworkStatisticContract.View {

    /**
     * 结束倒计时
     */
    @BindView(R.id.tv_countdown)
    TextView countdownTextView;
    /**
     * 作业类型
     */
    @BindView(R.id.tv_homework_type)
    TextView homeworkTypeTextView;
    /**
     * 总共完成数
     */
    @BindView(R.id.tv_complete_total)
    TextView completeTotalTextView;
    /**
     * 问题总数
     */
    @BindView(R.id.tv_question_total)
    TextView questionTotalTextView;
    /**
     * 今日完成量
     */
    @BindView(R.id.tv_complete_today)
    TextView completeTodayTextView;
    /**
     * 科目
     */
    @BindView(R.id.tv_subject)
    TextView subjectTextView;
    /**
     * 进度条
     */
    @BindView(R.id.view_progress)
    GradientView progressView;
    /**
     * 今日建议学习数量
     */
    @BindView(R.id.tv_suggest_num)
    TextView suggestNumTextView;
    /**
     * 去做作业
     */
    @BindView(R.id.tv_do_homework)
    GradientTextView doHomeworkTextView;
    /**
     * 打开日历
     */
    @BindView(R.id.tv_calendar)
    GradientTextView calendarTextView;

    @Inject
    HomeworkStatisticContract.Presenter mPresenter;

    @Inject
    public HomeworkStatisticFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_homework_statistic, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mPresenter.bindView(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.getHomeworkSchedule();
    }

    @OnClick(R.id.tv_do_homework)
    void doHomework() {
        if (mPresenter.isHomeworkFinished()) {
            ToastUtils.showShort(R.string.homework_time_has_expired);

            return;
        }

        Intent intent = getActivity().getIntent();
        intent.setClass(mContext, HomeworkDetailActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ll_mistake)
    void showMistake() {

//        Intent intent = getActivity().getIntent();
//        intent.setClass(mContext, HomeworkMistakeActivity.class);
//        startActivity(intent);
    }

    @OnClick(R.id.tv_calendar)
    void showCalendar() {

//        Intent intent = getActivity().getIntent();
//        intent.setClass(mContext, HomeworkCalendarActivity.class);
//        startActivity(intent);
    }

    @Override
    public void updateViews(HomeworkScheduleModel model) {
        getActivity().getIntent().putExtra(HomeworkStatisticActivity.QUESTION_COUNT, model.getHomeworkCountTopic());

        // 结束倒计时
        countdownTextView.setText(model.getCountdown() + "天");

        // 作业类型
        homeworkTypeTextView.setText(model.getHomeworkTypeName());

        // 总完成量
        completeTotalTextView.setText(String.valueOf(model.getAllFinishTopic()));

        // 问题总数
        questionTotalTextView.setText("/" + model.getHomeworkCountTopic());

        // 今日完成量
        completeTodayTextView.setText(String.valueOf(model.getTodayFinishTopic()));

        // 科目
        subjectTextView.setText(model.getCourseName());

        // 今日建议完成量
        suggestNumTextView.setText(String.valueOf(model.getSuggestFinish()));

        // 进度值
        int progress = model.getAllFinishTopic() * 100 / model.getHomeworkCountTopic();
        // 根据进度值设置圆角
        if (progress == 100) {

            progressView.setGradientCornerRadius(SizeUtils.dp2px(5));
        } else {

            progressView.setGradientCornerRadii(new float[]{
                    SizeUtils.dp2px(5), SizeUtils.dp2px(5),
                    0, 0,
                    0, 0,
                    SizeUtils.dp2px(5), SizeUtils.dp2px(5)
            });
        }
        // 设置进度条的比重来表示作业完成百分比
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) progressView.getLayoutParams();
        params.weight = progress;
        progressView.setLayoutParams(params);
    }
}
