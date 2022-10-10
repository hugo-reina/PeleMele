package com.example.pelemele;

import android.annotation.SuppressLint;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

public class SelectActivity extends AppCompatActivity {

    private int nb;
    private float x;
    private float y;
    private float [] rectangle;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        nb = 0;
        ImageView i = findViewById(R.id.img_select);
        SurfaceView surf = (SurfaceView)findViewById(R.id.surfaceView);
        surf.setZOrderOnTop(true);    // necessary
        SurfaceHolder sfhTrackHolder = surf.getHolder();
        sfhTrackHolder.setFormat(PixelFormat.TRANSPARENT);
        surf.setZOrderOnTop(true);

        i.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(nb == 0){
                    nb = nb + event.getPointerCount();
                    Log.i("Nombre de touche ", String.valueOf(nb)); // nombre de touche
                    x = x = event.getX();
                    y = event.getY();
                    Log.i("X et Y du 1er click ", x + " " + y);
                    //rectangle[0] = x; // je construit mon rectangle
                    //rectangle[1] = y;
                }else if (nb == 1) {
                    x = event.getX();
                    y = event.getY();
                    Log.i("X et Y  du 2ème click", x + " " + y);
                    //rectangle[2] = x;
                    //rectangle[3] = x;
                    nb=0;
                }

                return false;
            }
        });

    }
}