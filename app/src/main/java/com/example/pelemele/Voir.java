package com.example.pelemele;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Voir extends AppCompatActivity {
    //class voir permet de visualiser la photo prise

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir);

        //je récupère l'image dans activity_voir
        ImageView img = (ImageView) findViewById(R.id.img_voir);
        try {
            FileInputStream fis = openFileInput("image.data"); //je récupère la photo que j'ai prise
            Bitmap bm = BitmapFactory.decodeStream(fis);
            img.setImageBitmap(bm); //je place la photo prise dans l'imageView
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}