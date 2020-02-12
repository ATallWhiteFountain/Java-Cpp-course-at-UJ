package mandelbrot;

import javafx.scene.control.ProgressBar;
import javafx.scene.image.PixelWriter;
import mandelbrot.Complex;

public class MandelbrotFractal implements ComplexDrawable{
    public MandelbrotFractal(){}
    @Override
    public void draw(PixelWriter pw, Complex a, Complex b, int width, int height, int param) {
        double pixelWidth = (b.re() - a.re()) / (double) width;
        double pixelHeight = (b.im() - a.im()) / (double) height;
        Complex zN = new Complex();
        Complex p = new Complex();
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                //Complex zN = new Complex(0.0D, 0.0D);
                //Complex p = Complex.add(a, new Complex((double) i * (b.re() - a.re()) / (double) width, (double) j * (b.im() - a.im()) / (double) height));
                // POWYŻSZE DZIAłA
                zN.setRe(0.0D); zN.setIm(0.0D);
                //Complex p = a_copy.add( new Complex((double) i * (b.re() - a.re()) / (double) width, (double) j * (b.im() - a.im()) / (double) height));
                p.setRe(a.re() + pixelWidth * i);
                p.setIm(a.im() + pixelHeight * j);
                /*
                Przy każdym zoomie każdy piksel dostaje nową wartość p dla której potem liczy się granice w przybliżeniu
                i koloruje w odpowiedni sposób. By policzyć p(x, y) trzeba przeskalować obraz na płótnie do nowych Complex a i Complex b.
                    i / x  = width / (b.re() - a.re()) oraz
                    j / y  = height / (b.im() - a.im())
                    gdzie liczniki to globalne wartości u. współ. a mianowniki to lokalne wartości uzależnione od zooma.
                Robiąc zoom zmniejszamy zakres na osiach - np. dla szerokości: z [0; 2] do [1,75; 1,92]
                                                               dla wysokości:  z [0; 2] do [0,3; 0,5]
                przy tej samej liczbie pikseli przypadającej na zakresy przed i po.
                Dlatego obraz na płótnie jest tej samej wielkości, ale sam obraz zmienia się przy zoomach.
                 */

                int n; int runaway_speed = 0 ; boolean is_divergent = false; // flaga - czy ciag jest rozbiezny
                for (n = 0; n < 100; n++) {
                    //zN = Complex.add( zN.mul(zN), p );
                    zN.mul(zN).add(p);
                    if( zN.sqrAbs() >= param*param ) { // petle przerwana gdy wartosc kwadratu modulu przekracza 2, warunek rownowazny ze zbieznoscia ciagu
                        is_divergent = true; // ciag jest rozbiezny
                        runaway_speed = n; // zapisuje próg n dla jakiego |zN| < 2 co da wyraz prędkości ucieczki
                        break;
                    }
                } // 100 iteracji wystarcza do ustalenia granicy ciagu takie przybliżenie

                pw.setArgb( i, j, setColor(is_divergent, runaway_speed));
                /* Stores pixel data for a color into the specified coordinates of the surface. The 32-bit integer argb parameter should contain the 4 color components
                 in separate 8-bit fields in ARGB order from the most significant byte to the least significant byte.
                    Parameters:
                    x - the X coordinate of the pixel color to write
                    y - the Y coordinate of the pixel color to write
                    argb - the color information to write, specified in the format described by the INT_ARGB PixelFormat type */
            }
        } // podwojna petla ktora rysuje kazdy piksel
    }

    private int setColor(boolean flag, int i_param){
        if(flag){
            int color = 0xff000000 + 0x010000 * i_param
                    + 0x0100 * 2 * i_param
                    + 0x01 * 3 * i_param / 2;
            // 0xDDDD notacja heksadecymalna prefix 0x
            return color; // zwroc kolor uzalezniony od prędkości ucieczki (i_param)
        }
        return 0xff000000; // zwroc czarny kolor
    }
}
