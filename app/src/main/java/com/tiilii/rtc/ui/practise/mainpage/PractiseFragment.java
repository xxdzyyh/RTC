package com.tiilii.rtc.ui.practise.mainpage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tiilii.rtc.R;
import com.tiilii.rtc.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

/**
 * 习汉字
 *
 * @author fox
 * @since 2018/03/05
 */

public class PractiseFragment extends BaseFragment {

    /**
     * 标题
     */
    @BindView(R.id.tv_title)
    TextView titleTextView;

    // 右item

    @BindView(R.id.tv_side_text)
    TextView rightTextView;

    /**
     * 返回键。在这个页面隐藏掉。
     */
    @BindView(R.id.iv_back)
    ImageView backImageView;

    @Inject
    PractiseViewFragment practiseViewFragment;

    @Inject
    PractisePresenter practisePresenter;

    public static PractiseFragment newInstance() {
        return new PractiseFragment();
    }

    public PractiseFragment() {
        practisePresenter = new PractisePresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_practise, container, false);
        ButterKnife.bind(this, root);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.fl_practise_content, practiseViewFragment)
                .commit();

        titleTextView.setText("练运算");
        backImageView.setVisibility(View.GONE);

        rightTextView.setText("提交");

        return root;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @OnClick(R.id.tv_side_text)
    void commit() {
        Toast.makeText(mContext,"作业已完成",Toast.LENGTH_SHORT).show();
    }
}
