package start;

import static java.lang.Math.PI;

public class External {
    public class Internal implements Computation<Double> {
        @Override
        public Double compute(Double arg) {
            return PI * arg * arg;
        }
    }
}
