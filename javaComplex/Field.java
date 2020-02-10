package complex;

public interface Field<T> {
    // Poniższe metody modyfikują aktualny obiekt i zwracają "this".
    // Taka konstrukcja pozwala na łańcuchowe łączenie operacji. Np.
    // a.mul(a).add(b) --> a = a^2 + b
    T add(T t);          // dodawanie
    T sub(T t);          // odejmowanie
    T mul(T t);          // mnożenie
    T div(T t) throws DivideByZeroException;          // dzielenie

    String toString();
}


