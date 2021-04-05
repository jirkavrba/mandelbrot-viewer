package dev.vrba.experiments.math;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public class ComplexViewport {

    public ComplexNumber min;
    public ComplexNumber max;

    public ComplexViewport(@NotNull ComplexNumber center, double realInterval, double imaginaryInterval) {
        ComplexNumber delta = new ComplexNumber(realInterval / 2.0, imaginaryInterval / 2.0);

        this.min = center.minus(delta);
        this.max = center.plus(delta);
    }
}
