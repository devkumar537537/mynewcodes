package com.example.firebaseconcepts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
EditText emailedit,passwordedt,numberedit,otpedit;
Button loginbtn,movetoresigter,submitnumber,submintotp;
FirebaseAuth firebaseAuth;
String phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connextxlm();
        firebaseAuth = FirebaseAuth.getInstance();
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailtext = emailedit.getText().toString();
                String passwordtext = passwordedt.getText().toString();

                loginuser(emailtext,passwordtext);
            }
        });
        movetoresigter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });
        submitnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = numberedit.getText().toString().trim();
                phonenumber = "+91"+number;
                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(firebaseAuth)
                                .setPhoneNumber(phonenumber)       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(MainActivity.this)                 // Activity (for callback binding)
                                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
            }
        });

        submintotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String opttext = otpedit.getText().toString().trim();

                verfiymobilenumber(opttext);
            }
        });
    }

    private void loginuser(String emailtext, String passwordtext) {
        firebaseAuth.signInWithEmailAndPassword(emailtext,passwordtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                    finish();
                }else
                {
                    Toast.makeText(getApplicationContext(), "error "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

//       if( firebaseAuth.getCurrentUser() != null)
//       {
//           startActivity(new Intent(MainActivity.this,HomeActivity.class));
//           finish();
//       }
    }

    public  void connextxlm()
    {
        emailedit = findViewById(R.id.emailedit);
        passwordedt = findViewById(R.id.passwordedit);
        loginbtn = findViewById(R.id.lopginbtn);
        movetoresigter = findViewById(R.id.registerbtn);
        submitnumber = findViewById(R.id.submitnumber);
        submintotp = findViewById(R.id.submitotp);
        numberedit = findViewById(R.id.numberlayout);
        otpedit = findViewById(R.id.otplayout);
    }

    private String verification_code;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            verfiymobilenumber(code);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(MainActivity.this, "error "+e, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String sendedcode, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(sendedcode, forceResendingToken);
            verification_code = sendedcode;
        }
    };

    private void verfiymobilenumber(String code) {
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verification_code,code);
        verfityoverfirebase(phoneAuthCredential);
    }

    private void verfityoverfirebase(PhoneAuthCredential phoneAuthCredential) {

        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "user login successfullly", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(MainActivity.this,"error "+task.getException(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}