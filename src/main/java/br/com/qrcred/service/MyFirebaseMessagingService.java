package br.com.qrcred.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

import br.com.qrcred.R;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String title, message, receiver;
    public static final String NOTIFICATION_CHANNEL_ID = "br.com.qrcred";


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //String channerID = "CHANNEL_ID_NOTIFICATION";
        title=remoteMessage.getData().get("Title");
        message=remoteMessage.getData().get("Message");
        /*NotificationCompat.Builder builder = new
                NotificationCompat.Builder(getApplicationContext(),channerID);

                    builder.setSmallIcon(R.drawable.logo)
                    .setContentText(message);
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.notify(0,builder.build());

       /* Map<String, String> params = remoteMessage.getData();
        JSONObject object = new JSONObject(params);
        Log.e("JSON_OBJECT", object.toString());*/



       long[] pattern = {0, 1000, 500, 1000};

         NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Your Notifications",
                    NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription("Mensagem principal");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(pattern);
            notificationChannel.enableVibration(true);

            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel);
        }

        // to diaplay notification in DND Mode
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = mNotificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID);
            channel.canBypassDnd();
        }*/

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)

                .setContentTitle(getString(R.string.app_name))
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setVibrate(pattern)
                .setSmallIcon(R.drawable.logo)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message)
                        .setBigContentTitle(title)
                        .setSummaryText("convenios"))
                .setAutoCancel(true);
        Objects.requireNonNull(mNotificationManager).notify(1000, notificationBuilder.build());
    }
}
