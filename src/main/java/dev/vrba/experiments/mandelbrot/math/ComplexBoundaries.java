package dev.vrba.experiments.mandelbrot.math;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
public class ComplexBoundaries {
    private final ComplexNumber min;
    private final ComplexNumber max;

    public ComplexNumber scale(double x, double y) {
        return new ComplexNumber(
                min.getReal() + (max.getReal() - min.getReal()) * x,
                min.getImaginary() + (max.getImaginary() - min.getImaginary()) * y
        );
    }

    public boolean contains(ComplexNumber number) {
        return this.min.getReal() <= number.getReal() &&
                this.min.getImaginary() <= number.getImaginary() &&
                this.max.getReal() >= number.getReal() &&
                this.max.getImaginary() >= number.getImaginary();
    }
}
