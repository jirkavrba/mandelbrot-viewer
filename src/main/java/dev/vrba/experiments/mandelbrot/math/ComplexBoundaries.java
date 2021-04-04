package dev.vrba.experiments.mandelbrot.math;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString
@AllArgsConstructor
public class ComplexBoundaries {

    private final ComplexNumber min;

    private final ComplexNumber max;

    public ComplexBoundaries(@NotNull ComplexNumber center, double real, double imaginary) {
        this(
            new ComplexNumber(center.getReal() - real / 2, center.getImaginary() - imaginary / 2),
            new ComplexNumber(center.getReal() + real / 2, center.getImaginary() + imaginary / 2)
        );
    }

    public ComplexNumber scale(double x, double y) {
        return new ComplexNumber(
                min.getReal() + (max.getReal() - min.getReal()) * x,
                min.getImaginary() + (max.getImaginary() - min.getImaginary()) * y
        );
    }

    public ComplexNumber getCenter() {
        return new ComplexNumber(
            min.getReal() + (max.getReal() - min.getReal()),
                min.getImaginary() + (max.getImaginary() - min.getImaginary())
        );
    }

    public double getReal() {
        return this.max.getReal() - this.min.getReal();
    }

    public double getImaginary() {
        return this.max.getImaginary() - this.min.getImaginary();
    }

    public boolean contains(ComplexNumber number) {
        return this.min.getReal() <= number.getReal() &&
                this.min.getImaginary() <= number.getImaginary() &&
                this.max.getReal() >= number.getReal() &&
                this.max.getImaginary() >= number.getImaginary();
    }
}
