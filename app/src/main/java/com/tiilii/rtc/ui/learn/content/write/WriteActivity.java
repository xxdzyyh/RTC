package com.tiilii.rtc.ui.learn.content.write;

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
 * @since 2017/11/20
 */

public class WriteActivity extends BaseActivity {

    /**
     * 标题
     */
    @BindView(R.id.tv_title)
    TextView titleTextView;

    @Inject
    WriteFragment writeFragment;

    @Inject
    WritePresenter writePresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_write_content, writeFragment)
                .commit();

        titleTextView.setText("写字");
    }

    @OnClick(R.id.iv_back)
    void backActivity() {

        finish();
    }
}
