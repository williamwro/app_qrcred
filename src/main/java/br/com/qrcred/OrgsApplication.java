package br.com.qrcred;

import android.app.Application;

import com.google.android.material.color.DynamicColors;

public class OrgsApplication extends Application {
   @Override
   public void onCreate() {
      super.onCreate();
      DynamicColors.applyToActivitiesIfAvailable(this);
   }
}
