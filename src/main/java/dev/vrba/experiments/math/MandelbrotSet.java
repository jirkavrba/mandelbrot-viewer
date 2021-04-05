package dev.vrba.experiments.math;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class MandelbrotSet {

    private int iterations;

    private double threshold = 10.0;

    public double compute(@NotNull final ComplexNumber c) {
        ComplexNumber z = new ComplexNumber(0, 0);

        for (int i = 0; i < this.iterations; i ++) {
            z = z.square().plus(c);

            if (z.distance() > this.threshold) {
                return i;
            }
        }

        return 0;
    }
}
