package com.tiilii.rtc.ui.me;

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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by wangxuefeng on 2018/6/8.
 */

public class MyFragment extends BaseFragment {

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


    MyViewFragment myViewFragment;
    MyPresenter myPreseter;

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    public MyFragment() {
        myPreseter = new MyPresenter();
        myViewFragment = new MyViewFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, root);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.fl_my_content, myViewFragment)
                .commit();

        titleTextView.setText("个人中心");
        backImageView.setVisibility(View.GONE);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
