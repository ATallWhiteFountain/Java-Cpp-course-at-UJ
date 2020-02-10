package start;

public class Divide {
    protected static Double div(double a, double b) throws DivideByZeroException{

        if(b == 0) throw new DivideByZeroException(a, b);

        // wyrzucam wyjatek

        return a / b;
    }
    public static void main(String[] args){

      try {
              if(args.length < 2) throw new Exception();
                Double wynik = div(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
              System.out.println("\nSuma " + args[0] + " + " + args[1] + " wynosi " + wynik);

      } catch (DivideByZeroException e){
          e.printStackTrace();

          // metoda instancji Exception ktora wyswietla informacje ze stosu

          e.getMessage();

      } catch (Exception a){
          a.getMessage();
      }
    }
}
