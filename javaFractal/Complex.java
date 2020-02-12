package mandelbrot;

import java.lang.Math;

public class Complex implements Field<Complex> {
    private double r, i;

    public Complex() {
        this.r = 0;
        this.i = 0;
    }

    public Complex(double a) {
        this.r = a;
        this.i = 0;
    }

    public Complex(double a, double b) {
        this.r = a;
        this.i = b;
    }

    public Complex(Complex z) {
        this.r = z.r;
        this.i = z.i;
    }

    public Complex(String str) {
        Complex z = valueOf(str);
        this.r = z.r;
        this.i = z.i;
    }

    public boolean equals(Object obj) {

        if (this == obj) return true;

        // porownuje adresy w pamieci

        if (!(obj instanceof Complex)) return false;

        // jesli nieprawda jest ze obj jest instancja klasy Complex

        Complex newComplex = (Complex) obj;

        // rzutowanie typu po eliminacji mozliwosci ze obj nie jest klasy Complex

        return this.r == newComplex.r && this.i == newComplex.i;

        // sprawdzanie wartosci wszystkich pol Comples

    } // Porównanie wartości

    /* Poniższe metody modyfikują aktualny obiekt i zwracają 'this' */

    @Override
    public Complex add(Complex z) {
        this.r += z.r;
        this.i += z.i;
        return this;
    }   // Dodawanie

    @Override
    public Complex sub(Complex z) {
        this.r -= z.r;
        this.i -= z.i;
        return this;
    }   // Odejmowanie

    @Override
    public Complex mul(Complex z) {
        double temp = this.r;
        this.r = this.r * z.r - this.i * z.i;
        //this.i = temp * z.i + this.i * z.r;
        this.i = temp * z.i + this.i * temp;
        return this;
    }   // Mnożenie

    public Complex square() {
        double dBuf = this.r;
        this.r = this.r * this.r - this.i * this.i;
        this.i = 2 * dBuf * this.i;
        return this;
    }

    @Override
    public Complex div(Complex z) throws DivideByZeroException {
        if (z.r * z.r + z.i * z.i == 0) throw new DivideByZeroException(z.r, z.i);
        double dBuf = this.r;
        this.r = (this.r * z.r + this.i * z.i) / (z.r * z.r + z.i * z.i);
        this.i = (this.i * z.r - dBuf * z.i) / (z.r * z.r + z.i * z.i);
        return this;
    } // Dzielenie

    public double abs() {
        return Math.sqrt(this.r * this.r + this.i * this.i);
    }           // Moduł

    public double sqrAbs() {
        return this.r * this.r + this.i * this.i;
    }        // Kwadrat modułu

    public double phase() {
        return Math.atan2(this.i, this.r);
    }         // Faza

    public double re() {
        return this.r;
    }            // Część rzeczywista

    public double im() {
        return this.i;
    }          // Część urojona



    /* Metody statyczne dla powyższych operacji */

    public static Complex add(Complex z1, Complex z2) {
        Complex z = new Complex();
        z.r += z1.r + z2.r;
        z.i += z1.i + z2.i;
        return z;
    }

    public static Complex sub(Complex z1, Complex z2) {
        Complex z = new Complex();
        z.r += z1.r - z2.r;
        z.i += z1.i - z2.i;
        return z;
    }

    public static Complex mul(Complex z1, Complex z2) {
        Complex z = new Complex();
        z.r = z1.r * z2.r - z1.i * z2.i;
        z.i = z1.r * z2.i + z1.i * z2.r;
        return z;
    }

    public static Complex div(Complex z1, Complex z2) throws DivideByZeroException {
        if (z2.r * z2.r + z2.i * z2.i == 0) throw new DivideByZeroException(z2.r, z2.i);
        Complex z = new Complex();
        z.r += (z1.r * z2.r + z1.i * z2.i) / (z2.r * z2.r + z2.i * z2.i);
        z.i += (z1.i * z2.r - z1.r * z2.i) / (z2.r * z2.r + z2.i * z2.i);
        return z;
    }

    public static double abs(Complex z) {
        return Math.sqrt(z.r * z.r + z.i * z.i);
    }

    public static double phase(Complex z) {
        return Math.atan2(z.i, z.r);
    }

    public static double sqrAbs(Complex z) {
        return z.r * z.r + z.i * z.i;
    }

    public static double re(Complex z) {
        return z.r;
    }

    public static double im(Complex z) {
        return z.i;
    }

    /* Dodatkowe metody */

    @Override
    public String toString() {
        return this.r + "+" + this.i + "i";
    }
    /* Zwraca String z zapisaną
        liczbą zespoloną formacie "-1.23+4.56i" */

    public static Complex valueOf(String str) {
        String[] strarr = str.split("\\+", 2);
        String[] narr = strarr[1].split("i", 2);
        double a = Double.parseDouble(strarr[0]);
        double b = Double.parseDouble(narr[0]);
        return new Complex(a, b);
    }
    /* Zwraca liczbę zespolona o wartości podanej
        w argumencie w formacie "[double]+[double]i" */

    public void setRe(double a) {
        this.r = a;
    }
    /* Przypisuje podaną wartość części rzeczywistej */

    public void setIm(double b) {
        this.i = b;
    }
    /* Przypisuje podaną wartość części urojonej */

    public void setVal(Complex z) {
        this.r = z.r;
        this.i = z.i;
    }

    public void setVal(double a, double b) {
        this.r = a;
        this.i = b;
    }
    /* Przypisuje podaną wartość */
}
