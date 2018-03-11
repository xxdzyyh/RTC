package com.tiilii.rtc.ui.practise.student;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiilii.rtc.R;
import com.tiilii.rtc.base.BaseFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * @author fox
 * @since 2018/01/12
 */

public class StudentFragment extends BaseFragment implements StudentContract.View {

    @Inject
    StudentContract.Presenter mPresenter;

    @Inject
    public StudentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_student, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPresenter.bindView(this);
    }

}
