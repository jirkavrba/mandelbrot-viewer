package dev.vrba.experiments.mandelbrot;

import dev.vrba.experiments.mandelbrot.math.MandelbrotSet;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MainWindow {

    private final Canvas canvas;

    private final Slider iterations;

    private final MandelbrotSet mandelbrot;

    public final static int WIDTH = 640;

    public final static int HEIGHT = 480;


    public MainWindow() {
        this.canvas = new Canvas(WIDTH, HEIGHT);
        this.iterations = new Slider(1, 100, 1);

        this.mandelbrot = new MandelbrotSet();

        this.configureControls();
        this.registerEventListeners();
    }

    public Scene render() {
        HBox root = new HBox(
                new VBox(
                        new Label("Iterations count"),
                        this.iterations
                ),
                this.canvas
        );

        root.setSpacing(10);
        root.setPadding(new Insets(10));

        this.draw();

        return new Scene(root);
    }

    private void configureControls() {
        this.iterations.setBlockIncrement(1);
        this.iterations.setShowTickLabels(true);
        this.iterations.setShowTickMarks(true);
    }

    private void registerEventListeners() {
        this.iterations
                .valueProperty()
                .addListener(event -> {
                    DoubleProperty property = (DoubleProperty) event;

                    int iterations = (int) Math.ceil(property.getValue());

                    this.mandelbrot.setIterations(iterations);
                    this.draw();
                });
    }

    private void draw() {
        GraphicsContext context = this.canvas.getGraphicsContext2D();

        context.clearRect(0, 0, WIDTH, HEIGHT);

        double pixelSize = 10;

        for (int x = 0; x < WIDTH; x += pixelSize) {
            for (int y = 0; y < HEIGHT; y += pixelSize) {

                double value = this.mandelbrot.compute(
                        (double) x / WIDTH,
                        (double) y / HEIGHT
                );

                context.setFill(new Color(value, 0, 0, 1));
                context.fillRect(x, y, pixelSize, pixelSize);
            }
        }
    }
}
