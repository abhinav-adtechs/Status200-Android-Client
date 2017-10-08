package io.trollingninjas.status200_android_client;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import io.trollingninjas.status200_android_client.Dagger.Components.AppComponent;
import io.trollingninjas.status200_android_client.Dagger.Components.DaggerAppComponent;
import io.trollingninjas.status200_android_client.Dagger.Modules.SharedPrefModule;
import io.trollingninjas.status200_android_client.Model.FontsOverride;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    private AppComponent appComponent ;
    private RequestQueue mRequestQueue;

    private static AppController mInstance;


    @Override
    public void onCreate() {
        super.onCreate();

        this.appComponent = DaggerAppComponent.builder()
                .sharedPrefModule(new SharedPrefModule(this))
                .build();

        FontsOverride.setDefaultFont(this, "MONOSPACE", "OpenSans-Light.ttf");
        FontsOverride.setDefaultFont(this, "SANS", "OpenSans-Bold.ttf");
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public Context getAppContext(){
        return getApplicationContext() ;
    }

    public AppComponent get() {
        return appComponent;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
