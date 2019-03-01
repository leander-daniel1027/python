package com.solutionavenues.deedee.rest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestService  {

    private static long CONNECTION_TIMEOUT = 300;
   // private static String BASE_URL = "https://dee.hravenues.com/";
    private static String BASE_URL = "https://ddv.hravenues.com/";
    private static String API_BASE_URL = BASE_URL+"webServices/";
    public static String IMAGE_BASE_URL = BASE_URL+"uploads/";



    public static Rest getService() {
       /* if (TextUtils.isEmpty(MyApplication.getInstance().getAppPrefs().getCurrentServer())) {
            API_BASE_URL=  BASE_URL+"webServices/";
        }else{
           API_BASE_URL=MyApplication.getInstance().getAppPrefs().getCurrentServer();
        }*/
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient client = getOkHttpClient();

        Rest rest = new Retrofit.Builder().baseUrl(API_BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create(gson)).build()
                .create(Rest.class);
        return rest;
    }

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("DD", message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okClientBuilder.addInterceptor(httpLoggingInterceptor);
        okClientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        okClientBuilder.readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        okClientBuilder.writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        return okClientBuilder.build();
    }
}
