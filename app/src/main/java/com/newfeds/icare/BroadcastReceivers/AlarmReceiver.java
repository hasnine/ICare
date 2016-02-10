package com.newfeds.icare.BroadcastReceivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.newfeds.icare.R;
import com.newfeds.icare.helper.L;


/**
 * Created by GT on 1/23/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        L.log("Alarm recived");

        Notification n  = new Notification.Builder(context)
                .setContentTitle("Icare")
                .setContentText("Icare  test")
                .setSmallIcon(R.drawable.ic_action_alarm)
                .setAutoCancel(true).build();


        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }

}
