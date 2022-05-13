package com.example.myphpquizapi.servicess;

import com.example.myphpquizapi.models.AllQuestionModel;
import com.example.myphpquizapi.models.RegisterModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiServicess {
    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterModel> getmodel( @Field("email") String email,
                                @Field("password") String password,
                                 @Field("name") String name
                                 );


      @GET("getquestion.php")
    Call<AllQuestionModel> getallquestions(
            @Query("topic") String topic
      );
}
