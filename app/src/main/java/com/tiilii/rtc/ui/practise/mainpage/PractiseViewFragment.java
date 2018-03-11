package com.tiilii.rtc.ui.practise.mainpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiilii.rtc.R;
import com.tiilii.rtc.base.BaseFragment;
import com.tiilii.rtc.ui.learn.content.shot.ShotActivity;
import com.tiilii.rtc.ui.learn.content.write.WriteActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 习汉字
 *
 * @author fox
 * @since 2018/03/05
 */

public class PractiseViewFragment extends BaseFragment implements PractiseContract.View {


    @Inject
    PractiseContract.Presenter mPresenter;

    @Inject
    public PractiseViewFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_practise_view, container, false);
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


}
