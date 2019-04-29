package com.themaker.fshmo.klassikaplus.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.WorkManager;
import com.themaker.fshmo.klassikaplus.App;
import com.themaker.fshmo.klassikaplus.presentation.root.MainActivity;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

import java.util.UUID;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class NetworkUtils {

    private static NetworkUtils networkUtils;
    private NetworkReceiver networkReceiver = new NetworkReceiver();
    private NotificationTapReceiver notificationTapReceiver = new NotificationTapReceiver();
    private Subject<Boolean> networkState = BehaviorSubject.createDefault(isNetworkAvailable());


    public static NetworkUtils getInstance() {
        synchronized (App.class) {
            if (networkUtils == null)
                synchronized (App.class) {
                    networkUtils = new NetworkUtils();
                }
            return networkUtils;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getInstance()
                .getApplicationContext()
                .getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager == null)
            return false;
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    public NetworkReceiver getNetworkReceiver() {
        return networkReceiver;
    }

    public Single<Boolean> getOnlineNetwork() {
        return networkState.subscribeOn(Schedulers.io())
                .filter(online -> online)
                .firstOrError();
    }

    public NotificationTapReceiver getNotificationTapReceiver() {
        return notificationTapReceiver;
    }

    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            networkState.onNext(isNetworkAvailable());
        }
    }

    public class NotificationTapReceiver extends BroadcastReceiver {

        public static final String ACTION_TAP = "Notification tapped";
        private final String TAG = NotificationTapReceiver.class.getName();
        private UUID workRequestId;

        @Override
        public void onReceive(Context context, Intent intent) {
            MainActivity.start(context);
            Log.i(TAG, "Starting App");
        }

        public void setWorkRequestId(@NonNull UUID workRequestId) {
            this.workRequestId = workRequestId;
        }
    }
}