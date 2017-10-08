package io.trollingninjas.status200_android_client.Firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import io.trollingninjas.status200_android_client.R;
import io.trollingninjas.status200_android_client.View.MainActivity;


public class CustomMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.i("TAG", "onMessageReceived: " + remoteMessage.getData().get("title"));

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        Notification n  = new Notification.Builder(this)
                .setSmallIcon(R.drawable.globe)
                .setContentTitle("[Urgent] Alert")
                .setContentText("Your droplet has unexpectedly stopped.")
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();


        notificationManager.notify(0, n);
    }
}
