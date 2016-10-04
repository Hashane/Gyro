package jude.hashane.gyro;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Jude on 10/4/2016.
 */

public class RectPlayer implements GameObject {

    private Rect rectangle;
    private int colors;

    //getter
    public Rect getRectangle(){
        return rectangle;
    }

    /**
     * constructor
     */
    public  RectPlayer(Rect rectangle, int color){
        this.rectangle =  rectangle;
        this.colors = color;
    }
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(colors);
        canvas.drawRect(rectangle,paint);

    }

    @Override
    public void update() {


    }

    //method overloaded
    public void update(Point point){
        //left,top,right,bottom
        rectangle.set(point.x - rectangle.width()/2,point.y - rectangle.height()/2,point.x + rectangle.width()/2,point.y + rectangle.height()/2);
    }
}
