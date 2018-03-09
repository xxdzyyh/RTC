package com.tiilii.rtc.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiilii.rtc.R;
import com.tiilii.rtc.base.BaseFragment;
import com.tiilii.rtc.ui.learn.mainpage.LearnFragment;
import com.tiilii.rtc.ui.practise.teacher.list.HomeworkListActivity;
import com.tiilii.rtc.ui.read.ReadFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页
 *
 * @author fox
 * @since 2017/09/22
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {

    ReadFragment readFragment;
    LearnFragment learnFragment;

    @Inject
    HomeContract.Presenter mPresenter;

    @Inject
    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);

        readFragment = ReadFragment.newInstance();

        addFragment(readFragment, "read");
        showFragment(readFragment, "read");
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPresenter.bindView(this);
    }


    /**
     * 点击颂经典
     */
    @OnClick(R.id.tv_read)
    void showRead() {


        hideFragment(learnFragment, "learn");
        showFragment(readFragment, "read");
    }

    /**
     * 点击习汉字
     */
    @OnClick(R.id.tv_learn)
    void showLearn() {


        if (learnFragment == null) {
            learnFragment = LearnFragment.newInstance();

            addFragment(learnFragment, "learn");
        }

        hideFragment(readFragment, "read");
        showFragment(learnFragment, "learn");
    }

    @OnClick(R.id.tv_practise)
    void clickPractise() {

        Intent intent = new Intent(mContext, HomeworkListActivity.class);
        startActivity(intent);
    }

    /**
     * 隐藏Fragment
     */
    private void hideFragment(Fragment fragment, String tag) {
        if (getChildFragmentManager().findFragmentByTag(tag) != null) {

            getChildFragmentManager().beginTransaction()
                    .hide(fragment)
                    .commit();
        }
    }

    /**
     * 显示Fragment
     */
    private void showFragment(Fragment fragment, String tag) {
        if (getChildFragmentManager().findFragmentByTag(tag) != null) {

            getChildFragmentManager().beginTransaction()
                    .show(fragment)
                    .commit();
        }
    }

    /**
     * 添加Fragment
     */
    private void addFragment(Fragment fragment, String tag) {

        getChildFragmentManager().beginTransaction()
                .add(R.id.fl_fragment_content, fragment, tag)
                .commit();
    }
}
