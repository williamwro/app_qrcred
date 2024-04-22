package br.com.qrcred;

import android.app.Application;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Map;

public class App__ extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();

        Map<String,Object> defaultValue = new HashMap<>();

        defaultValue.put(UpdateHelper.KEY_UPDATE_ENABLE,true);
        defaultValue.put(UpdateHelper.KEY_UPDATE_VERSION,"1.0");
        defaultValue.put(UpdateHelper.KEY_UPDATE_URL,"your app url on App Store");

        //remoteConfig.setDefaults(defaultValue);
        remoteConfig.fetch(5)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        //remoteConfig.activateFetched();
                        remoteConfig.activate();
                    }
                });
    }
}
