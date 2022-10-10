package com.example.pelemele;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Contact extends AppCompatActivity {

    private ArrayList Mescontacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        //Thread
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                recupContact();
            }
        });

    }


    //récuperation des contacts
    public void recupContact(){

        ContentResolver contentResolver = this.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE,ContactsContract.CommonDataKinds.Phone.NUMBER},null,null,null);
        //récupère le nom et le numéro de telephone
        if(cursor==null){
                Log.d("erreur","erreur");
            }else{
                EditText txt = (EditText) findViewById(R.id.edit_contact);
                while (cursor.moveToNext()){
                  @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE));
                  @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                  txt.setText(txt.getText().toString() + "\n\r" + name + " : " + phone);
                }
                if(cursor.isClosed()==false){
                    cursor.close();
                }

            }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}