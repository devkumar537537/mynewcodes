package com.batch12pm.practiceoflibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegsiterActivty extends AppCompatActivity {
    EditText numberedit,emailedit,passwordedit,namedit;
    Button signupbtn;
    ProgressBar registerbar;

    FirebaseFirestore mydb;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsiter_activty);
        connectml();
        mydb = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtext = emailedit.getText().toString().trim();
                String passorttext = passwordedit.getText().toString().trim();
                String name_text = namedit.getText().toString().trim();
                String numbert_text = numberedit.getText().toString().trim();

                if(TextUtils.isEmpty(emailtext))
                {
                    emailedit.setError("Empty Email");
                }else if(TextUtils.isEmpty(passorttext))
                {
                    passwordedit.setError("Empty password");
                }else if(TextUtils.isEmpty(name_text))
                {
                    namedit.setError("Empty name");
                }else if(TextUtils.isEmpty(numbert_text))
                {
                    numberedit.setError("Empty number");
                }else
                {
                    registerbar.setVisibility(View.VISIBLE);
                    createuser(emailtext,passorttext,numbert_text,name_text);
                }
            }
        });
    }

    private void createuser(String emailtext, String passorttext, String numbert_text, String name_text) {
        firebaseAuth.createUserWithEmailAndPassword(emailtext,passorttext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String userid = firebaseAuth.getCurrentUser().getUid();

                if(task.isSuccessful())
                {
                    HashMap<String,String> usermape = new HashMap<>();
                    usermape.put("emailText",emailtext);
                    usermape.put("passwordText",passorttext);
                    usermape.put("number",numbert_text);
                    usermape.put("nameText",name_text);
                    usermape.put("userId",userid);


                    mydb.collection("TenamBatchData").document(userid).set(usermape).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                registerbar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "register successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegsiterActivty.this,HomeActivity.class));
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

    private void connectml() {
        numberedit = findViewById(R.id.numberlayoutregister);
        emailedit = findViewById(R.id.emailayoutregister);
        passwordedit = findViewById(R.id.passwordlayotlayoutregister);
        namedit = findViewById(R.id.namelayoutregister);
        signupbtn = findViewById(R.id.signuptbtn);
        registerbar = findViewById(R.id.registerprogress);
    }
}