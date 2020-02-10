package start;

public interface Computation<T> {
    public T compute(T arg) throws DivideByZeroException;

    // Interfejs funkcyjny - cale zadanie opiera sie na wykorzystywaniu na rozne sposoby przeciazonych metod compute()

}
