package com.example.pelemele;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Photo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ImageView b = (ImageView) findViewById(R.id.img_photo);
        FileInputStream fis = null;
        try {
            fis = openFileInput("image.data");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);
        b.setImageBitmap(bm);
    }

}