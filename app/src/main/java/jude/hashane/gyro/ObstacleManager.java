package jude.hashane.gyro;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by Jude on 10/4/2016.
 */

public class ObstacleManager  {

    //Higher index = lower on screen = higher Y values
    private ArrayList<Obstacles> obstacles;
    private int playerGap;
    private int obstaclesGap;
    private int obstacleHeight;
    private int color;

    private long startTime;
    private long initTime;

    private int score =0;


    public ObstacleManager(int playerGap,int obstaclesGap, int obstacleHeight,int color){
        this.playerGap = playerGap;
        this.obstaclesGap = obstaclesGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = initTime = System.currentTimeMillis();
        obstacles  = new ArrayList<>();
        populateObstacles();
    }

    public boolean playerCollide(RectPlayer player)
    {
        for(Obstacles ob: obstacles){
            if(ob.playerCollide(player))
                return true;
        }
        return false;
    }

    private void populateObstacles(){
        int currentY = -5 *Constants.SCREEN_HEIGHT/4;
        while (currentY < 0)
        {
          int xStart =(int)( Math.random()*(Constants.SCREEN_WIDTH - playerGap)); //make enough room to player to creep through
            obstacles.add(new Obstacles(obstacleHeight,color,xStart,currentY,playerGap));
            currentY += obstacleHeight + obstaclesGap;


        }


    }



    public void update()
    {
      int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime =System.currentTimeMillis();
        float speed = (float)(Math.sqrt(1+ (startTime -initTime)/1000.0))*Constants.SCREEN_HEIGHT/(10000.0f); //Setting the speed as time goes.In here it's 1000.0 every 1sec
        for(Obstacles ob: obstacles){
            ob.incrementY(speed * elapsedTime);                //Speed of the obstacles - according to speed two obstacles moves down faster as time goes .
        }
        if(obstacles.get(obstacles.size()-1).getRectangle().top >= Constants.SCREEN_HEIGHT){
            int xStart =(int)( Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new Obstacles(obstacleHeight,color,xStart,obstacles.get(0).getRectangle().top - obstacleHeight - obstaclesGap,playerGap));
            obstacles.remove(obstacles.size()-1);  // Removing obstacles one by one as it goes out of the screen
            score++;
        }
    }
    public void draw(Canvas canvas){
        for(Obstacles ob : obstacles){
            ob.draw(canvas);
            Paint paint = new Paint();
            paint.setTextSize(50);
            paint.setColor(Color.BLUE);
            canvas.drawText("Score :"+score,50,50,paint);

        }
    }
}
