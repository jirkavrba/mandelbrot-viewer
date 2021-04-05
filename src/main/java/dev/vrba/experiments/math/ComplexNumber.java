package dev.vrba.experiments.math;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * A very simplified version of complex number representation, it only supports two operations used when calculating
 * the Mandelbrot set: plus and square
 */
@AllArgsConstructor
public class ComplexNumber {

    @Getter
    private final double real;

    @Getter
    private final double imaginary;

    @NotNull
    public ComplexNumber plus(@NotNull ComplexNumber another) {
        return new ComplexNumber(
                this.real + another.real,
                this.imaginary + another.imaginary
        );
    }

    @NotNull
    public ComplexNumber minus(@NotNull ComplexNumber another) {
        return new ComplexNumber(
                this.real - another.real,
                this.imaginary - another.imaginary
        );
    }

    @NotNull
    public ComplexNumber square() {
        // (a + bi)^2 = a^2 - b^2 + 2abi
        return new ComplexNumber(
            this.real * this.real - this.imaginary * this.imaginary,
                this.real * this.imaginary * 2.0
        );
    }

    public double distance() {
        return Math.sqrt(this.real * this.real + this.imaginary * this.imaginary);
    }
}
