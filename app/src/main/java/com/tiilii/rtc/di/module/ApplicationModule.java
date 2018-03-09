package com.tiilii.rtc.di.module;

import android.app.Application;
import android.content.Context;

import com.tiilii.rtc.network.CloudApi;
import com.tiilii.rtc.utils.SharedPreferencesUtil;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * app生命周期变量
 *
 * @author fox
 * @since 2017/09/21
 */

@Module
public class ApplicationModule {

    @Provides
    CloudApi providesCloudApi(Retrofit retrofit) {
        return retrofit.create(CloudApi.class);
    }


    @Provides
    Context providesContext(Application application) {
        return application.getApplicationContext();
    }

    /**
     * 用户信息
     */
    @Named("user_info")
    @Provides
    SharedPreferencesUtil providesUserInfoSp(Context context) {

        return SharedPreferencesUtil.getInstance(context, "user_info");
    }

    /**
     * 历史用户
     */
    @Named("user_history")
    @Provides
    SharedPreferencesUtil providesUserHistorySp(Context context) {

        return SharedPreferencesUtil.getInstance(context, "user_history");
    }

    /**
     * 做题时间
     */
    @Named("homework_time")
    @Provides
    SharedPreferencesUtil providesHomeworkTimeSp(Context context) {

        return SharedPreferencesUtil.getInstance(context, "homework_time");
    }
}
