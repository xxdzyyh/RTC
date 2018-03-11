package com.tiilii.rtc.ui.practise.student;

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
 * @author fox
 * @since 2018/01/12
 */

public class StudentActivity extends BaseActivity {

    /**
     * 标题
     */
    @BindView(R.id.tv_title)
    TextView titleTextView;

    @Inject
    StudentFragment studentFragment;

    @Inject
    StudentPresenter studentPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_student_content, studentFragment)
                .commit();

        titleTextView.setText("自主练习");
    }

    @OnClick(R.id.iv_back)
    void backActivity() {

        finish();
    }
}
