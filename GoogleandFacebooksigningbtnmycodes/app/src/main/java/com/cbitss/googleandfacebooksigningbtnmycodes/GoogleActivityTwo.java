package com.cbitss.googleandfacebooksigningbtnmycodes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

@SuppressWarnings("deprecation")
public class GoogleActivityTwo extends AppCompatActivity {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;


    SignInButton signInButton;
    TextView statuse,uid;
    ImageView profileimage;
    Button signout,disconnextbtn;
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInAccount account;
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_two);
        connectxml();

firebaseAuth =FirebaseAuth.getInstance();
        GoogleSignInOptions gso =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("921838982915-n0ene31mllgdmjuq2531r4eoh9m3f0dr.apps.googleusercontent.com")
                .requestEmail()
                .build();
       googleSignInClient = GoogleSignIn.getClient(this,gso);

       signInButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent= googleSignInClient.getSignInIntent();
               startActivityForResult(intent,RC_SIGN_IN);
           }
       });
signout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        firebaseAuth.signOut();

        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete( Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(GoogleActivityTwo.this, "you signout", Toast.LENGTH_SHORT).show();
                    updateUI(firebaseAuth.getCurrentUser());
                }
            }
        });
    }
});

disconnextbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        firebaseAuth.signOut();

        // Google revoke access
        googleSignInClient.revokeAccess().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                updateUI(firebaseAuth.getCurrentUser());
            }
        });
    }
});

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN && data != null)
        {

            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                account = task.getResult(ApiException.class);

            } catch (ApiException e) {
                e.printStackTrace();
                Log.w(TAG, "Google sign in failed", e);
              // [START_EXCLUDE]
          updateUI(null);
            }
            Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

            firebaseAuthWithGoogle(account.getIdToken());

        }else{
            Toast.makeText(this, "NO data found", Toast.LENGTH_SHORT).show();
        }


    }

    private void firebaseAuthWithGoogle(String idToken) {

      //  AuthCredential googleauthcrediatl = GoogleAuthProvider.getCredential(idToken,null);
        AuthCredential googlecrediatl = GoogleAuthProvider.getCredential(idToken,null);

firebaseAuth.signInWithCredential(googlecrediatl).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful())
        {
            Toast.makeText(GoogleActivityTwo.this, "LogIn Success full", Toast.LENGTH_SHORT).show();
            FirebaseUser user = firebaseAuth.getCurrentUser();
            updateUI(user);
        }else{
            Toast.makeText(GoogleActivityTwo.this, "error "+task.getException(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(MainActivity.this, "error"+task.getException(), Toast.LENGTH_SHORT).show();
            updateUI(null);
        }
    }
});
    }

    private void updateUI(FirebaseUser user) {
        if(user != null)
        {
            statuse.setText("useremail =>"+user.getEmail());
            uid.setText("username =>"+user.getDisplayName());

            String url = user.getPhotoUrl().toString();
            Glide.with(getApplicationContext()).load(url).into(profileimage);
            signInButton.setVisibility(View.GONE);
            signout.setVisibility(View.VISIBLE);
            disconnextbtn.setVisibility(View.VISIBLE);
        }else{
            statuse.setText("user is not signin");
            uid.setText("null");
            profileimage.setImageResource(R.drawable.ic_launcher_background);
            signInButton.setVisibility(View.VISIBLE);
            signout.setVisibility(View.GONE);
            disconnextbtn.setVisibility(View.GONE);
        }
    }

    private void connectxml() {
        signInButton=findViewById(R.id.googlesinginbutn);
        statuse = findViewById(R.id.userstatus);
        uid = findViewById(R.id.userdetail);
        profileimage = findViewById(R.id.userprofiel);
        signout = findViewById(R.id.signoutbtn);
        disconnextbtn = findViewById(R.id.disconnextbtn);

    }
}