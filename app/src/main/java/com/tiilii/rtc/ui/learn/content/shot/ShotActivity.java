package com.tiilii.rtc.ui.learn.content.shot;

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

public class ShotActivity extends BaseActivity {


    @Inject
    ShotFragment shotFragment;

    @Inject
    ShotPresenter shotPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_shot_content, shotFragment)
                .commit();
    }
}
