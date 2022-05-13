package com.example.firebaseconcepts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText numberedit,emailedit,passwordedit,namedit;
    Button signupbtn;
    ProgressBar registerbar;
    FirebaseAuth firebaseAuth;
FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        connectxml();
      firebaseAuth = FirebaseAuth.getInstance();
      firebaseFirestore = FirebaseFirestore.getInstance();
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailtext = emailedit.getText().toString().trim();
                String passorttext = passwordedit.getText().toString().trim();
                String name_text = namedit.getText().toString().trim();
                String numbert_text = numberedit.getText().toString().trim();
                registerbar.setVisibility(View.VISIBLE);
                createuser(emailtext,passorttext,name_text,numbert_text);
            }
        });
    }

    private void createuser(String emailtext, String passorttext, String name_text, String numbert_text) {
        firebaseAuth.createUserWithEmailAndPassword(emailtext,passorttext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    String userid = firebaseAuth.getCurrentUser().getUid();



                    HashMap<String,String> usermap = new HashMap<>();
                    usermap.put("userEmail",emailtext);
                    usermap.put("userName",name_text);
                    usermap.put("userPassword",passorttext);
                    usermap.put("userNumber",numbert_text);
                    usermap.put("userId",userid);
                    usermap.put("userImage","default");

                    firebaseFirestore.collection("RegisteredUser").document(userid).set(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                registerbar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "user Registered Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                finish();
                            }else
                            {
                                registerbar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "error "+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else
                {
                    registerbar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "error "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public void connectxml()
    {
        numberedit = findViewById(R.id.numberlayoutregister);
        emailedit = findViewById(R.id.emailayoutregister);
        passwordedit = findViewById(R.id.passwordlayotlayoutregister);
        namedit = findViewById(R.id.namelayoutregister);
        signupbtn = findViewById(R.id.signuptbtn);
        registerbar = findViewById(R.id.registerprogress);
    }
}