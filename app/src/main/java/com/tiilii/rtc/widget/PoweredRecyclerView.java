package com.tiilii.rtc.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tiilii.rtc.utils.DensityUtils;


/**
 * Created by Retina975 on 17/6/14.
 * <p>
 * 新版PoweredRecyclerView
 */
public class PoweredRecyclerView extends SwipeRefreshLayout {

    /**
     * 主NestedScrollView
     */
    private NestedScrollView mainNestedScrollView;
    /**
     * 顶部视图容器,用来储存将要加载的顶部视图
     */
    private FrameLayout headerFrameLayout;
    /**
     * 内容RecyclerView
     */
    private RecyclerView contentRecyclerView;
    /**
     * 底部视图
     */
    private FrameLayout footerFrameLayout;
    /**
     * 空视图
     */
    private FrameLayout emptyFrameLayout;
    /**
     * 上下文
     */
    private Context context;
    /**
     * 是否已经在底部
     */
    private static final int DETECT_BOTTOM = 1;
    /**
     * 数据改变监听
     */
    private OnDataChangeListener onDataChangeListener;
    /**
     * 底部视图
     */
    private OnFooterViewChangedListener onFooterViewChanged;

    /**
     * 刷新时间
     */
    private long refreshTime = 300;
    /**
     * 加载时间
     */
    private long loadMoreTime = 300;
    /**
     * 检测时间
     */
    private long detectTime = 300;

    public PoweredRecyclerView(Context context) {
        super(context);

        this.context = context;
        init();
    }

