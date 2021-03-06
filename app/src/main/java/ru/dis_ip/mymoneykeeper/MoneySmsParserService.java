package ru.dis_ip.mymoneykeeper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;

/**
 * Created by ilia on 14.11.17.
 */

public class MoneySmsParserService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Bundle extras = intent.getExtras();
        if (extras != null) {
            String sms_body = extras.getString("sms_body");
            showNotification(sms_body);
            return START_STICKY;
        } else {
            return START_NOT_STICKY;
        }
    }

    private void showNotification(String text) {
        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MoneyViewActivity.class), 0);

        // Set the info for the views that show in the notification panel.
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)  // the status icon
                .setTicker(text)  // the status text
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentTitle(getResources().getString(R.string.content_title))  // the label of the entry
                .setContentText(text)  // the contents of the entry
                .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
                .build();

        NotificationManager mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        // Send the notification.
        mNM.notify(R.mipmap.ic_launcher, notification);

        SharedPreferences sharedPref = getSharedPreferences("store_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("data_1", 555);
        editor.apply();
    }
}
