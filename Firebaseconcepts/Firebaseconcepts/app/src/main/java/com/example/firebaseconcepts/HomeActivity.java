package com.example.firebaseconcepts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
FirebaseAuth firebaseAuth;
TextView emailview,nameview,addllldata;
ImageView imageView;
FirebaseFirestore firebaseFirestore ;
//ArrayList<UserModel> userlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        emailview = findViewById(R.id.emailview);
        nameview =findViewById(R.id.nameview);
        addllldata =findViewById(R.id.alldata);
        imageView = findViewById(R.id.imageview);
      //  userlist =new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        fetchdatainsingle();
       fetchdatainlist();
    }

    private void fetchdatainsingle() {
String userid = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("RegisteredUser").document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
             DocumentSnapshot documentSnapshot = task.getResult();
             if(documentSnapshot.exists())
             {
String emailtext = documentSnapshot.get("userEmail").toString();
String nametext = documentSnapshot.get("userName").toString();
String urltext = documentSnapshot.get("userImage").toString();

if(urltext.equals("default"))
{
    Toast.makeText(getApplicationContext(), "Image is not available here", Toast.LENGTH_SHORT).show();
}else
{
    Glide.with(getApplicationContext()).load(urltext).into(imageView);
}
                 nameview.setText(nametext);
                 emailview.setText(emailtext);
             }else{
                 Toast.makeText(getApplicationContext(),"NO data exist",Toast.LENGTH_SHORT).show();
             }

            }
        });
    }

    private void fetchdatainlist() {
        firebaseFirestore.collection("RegisteredUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    StringBuilder stringBuilder = new StringBuilder();
                    for(QueryDocumentSnapshot documentSnapshot:task.getResult())
                    {
                        UserModel model = documentSnapshot.toObject(UserModel.class);
                        stringBuilder.append("name => "+model.getUserName()+"\n");
                        stringBuilder.append("email => "+model.getUserEmail()+"\n\n");
                    }
                    addllldata.setText(stringBuilder);
                }
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logoutoption)
        {
            firebaseAuth.signOut();
            startActivity(new Intent(HomeActivity.this,MainActivity.class));
            finish();
        }else if(item.getItemId() == R.id.movetoupdate)
        {
            startActivity(new Intent(HomeActivity.this,UpdateActivity.class));
        }
        return true;
    }
}