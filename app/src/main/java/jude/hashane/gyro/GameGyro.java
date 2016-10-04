package jude.hashane.gyro;

import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Jude on 10/4/2016.
 */

public class GameGyro extends SurfaceView implements SurfaceHolder.Callback{
    private mThread mThread;

    /////private SceneManager manager;


    private Rect r = new Rect();

    private RectPlayer player;
    private Point playerLoca;
    private ObstacleManager obstacleManager;

    private boolean movingPlayer = false;
    private boolean gameOver = false;

    private long GameOverTime;

    public GameGyro(Context context){
        super(context);

        getHolder().addCallback(this);

        mThread = new mThread(getHolder(),this);

      /////  this.manager= new SceneManager();

        player = new RectPlayer(new Rect(100,100,200,200), Color.rgb(255, 0, 0));

        playerLoca =  new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(playerLoca);

        obstacleManager = new ObstacleManager(200,350,75,Color.BLACK);

        setFocusable(true);
    }

    public void reset(){
        playerLoca =  new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(playerLoca);

        obstacleManager = new ObstacleManager(200,350,75,Color.BLACK);
        movingPlayer =true;

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //Restart our thread re-run
        mThread =  new mThread(getHolder(),this);

        mThread.setRunning(true);
        mThread.start();

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (true){
            try {
                mThread.setRunning(true);
                mThread.join();
            }catch (Exception e){e.printStackTrace();}
            retry =false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
      ///////////  manager.receiveTouch(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && player.getRectangle().contains((int)(event.getX()),(int)event.getY()))    //event.getX/Y -points  where out finger is on the screen
                    movingPlayer = true;
                if(gameOver && System.currentTimeMillis() - GameOverTime >= 2000){

                    reset();
                    gameOver = false;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                playerLoca.set((int)event.getX(),(int)event.getY());

            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;

        }
        return true;
        //return super.onTouchEvent(event);
    }

    public void update(){
       /////////// manager.update();
        if(!gameOver){
            player.update(playerLoca);
            obstacleManager.update();

            if(obstacleManager.playerCollide(player)){
                gameOver = true;
                GameOverTime = System.currentTimeMillis();
            }
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
   /////////     manager.draw(canvas);
        canvas.drawColor(Color.WHITE);

        player.draw(canvas);
        obstacleManager.draw(canvas);
        if(gameOver){
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            drawCenterText(canvas,paint,"Game Over");

        }
    }

    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);

    }
}
