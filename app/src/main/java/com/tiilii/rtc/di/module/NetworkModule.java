package com.tiilii.rtc.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tiilii.rtc.network.HttpUtils;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求模组
 *
 * @author fox
 * @since 2017/09/20
 */

@Module
public class NetworkModule {

    @Provides
    OkHttpClient providesOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    GsonConverterFactory providesGsonConverterFactory() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return GsonConverterFactory.create(gson);
    }

    @Provides
    Retrofit providesCloudRetrofit(OkHttpClient client, GsonConverterFactory factory) {
        return new Retrofit.Builder()
                .baseUrl(HttpUtils.HOST)
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

}
