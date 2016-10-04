package jude.hashane.gyro;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Jude on 10/4/2016.
 */

public interface Scene {
    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void receiveTouch(MotionEvent event);
}
