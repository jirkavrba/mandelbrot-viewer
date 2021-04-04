package dev.vrba.experiments.mandelbrot;

import dev.vrba.experiments.mandelbrot.math.MandelbrotSet;
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

    private final Slider resolution;

    private final MandelbrotSet mandelbrot;

    // This is just to prevent my PC from straight up dying
    private final static int MIN_RESOLUTION = 2;

    private int scale = 1;

    private static final int MAX_SCALE = 10;

    private static final double DOUBLE_EPSILON = 0.001;

    public final static int WIDTH = 640;

    public final static int HEIGHT = 480;


    public MainWindow() {
        this.mandelbrot = new MandelbrotSet();

        this.canvas = new Canvas(WIDTH, HEIGHT);
        this.iterations = new Slider(1, 100, this.mandelbrot.getIterations());
        this.resolution = new Slider(1, 10, 5);

        this.configureControls();
        this.registerEventListeners();
    }

    public Scene render() {
        HBox root = new HBox(
                new VBox(
                        new Label("Iterations count"),
                        this.iterations,

                        new Label("Resolution"),
                        this.resolution
                ),
        this.canvas
        );

        root.setSpacing(10);
        root.setPadding(new Insets(10));

        this.draw();

        return new Scene(root);
    }

    private void configureControls() {
        this.iterations.setBlockIncrement(5);
        this.resolution.setBlockIncrement(1);
    }

    private void registerEventListeners() {
        this.iterations.valueProperty().addListener(event -> this.draw());
        this.resolution.valueProperty().addListener(event -> this.draw());

        this.canvas.setOnScroll(event -> {
            if (Math.abs(event.getDeltaY()) < DOUBLE_EPSILON) {
                return;
            }

            // TODO: add boundaries shift based on the position

            if (event.getDeltaY() > 0) {
                this.scale = Math.min(this.scale + 1, MAX_SCALE);
            }
            else {
                this.scale = Math.max(this.scale - 1, 1);
            }

            this.draw();
        });
    }

    private void draw() {
        this.mandelbrot.setIterations((int) this.iterations.getValue());

        GraphicsContext context = this.canvas.getGraphicsContext2D();
        context.clearRect(0, 0, WIDTH, HEIGHT);

        int pixel = (int) (this.resolution.getMax() - this.resolution.getValue()) + MIN_RESOLUTION;

        for (int x = 0; x < WIDTH; x += pixel) {
            for (int y = 0; y < HEIGHT; y += pixel) {

                double value = this.mandelbrot.compute(
                        (double) x / this.scale / WIDTH,
                        (double) y / this.scale / HEIGHT
                );

                context.setFill((value < DOUBLE_EPSILON)
                        ? Color.BLACK
                        : new Color(value, Math.pow(value, 4), Math.pow(value, 8), 1)
                );

                context.fillRect(x, y, pixel, pixel);
            }
        }
    }
}
