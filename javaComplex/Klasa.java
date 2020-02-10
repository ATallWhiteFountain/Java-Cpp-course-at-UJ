package complex;

public class Klasa implements InterfejsFunkcyjny<Double>, Interfejs<Double> {

    private double data;

    public Klasa(double dArg){
        this.data = dArg;
    }

   @Override
    public void call(double dArg){

        // nadpisuje domyslna (default) metode z interfejsu

        Interfejs.louderCall(dArg);

        // z poziomu klasy moge wywolac metode statyczna interfejsu
    }

    @Override
    public Double pole() {
        return Math.PI*this.data*this.data;
    }

    @Override
    public Double obwod() {
        return Math.PI*2*this.data;
    }

    @Override
    public Double srednica() {
        return 2*this.data;
    }

    @Override
    public Double endComment() {
        return null;
    }

    public static void main(String[] args) {
        Klasa obliczenia = new Klasa(216);
        obliczenia.pole();
        obliczenia.obwod();
        obliczenia.srednica();
        obliczenia.call(obliczenia.data);
        obliczenia.endComment();

    }

}
