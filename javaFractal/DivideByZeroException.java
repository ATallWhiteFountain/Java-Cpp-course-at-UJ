package mandelbrot;

import java.lang.Exception;

public class DivideByZeroException extends Exception {

    private String sSentence;

    public DivideByZeroException(double a, double b) {
        this.sSentence = "\nWystapil blad, nie mozna dzielic przez zero\n";
    }

    @Override
    public String getMessage() {
        return sSentence;
    }
}