package dev.vrba.experiments.mandelbrot;

import dev.vrba.experiments.mandelbrot.math.ComplexBoundaries;
import dev.vrba.experiments.mandelbrot.math.ComplexNumber;
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
import javafx.scene.text.Font;
import javafx.util.Pair;

public class MainWindow {

    private final Canvas canvas;

    private final Slider iterations;

    private final Slider resolution;

    private final MandelbrotSet mandelbrot;

    // This is just to prevent my PC from straight up dying
    private final static int MIN_RESOLUTION = 2;

    private int scale = 1;

    private final Label scaleLabel = new Label();

    private static final int MAX_SCALE = 2000;

    private static final double MOVE_COEFFICIENT = 1.5;

    private static final double DOUBLE_EPSILON = 0.001;

    public final static int WIDTH = 640;

    public final static int HEIGHT = 480;

    private Pair<Double, Double> dragStart;


    public MainWindow() {
        this.mandelbrot = new MandelbrotSet();

        this.canvas = new Canvas(WIDTH, HEIGHT);
        this.iterations = new Slider(1, 1000, this.mandelbrot.getIterations());
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
                        this.resolution,

                        new Label("Scale"),
                        this.scaleLabel
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
        this.scaleLabel.setFont(new Font(20));
    }

    private void registerEventListeners() {
        this.iterations.valueProperty().addListener(event -> this.draw());
        this.resolution.valueProperty().addListener(event -> this.draw());

        this.canvas.setOnScroll(event -> {
            if (Math.abs(event.getDeltaY()) < DOUBLE_EPSILON) {
                return;
            }

            boolean zoomIn = event.getDeltaY() > 0;

            if (zoomIn) {
                this.scale = Math.min((int) (1.25 * this.scale + 1 ), MAX_SCALE);
            } else {
                this.scale = Math.max((int) (this.scale * 0.75 - 1), 1);
            }

            // TODO: add boundaries shift based on the position
            if (this.scale == 1) {
                this.mandelbrot.setBoundaries(MandelbrotSet.MAX_BOUNDARY);
            }
            else if (this.scale == MAX_SCALE) {
                return;
            }
            else {
                double left = event.getX() / WIDTH;
                double top = event.getY() / HEIGHT;

                ComplexNumber center = this.mandelbrot.getBoundaries().scale(left, top);

                ComplexBoundaries updated = new ComplexBoundaries(
                        center,
                        MandelbrotSet.MAX_BOUNDARY.getReal() / this.scale,
                        MandelbrotSet.MAX_BOUNDARY.getImaginary() / this.scale
                );
                this.mandelbrot.setBoundaries(updated);
            }


            this.draw();
        });

        this.canvas.setOnMousePressed(event -> this.dragStart = new Pair<>(event.getX() / WIDTH, event.getY() / HEIGHT));
        this.canvas.setOnMouseReleased(event -> {
            ComplexNumber delta = new ComplexNumber(
                    -(event.getX() / WIDTH - this.dragStart.getKey()) / this.scale * MOVE_COEFFICIENT,
                    -(event.getY() / HEIGHT - this.dragStart.getValue()) / this.scale * MOVE_COEFFICIENT
            );


            this.mandelbrot.setBoundaries(
                    new ComplexBoundaries(
                            this.mandelbrot.getBoundaries().getCenter().add(delta),
                            this.mandelbrot.getBoundaries().getReal(),
                            this.mandelbrot.getBoundaries().getImaginary()
                    )
            );

            this.draw();
        });
    }

    private void draw() {
        this.scaleLabel.setText(this.scale + "x");
        this.mandelbrot.setIterations((int) this.iterations.getValue());

        GraphicsContext context = this.canvas.getGraphicsContext2D();
        context.clearRect(0, 0, WIDTH, HEIGHT);

        int pixel = (int) (this.resolution.getMax() - this.resolution.getValue()) + MIN_RESOLUTION;

        for (int x = 0; x < WIDTH; x += pixel) {
            for (int y = 0; y < HEIGHT; y += pixel) {

                double value = this.mandelbrot.compute(
                        (double) x / WIDTH,
                        (double) y / HEIGHT
                );

                context.setFill(this.colorForValue(value));
                context.fillRect(x, y, pixel, pixel);
            }
        }
    }

    private Color colorForValue(double value)  {
        // The point is inside the Mandelbrot's set
        if (value < DOUBLE_EPSILON) {
                return Color.BLACK;
        }

        return new Color(
                Math.min(value * 4, 1),
                value,
                Math.min(value * 2, 1),
                1
        );

    }
}
