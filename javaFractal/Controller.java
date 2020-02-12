package mandelbrot;

import javafx.event.ActionEvent;
// An Event representing some type of action. This event type is widely used to represent a variety of things,
// such as when a Button has been fired, when a KeyFrame has finished, and other such usages
import javafx.scene.canvas.Canvas;
// Canvas is an image that can be drawn on using a set of graphics commands provided by a GraphicsContext
import javafx.scene.canvas.GraphicsContext;
// This class is used to issue draw calls to a Canvas using a buffer
// Label is a non-editable text control
import javafx.scene.effect.BlendMode;
// A blending mode defines the manner in which the inputs of a Blend effect
// are composited together or how a Node is blended into the background of a scene
import javafx.scene.input.MouseEvent;
// When mouse event occurs, the top-most node under cursor is picked and the event is delivered to it
// through capturing and bubbling phases described at EventDispatcher
import javafx.scene.paint.Color;
// The Color class is used to encapsulate colors in the default sRGB color space
import javafx.scene.image.WritableImage;
// The WritableImage class represents a custom graphical image that is constructed from pixels supplied by the application,
// and possibly from PixelReader objects from any number of sources, including images read from a file or UR
import javafx.scene.image.PixelWriter;
// This interface defines methods for writing the pixel data of a WritableImage or other surface containing writable pixels
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class Controller {

    public TextField textParameter;                                 // Pole tekstowe - od parametru r
    public TextField textComplexAx;                                 // Pole tekstowe - od zasiegu aX
    public TextField textComplexBx;                                 // Pole tekstowe - od zasiegu bX
    public TextField textComplexAy;                                 // Pole tekstowe - od zasiegu aY
    public TextField textComplexBy;                                 // Pole tekstowe - od zasiegu bY
    public TextField textWidth;                                 // Pole tekstowe - od szerokosci okna
    public TextField textHeight;                                 // Pole tekstowe - od wysokości okna
    public Canvas canvas;										// "Płótno" do rysowania
    private GraphicsContext gc;									// Kontekst graficzny do "płótna"
    private double x1, y1, x2, y2;								// Współrzędne ramki prostokąta
    private double aX, aY, bX, bY;                              // Skalowalne współrzędne do Complex a i Complex b
    private int width, height;                                  // Parametry płótna - ilość pikseli, wielkość płótna
    private int parameter;                                      // parametr dokładności selekcji do ciągu M.
    private int p;                                              // zakres p na u. współrzędnych

    public Controller() {}


    public void setParameter(ActionEvent actionEvent){
        parameter = Integer.parseInt(textParameter.getText());
    }

    public void setAx(ActionEvent actionEvent){
        aX = Double.parseDouble(textComplexAx.getText());
    }

    public void setAy(ActionEvent actionEvent){
        aY = Double.parseDouble(textComplexAy.getText());
    }

    public void setBx(ActionEvent actionEvent){
        bX = Double.parseDouble(textComplexBx.getText());
    }

    public void setBy(ActionEvent actionEvent){
        bY = Double.parseDouble(textComplexBy.getText());
    }

    public void setWidth(ActionEvent actionEvent){
        width = Integer.parseInt(textWidth.getText());
    }
    public void setHeight(ActionEvent actionEvent){
        parameter = Integer.parseInt(textHeight.getText());
    }


    public void mouseMoves(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        gc.setGlobalBlendMode(BlendMode.DIFFERENCE);
        // DIFFERENCE - ciemniejszy kolor z obydwu warstw jest odjęty
        gc.setStroke(Color.WHITE);
        // setStroke() decyduje o kolorze który jest użyty do rysowania krawędzi mojego prostokąta
        rect(gc);
        x2 = x;
        y2 = y;
        rect(gc);
    } // pobieram dane x1, y1, x2, y2

    public void mousePressed(MouseEvent mouseEvent) {
        x1 = mouseEvent.getX();
        y1 = mouseEvent.getY();
        x2 = x1;
        y2 = y1;
        rect(gc);
    } // aktualizuje dane x2, y2

    public void mouseReleased(MouseEvent mouseEvent) {
        rect(gc);
        calibrate();
        WritableImage wi = new WritableImage(width, height);
        PixelWriter pw = wi.getPixelWriter();
        MandelbrotFractal m = new MandelbrotFractal();
        m.draw(pw, new Complex(this.aX, this.aY), new Complex(this.bX, this.bY), width, height, parameter);
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        gc.drawImage(wi, 0.0D, 0.0D, width, height);
        System.out.format("%f %f %f %f\n", x1, y1, x2, y2);
    } // rysuje fraktal w powiększeniu

    private void calibrate(){
        double var1 = aX;
        double var2 = bX;
        double var3 = bY;
        double var4 = aY;
        aX += (Math.min(x1, x2)) * (var2 - var1) / (double)width;
        if (x1 != x2) {
            bX = var1 + (Math.max(x1, x2)) * (var2 - var1) / (double)width;
        } else {
            bX = aX + 8.0D * (var2 - var1) / (double)width;
        }

        aY += (Math.min(y1, y2)) * (var3 - var4) / (double)height;
        if (y1 != y2) {
            bY = var4 + (Math.max(y1, y2)) * (var3 - var4) / (double)height;
        } else {
            bY = aY + 8.0D * (var3 - var4) / (double) height;
        }
    } // aktualizuje aX, bX, aY, bY na podstawie danych z ramki prostokąta x1, y1, x2, y2 (wymagane skalowanie z pikseli do u. współrzędnych)

    public void clearCanvas(ActionEvent actionEvent) {
        clear(gc);
        aX = -2D; bX = 0.75D; aY = -1.25D; bY = 1.25D;
        width = 512;
        height = 512;
    } // metoda wywołuje clear() w odpowiedzi na jakieś zdarzenie

    public void draw(ActionEvent actionEvent) {
        WritableImage wr = new WritableImage(width, height);
        // WritableImage reprezentuje modyfikowalny obraz do ktorego moge rysować
        PixelWriter pw = wr.getPixelWriter();
        // getPixelWriter() to metoda klasy WritableImage która daje możliwość pisać do pikseli WritableImage
        // PixelWriter to interfejs definiujący metody pisania pikseli

        MandelbrotFractal m = new MandelbrotFractal();

        m.draw(pw, new Complex( aX, aY), new Complex(bX, bY), width, height, parameter);

        // wywołuje metodę rysującą fraktal - zapisuje ją w pw
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        // górna warstwa zakrywa tę pod spodem
        gc.drawImage(wr, 0, 0, width, height);
        // rysuje zawartość wr obiektu WritableImage, zapisany w nim mam mój fraktal
    } // metoda rysująca mój fraktal

    private void rect(GraphicsContext gc) {
        double x = x1;
        double y = y1;
        double w = x2 - x1;
        double h = y2 - y1;

        if (w < 0) {
            x = x2;
            w = -w;
        }

        if (h < 0) {
            y = y2;
            h = -h;
        }
        // korekta współrzędnych przy rysowaniu prostokąta 'wspak'
        gc.strokeRect(x + 0.5, y + 0.5, w, h);
        // metoda kreśli prostokąt na płótnie o wybranym wcześniej kolorze krawędzi
    } // Metoda rysuje prostokąt o rogach (x1, y1) i (x2, y2)

    private void clear(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        // metoda ustawiająca kolor którym można później wypełniać kształty
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        // powyższa metoda ustawia sposób łączenia dwóch (lub więcej) warstw na płótnie
        // SRC_OVER - warstwa na wierzchu zakryje tą pod nią
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // wypełnia prostokąt o podanych współrzędnych x, y, w, h wybranym wcześniej kolorem (tutaj setFill() wyżej)
    } // wymazuje obraz

    public void initialize() {
        width = 512;
        height = 512;
        aX = -2D; bX = 0.75D; aY = -1.25D; bY = 1.25D;
        parameter = 2;
        gc = canvas.getGraphicsContext2D();
        // przyłączam obiekt GraphicsContext do płótna
        clear(gc); // clear() inna metoda w mojej klasie
        WritableImage wr = new WritableImage(width, height);
        // WritableImage reprezentuje modyfikowalny obraz do ktorego moge rysować
        PixelWriter pw = wr.getPixelWriter();
        // getPixelWriter() to metoda klasy WritableImage która daje możliwość pisać do pikseli WritableImage
        // PixelWriter to interfejs definiujący metody pisania pikseli

        MandelbrotFractal m = new MandelbrotFractal();

        m.draw(pw, new Complex( aX, aY), new Complex(bX, bY), width, height, parameter);

        // wywołuje metodę rysującą fraktal - zapisuje ją w pw
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        // górna warstwa zakrywa tę pod spodem
        gc.drawImage(wr, 0, 0, width, height);
        // rysuje zawartość wr obiektu WritableImage, zapisany w nim mam mój fraktal
    } // przypisuje canvas do gc
}
