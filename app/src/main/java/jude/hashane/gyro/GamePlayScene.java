package jude.hashane.gyro;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by Jude on 10/4/2016.
 */

public class GamePlayScene implements Scene {

    private Rect r = new Rect();

    private RectPlayer player;
    private Point playerLoca;
    private ObstacleManager obstacleManager;

    private boolean movingPlayer = false;
    private boolean gameOver = false;

    private long GameOverTime;


    public GamePlayScene(){
        player = new RectPlayer(new Rect(100,100,200,200), Color.rgb(255, 0, 0));

        playerLoca =  new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(playerLoca);

        obstacleManager = new ObstacleManager(200,350,75,Color.BLACK);
    }

    public void reset(){
        playerLoca =  new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(playerLoca);

        obstacleManager = new ObstacleManager(200,350,75,Color.BLACK);
        movingPlayer =true;

    }

    @Override
    public void update() {

        if(!gameOver){
            player.update(playerLoca);
            obstacleManager.update();

            if(obstacleManager.playerCollide(player)){
                gameOver = true;
                GameOverTime = System.currentTimeMillis();
            }
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

    @Override
    public void draw(Canvas canvas) {
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

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!gameOver && player.getRectangle().contains((int) (event.getX()), (int) event.getY()))    //event.getX/Y -points  where out finger is on the screen
                    movingPlayer = true;
                if (gameOver && System.currentTimeMillis() - GameOverTime >= 2000) {

                    reset();
                    gameOver = false;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                playerLoca.set((int) event.getX(), (int) event.getY());

            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }
    }

}
