package com.tiilii.rtc.ui.practise.teacher.statistic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.tiilii.rtc.R;
import com.tiilii.rtc.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

/**
 * 作业统计
 *
 * @author fox
 * @since 2018/01/12
 */

public class HomeworkStatisticActivity extends BaseActivity {

    /**
     * intent获取作业id的key
     */
    public final static String MY_HOMEWORK_UID = "my_homework_uid";
    /**
     * 作业uid
     */
    public final static String HOMEWORK_UID = "homework_uid";
    /**
     * 作业科目
     */
    public final static String COURSE_NAME = "course_name";
    /**
     * 作业题目总数
     */
    public final static String QUESTION_COUNT = "question_count";

    /**
     * 标题
     */
    @BindView(R.id.tv_title)
    TextView titleTextView;

    @Inject
    HomeworkStatisticFragment homeworkStatisticFragment;

    @Inject
    HomeworkStatisticPresenter homeworkStatisticPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_statistic);
        ButterKnife.bind(this);

        homeworkStatisticPresenter.setIntent(getIntent());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_homework_statistic_content, homeworkStatisticFragment)
                .commit();

        titleTextView.setText("作业统计");
    }

    /**
     * 标题栏返回按钮
     */
    @OnClick(R.id.iv_back)
    void backActivity() {

        finish();
    }
}
