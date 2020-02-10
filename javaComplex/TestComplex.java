package complex;

public class TestComplex {
    static void testAdd(){
        Complex a = new Complex("4+2i");
        Complex b = Complex.valueOf("-6+-7i");
        Complex c = Complex.valueOf("-2+-5i");    // c to wynik dodawania zespolonych a*b
        Complex d = Complex.add(a, b);  // dodanie a+b metoda statyczna
        a.add(b);        // dodania a i b metoda niestatyczna
        assert a.equals(c) : "Error in instance method add()";      // wyrzucenie wyjatku a+b != c
        assert d.equals(c) : "Error in static method add()";      // wyrzucenie wyjatku a+b != c
    }

    static void testMul() {
        Complex a = new Complex("-1.2+3.4i");
        Complex b = Complex.valueOf("5.6+-7.8i");
        Complex c = Complex.valueOf("19.8+28.4i");    // c to wynik mnozenia zespolonych a*b
        Complex d = Complex.mul(a, b);  // przemnozenie a*b metoda statyczna
        a.mul(b);        // przemnozenie a przez b metoda niestatyczna
        assert a.equals(c) : "Error in instance method mul()";      // wyrzucenie wyjatku a*b != c
        assert d.equals(c) : "Error in static method mul()";        // wyrzucenie wyjatku a*b != c
    }

   static void testDiv(){
        Complex a = new Complex( 3,  2);
        Complex b = new Complex( 4,  -3);
        Complex c = new Complex( 0.24,  0.68);
        try {
            Complex d = Complex.div(a, b);
            assert c.equals(d) : "Error in static method div()";      // wyrzucenie wyjatku a*b != c
            d = a.div(b);
            assert d.equals(c) : "Error in instance method div()";      // wyrzucenie wyjatku a*b != c
        } catch (DivideByZeroException err){
            err.getMessage();
            err.printStackTrace();
        }

   }

   static void testToString(){
       Complex a = new Complex(-1.2, 3.6);
       String str = "-1.2+3.6i";
       String str2 = a.toString();
       assert str.equals(str2) : "Error in instance method toString()";
   }

   static void testValueOf(){
       Complex a = new Complex(-1.2, -3.4);
       String str = "-1.2+-3.4i";
       String str2 = a.toString();
       assert str.equals(str2) : "Error in instance method valueOf()";
   }

   public static void main(String[] args) {
   try {
       testAdd();
       testMul();
       testDiv();
       testToString();
       testValueOf();
   }catch (AssertionError a){
       a.printStackTrace();
       a.getMessage();
   }
   }
}
