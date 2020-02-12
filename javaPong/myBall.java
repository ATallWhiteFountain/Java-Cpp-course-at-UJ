package watki;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class myBall implements Entity {
    double x, y, xVel, yVel;

    public myBall(){
        x = 350;
        y = 250;
        xVel = -4;
        yVel = 1;
    }

    public int getX() {
        return (int) x;
    }

    @Override
    public int getY() {
        return (int) y;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillOval((int)x - 15, (int)y - 15, 30, 30);
    }

    @Override
    public void move() {
        x+=xVel;
        y+=yVel;
        if(y < 15) yVel = -yVel;
        if(y > 665) yVel = -yVel;
    }

    public void checkRacketCollision(myRacket p1, AIRacket p2){
        if(x <= 55){
            if(y >= p1.getY() && y <= p1.getY() + 80){
                xVel = -xVel;
            }
        } else if( x >= 969){
            if(y >= p2.getY() && y <= p2.getY() + 80){
                xVel = -xVel;
            }
        }
    }

}
