package com.example.myphpquizapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myphpquizapi.Clients.ApiClient;
import com.example.myphpquizapi.models.RegisterModel;
import com.example.myphpquizapi.servicess.ApiServicess;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
EditText editemail,editpassword,editname;
Button registerbtn,movebtn;
ApiServicess apiServicess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectxml();
apiServicess = ApiClient.getretrofite().create(ApiServicess.class);
        registerbtn.setOnClickListener(v -> {
            String emailtext = editemail.getText().toString().trim();
            String passwordtext = editpassword.getText().toString().trim();
            String nametext = editname.getText().toString().trim();

      Call<RegisterModel> call = apiServicess.getmodel(emailtext,passwordtext,nametext);

      call.enqueue(new Callback<RegisterModel>() {
          @Override
          public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
              Toast.makeText(getApplicationContext(), "message "+response.toString(), Toast.LENGTH_SHORT).show();
          }

          @Override
          public void onFailure(Call<RegisterModel> call, Throwable t) {
              Toast.makeText(getApplicationContext(), "error "+t.getMessage(), Toast.LENGTH_SHORT).show();
          }
      });
        });

        movebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AllQuestionActivity.class));
            }
        });
    }

    private void connectxml() {
        editemail = findViewById(R.id.emailedit);
        editpassword = findViewById(R.id.passwordedit);
        editname = findViewById(R.id.namedit);
        registerbtn = findViewById(R.id.registerbtn);
        movebtn = findViewById(R.id.movetbtn);

    }
}