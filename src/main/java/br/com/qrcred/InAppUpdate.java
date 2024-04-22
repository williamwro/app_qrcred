package br.com.qrcred;

import static android.app.Activity.RESULT_CANCELED;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import static com.mikepenz.iconics.Iconics.getApplicationContext;


import android.app.Activity;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;

public class InAppUpdate {

   private final Activity parentActivity;

   private final AppUpdateManager appUpdateManager;

   private final int appUpdateType = AppUpdateType.FLEXIBLE;
   private final int MY_REQUEST_CODE = 10;

   public InAppUpdate(Activity activity) {
      this.parentActivity = activity;
      appUpdateManager = AppUpdateManagerFactory.create(parentActivity);
   }
   // DOWNLOAD FLEXIVEL
   InstallStateUpdatedListener stateUpdatedListener = installState -> {
      if (installState.installStatus() == InstallStatus.DOWNLOADED) {
         popupSnackBarForCompleteUpdate();
      }
   };

   public void checkForAppUpdate() {
      appUpdateManager.getAppUpdateInfo().addOnSuccessListener(info -> {
         boolean isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE;
         boolean isUpdateAllowed = info.isUpdateTypeAllowed(appUpdateType);

         if (isUpdateAvailable && isUpdateAllowed) {
            try {
                /*  appUpdateManager.startUpdateFlowForResult(
                       info,
                       appUpdateType,
                       parentActivity,
                       MY_REQUEST_CODE
               );*/
               appUpdateManager.startUpdateFlowForResult(
                       // Pass the intent that is returned by 'getAppUpdateInfo()'.
                       info,
                       // an activity result launcher registered via registerForActivityResult
                       activityResultLauncher,
                       // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
                       // flexible updates.
                       AppUpdateOptions.newBuilder(appUpdateType).build());
               //AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      });
      appUpdateManager.registerListener(stateUpdatedListener);
   }

   //public ActivityResultLauncher<Intent> activityResultLauncher;
  public ActivityResultLauncher<IntentSenderRequest> activityResultLauncher = registerForActivityResult(
           new ActivityResultContracts.StartActivityForResult(),
           result -> {

                 if (result.getResultCode() == Activity.RESULT_OK) {
                    checkForAppUpdate();

                 }else if (result.getResultCode() == RESULT_CANCELED) {
                       Toast.makeText(getApplicationContext(), "Atualização cancelada pelo usuário", Toast.LENGTH_SHORT).show();
                 }

           });

   private ActivityResultLauncher<IntentSenderRequest> registerForActivityResult(
           ActivityResultContracts.StartActivityForResult startActivityForResult,
           ActivityResultCallback<ActivityResult> atualizaçãoCanceladaPeloUsuário) {
      return activityResultLauncher;

   }


   private void popupSnackBarForCompleteUpdate() {
      Snackbar.make(
              parentActivity.findViewById(R.id.frameUsuarioMenu),
              "Uma atualização acabou de ser baixada!",
              Snackbar.LENGTH_INDEFINITE
      ).setAction(
              "INSTALAR", view -> {
                 if (appUpdateManager != null) {
                    appUpdateManager.completeUpdate();
                 }
              }
      ).show();
   }

   public void onResume() {
      if (appUpdateManager != null) {
         appUpdateManager.getAppUpdateInfo().addOnSuccessListener(info -> {
            if (info.installStatus() == InstallStatus.DOWNLOADED) {
               // DOWNLOAD FLEXIVEL
               popupSnackBarForCompleteUpdate();
            }
         });
      }
   }
  // DOWNLOAD FLEXIVEL
   public void onDestroy() {
      if (appUpdateManager != null) {
         appUpdateManager.unregisterListener(stateUpdatedListener);
      }
   }

}
