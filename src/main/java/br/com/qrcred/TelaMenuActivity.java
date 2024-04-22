package br.com.qrcred;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.pm.PackageInfoCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.FirebaseApp;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import br.com.qrcred.databinding.ActivityTelaMenuBinding;

import android.Manifest;

/*

O App utiliza sua localizaçao para disponibilizar o melhor conteudo baseado na sua regiao.

Permita o acesso de sua localizaçao na proxima tela para ficar informado das atualizaçoes do App

 */
public class TelaMenuActivity extends AppCompatActivity {

    FrameLayout ViwAssociado;
    FrameLayout ViwConvenio;

    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private AppBarConfiguration appBarConfiguration;
    //private ViewDataBinding binding;
    ActivityTelaMenuBinding activityTelaMenuBinding;
    AppUpdateManager appUpdateManager;

    private static final int MY_REQUEST_CODE = 10;
    private static final int REQ_CODE_VERSION_UPDATE = 530;
    private static final String TAG = "Sample";
    private br.com.qrcred.libupdate.InAppUpdateManager inAppUpdateManager;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_menu);
        FirebaseApp.initializeApp(this);
        activityTelaMenuBinding = ActivityTelaMenuBinding.inflate(getLayoutInflater());

        setContentView(activityTelaMenuBinding.getRoot());
        //inAppUpdate = new InAppUpdate(TelaMenuActivity.this);
        //inAppUpdate.checkForAppUpdate();

        ViwAssociado = findViewById(R.id.frameUsuarioMenu);
        ViwConvenio = findViewById(R.id.frameConvenioMenu);


        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbarmenu1);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        //toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_theme_light_onPrimary));
        Objects.requireNonNull(getSupportActionBar()).setTitle("QRCRED");

        Map<String, Object> defalutRate = new HashMap<>();
        defalutRate.put("version", String.valueOf(getVersionCode()));

        int versionCode = BuildConfig.VERSION_CODE;
        String versionCodex = String.valueOf(versionCode);
        String versao_ = "versão : " + versionCodex;
        activityTelaMenuBinding.TvPVersao.setText(versao_);
        defalutRate.put(UpdateHelper.KEY_UPDATE_ENABLE, false);
        defalutRate.put(UpdateHelper.KEY_UPDATE_VERSION, versionCodex);
        defalutRate.put(UpdateHelper.KEY_UPDATE_URL, "https://play.google.com/store/apps/details?id=br.com.qrcred");

        activityTelaMenuBinding.frameUsuarioMenu.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Activity_tela_User.class);
            startActivity(intent);
        });
        activityTelaMenuBinding.frameConvenioMenu.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Menu2MainActivity.class);
            startActivity(intent);
        });
        activityTelaMenuBinding.TvPrivacidade.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PoliticaPrivacideActivity.class);
            startActivity(intent);
        });

        askNotificationPermission();
        checkForAppUpdate();

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
    public PackageInfo pInfo;

    @SuppressWarnings({"unchecked", "deprecation"})
    public int getVersionCode() {
        pInfo = null;
        int versionCode = 0;
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.PackageInfoFlags.of(0));
            } else {

                pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            }


            long longVersionCode = PackageInfoCompat.getLongVersionCode(pInfo);
            versionCode = (int) longVersionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.i("MYLOG", "Name not found Exption" + e.getMessage());
        }

        return versionCode;
    }

    @Override
    protected void onResume() {
        super.onResume();

        //inAppUpdate.onResume();
    }

    // DOWNLOAD FLEXIVEL
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //inAppUpdate.onDestroy();
    }

    private void checkForAppUpdate() {
        appUpdateManager = AppUpdateManagerFactory.create(this);

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {

                    AestheticDialog.Builder dialog1 =
                            new AestheticDialog.Builder(TelaMenuActivity.this,
                                    DialogStyle.FLAT, DialogType.INFO);
                    dialog1.setTitle("Atualização disponível!");
                    dialog1.setMessage("Recomendamos primeiro que desinstale a versão atual e depois instale a nova versão na PlayStore!");
                    dialog1.setCancelable(false);
                    dialog1.setDarkMode(true);
                    dialog1.setGravity(Gravity.CENTER);
                    dialog1.setAnimation(DialogAnimation.SHRINK);
                    dialog1.setOnClickListener(new OnDialogClickListener() {
                        @Override
                        public void onClick(@NonNull AestheticDialog.Builder builder) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=br.com.qrcred"));
                            intent.setPackage("com.android.vending");
                            startActivity(intent);
                            builder.dismiss();

                        }
                    });
                    dialog1.show();
            }
        });
    }
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }
}
