package mandelbrot;

import javafx.scene.image.PixelWriter;
import mandelbrot.Complex;

public interface ComplexDrawable {
    void draw( PixelWriter pw, Complex c1, Complex c2, int i_var1, int i_var2, int p );
}
