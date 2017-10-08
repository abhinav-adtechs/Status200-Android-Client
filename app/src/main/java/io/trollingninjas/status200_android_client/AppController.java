package io.trollingninjas.status200_android_client;

import android.app.Application;

import io.trollingninjas.status200_android_client.Dagger.Components.AppComponent;
import io.trollingninjas.status200_android_client.Dagger.Components.DaggerAppComponent;
import io.trollingninjas.status200_android_client.Dagger.Modules.SharedPrefModule;
import io.trollingninjas.status200_android_client.Model.FontsOverride;

public class AppController extends Application {

    private AppComponent appComponent ;

    @Override
    public void onCreate() {
        super.onCreate();

        this.appComponent = DaggerAppComponent.builder()
                .sharedPrefModule(new SharedPrefModule(this))
                .build();

        FontsOverride.setDefaultFont(this, "MONOSPACE", "OpenSans-Light.ttf");
        FontsOverride.setDefaultFont(this, "SANS-SERIF", "OpenSans-Bold.ttf");
    }

    public AppComponent get() {
        return appComponent;
    }
}
