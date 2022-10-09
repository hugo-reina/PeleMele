package com.example.pelemele;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Contact extends AppCompatActivity {

    private ArrayList Mescontacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        recupContact();
    }
    public void recupContact(){

            ContentResolver contentResolver = this.getContentResolver();
            Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
            if(cursor==null){
                    Log.d("erreur","erreur");
            }else{
                @SuppressLint("WrongViewCast") EditText txt = (EditText) findViewById(R.id.txtContact);
                while (cursor.moveToNext()){
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE));
                    @SuppressLint("Range") String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    txt.setText(txt.getText().toString() + "\n\r"+name + " " + phoneNumber);

                }
                cursor.close();
            }

    }
}