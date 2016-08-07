package com.techtask.android.picscramble.transport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.techtask.android.picscramble.util.AppUrls;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static OkHttpClient httpClient = new OkHttpClient.Builder().build();

    private static Gson gson = new GsonBuilder().setLenient().create();

    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(AppUrls.BASE_URL).
            addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}
