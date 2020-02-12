package watki;

import javafx.scene.canvas.GraphicsContext;

public interface Entity {
    public void draw(GraphicsContext gc);
    public void move();
    public int getY();
}
