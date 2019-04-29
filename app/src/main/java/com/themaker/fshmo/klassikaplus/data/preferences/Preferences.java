package com.themaker.fshmo.klassikaplus.data.preferences;

import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import io.reactivex.subjects.PublishSubject;

import javax.inject.Inject;

/**
 * Враппер над SharedPreferences.
 */
public class Preferences {

    private static final String KEY_FIRST_RUN = "FIRST_RUN";
    private static final String KEY_MOCK = "MOCK";
    private static final Gson GSON = new Gson();
    private static final String KEY_USER = "USER";
    private static final String KEY_F_USER_ID = "F_USER_ID";
    private static final String KEY_REVISION = "REVISION";
    private static final int DEFAULT_REVISION = 1;
    private static final String TAG = Preferences.class.getName();


    private final SharedPreferences sharedPreferences;

    @Inject
    public Preferences(SharedPreferences preferences) {
        sharedPreferences = preferences;
    }

    public void updateRevision(@NonNull Integer revision) {
        Log.i(TAG, "updateRevision: updating to: " + revision);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_REVISION, revision);
        editor.apply();
    }

    public Integer getRevision() {
        Integer rev = sharedPreferences.getInt(KEY_REVISION, DEFAULT_REVISION);
        Log.i(TAG, "getRevision: current revision: " + rev);
        return rev;
    }

    public void setFirstTimeAppLaunch() {
        Log.i(TAG, "setFirstTimeAppLaunch: disabling first launch features");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_FIRST_RUN, false);
        editor.apply();
    }

    public boolean isFirstTimeAppLaunch() {
        Log.i(TAG, "isFirstTimeAppLaunch: ");
        return sharedPreferences.getBoolean(KEY_FIRST_RUN, true);
    }
}