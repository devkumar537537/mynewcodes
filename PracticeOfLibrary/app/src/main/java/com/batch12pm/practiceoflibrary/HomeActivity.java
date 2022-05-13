package com.batch12pm.practiceoflibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    TextView emailtext,numbertextview,ttitleview;
    ImageView profilepick;
  //  RecyclerView recyclerView;
    String value;
    private static final String TAG = "HomeActivity";
    ArrayList<TopicListModel> topiclist;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        emailtext = findViewById(R.id.textView);
        numbertextview = findViewById(R.id.textView2);
        profilepick = findViewById(R.id.profilepick);
     //   recyclerView = findViewById(R.id.recyclerview);
        ttitleview = findViewById(R.id.textViewtitle);
        firebaseAuth = FirebaseAuth.getInstance();
        topiclist = new ArrayList<>();

        db = FirebaseFirestore.getInstance();
      //  fetchsgledata();
      //  fetchinsquence();
        fetchdocumentonly();
    }

    private void fetchdocumentonly() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


        firebaseFirestore.collection("Quiz").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                       TopicListModel topicListModel = document.toObject(TopicListModel.class);

                       topiclist.add(topicListModel);
                    }

                    for(TopicListModel topicListModel: topiclist)
                    {
                        Log.e(TAG, "topic => "+topicListModel.getTopic());
                    }


            }
        });
    }

    private void fetchinsquence() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("TenamBatchData").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                      ModelClass modelClass = document.toObject(ModelClass.class);

                        Log.e(TAG, "nmuber is "+modelClass.getNumber()+"\n and email is "+modelClass.getEmailText());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void fetchsgledata() {
        String userid = firebaseAuth.getCurrentUser().getUid();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("TenamBatchData").document(userid) .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                       String nametext =document.get("nameText").toString();
                       ttitleview.setText(nametext);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id  = item.getItemId();

        switch (id)
        {
            case R.id.logoutbtn:
                firebaseAuth.signOut();
                startActivity(new Intent(HomeActivity.this, LogInActivity.class));
                finish();
                break;
            case R.id.updatebtn:
                startActivity(new Intent(HomeActivity.this,UpdateActivity.class));
                break;
        }
        return true;
    }
}