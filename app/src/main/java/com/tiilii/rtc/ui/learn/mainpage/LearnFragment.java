package com.tiilii.rtc.ui.learn.mainpage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiilii.rtc.R;
import com.tiilii.rtc.base.BaseFragment;
import com.tiilii.rtc.ui.home.HomePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * 习汉字
 *
 * @author fox
 * @since 2018/03/05
 */

public class LearnFragment extends BaseFragment {

    /**
     * 标题
     */
    @BindView(R.id.tv_title)
    TextView titleTextView;
    /**
     * 返回键。在这个页面隐藏掉。
     */
    @BindView(R.id.iv_back)
    ImageView backImageView;

    @Inject
    LearnViewFragment learnViewFragment;

    @Inject
    LearnPresenter learnPresenter;

    public static LearnFragment newInstance() {
        return new LearnFragment();
    }

    @Inject
    public LearnFragment() {
        learnPresenter = new LearnPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_learn, container, false);
        ButterKnife.bind(this, root);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.fl_learn_content, learnViewFragment)
                .commit();

        titleTextView.setText("习汉字");
        backImageView.setVisibility(View.GONE);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

}
