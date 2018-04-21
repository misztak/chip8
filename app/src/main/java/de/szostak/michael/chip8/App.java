package de.szostak.michael.chip8;

import android.app.Application;
import android.content.res.AssetManager;

public class App extends Application {
    protected static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    // work-around to get assets inside normal classes
    public static AssetManager getAssetManager() {
        return instance.getAssets();
    }
}
