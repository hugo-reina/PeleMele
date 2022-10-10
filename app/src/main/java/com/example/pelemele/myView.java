package com.example.pelemele;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class myView extends View{

    private Path path;
    private Paint paint;
    private float x;
    private float y;

    public myView(Context context, @Nullable AttributeSet at) {
        super(context,at);
        path = new Path();
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,paint);
        canvas.drawLine((float) getHeight()/2,(float)  getWidth()/2,x,y,paint);
    }

}
