package com.tiilii.rtc.ui.read;

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
 * 主页
 *
 * @author fox
 * @since 2017/10/30
 */
public class ReadFragment extends BaseFragment {

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
    ReadViewFragment readViewFragment;

    @Inject
    ReadPresenter readPresenter;

    public static ReadFragment newInstance() {
        return new ReadFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_read, container, false);
        ButterKnife.bind(this, root);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.fl_homemain_content, readViewFragment)
                .commit();

        titleTextView.setText("诵经典");
        backImageView.setVisibility(View.GONE);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

}
