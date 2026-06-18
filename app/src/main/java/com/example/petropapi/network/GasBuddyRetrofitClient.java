package com.example.petropapi.network;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GasBuddyRetrofitClient {
    private static final String BASE_URL = "https://www.gasbuddy.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request original = chain.request();
                        Request newRequest = original.newBuilder()
                                .header("Content-Type", "application/json")
                                .header("accept", "*/*")
                                .header("accept-language", "en-US,en;q=0.9")
                                .header("apollo-require-preflight", "true")
                                .header("gbcsrf", "1.meTUIoHxkwpNhiis")
                                .header("origin", "https://www.gasbuddy.com")
                                .header("referer", "https://www.gasbuddy.com/station/45933")
                                .header("sec-ch-ua", "\"Not A(Brand\";v=\"8\", \"Chromium\";v=\"132\", \"Android WebView\";v=\"132\"")
                                .header("sec-ch-ua-mobile", "?1")
                                .header("sec-ch-ua-platform", "\"Android\"")
                                .header("sec-fetch-dest", "empty")
                                .header("sec-fetch-mode", "cors")
                                .header("sec-fetch-site", "same-origin")
                                .header("user-agent", "Mozilla/5.0 (Linux; Android 11; Pixel 4 XL Build/RQ2A.210305.006) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Mobile Safari/537.36")
                                .build();
                        return chain.proceed(newRequest);
                    })
                    .addInterceptor(loggingInterceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static GasBuddyApiService getService() {
        return getRetrofitInstance().create(GasBuddyApiService.class);
    }
}
