package dev.vrba.experiments.math;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString
@AllArgsConstructor
public class ComplexViewport {

    public ComplexNumber min;

    public ComplexNumber max;

    public static final ComplexViewport DEFAULT_VIEWPORT = new ComplexViewport(
            new ComplexNumber(-2.0, -1.0),
            new ComplexNumber(1.0, 1.0)
    );

    public ComplexViewport(@NotNull ComplexNumber center, double realInterval, double imaginaryInterval) {
        ComplexNumber delta = new ComplexNumber(realInterval / 2.0, imaginaryInterval / 2.0);

        this.min = center.minus(delta);
        this.max = center.plus(delta);
    }

    public boolean contains(@NotNull ComplexNumber number) {
        return this.min.getReal() <= number.getReal() && this.min.getImaginary() <= number.getImaginary() &&
                this.max.getReal() >= number.getReal() && this.max.getImaginary() >= number.getImaginary();
    }

    public ComplexNumber getRelativeComplexNumber(double real, double imaginary) {
        assert real >= 0.0 && real <= 1.0;
        assert imaginary >= 0.0 && imaginary <= 1.0;

        return new ComplexNumber(
                min.getReal() + (max.getReal() - min.getReal()) * real,
                min.getImaginary() + (max.getImaginary() - min.getImaginary()) * imaginary
        );
    }
}
