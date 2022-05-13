package com.batch12pm.practiceoflibrary;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import android.widget.EditText;
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
    ImageView imageView;
    Button chooseFromgallary,choosefromCamera,uploadimagebtn;
    ProgressBar progressBar;

    String[] permiissons = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.INTERNET};
    int permisssioncode = 123;
    int gallaryrequest = 23;
    int camerarequest = 45;
    Uri imageuri;
    FirebaseAuth firebaseAuth;
    StorageReference storageReference;
    EditText keyedit,valueedit;
    Button submitvalue,deleteuserbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        combine();
        firebaseAuth = FirebaseAuth.getInstance();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(permiissons,permisssioncode);
            }

        }

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
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
              //  startActivityForResult(intent,gallaryrequest);
                GalleryResultLauncher.launch(intent);
            }
        });

        choosefromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFromgallary.setVisibility(View.GONE);
                Intent intent = new Intent();
                intent.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

//                startActivityForResult(intent,camerarequest);
                    CamereResultLauncher.launch(intent);
        }
        });


        uploadimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosefromCamera.setVisibility(View.GONE);
                chooseFromgallary.setVisibility(View.GONE);

                if(imageuri != null)
                {
                    progressBar.setVisibility(View.VISIBLE);
                    uploadimage();
                }
                uploadimagebtn.setVisibility(View.GONE);
            }
        });
        submitvalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid = firebaseAuth.getCurrentUser().getUid();
                String keytext  = keyedit.getText().toString().trim();
                String valutetext = valueedit.getText().toString().trim();

                Map<String,Object> map = new HashMap<>();
                map.put(keytext,valutetext);
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection("TenamBatchData").document(userid).update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "updated "+keytext, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "error "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        deleteuserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid = firebaseAuth.getCurrentUser().getUid();
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection("TenamBatchData").document(userid).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "date deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void uploadimage() {
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference("UserImage").child(userid);
        storageReference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
           storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
               @Override
               public void onSuccess(Uri uri) {
                   String url = uri.toString();
                   FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

                   HashMap<String,String> hashMap = new HashMap<>();
                   hashMap.put("userid",userid);
                   hashMap.put("imagurl",url);
                   firebaseFirestore.collection("userimages").document(userid).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                               progressBar.setVisibility(View.GONE);
                               Toast.makeText(getApplicationContext(), "image upload success fully", Toast.LENGTH_SHORT).show();
                           }else
                           {
                               progressBar.setVisibility(View.GONE);
                               Toast.makeText(getApplicationContext(), "error "+task.getException(), Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
               }
           }) ;
            }
        });
    }

    ActivityResultLauncher<Intent> GalleryResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        imageuri = result.getData().getData();
                        imageView.setImageURI(imageuri);
                    }
                }
            });

    ActivityResultLauncher<Intent> CamereResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Bitmap bitmap =(Bitmap) result.getData().getExtras().get("data");



                        imageView.setImageBitmap(bitmap);
                        imageuri = getUriFromBitmap(bitmap,getApplicationContext());



                    }
                }
            });
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == camerarequest && resultCode == Activity.RESULT_OK && data != null)
        {
            Bitmap bitmap =(Bitmap) data.getExtras().get("data");



            imageView.setImageBitmap(bitmap);
            imageuri = getUriFromBitmap(bitmap,getApplicationContext());

        }else if(requestCode == gallaryrequest && resultCode == Activity.RESULT_OK && data != null);
        {
            imageuri = data.getData();
            imageView.setImageURI(imageuri);
        }
    }

    private Uri getUriFromBitmap(Bitmap bitmap, Context applicationContext) {
        ByteArrayOutputStream bytobj = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bytobj);

        String path = MediaStore.Images.Media.insertImage(applicationContext.getContentResolver(),bitmap,"Title",null);
        return Uri.parse(path);
    }
    private void combine() {
        imageView = findViewById(R.id.selectedimage);
        choosefromCamera = findViewById(R.id.choosefromcamera);
        chooseFromgallary = findViewById(R.id.choosefromgallary);
        uploadimagebtn = findViewById(R.id.upload_imagebtn);
        progressBar = findViewById(R.id.imageprogressbar);
        keyedit = findViewById(R.id.keyeidt);
        submitvalue = findViewById(R.id.updatebtn);
        valueedit = findViewById(R.id.valueeidt);
        deleteuserbtn = findViewById(R.id.deletebtn);

    }
}