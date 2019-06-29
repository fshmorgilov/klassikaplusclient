package com.themaker.fshmo.klassikaplus.data.web.catalog;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class CatalogApi {

    private static final String url_dev = "http://192.168.0.4:63000/";
    private static final String url_test = "https://klassikaplus-test.herokuapp.com/";

    private static final int TIMEOUT_SECONDS = 2;

    private final CatalogEndpoint catalogEndpoint;
    private final RevisionEndpoint revisionEndpoint;
    private final OkHttpClient client;

    private static CatalogApi api;

    public static CatalogApi getInstance() {
        if (api == null) {
            api = new CatalogApi();
        }
        return api;
    }

    private CatalogApi() {
        final Retrofit retrofit;
        client = buildOkHttpClient();
        retrofit = buildRetrofit();
        catalogEndpoint = retrofit.create(CatalogEndpoint.class);
        revisionEndpoint = retrofit.create(RevisionEndpoint.class);
    }

    @NonNull
    private Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(url_test)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @NonNull
    private OkHttpClient buildOkHttpClient() {
        final HttpLoggingInterceptor networkLoggingInterceptor = new HttpLoggingInterceptor();
        networkLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(networkLoggingInterceptor)
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    public CatalogEndpoint catalog() {
        return catalogEndpoint;
    }

    public RevisionEndpoint revision(){
        return revisionEndpoint;
    }

}