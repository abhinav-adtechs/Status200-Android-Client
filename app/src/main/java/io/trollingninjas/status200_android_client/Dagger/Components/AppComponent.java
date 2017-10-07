package io.trollingninjas.status200_android_client.Dagger.Components;


import javax.inject.Singleton;
import dagger.Component;
import io.trollingninjas.status200_android_client.Dagger.Modules.SharedPrefModule;

@Component(modules = SharedPrefModule.class)
@Singleton
public interface AppComponent {


}
