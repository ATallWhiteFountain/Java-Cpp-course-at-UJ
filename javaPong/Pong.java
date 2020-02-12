package watki;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Pong implements Runnable {

    @FXML
    Canvas canvas;
    GraphicsContext gc;
    Thread thread;
    myRacket leftRacket;
    AIRacket rightRacket;
    myBall ball;

    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        leftRacket = new myRacket(0);
        ball = new myBall();
        rightRacket = new AIRacket(1, ball);
        paint(gc);
        thread = new Thread(this); // argument czyli target to sam Pong
        thread.start(); // start głównego wątku
    } // przypisuje canvas do gc

    public void paint(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.DIMGRAY);
        for (int placement = 0; placement < canvas.getHeight(); placement += 60) {
            gc.fillRect(507, placement, 10, 40);
        }
        if(ball.getX() < -15 || ball.getX() > 1039){
            gc.setStroke(Color.ROYALBLUE);
            gc.strokeText("Game Over", 100, 100, 150);
        }else {
            leftRacket.draw(gc);
            rightRacket.draw(gc);
            ball.draw(gc);
        }
    }

    public void update(GraphicsContext gc) {
        paint(gc);
    }

    public void keyPressed(KeyEvent key) {
        //System.out.print("działam");
        if (key.getCode() == KeyCode.UP) {
            leftRacket.setUpAccel(true);
        } else if (key.getCode() == KeyCode.DOWN) {
            leftRacket.setDownAccel(true);
        }
    }

    public void keyReleased(KeyEvent key) {
        //System.out.print("ja też!");
        if (key.getCode() == KeyCode.UP) {
            leftRacket.setUpAccel(false);
        } else if (key.getCode() == KeyCode.DOWN) {
            leftRacket.setDownAccel(false);
        }
    }

    @Override
    public void run() {
        for (; ; ) {
            leftRacket.move();
            rightRacket.move();
            ball.move();
            ball.checkRacketCollision(leftRacket, rightRacket);
            update(gc);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
