package io.trollingninjas.status200_android_client.Dagger.Modules;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.trollingninjas.status200_android_client.Model.Constants;

@Module
public class SharedPrefModule {

    Context context;
    SharedPreferences sharedPrefs;

    public SharedPrefModule(Context context){
        this.context=context;
        sharedPrefs = context.getSharedPreferences(Constants.sharedPreferenceUser,Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPrefs() {
        return sharedPrefs;
    }

    @Provides
    @Singleton
    SharedPreferences.Editor providesSharedPrefsEditor() {
        return context.getSharedPreferences(Constants.sharedPreferenceUser,Context.MODE_PRIVATE).edit();
    }
}