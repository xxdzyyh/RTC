package com.tiilii.rtc.ui.learn.mainpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.tiilii.rtc.R;
import com.tiilii.rtc.base.BaseFragment;
import com.tiilii.rtc.ui.learn.content.shot.ShotActivity;
import com.tiilii.rtc.ui.learn.content.write.WriteActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.inject.Inject;
import javax.sql.DataSource;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.baidu.speech.core.LogUtil.TAG;

/**
 * 习汉字
 *
 * @author fox
 * @since 2018/03/05
 */

public class LearnViewFragment extends BaseFragment implements LearnContract.View {

    @BindView(R.id.sv_write)
    SearchView searchView;


    @BindView(R.id.iv_word)
    ImageView imageView;

    @Inject
    LearnContract.Presenter mPresenter;

    @Inject
    public LearnViewFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_learn_view, container, false);
        ButterKnife.bind(this, root);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                Log.e("LearnViewFragment", s);

                if (TextUtils.isEmpty(s)) {
                    Toast.makeText(mContext, "请输入查找内容！", Toast.LENGTH_SHORT).show();
                } else if (s.length() >= 1) {
                    // 查找
                    String string = null;

                    try {
                        string = URLEncoder.encode(s.substring(0,1),"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    //
                    String url = "http://fs.tiilii.com/rtc/hanzi/" + string +".gif";

                    Log.e("Glide",url);
                    Log.e("Thread",Thread.currentThread().toString());

                    imageView.setBackgroundColor(getResources().getColor(R.color.blue_1));

                    Glide.with(imageView.getContext())
                            .load(url)
                            .asGif()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(imageView);

                    if (searchView != null) {
                        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

                        if (imm != null) {
                            // 这将让键盘在所有的情况下都被隐藏，但是一般我们在点击搜索按钮后，输入法都会乖乖的自动隐藏的。
                            imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0); // 输入法如果是显示状态，那么就隐藏输入法
                        }
                        searchView.clearFocus(); // 不获取焦点
                    }
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

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
