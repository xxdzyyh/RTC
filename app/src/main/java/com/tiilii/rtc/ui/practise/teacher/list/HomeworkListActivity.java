package com.tiilii.rtc.ui.practise.teacher.list;

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
 * 作业列表
 *
 * @author fox
 * @since 2018/01/12
 */

public class HomeworkListActivity extends BaseActivity {

    /**
     * 标题
     */
    @BindView(R.id.tv_title)
    TextView titleTextView;

    @Inject
    HomeworkListFragment homeworkListFragment;

    @Inject
    HomeworkListPresenter homeworkListPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_list);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_homework_list_content, homeworkListFragment)
                .commit();

        homeworkListPresenter.setIntent(getIntent());

        titleTextView.setText("作业");
    }

    /**
     * 标题栏返回按钮
     */
    @OnClick(R.id.iv_back)
    void backActivity() {

        finish();
    }

}
