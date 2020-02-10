package start;

import static java.lang.Math.PI;
import static java.lang.Math.cos;

import java.util.Random;
import java.lang.String;

public class Compute{

    public static Double compute(Double dx) { return cos(dx);}

    public static void plot(double xmin, double xmax, Computation<Double> f) throws DivideByZeroException {
        final int n = 20;
        double okres = (xmax - xmin) / n;

        for(double x = xmin; x < xmax; x += okres )
            System.out.println("\nx = " + x + "\t\t\tf(x) = " + String.valueOf(f.compute(x)));
        System.out.println("\nKoniec serii");
    }

    private static void apply(double[] array, Computation<Double> f) throws DivideByZeroException {
        for(int i = 0; i < array.length; i++) {
            array[i] = f.compute(array[i]);
        }
    }

    public static void main(String[] args) throws DivideByZeroException {
        System.out.println("Hallo");

        plot(1, 50, new Multiplier(2 * PI));

        // instacja compute() w obiekcie Multiplier zwraca a*x gdzie a to argument konstruktora, tutaj 2 * PI,
        // x jest wprowadzane pozniej w metodzie plot(), ostatecznie zwracane jest a*x

        External ext = new External();
        External.Internal in = ext.new Internal();
        plot(50, 100, in );

        // w powyzszych trzech linijkach kolejno tworze obiekt klasy zewnetrznej, na jego podstawie tworze obiekt klasy
        // wewnetrznej, ktory nastepnie wprowadzam jako argument do metody plot()
        // Argument x do compute() przekazywany jest wewnatrz plot(), poza tym argumentem compute() nie potrzebuje juz
        // nic wiecej by zwrocic wynik, bo zwraca PI*x*x


        Computation<Double> lambda = arg -> {
           // return -arg;
            return Divide.div(1000, arg);
        };
        plot(100, 150, lambda);

        // wyrazenie lambda: tworze zmienna o nazwie lambda typu Computation<Double> jawnie korzystajac z interfejsu;
        // do zmiennej lambda przypisuje argument, po wykonaniu akcji przez wyrazenie lambda zwracany (return) zostaje
        // ten sam argument z przeciwna wartoscia
        // lambda jest wyrazeniem korzystajacym z metody opisanej w interfejsie, interfejs funkcyjny ma zawsze jedna
        // metode, wiec kompilator nie ma problemu z zastapieniem metody w interfejsie instrukcjami lambdy

         plot( 150, 200, Compute::compute);

        // referencja do metody statycznej

        //plot( 200, 250, new KlasaPierwsza());
        KlasaPierwsza obj = new KlasaPierwsza();
        plot( 200, 250, obj::compute);

        // referencja do metody niestatycznej - tworze obiekt klasy by dostac sie do metody obiektu

        int N = 20;
        double[] array = new double[N];
        Random rand = new Random();
        for(int i = 0; i < N; i++){
            array[i] = rand.nextDouble();
        }
        apply( array, new Multiplier(2*PI));
        for(double auto : array) System.out.println(auto);

        // powyzej tworze tablice typu double o rozmiarze N = 20, korzystajac z generatora liczb pseudolosowych
        // ( obiekt klasy Random z java.util.Random ) przypisuje do tablicy liczby typu double o wartosciach
        // wygenerowanych przez generator, po czym stosuje na tej tablicy metode apply() wykonujaca dzialania na
        // wartosciach w tablicy, a na koniec wypisuje na ekran zawartosc tablicy
    }
}
