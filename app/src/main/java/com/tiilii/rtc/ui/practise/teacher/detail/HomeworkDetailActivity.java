package com.tiilii.rtc.ui.practise.teacher.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tiilii.rtc.R;
import com.tiilii.rtc.base.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

/**
 * @author fox
 * @since 2018/01/12
 */

public class HomeworkDetailActivity extends BaseActivity {


    @Inject
    HomeworkDetailFragment homeworkDetailFragment;

    @Inject
    HomeworkDetailPresenter homeworkDetailPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_detail);
        ButterKnife.bind(this);

        homeworkDetailPresenter.setIntent(getIntent());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_homework_detail_content, homeworkDetailFragment)
                .commit();
    }

    @OnClick(R.id.iv_back)
    void backActivity() {

        finish();
    }
}
