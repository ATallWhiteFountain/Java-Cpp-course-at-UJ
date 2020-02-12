package watki;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AIRacket implements Entity{

    private static final double BRAKES = 0.94;
    double y, yVel;
    boolean upAccel, downAccel;
    int player, x;
    myBall b1;
    // x, y - pozycja rakietki na scenie, w player zapisane jest info ktorego obiektu to rakietka
    // upAccel, downAccel - w ktora strone rakietka przyspiesza

    public AIRacket(int player, myBall b){
        upAccel = false; downAccel = false;
        y = 310; yVel = 0;
        if(player == 0) x = 20;
        else x = 984;
        b1 = b;
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
        y = b1.getY() - 40;
        if(y < 0) y = 0;
        if(y > 600) y = 600;
    }

    @Override
    public int getY() {
        return (int)y;
    }
}
