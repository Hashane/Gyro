package jude.hashane.gyro;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Jude on 10/4/2016.
 */

public class Obstacles implements GameObject {

    private Rect rectangle;
    private Rect rectangle2;
    private int colors;

    public  Obstacles(int RectHeight,int color,int startX,int startY,int playerGap){

        this.colors = color;

        rectangle = new Rect(0,startY,startX,startY +  RectHeight);
        rectangle2 = new Rect(startX + playerGap, startY,Constants.SCREEN_WIDTH,startY+ RectHeight);

    }

   /**
    *Point indexes are changed .As values goes high obstacles moves down on Y axis vertically.
    */
    public void incrementY(float y){
        rectangle.top += y;
        rectangle.bottom +=y;
        rectangle2.top += y;
        rectangle2.bottom +=y;
    }
    public boolean playerCollide(RectPlayer player){
        return Rect.intersects(rectangle,player.getRectangle()) || Rect.intersects(rectangle2,player.getRectangle());

    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(colors);
        canvas.drawRect(rectangle,paint);
        canvas.drawRect(rectangle2,paint);
    }

    @Override
    public void update() {

    }

    public Rect getRectangle() {
        return rectangle;
    }
}
