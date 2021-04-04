package dev.vrba.experiments.mandelbrot;

import javafx.application.Application;
import javafx.stage.Stage;

public class MandelbrotApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setWidth(810);
        primaryStage.setHeight(540);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Mandelbrot set - @jirkavrba");

        MainWindow window = new MainWindow();

        primaryStage.setScene(window.render());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(MandelbrotApplication.class, args);
    }
}
