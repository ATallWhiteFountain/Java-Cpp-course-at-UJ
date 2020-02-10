package complex;

public interface Interfejs<T> {
    T pole();
    T obwod();
    T srednica();

    default void call(double t){
        System.out.println(t);
        whisperCall(t);

        //

    }
    /* Metoda defaultowa. Musi posiadac cialo w interfejsie. W klasie nie musi byc ponownie implementowana.
    *  Jednak implementacja w klasie zmieni jej definicje przy kompilacji. Defaultowe metody Javy pomagaja w np.     *      zastepowaniu
    *  superklas domyslnymi implementacjami, a z poziomu klasy mozna wybrac ktora implementacje chce sie nadpisac.*/

    static void louderCall(double t){
        whisperCall(t);
    }

    /* Metoda statyczna. Podobna do defaultowej, roznica jest niemozliwosc implementacji w klasie.
    * Jest tylko i wylacznie czescia interfejsu, pozostale metody interfejsu moga swobodnie korzystac z metod
    *  statycznych*/

    private static void whisperCall(double t){

        // oznaczylem sb te metode jako nie tylko prywatna, ale tez statyczna, bo chcialem ja wykorzystac w louderCall()

        System.out.println("Pozdrowienia z klasy prywatnej, wynik = " + t);
    }

    /* Metoda prywatna pozwoli na korzystanie z niej tylko wewnatrz interfejsu */
}
