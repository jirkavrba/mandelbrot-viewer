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
}
