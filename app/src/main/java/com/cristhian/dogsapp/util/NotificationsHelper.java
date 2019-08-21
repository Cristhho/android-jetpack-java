package com.cristhian.dogsapp.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.cristhian.dogsapp.R;
import com.cristhian.dogsapp.view.MainActivity;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationsHelper {

    private static final String CHANNEL_ID = "Dogs channel";
    private static final int NOTIFICATION_ID = 123;

    private static NotificationsHelper instance;
    private Context mContext;

    private NotificationsHelper(Context context) {
        mContext = context;
    }

    public static NotificationsHelper getInstance(Context context) {
        if(instance == null) {
            instance = new NotificationsHelper(context);
        }

        return instance;
    }

    public void createNotification() {
        createNotificationChannel();

        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

        Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.dog);

        Notification notification = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.dog_icon)
                .setLargeIcon(icon)
                .setContentTitle("Dogs retrieved")
                .setContentText("This is a notification to let you know that the dog information has been retrieved")
                .setStyle(
                        new NotificationCompat.BigPictureStyle()
                        .bigPicture(icon)
                        .bigLargeIcon(null)
                )
                .setContentIntent(pIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        NotificationManagerCompat.from(mContext).notify(NOTIFICATION_ID, notification);
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = CHANNEL_ID;
            String desc = "Dogs retrieved notifications channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(desc);
            NotificationManager notificationManager = (NotificationManager) mContext
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
