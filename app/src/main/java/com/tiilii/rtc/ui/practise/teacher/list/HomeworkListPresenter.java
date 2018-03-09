package com.tiilii.rtc.ui.practise.teacher.list;

import android.content.Intent;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tiilii.rtc.base.RtcApplication;
import com.tiilii.rtc.data.CloudDataSource;
import com.tiilii.rtc.data.CloudRemoteDataSource;
import com.tiilii.rtc.di.ActivityScoped;
import com.tiilii.rtc.model.HomeworkList;
import com.tiilii.rtc.model.HomeworkModel;
import com.tiilii.rtc.network.CloudApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * 作业列表
 *
 * @author fox
 * @since 2018/01/12
 */

@ActivityScoped
public class HomeworkListPresenter implements HomeworkListContract.Presenter {

    private HomeworkListContract.View mView;

    private CompositeDisposable disposable;

    private Intent mIntent;

    private List<HomeworkModel> mHomeworkModelList = new ArrayList<>();

    /**
     * 需要加载的页面
     */
    private int mPageNum = 1;
    /**
     * 每页的容量
     */
    private int mPageSize = 5;
    /**
     * 数据总数
     */
    private int mTotalPageNum = 0;
    /**
     * 已加载数据总数
     */
    private int mLoadedPageNum = 0;
    private Map<Integer, Long> mListTime = new HashMap<>();

    @Inject
    CloudApi mCloudApi;
    @Inject
    CloudRemoteDataSource mCloudRemoteDataSource;


    @Inject
    public HomeworkListPresenter() {

        disposable = new CompositeDisposable();
    }

    @Override
    public void bindView(HomeworkListContract.View view) {

        mView = view;
    }

    @Override
    public void getHomeworkList() {

        Map<String, String> params = new HashMap<>();
        params.put("userUid", String.valueOf(RtcApplication.userUid));
        params.put("pageNum", String.valueOf(mPageNum));
        params.put("pageSize", String.valueOf(mPageSize));

        String homeworkDay = mIntent.getStringExtra("date");
        if (homeworkDay != null) {
            params.put("homeworkDay", homeworkDay);
        }

        mCloudRemoteDataSource.getHomeworkList(params,
                new CloudDataSource.GetDataCallback<HomeworkList>() {
                    @Override
                    public void onLoadSuccess(HomeworkList homeworkList) {

                        if (homeworkList.getFlag().equals("1")) {

                            if (mPageNum == 1) {

                                mView.clearHomeworkList();
                                mHomeworkModelList.clear();
                                if (homeworkList.getHomeworkList() == null || homeworkList.getHomeworkList().size() == 0) {

                                    mView.showEmptyView();
                                }
                            }

                            mTotalPageNum = homeworkList.getPageTotalCount();
                            mLoadedPageNum += homeworkList.getHomeworkList().size();
                            mView.updateHomeworkList(homeworkList.getHomeworkList());
                            mHomeworkModelList.addAll(homeworkList.getHomeworkList());

                            for (int i = 0; i < homeworkList.getHomeworkList().size(); i++) {

                                HomeworkModel model = homeworkList.getHomeworkList().get(i);
                                if (model.getHomeworkStatus() == 0) {// 计算开始时间

                                    long time = TimeUtils.string2Millis(model.getHomeworkBeginDatetime());
                                    int index = i + mPageSize * (mPageNum - 1);
                                    mListTime.put(index, time);
                                } else if (model.getHomeworkStatus() == 1) {// 计算结束时间

                                    long time = TimeUtils.string2Millis(model.getHomeworkEndDatetime());
                                    int index = i + mPageSize * (mPageNum - 1);
                                    mListTime.put(index, time);
                                }
                            }
                            getSystemTime();

                        } else {

                            if (mPageNum == 1) {

                                ToastUtils.showShort(homeworkList.getMsg());
                                mView.showEmptyView();
                            }
                        }
                    }

                    @Override
                    public void onLoadError() {

                    }

                    @Override
                    public void onLoadComplete() {

                    }
                });

    }

    @Override
    public void getSystemTime() {

        mCloudRemoteDataSource.getSystemTime(new CloudDataSource.GetSystemTimeCallback() {
            @Override
            public void onSystemTimeLoaded(long time) {

                Set<Integer> integerSet = mListTime.keySet();
                for (int i : integerSet) {

                    if (i >= (mPageNum - 1) * mPageSize && i < mPageNum * mPageSize) {

                        mListTime.put(i, mListTime.get(i) - time);
                    }

                }

                Completable.complete()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action() {
                            @Override
                            public void run() throws Exception {

                                update();
                            }
                        });
            }
        });
    }

    @Override
    public void startCountdown() {

        disposable.add(Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {

                        update();
                    }

                    @Override
                    public void onError(Throwable e) {

                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void unSubscribe() {
        disposable.dispose();
    }

    @Override
    public void clearSubscribe() {

        mListTime.clear();
        disposable.clear();
    }

    @Override
    public void refreshHomeworkList() {
        mPageNum = 1;
        mLoadedPageNum = 0;

        getHomeworkList();
    }

    @Override
    public void loadMoreHomeworkList() {
        mPageNum++;

        getHomeworkList();
    }

    @Override
    public boolean hasMoreHomeworkList() {

        return mLoadedPageNum < mTotalPageNum;
    }

    @Override
    public void setIntent(Intent intent) {

        mIntent = intent;
    }

    private void update() {

        Map<Integer, String> times = new HashMap<>();

        Set<Integer> integerSet = mListTime.keySet();
        for (int i : integerSet) {

            // 时间减去一秒
            long time = mListTime.get(i);
            mListTime.put(i, time);

            // 转换成具体倒计时
//            int day = (int) (time / (1000 * 60 * 60 * 24));
            int day = Integer.valueOf(mHomeworkModelList.get(i).getCountdown());
            int hour = (int) ((time / (1000 * 60 * 60)) % 24);
            int minute = (int) ((time / (1000 * 60)) % 60);
            int second = (int) ((time / 1000) % 60);

            // 倒计时已过
            if (second < 0) {

                mListTime.clear();
                refreshHomeworkList();
                return;
            }
            // 倒计时没过
            if (day > 1) {// 多少天

                String countdown = String.valueOf(day) + "@天后";
                times.put(i, countdown);
            } else if (hour > 0) {// 24小时内

                String countdown = String.valueOf(hour) + "@小时后";
                times.put(i, countdown);
//                String format = "%02d";
//                String countdown = String.format(Locale.getDefault(), format, hour) + ":" +
//                        String.format(Locale.getDefault(), format, minute) + ":" +
//                        String.format(Locale.getDefault(), format, second);
//                times.put(i, countdown);
            } else if (minute > 0) {// 60分钟内

                String countdown = String.valueOf(minute) + "@分钟后";
                times.put(i, countdown);
            }
        }
        mView.updateAdapter(times);
    }
}
