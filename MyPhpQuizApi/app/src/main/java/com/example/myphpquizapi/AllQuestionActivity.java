package com.example.myphpquizapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myphpquizapi.Clients.ApiClient;
import com.example.myphpquizapi.models.AllQuestionModel;
import com.example.myphpquizapi.models.Question;
import com.example.myphpquizapi.servicess.ApiServicess;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllQuestionActivity extends AppCompatActivity {
TextView textView;
private ApiServicess apiServicess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_question);
        textView = findViewById(R.id.questionview);

        apiServicess = ApiClient.getretrofite().create(ApiServicess.class);

      Call<AllQuestionModel> call =  apiServicess.getallquestions("Flutter");
      call.enqueue(new Callback<AllQuestionModel>() {
          @Override
          public void onResponse(Call<AllQuestionModel> call, Response<AllQuestionModel> response) {
              if(!response.isSuccessful())
              {
                  Toast.makeText(AllQuestionActivity.this, "error "+response.code(), Toast.LENGTH_SHORT).show();
                  return;
              }

              StringBuilder stringBuilder = new StringBuilder();
                 AllQuestionModel allQuestionModel = response.body();
              for(Question postclass : allQuestionModel.getQuestion())
              {
                  stringBuilder.append("id => "+postclass.getId()+"\n");
                  stringBuilder.append("question => "+postclass.getQuestion()+"\n");
                  stringBuilder.append("Annswer => "+postclass.getRightoption()+"\n");


              }

              textView.setText(stringBuilder.toString());
          }

          @Override
          public void onFailure(Call<AllQuestionModel> call, Throwable t) {
              Toast.makeText(getApplicationContext(), "error "+t.getMessage(), Toast.LENGTH_SHORT).show();
          }
      });
    }
}