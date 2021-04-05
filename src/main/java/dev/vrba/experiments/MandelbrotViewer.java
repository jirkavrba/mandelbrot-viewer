package dev.vrba.experiments;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;

public class MandelbrotViewer extends SimpleApplication {

    public static void main(String[] arguments) {
        MandelbrotViewer app = new MandelbrotViewer();

        AppSettings settings = new AppSettings(true);

        settings.setTitle("Mandelbrot viewer @jirkavrba");
        settings.setWidth(640);
        settings.setHeight(480);
        settings.setFullscreen(false);
        settings.setResizable(false);

        app.setSettings(settings);
        app.setShowSettings(false);
        app.setDisplayFps(true);
        app.start();
    }

    @Override
    public void simpleInitApp() {

    }
}
