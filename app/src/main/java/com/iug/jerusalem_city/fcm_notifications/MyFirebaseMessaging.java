package com.iug.jerusalem_city.fcm_notifications;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.iug.jerusalem_city.R;
import com.iug.jerusalem_city.ui.main.MainActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

public class MyFirebaseMessaging extends FirebaseMessagingService {

    String TAG = "MyFirebaseMessagingService";
    private final String CHANNEL_NAME = "Channel One";
    private final String notification_channel_id = "notification_channel_id";
    private final int importance = NotificationManager.IMPORTANCE_DEFAULT;
    private String imageUrl = null;

    @SuppressLint("CommitPrefEdits")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            try {

                if (remoteMessage.getNotification().getImageUrl() != null) {
                    imageUrl = remoteMessage.getNotification().getImageUrl().toString();
                }

                if (imageUrl != null) {
                    notification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(),
                            getBitmapfromUrl(imageUrl));

                } else {
                    notification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(),
                            null);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void notification(String title, String body, Bitmap imgBitmap) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(notification_channel_id,
                    CHANNEL_NAME, importance);
            channel.setDescription("Description");
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            channel.setVibrationPattern(new long[]{1000, 200, 400, 500, 700, 250});
            channel.enableLights(true);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(getBaseContext(), MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), notification_channel_id);
        builder.setSmallIcon(R.drawable.ic_dome_of_the_rock);
        builder.setColor(getResources().getColor(R.color.colorNotification));
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        if (imgBitmap != null) {
            builder.setLargeIcon(imgBitmap);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());

    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }

}