    public PoweredRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        init();
    }

    private void init() {

        initView();
        setListener();
    }

    private void initView() {
        int wc = LayoutParams.WRAP_CONTENT;
        int mp = LayoutParams.MATCH_PARENT;

        // NestedScrollView
        mainNestedScrollView = new NestedScrollView(context);
        LayoutParams nsv_params = new LayoutParams(mp, mp);
        mainNestedScrollView.setLayoutParams(nsv_params);
        mainNestedScrollView.setFillViewport(true);
        addView(mainNestedScrollView);

        // LinearLayout
        LinearLayout linearLayout = new LinearLayout(context);
        LayoutParams ll_params = new LayoutParams(mp, mp);
        linearLayout.setLayoutParams(ll_params);
        linearLayout.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        mainNestedScrollView.addView(linearLayout);

        // Header
        headerFrameLayout = new FrameLayout(context);
        LayoutParams header_params = new LayoutParams(mp, wc);
        headerFrameLayout.setLayoutParams(header_params);
        linearLayout.addView(headerFrameLayout);

        // Content帧布局,包含Content和Empty
        FrameLayout contentFrameLayout = new FrameLayout(context);
        LayoutParams content_frame_params = new LayoutParams(mp, mp);
        contentFrameLayout.setLayoutParams(content_frame_params);
        linearLayout.addView(contentFrameLayout);

        // Content线性布局,包含RecyclerView和FootFrameLayout
        LinearLayout contentLinearLayout = new LinearLayout(context);
        LayoutParams content_params = new LayoutParams(mp, mp);
        contentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        contentLinearLayout.setLayoutParams(content_params);
        contentFrameLayout.addView(contentLinearLayout);

        // Content
        contentRecyclerView = new RecyclerView(context);
        LayoutParams rv_params = new LayoutParams(mp, wc);
        contentRecyclerView.setLayoutParams(rv_params);
        contentRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        contentRecyclerView.setNestedScrollingEnabled(false);
        contentLinearLayout.addView(contentRecyclerView);

        // Footer
        footerFrameLayout = new FrameLayout(context);
        LayoutParams footer_params = new LayoutParams(mp, mp);
        footerFrameLayout.setLayoutParams(footer_params);
        contentLinearLayout.addView(footerFrameLayout);

        // Empty
        emptyFrameLayout = new FrameLayout(context);
        LayoutParams empty_params = new LayoutParams(mp, mp);
        emptyFrameLayout.setLayoutParams(empty_params);
        emptyFrameLayout.setVisibility(View.GONE);
        contentFrameLayout.addView(emptyFrameLayout);

        // EmptyView
        TextView emptyTextView = new TextView(context);
        LayoutParams emptyTextParams = new LayoutParams(mp, mp);
        emptyTextView.setLayoutParams(emptyTextParams);
        emptyTextView.setGravity(Gravity.CENTER);
        emptyTextView.setText("没有数据");
        emptyTextView.setTextSize(16);
        emptyTextView.setTextColor(Color.parseColor("#323232"));
        emptyFrameLayout.addView(emptyTextView);

        // FooterView
        FooterViewInner footerView = new FooterViewInner(context);
        FrameLayout.LayoutParams footerViewParams = new FrameLayout.LayoutParams(wc, wc);
        footerViewParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        footerViewParams.topMargin = DensityUtils.dpToPx(context, 20);
        footerViewParams.bottomMargin = DensityUtils.dpToPx(context, 20);
        footerView.setLayoutParams(footerViewParams);
        footerFrameLayout.addView(footerView);

        onFooterViewChanged = footerView;
    }

    private void setListener() {
        // 检测是否一开始就触底
        handler.sendEmptyMessageDelayed(DETECT_BOTTOM, detectTime);

        // 检测刷新事件
        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessageDelayed(DETECT_BOTTOM, detectTime);
                if (onFooterViewChanged != null) {

                    onFooterViewChanged.showLoadMoreView(footerFrameLayout);
                }

                if (onDataChangeListener != null) {

                    onDataChangeListener.onRefreshData();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setRefreshing(false);

                    }
                }, refreshTime);
            }
        });

        // 滑动监听
        mainNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {

                    loadInner();

                }
            }
        });
    }

    /**
     * 内部加载方法
     */
    private void loadInner() {
        if (onDataChangeListener != null) {

            boolean continueLoad = onDataChangeListener.onLoadStateSet();

            if (continueLoad) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        onDataChangeListener.onLoadMoreData();

                    }
                }, loadMoreTime);
            } else {
                if (onFooterViewChanged != null) {

                    onFooterViewChanged.showLoadEndView(footerFrameLayout);
                }
            }
        }
    }

    /**
     * 用来检测RecyclerView内容的高度是否超过NestedScrollView
     */
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DETECT_BOTTOM:

                    int scrollviewHeight = mainNestedScrollView.getMeasuredHeight();
                    int contentHeight = mainNestedScrollView.getChildAt(0).getMeasuredHeight();

                    if (scrollviewHeight == contentHeight) {
                        loadInner();

                        this.sendEmptyMessageDelayed(DETECT_BOTTOM, detectTime);
                    }
                    break;
            }
        }
    };

    /**
     * 设置顶部视图
     *
     * @param layout
     */
    public final void setHeaderView(@LayoutRes int layout) {
        headerFrameLayout.removeAllViews();
        View view = LayoutInflater.from(context).inflate(layout, headerFrameLayout, false);
        headerFrameLayout.addView(view);
    }

    /**
     * 设置顶部视图
     *
     * @param view
     * @param params
     */
    public final void setHeaderView(View view, FrameLayout.LayoutParams params) {
        headerFrameLayout.removeAllViews();

        headerFrameLayout.addView(view, params);
    }

    /**
     * 设置底部视图
     *
     * @param layout
     */
    public final void setFooterView(@LayoutRes int layout) {
        footerFrameLayout.removeAllViews();
        View view = LayoutInflater.from(context).inflate(layout, footerFrameLayout, false);
        footerFrameLayout.addView(view);
        onFooterViewChanged = null;
    }

    /**
     * 设置底部视图
     *
     * @param view
     */
    public final void setFooterView(View view, FrameLayout.LayoutParams params) {
        footerFrameLayout.removeAllViews();

        footerFrameLayout.addView(view, params);
        onFooterViewChanged = null;
    }

    /**
     * 设置底部视图是否可见
     *
     * @param show
     */
    public final void showContentView(boolean show) {
        if (show) {
            contentRecyclerView.setVisibility(VISIBLE);
            footerFrameLayout.setVisibility(VISIBLE);
            emptyFrameLayout.setVisibility(INVISIBLE);
        } else {
            contentRecyclerView.setVisibility(INVISIBLE);
            footerFrameLayout.setVisibility(INVISIBLE);
            emptyFrameLayout.setVisibility(VISIBLE);
        }
    }

    /**
     * 设置空视图
     *
     * @param layout
     */
    public final void setEmptyView(@LayoutRes int layout) {
        emptyFrameLayout.removeAllViews();
        View view = LayoutInflater.from(context).inflate(layout, emptyFrameLayout, false);
        emptyFrameLayout.addView(view);
    }

    /**
     * 设置空视图
     *
     * @param view
     * @param params
     */
    public final void setEmptyView(View view, FrameLayout.LayoutParams params) {
        emptyFrameLayout.removeAllViews();

        emptyFrameLayout.addView(view, params);
    }




    /**
     * 获取内容RcyclerView
     *
     * @return
     */
    public RecyclerView getContentRecyclerView() {
        return contentRecyclerView;
    }

    /**
     * 设置数据变化监听
     *
     * @param onDataChangeListener
     */
    public final void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener;
    }

    /**
     * 设置底部视图变化监听
     *
     * @param onFooterViewChanged
     */
    public final void setOnFooterViewChanged(OnFooterViewChangedListener onFooterViewChanged) {
        this.onFooterViewChanged = onFooterViewChanged;
    }

    /**
     * 设置检测时间
     *
     * @param detectTime
     */
    public final void setDetectTime(long detectTime) {
        this.detectTime = detectTime;
    }

    /**
     * 设置刷新时间
     *
     * @param refreshTime
     */
    public final void setRefreshTime(long refreshTime) {
        this.refreshTime = refreshTime;
    }

    /**
     * 设置加载时间
     *
     * @param loadMoreTime
     */
    public final void setLoadMoreTime(long loadMoreTime) {
        this.loadMoreTime = loadMoreTime;
    }

    /**
     * 内部实现的底部视图
     */
    private class FooterViewInner extends LinearLayout implements OnFooterViewChangedListener {
        private Context context;
        private ProgressBar loadProgressBar;
        private TextView loadTextView;

        public FooterViewInner(Context context) {
            super(context);
            this.context = context;
            init();
        }

        private void init() {
            int wc = ViewGroup.LayoutParams.WRAP_CONTENT;
            setOrientation(HORIZONTAL);

            // ProgressBar
            loadProgressBar = new ProgressBar(context);
            LayoutParams progressBarParams = new LayoutParams(
                    DensityUtils.dpToPx(context, 20),
                    DensityUtils.dpToPx(context, 20));
            loadProgressBar.setLayoutParams(progressBarParams);
            addView(loadProgressBar);

            // TextView
            loadTextView = new TextView(context);
            LayoutParams loadParams = new LayoutParams(wc, wc);
            loadParams.gravity = Gravity.CENTER_VERTICAL;
            loadParams.leftMargin = DensityUtils.dpToPx(context, 15);
            loadTextView.setLayoutParams(loadParams);
            loadTextView.setText("正在加载数据...");
            loadTextView.setTextColor(Color.parseColor("#323232"));
            loadTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            addView(loadTextView);

        }

        @Override
        public void showLoadMoreView(View view) {
            loadTextView.setText("正在加载数据...");
            loadProgressBar.setVisibility(View.VISIBLE);

        }

        @Override
        public void showLoadEndView(View view) {
            loadTextView.setText("");
            loadProgressBar.setVisibility(View.GONE);
        }
    }

    /**
     * 监听底部视图变化
     */
    public interface OnFooterViewChangedListener {

        /**
         * 显示还有数据可以加载的视图
         *
         * @param view
         */
        void showLoadMoreView(View view);

        /**
         * 显示没有数据可以加载的视图
         *
         * @param view
         */
        void showLoadEndView(View view);
    }


    /**
     * 监听数据变化
     */
    public interface OnDataChangeListener {

        /**
         * 刷新数据
         */
        void onRefreshData();

        /**
         * 加载数据
         */
        void onLoadMoreData();

        /**
         * @return true 还有数据可以加载 false 没有数据可以加载
         */
        boolean onLoadStateSet();
    }
}
