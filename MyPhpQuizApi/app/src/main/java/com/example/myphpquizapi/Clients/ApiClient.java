package com.example.myphpquizapi.Clients;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL ="https://quizegame537.000webhostapp.com/";

  public  static Retrofit getretrofite()
    {
retrofit2.Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
return retrofit;
    }
}
