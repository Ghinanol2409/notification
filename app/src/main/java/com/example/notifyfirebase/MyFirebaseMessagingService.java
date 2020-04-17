package com.example.notifyfirebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import androidx.core.app.NotificationCompat;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        Map<String, String> extraData = remoteMessage.getData();

        String brandID = extraData.get("brandID");
        String Category = extraData.get("Category");

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this,"TAG")
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_launcher_background);

        Intent intent;
        if (Category.equals("SHoes")){
            intent = new Intent(this, ReceiverNotificationActivity.class);
            intent.putExtra("brandID", brandID);
            intent.putExtra("Category",Category);
        } else {
            intent = new Intent(this, ReceiverNotificationActivity.class);
        }
        intent.putExtra("brandID", brandID);
        intent.putExtra("Category",Category);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,10,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        int id = (int) System.currentTimeMillis();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("TAG","Demo",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(id,notificationBuilder.build());
    }
}
