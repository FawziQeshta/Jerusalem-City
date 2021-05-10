package com.iug.jerusalem_city.api;

import com.iug.jerusalem_city.models.LastNewsModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JerusalemCityServiceApi {

    @GET("everything")
    Call<LastNewsModel> getLastNews(@Query("q") String q,
                              @Query("from") String from,
                              @Query("to") String to,
                              @Query("sortBy") String sortBy,
                              @Query("apiKey") String apiKey);

}
