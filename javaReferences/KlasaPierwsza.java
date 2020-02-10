package start;

import static java.lang.Math.sin;

public class KlasaPierwsza implements Computation<Double> {
    @Override
    public Double compute(Double arg) {
        return sin(arg);
    }

        }
