package com.tiilii.rtc.data;

import com.blankj.utilcode.util.ToastUtils;
import com.tiilii.rtc.model.BaseStatus;
import com.tiilii.rtc.model.HomeworkAnswer;
import com.tiilii.rtc.model.HomeworkList;
import com.tiilii.rtc.model.HomeworkNextQuestion;
import com.tiilii.rtc.model.HomeworkSchedule;
import com.tiilii.rtc.model.SystemTime;
import com.tiilii.rtc.network.CloudApi;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.QueryMap;

/**
 * 网络请求实现类
 *
 * @author fox
 * @since 2018/02/05
 */

public class CloudRemoteDataSource implements CloudDataSource {

    private CompositeDisposable mDisposable;

    /**
     * API
     */
    @Inject
    CloudApi mCloudApi;

    @Inject
    public CloudRemoteDataSource() {

        mDisposable = new CompositeDisposable();
    }

    @Override
    public void getSystemTime(final GetSystemTimeCallback callback) {

        mDisposable.add(mCloudApi.getSystemTime()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<SystemTime>() {
                    @Override
                    public void onSuccess(SystemTime systemTime) {

                        if (systemTime.getFlag().equals("1")) {

                            callback.onSystemTimeLoaded(systemTime.getServerTimeValue());
                        } else if (systemTime.getFlag().equals("0")) {

                            ToastUtils.showShort(systemTime.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        e.printStackTrace();
                    }
                }));
    }

    @Override
    public void complainHomeworkQuestion(@QueryMap Map<String, String> params, final DataLoadFinishCallback callback) {

        mDisposable.add(mCloudApi.complainHomeworkQuestion(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseStatus>() {
                    @Override
                    public void onSuccess(BaseStatus baseStatus) {

                        if (baseStatus.getFlag().equals("1")) {

                            callback.onDataAvailable();
                            ToastUtils.showShort(baseStatus.getMsg());
                        } else if (baseStatus.getFlag().equals("0")) {

                            callback.onDataNotAvailable();
                            ToastUtils.showShort(baseStatus.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        e.printStackTrace();
                    }
                }));
    }

    @Override
    public void getHomeworkList(Map<String, String> params, final GetDataCallback<HomeworkList> callback) {

        mDisposable.add(mCloudApi.getHomeworkList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<HomeworkList>() {
                    @Override
                    public void onNext(HomeworkList homeworkList) {

                        callback.onLoadSuccess(homeworkList);
                    }

                    @Override
                    public void onError(Throwable e) {

                        e.printStackTrace();
                        callback.onLoadError();
                    }

                    @Override
                    public void onComplete() {

                        callback.onLoadComplete();
                    }
                }));
    }

    @Override
    public void getHomeworkSchedule(Map<String, String> params, final GetDataCallback<HomeworkSchedule> callback) {
        mDisposable.add(mCloudApi.getHomeworkSchedule(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<HomeworkSchedule>() {
                    @Override
                    public void onNext(HomeworkSchedule homeworkSchedule) {

                        callback.onLoadSuccess(homeworkSchedule);
                    }

                    @Override
                    public void onError(Throwable e) {

                        e.printStackTrace();
                        callback.onLoadError();
                    }

                    @Override
                    public void onComplete() {

                        callback.onLoadComplete();
                    }
                }));
    }

    @Override
    public void getNextTopicForHomework(Map<String, String> params, final GetDataCallback<HomeworkNextQuestion> callback) {

        mDisposable.add(mCloudApi.getNextTopicForHomework(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<HomeworkNextQuestion>() {
                    @Override
                    public void onNext(HomeworkNextQuestion homeworkNextQuestion) {

                        callback.onLoadSuccess(homeworkNextQuestion);
                    }

                    @Override
                    public void onError(Throwable e) {

                        e.printStackTrace();
                        callback.onLoadError();
                    }

                    @Override
                    public void onComplete() {

                        callback.onLoadComplete();
                    }
                }));
    }

    @Override
    public void saveStuHomeworkAnswer(String url, final GetDataCallback<HomeworkAnswer> callback) {

        mDisposable.add(mCloudApi.saveStuHomeworkAnswer(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<HomeworkAnswer>() {
                    @Override
                    public void onNext(HomeworkAnswer homeworkAnswer) {

                        callback.onLoadSuccess(homeworkAnswer);
                    }

                    @Override
                    public void onError(Throwable e) {

                        e.printStackTrace();
                        callback.onLoadError();
                    }

                    @Override
                    public void onComplete() {

                        callback.onLoadComplete();
                    }
                }));
    }
}
