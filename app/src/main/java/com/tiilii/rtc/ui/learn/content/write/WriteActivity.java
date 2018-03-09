package com.tiilii.rtc.ui.learn.content.write;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tiilii.rtc.R;
import com.tiilii.rtc.base.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

/**
 * @author fox
 * @since 2017/11/20
 */

public class WriteActivity extends BaseActivity {


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
    }
}
