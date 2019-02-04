package com.themaker.fshmo.klassikaplus.data.web.catalog;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class CatalogApi { // TODO: 1/16/2019 refactor to bean

    private static final String url = "http://api.nytimes.com/svc/topstories/v2/"; // FIXME: 1/16/2019
    private static final String API_KEY = "258d5b758edc4dce91a904e111f29f41";

    private static final int TIMEOUT_SECONDS = 2;

    private final CatalogEndpoint endpoint;
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

        endpoint = retrofit.create(CatalogEndpoint.class);
    }

    @NonNull
    private Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @NonNull
    private OkHttpClient buildOkHttpClient() {
        final HttpLoggingInterceptor networkLoggingInterceptor = new HttpLoggingInterceptor();
        networkLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder()
                .addInterceptor(networkLoggingInterceptor)
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    public CatalogEndpoint catalog() {
        return endpoint;
    }

}