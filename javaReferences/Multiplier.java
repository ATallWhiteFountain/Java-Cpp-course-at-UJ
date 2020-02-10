package start;

public class Multiplier implements Computation<Double>{
    // implements - wiele, extends - pojedynczy raz
    private Double a;

    public Multiplier(double a){
        this.a = a;
    }

    @Override
    public Double compute(Double x) {

        // parametr double nie zadziala, konwersja nie dziala automatycznie z jakiegos powodu

        return a*x;
    }
}
