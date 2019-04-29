package com.themaker.fshmo.klassikaplus;

import android.app.Application;
import androidx.work.*;
import com.facebook.stetho.Stetho;
import com.themaker.fshmo.klassikaplus.dagger.AppComponent;
import com.themaker.fshmo.klassikaplus.dagger.DaggerAppComponent;
import com.themaker.fshmo.klassikaplus.dagger.module.ApplicationModule;
import com.themaker.fshmo.klassikaplus.dagger.module.DataModule;
import com.themaker.fshmo.klassikaplus.service.NetworkUtils;
import com.themaker.fshmo.klassikaplus.service.RevisionRequestService;

import java.util.concurrent.TimeUnit;

public class App extends Application {

    private static AppComponent appComponent;
    private static App APP_INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        APP_INSTANCE = this;
        Stetho.initializeWithDefaults(this);
        appComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .dataModule(new DataModule())
                .build();
        performScheduledWork();
    }

    private static void performScheduledWork() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(
                RevisionRequestService.class,
                RevisionRequestService.REQUEST_INTERVAL,
                TimeUnit.HOURS)
                .addTag(RevisionRequestService.WORK_TAG)
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 2, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();
        NetworkUtils.getInstance()
                .getNotificationTapReceiver()
                .setWorkRequestId(workRequest.getId());
        WorkManager.getInstance()
                .enqueueUniquePeriodicWork(RevisionRequestService.WORK_TAG, ExistingPeriodicWorkPolicy.KEEP, workRequest);
    }

    public static App getInstance() {
        return APP_INSTANCE;
    }


    public AppComponent getComponent() {
        return appComponent;
    }
}
