package com.example.pelemele;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Voir extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir);

        ImageView img = (ImageView) findViewById(R.id.img_voir);
        try {
            FileInputStream fis = openFileInput("image.data");
            Bitmap bm = BitmapFactory.decodeStream(fis);
            img.setImageBitmap(bm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}