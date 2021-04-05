package dev.vrba.experiments;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.opengl.GL;
import com.jme3.system.AppSettings;
import dev.vrba.experiments.math.ComplexViewport;
import dev.vrba.experiments.math.MandelbrotSet;

public class MandelbrotViewer extends SimpleApplication {

    private static final int WIDTH = 640;

    private static final int HEIGHT = 480;

    private final MandelbrotSet mandelbrot = new MandelbrotSet();

    private ComplexViewport viewport = ComplexViewport.DEFAULT_VIEWPORT;

    private int pixelSize = 10;

    private boolean needsUpdate = true;

    public static void main(String[] arguments) {
        MandelbrotViewer app = new MandelbrotViewer();

        AppSettings settings = new AppSettings(true);

        settings.setTitle("Mandelbrot viewer @jirkavrba");
        settings.setWidth(WIDTH);
        settings.setHeight(HEIGHT);
        settings.setFullscreen(false);
        settings.setResizable(false);

        app.setSettings(settings);
        app.setShowSettings(false);
        app.setDisplayFps(true);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        this.flyCam.setEnabled(false);
        this.render();
    }

    @Override
    public void update() {
        super.update();
    }

    private void render() {
        if (this.needsUpdate) {
            double[][] buffer = new double[WIDTH / this.pixelSize][HEIGHT / this.pixelSize];

            for (int x = 0; x < WIDTH / this.pixelSize; x ++) {
                for (int y = 0; y < HEIGHT / this.pixelSize; y ++) {
                    buffer[x][y] = this.mandelbrot.compute(
                            this.viewport.getRelativeComplexNumber(
                                    x * this.pixelSize / (double) WIDTH,
                                    y * this.pixelSize / (double) HEIGHT
                            )
                    );
                }
            }


            this.needsUpdate = false;
        }
    }
}
