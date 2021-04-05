package dev.vrba.experiments.math;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class MandelbrotSet {

    private int iterations = 100;

    private double threshold = 1.0;

    public double compute(@NotNull final ComplexNumber c) {
        ComplexNumber z = ComplexNumber.ZERO;

        for (int i = 0; i < this.iterations; i ++) {
            z = z.square().plus(c);

            if (!ComplexViewport.DEFAULT_VIEWPORT.contains(z)) {
                return (double) iterations / i;
            }
        }

        return 0;
    }
}
