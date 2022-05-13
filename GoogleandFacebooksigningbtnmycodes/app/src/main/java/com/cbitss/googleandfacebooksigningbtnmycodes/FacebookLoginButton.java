package com.cbitss.googleandfacebooksigningbtnmycodes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;


@SuppressWarnings("deprecation")
public class FacebookLoginButton extends AppCompatActivity {
    ImageView profileimage;
    TextView email,name,id;
    Button signoutbtn;
    LoginButton loginButton;
    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private static final String TAG = "ProfileActivity";
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login_button);
        connectxml();




        firebaseAuth = FirebaseAuth.getInstance();
        loginButton = findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile", "user_friends");

////        LoginManager.getInstance().registerCallback(callbackManager,
////                new FacebookCallback<LoginResult>() {
////                    @Override
////                    public void onSuccess(LoginResult loginResult) {
////                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
////                        handfacboologinwithfirebase(loginResult.getAccessToken());
////                    }
////
////                    @Override
////                    public void onCancel() {
////                        Toast.makeText(getApplicationContext(), "error ", Toast.LENGTH_SHORT).show();
////
////                        updateUI(null);
////                    }
////
////                    @Override
////                    public void onError(FacebookException exception) {
////                        Log.d(TAG, "facebook:onError"+ exception);
////                        Toast.makeText(getApplicationContext(), "error "+exception, Toast.LENGTH_SHORT).show();
////                        updateUI(null);
////                    }
////                });
//      //  loginButton.setReadPermissions(Arrays.asList(EMAIL));
//        // If you are using in a fragment, call loginButton.setFragment(this);
//
//        // Callback registration
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//                LoginManager.getInstance().registerCallback(callbackManager,
//                        new FacebookCallback<LoginResult>() {
//                            @Override
//                            public void onSuccess(LoginResult loginResult) {
//                                Log.d(TAG, "facebook:onSuccess:" + loginResult);
//                                handfacboologinwithfirebase(loginResult.getAccessToken());
//                            }
//
//                            @Override
//                            public void onCancel() {
//                                Toast.makeText(getApplicationContext(), "error ", Toast.LENGTH_SHORT).show();
//
//                                updateUI(null);
//                            }
//
//                            @Override
//                            public void onError(FacebookException exception) {
//                                Log.d(TAG, "facebook:onError"+ exception);
//                                Toast.makeText(getApplicationContext(), "error "+exception, Toast.LENGTH_SHORT).show();
//                                updateUI(null);
//                            }
//                        });
//            }
//        });
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(FacebookLoginButton.this, "success login", Toast.LENGTH_SHORT).show();
           handfacboologinwithfirebase(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "error ", Toast.LENGTH_SHORT).show();

                updateUI(null);
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(), "error "+exception.toString(), Toast.LENGTH_SHORT).show();

                updateUI(null);
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null)
        {
            name.setText(user.getDisplayName());
            email.setText(user.getEmail());
            id.setText(user.getUid());

            String imageure = user.getPhotoUrl().toString();
            if(user.getPhotoUrl().toString() == null)
            {
                Toast.makeText(this, "There is no url in image", Toast.LENGTH_SHORT).show();
            }else
            {
                Glide.with(getApplicationContext()).load(imageure).into(profileimage);
            }



            loginButton.setVisibility(View.GONE);
            signoutbtn.setVisibility(View.VISIBLE);
        }

    }

    private void handfacboologinwithfirebase(AccessToken accessToken) {
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());

        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(FacebookLoginButton.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "login successfull", Toast.LENGTH_SHORT).show();
                    FirebaseUser user =  firebaseAuth.getCurrentUser();
                    updateUI(user);
                }else
                {
                    Toast.makeText(FacebookLoginButton.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    private void connectxml() {

        loginButton = findViewById(R.id.login_button);
        profileimage = findViewById(R.id.profileImage);
        email =findViewById(R.id.email);
        name = findViewById(R.id.name);
        id = findViewById(R.id.userId);
        signoutbtn = findViewById(R.id.facesignout);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}