package dev.vrba.experiments.mandelbrot.math;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class MandelbrotSet {

    private int iterations;

    private int exponent;

    private ComplexBoundaries boundaries;

    public static final ComplexBoundaries MAX_BOUNDARY = new ComplexBoundaries(
            new ComplexNumber(-2, -1),
            new ComplexNumber(1, 1)
    );

    public MandelbrotSet() {
        this.iterations = 10;
        this.exponent = 2;
        this.boundaries = MAX_BOUNDARY;
    }

    public double compute(double x, double y) {
        return this.compute(this.boundaries.scale(x, y));
    }

    public double compute(@NotNull final ComplexNumber c) {
        ComplexNumber z = new ComplexNumber(0, 0);

        for (int i = 0; i < iterations; i++) {
            z = z.square().add(c);

            if (!MAX_BOUNDARY.contains(z)) {
               return (i) / (double) iterations;
            }
        }

        return 0;
    }
}
