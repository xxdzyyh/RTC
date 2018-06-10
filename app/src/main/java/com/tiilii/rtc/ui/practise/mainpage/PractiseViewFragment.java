package com.tiilii.rtc.ui.practise.mainpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.tiilii.rtc.R;
import com.tiilii.rtc.base.BaseFragment;
import com.tiilii.rtc.ui.practise.student.StudentActivity;
import com.tiilii.rtc.ui.practise.teacher.list.HomeworkListActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 练运算
 *
 * @author fox
 * @since 2018/03/05
 */

public class PractiseViewFragment extends BaseFragment implements PractiseContract.View {

    private View view0,view1,view2,view3,view4;
    private WebView webView0,webView1,webView2,webView3,webView4;

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    MyPagerAdapter pagerAdapter;
    List<View>views = new ArrayList<>();


    @BindView(R.id.pre_button)
    Button preButton;

    @BindView(R.id.next_button)
    Button nextButton;

    @Inject
    PractiseContract.Presenter mPresenter;

    @Inject
    public PractiseViewFragment() {
        mPresenter = new PractisePresenter();
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

        if (pagerAdapter == null) {

            LayoutInflater lf = getLayoutInflater();

            view0 = lf.inflate(R.layout.question_web_view,null);
            view1 = lf.inflate(R.layout.question_web_view,null);
            view2 = lf.inflate(R.layout.question_web_view,null);
            view3 = lf.inflate(R.layout.question_web_view,null);
            view4 = lf.inflate(R.layout.question_web_view,null);

            webView0 = view0.findViewById(R.id.question_web_view);
            webView1 = view1.findViewById(R.id.question_web_view);
            webView2 = view2.findViewById(R.id.question_web_view);
            webView3 = view3.findViewById(R.id.question_web_view);
            webView4 = view4.findViewById(R.id.question_web_view);

            setupWebView(webView0);
            setupWebView(webView1);
            setupWebView(webView2);
            setupWebView(webView3);
            setupWebView(webView4);

//            267252、267371、111779、262544、267467

            String url = "http://59.175.213.78:30164/yunduo/homeworkStu/getTopicForAnswer.do?userUid=4011&myHomeworkUid=911&topicUid=";

            String murl = url + "267252";
            webView0.loadUrl(murl);

            murl = url + "267371";
            webView1.loadUrl(murl);

            murl = url + "111779";
            webView2.loadUrl(murl);

            murl = url + "262544";
            webView3.loadUrl(murl);

            murl = url + "267467";
            webView4.loadUrl(murl);

            views.add(view0);
            views.add(view1);
            views.add(view2);
            views.add(view3);
            views.add(view4);

            pagerAdapter = new MyPagerAdapter(views);
        }

        viewPager.setAdapter(pagerAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == views.size()-1) {
                    nextButton.setEnabled(false);
                } else {
                    nextButton.setEnabled(true);
                }

                if(position == 0) {
                    preButton.setEnabled(false);
                } else {
                    preButton.setEnabled(true);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position == views.size()-1) {
                    nextButton.setEnabled(false);
                } else {
                    nextButton.setEnabled(true);
                }

                if(position == 0) {
                    preButton.setEnabled(false);
                } else {
                    preButton.setEnabled(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    void setupWebView(WebView webView) {

        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//设置webview推荐使用的窗口
        webSettings.setLoadWithOverviewMode(true);//设置webview加载的页面的模式
        webSettings.setDisplayZoomControls(false);//隐藏webview缩放按钮
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(true); // 支持缩放

        //主要用于平板，针对特定屏幕代码调整分辨率
        DisplayMetrics metrics = new DisplayMetrics();

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int mDensity = metrics.densityDpi;
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }

        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型：
         * 1、LayoutAlgorithm.NARROW_COLUMNS ：适应内容大小
         * 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
         */
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
    }


    @OnClick(R.id.next_button)
    void nextQuestion() {
        int currentIndex = viewPager.getCurrentItem();
        int index = 0;

        if (currentIndex == 4) {
            // 最后一题

        } else {
            index = currentIndex + 1;

            viewPager.setCurrentItem(index, true);
        }
    }

    @OnClick(R.id.pre_button)
    void  preQuestion() {
        int currentIndex = viewPager.getCurrentItem();
        int index = 0;

        if (currentIndex == 0) {
            //
        } else {
            index = currentIndex - 1;

            viewPager.setCurrentItem(index, true);
        }
    }




//    @OnClick(R.id.tv_student)
//    void showShot() {
//
//        Intent intent = new Intent(mContext, StudentActivity.class);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.tv_teacher)
//    void showWrite() {
//
//        Intent intent = new Intent(mContext, HomeworkListActivity.class);
//        startActivity(intent);
//    }


}
