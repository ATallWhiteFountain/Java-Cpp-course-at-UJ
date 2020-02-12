package watki;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class myRacket implements Entity{

    private static final double BRAKES = 0.94;
    double y, yVel;
    boolean upAccel, downAccel;
    int player, x;
    // x, y - pozycja rakietki na scenie, w player zapisane jest info ktorego obiektu to rakietka
    // upAccel, downAccel - w ktora strone rakietka przyspiesza

    public myRacket(int player){
        upAccel = false; downAccel = false;
        y = 310; yVel = 0;
        if(player == 0) x = 20;
        else x = 984;
    }

    public void setUpAccel(boolean input){
        upAccel = input;
    }

    public void setDownAccel(boolean input){
        downAccel = input;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(x, (int)y, 20, 80);
    }

    @Override
    public void move() {
        if (upAccel){
            yVel =- 2;
        }
        else if(downAccel){
            yVel =+ 2;
        }
        else if(!upAccel && !downAccel){
            yVel *= BRAKES;
        }

        if(yVel >= 5) yVel = 5;
        else if(yVel <= -5) yVel = -5;

        y += yVel;

        if(y < 0) y = 0;
        if(y > 600) y = 600;
    }

    @Override
    public int getY() {
        return (int)y;
    }
}
