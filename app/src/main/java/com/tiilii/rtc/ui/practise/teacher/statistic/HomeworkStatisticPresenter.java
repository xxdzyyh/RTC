package com.tiilii.rtc.ui.practise.teacher.statistic;

import android.content.Intent;

import com.blankj.utilcode.util.ToastUtils;
import com.tiilii.rtc.base.RtcApplication;
import com.tiilii.rtc.data.CloudDataSource;
import com.tiilii.rtc.data.CloudRemoteDataSource;
import com.tiilii.rtc.di.ActivityScoped;
import com.tiilii.rtc.model.HomeworkSchedule;
import com.tiilii.rtc.model.HomeworkScheduleModel;
import com.tiilii.rtc.network.CloudApi;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 作业统计
 *
 * @author fox
 * @since 2018/01/12
 */

@ActivityScoped
public class HomeworkStatisticPresenter implements HomeworkStatisticContract.Presenter {

    private HomeworkScheduleModel mHomeworkScheduleModel = null;

    private HomeworkStatisticContract.View mView;

    private CompositeDisposable mDisposable;

    private Intent mIntent;

    @Inject
    CloudApi mCloudApi;
    @Inject
    CloudRemoteDataSource mCloudRemoteDataSource;

    @Inject
    public HomeworkStatisticPresenter() {

        mDisposable = new CompositeDisposable();
    }

    @Override
    public void bindView(HomeworkStatisticContract.View view) {

        mView = view;
    }

    @Override
    public void getHomeworkSchedule() {

        Map<String, String> params = new HashMap<>();
        params.put("userUid", String.valueOf(RtcApplication.userUid));
        params.put("myHomeworkUid", mIntent.getStringExtra(HomeworkStatisticActivity.MY_HOMEWORK_UID));


        mCloudRemoteDataSource.getHomeworkSchedule(params,
                new CloudDataSource.GetDataCallback<HomeworkSchedule>() {
                    @Override
                    public void onLoadSuccess(HomeworkSchedule homeworkSchedule) {

                        if (homeworkSchedule.getFlag().equals("1")) {

                            mView.updateViews(homeworkSchedule.getHomeworkSchedule());
                            mHomeworkScheduleModel = homeworkSchedule.getHomeworkSchedule();
                        } else {

                            ToastUtils.showShort(homeworkSchedule.getMsg());
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
    public void setIntent(Intent intent) {

        mIntent = intent;
    }

    @Override
    public boolean isHomeworkFinished() {
        if (mHomeworkScheduleModel != null && mHomeworkScheduleModel.getHomeworkStatus() == 3) {
            // 作业时间已过，学生作业未完成

            return true;
        }
        return false;
    }


}
