package com.tiilii.rtc.di.component;

import android.app.Application;

import com.tiilii.rtc.base.RtcApplication;
import com.tiilii.rtc.di.module.ActivityBuilder;
import com.tiilii.rtc.di.module.ApplicationModule;
import com.tiilii.rtc.di.module.NetworkModule;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * app模组
 *
 * @author fox
 * @since 2017/09/21
 */

@Component(modules = {
        AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        ActivityBuilder.class,
        NetworkModule.class
})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

    void inject(RtcApplication application);
}
