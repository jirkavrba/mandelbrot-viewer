package dev.vrba.experiments.mandelbrot.math;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
@ToString
public class ComplexNumber {

    private double real;

    private double imaginary;

    public ComplexNumber square() {
        // (a + bi)^2 = a^2 - b^2 + 2abi
        return new ComplexNumber(
            this.real * this.real - this.imaginary * this.imaginary,
            this.real * this.imaginary * 2
        );
    }

    public ComplexNumber add(@NotNull ComplexNumber number) {
        this.real += number.getReal();
        this.imaginary += number.getImaginary();

        return this;
    }
}
