package com.tiilii.rtc.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tiilii.rtc.R;
import com.tiilii.rtc.base.BaseFragment;
import com.tiilii.rtc.base.mvp.WebViewActivity;
import com.tiilii.rtc.ui.learn.content.shot.ShotActivity;
import com.tiilii.rtc.ui.learn.content.write.WriteActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangxuefeng on 2018/6/8.
 */

public class MyViewFragment extends BaseFragment implements MyContract.View,View.OnClickListener {

    Button jieduanScoreButton;
    Button finalScoreButton;
    Button queryButton;
    Button actionButton;
    Button settingButton;

    @Inject
    MyContract.Presenter mPresenter;

    @Inject
    public MyViewFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_view, container, false);

        jieduanScoreButton = (Button)root.findViewById(R.id.jieduan_button);
        finalScoreButton = (Button)root.findViewById(R.id.final_button);
        queryButton = (Button)root.findViewById(R.id.query_button);
        actionButton = (Button)root.findViewById(R.id.action_button);
        settingButton = (Button)root.findViewById(R.id.setting_button);

        jieduanScoreButton.setOnClickListener(this);
        finalScoreButton.setOnClickListener(this);
        queryButton.setOnClickListener(this);
        actionButton.setOnClickListener(this);
        settingButton.setOnClickListener(this);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @OnClick(R.id.tv_shot)
    void showShot() {

        Intent intent = new Intent(mContext, ShotActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_write)
    void showWrite() {

        Intent intent = new Intent(mContext, WriteActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {

        Intent intent = new Intent(mContext, WebViewActivity.class);

        if (view == jieduanScoreButton) {

            intent.putExtra("url","http://fs.tiilii.com/rtc/image/proc.png");
        } else if (view == finalScoreButton) {

            intent.putExtra("url","http://fs.tiilii.com/rtc/image/final.png");
        } else if (view == queryButton) {

            intent.putExtra("url","http://fs.tiilii.com/rtc/image/exam.png");
        } else if (view == actionButton) {

            intent.putExtra("url","http://fs.tiilii.com/rtc/image/habit.png");
        } else if (view == settingButton) {

        }

        startActivity(intent);
    }
}
