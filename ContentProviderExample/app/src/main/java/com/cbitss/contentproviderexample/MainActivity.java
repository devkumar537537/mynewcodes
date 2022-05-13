package com.cbitss.contentproviderexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
@SuppressLint("Range")
public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_READ_CONTACTS = 79;
    ProgressBar progressBar;
    HashMap<Integer,String> nameMap;
    HashMap<Integer,String> numberMap;
    TextView textView ;

    private String[] mColoumnProjection= new String[]{
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.HAS_PHONE_NUMBER
    };

    String[] permissions = {Manifest.permission.READ_CONTACTS,Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS,Manifest.permission.CALL_PHONE,Manifest.permission.SEND_SMS,
            Manifest.permission.READ_PHONE_STATE};
    int requestcodes = 123;
    boolean res = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connextxml();
        nameMap = new HashMap<>();
        numberMap = new HashMap<>();
        if(res)
        {
            getPermissons();
        }else
        {
            getname();
        }
    }

    private void getname() {
        nameMap = getAllContacts();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=1;i<=nameMap.size();i++)
        {


            stringBuilder.append("Name => ").append(nameMap.get(i)).append(" \n");
            stringBuilder.append("Number => ").append(numberMap.get(i)).append("\n\n");


        }
        progressBar.setVisibility(View.GONE);
        textView.setText(stringBuilder.toString());
    }

    private HashMap<Integer, String> getAllContacts() {

        HashMap<Integer, String> nameList = new HashMap<>();

        int count = 0;

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, mColoumnProjection, null, null, null);
        if (((cur != null) ? cur.getCount() : 0) > 0) {
            while (cur.moveToNext()) {
                count++;//1
                String name = cur.getString(0);
                String id = cur.getString(1);
                int status = Integer.parseInt(cur.getString(2));
                nameList.put(count, name);

                if (status > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);

                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        numberMap.put(count, phoneNo);
                    }
                    pCur.close();
                }
            }
        }
        cur.close();
        return nameList;
    }



    private void getPermissons() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                    == PackageManager.PERMISSION_GRANTED) {
                getname();
            } else {
                requestPermissions(permissions,requestcodes);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == requestcodes && grantResults.length > 0  &&  grantResults[0] == PackageManager.PERMISSION_GRANTED){
            res =false;
            getname();
        }else{
            res= true;
            getPermissons();
        }
    }

    private void connextxml() {
        progressBar = findViewById(R.id.progressbar);
        textView = findViewById(R.id.textcontact);
    }
}