package jude.hashane.gyro;

import android.graphics.Canvas;
import android.provider.Settings;
import android.support.annotation.MainThread;
import android.view.SurfaceHolder;

/**
 * Created by Jude on 10/4/2016.
 */

public class mThread extends Thread {
    public final int MAX_FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GameGyro gameGyro;
    private boolean running;
    private static Canvas canvas;

    public void setRunning(boolean running){
        this.running =  running;
    }

    public mThread(SurfaceHolder surfaceHolder,GameGyro gameGyro){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameGyro = gameGyro;

    }

    @Override
    public void run() {
        super.run();
        long startTime;
        long timeMillis = 1000/MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/MAX_FPS;

        while (running){
            startTime = System.nanoTime();

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gameGyro.update();
                    this.gameGyro.draw(canvas);

                }
            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                if(canvas != null)
                {
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            /*
            * Maintaining the FPS throught the game
            * */

            timeMillis = (System.nanoTime()- startTime /1000000);
            waitTime = targetTime - timeMillis;

            try {
                if(waitTime > 0 ){             // if it finished the frame earlier than target time
                    this.sleep(waitTime);      //then pause for awhile
                }
            }catch (Exception e){e.printStackTrace();}

            totalTime += System.nanoTime() - startTime;
            frameCount++;

            //After we sleep begin different value
            if(frameCount == MAX_FPS){
                averageFPS  = 1000/((totalTime/frameCount)/1000000);      //getting average FPS
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }


        }

    }
}
