package com.example.firebaseconcepts;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {
String[] permissions = {Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,
Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET};
int permissioncode = 123;
    ImageView imageView;
    Button chooseFromgallary,choosefromCamera,uploadimagebtn;
    private Uri imageuri;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(permissions,permissioncode);
            }
        }
        combine();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosefromCamera.setVisibility(View.VISIBLE);
                chooseFromgallary.setVisibility(View.VISIBLE);
                uploadimagebtn.setVisibility(View.VISIBLE);
            }
        });

        chooseFromgallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosefromCamera.setVisibility(View.GONE);
                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                galleryResultlancher.launch(intent);
            }
        });

        choosefromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFromgallary.setVisibility(View.GONE);
                Intent intent = new Intent();


                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
cameraResultlancher.launch(intent);
            }
        });
        uploadimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageuri != null)
                {
                    progressBar.setVisibility(View.VISIBLE);
                    uploaimage(imageuri);
                }else
                {
                    Toast.makeText(getApplicationContext(), "pls select image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploaimage(Uri imageuri) {
String userid = firebaseAuth.getCurrentUser().getUid();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("UserImage").child(userid);
        storageReference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageurl = uri.toString();
                        Map<String,Object> updatemapt = new HashMap<>();
                        updatemapt.put("userImage",imageurl);
                        firebaseFirestore.collection("RegisteredUser").document(userid).update(updatemapt).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                   progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "image uploaded Successfully", Toast.LENGTH_SHORT).show();
                                }else
                                {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "image updation failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });
            }
        });

    }


    ActivityResultLauncher<Intent> galleryResultlancher= registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
              if(result.getResultCode() == Activity.RESULT_OK)
              {
                  imageuri = result.getData().getData();
                  imageView.setImageURI(imageuri);
              }
            }
        }
);
    ActivityResultLauncher<Intent> cameraResultlancher= registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if(result.getResultCode() == Activity.RESULT_OK)
                    {
                        Bitmap bitmap =(Bitmap) result.getData().getExtras().get("data");
                        imageView.setImageBitmap(bitmap);
                        imageuri = getImageurifrombitampe(getApplicationContext(),bitmap);
                    }

                }
            }
    );

    private Uri getImageurifrombitampe(Context applicationContext, Bitmap bitmap) {

        ByteArrayOutputStream bytobj =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bytobj);
        String  path = MediaStore.Images.Media.insertImage(applicationContext.getContentResolver(),bitmap,"anything","this is descrition");
        return Uri.parse(path);
    }

    private void combine() {
        imageView = findViewById(R.id.selectedimage);
        choosefromCamera = findViewById(R.id.choosefromcamera);
        chooseFromgallary = findViewById(R.id.choosefromgallary);
        uploadimagebtn = findViewById(R.id.upload_imagebtn);
        progressBar = findViewById(R.id.imageprogressbar);


    }
}