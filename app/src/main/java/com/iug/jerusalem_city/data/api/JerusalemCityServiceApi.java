package com.iug.jerusalem_city.data.api;

import com.iug.jerusalem_city.data.models.LastNewsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JerusalemCityServiceApi {

    @GET("everything")
    Call<LastNewsModel> getJerusalemCityLastNewsQuery(@Query("q") String q,
                                                      @Query("from") String from,
                                                      @Query("to") String to,
                                                      @Query("sortBy") String sortBy,
                                                      @Query("Language ") String Language,
                                                      @Query("apiKey") String apiKey);

}
