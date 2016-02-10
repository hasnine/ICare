package com.newfeds.icare.BroadcastReceivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import com.newfeds.icare.R;
import com.newfeds.icare.helper.L;
import com.newfeds.icare.services.OnBootService;

/**
 * Created by GT on 1/21/2016.
 */
public class StartIcareAtBootReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            L.log("Boot received");
            Intent intentNotification = new Intent(context, NotificationManager.class);
            PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
            Notification n  = new Notification.Builder(context)
                    .setContentTitle("Icare")
                    .setContentText("Icare boot test")
                    .setSmallIcon(R.drawable.ic_action_alarm)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true).build();


            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, n);

            Intent myServiceIntent = new Intent(context, OnBootService.class);
            context.startService(myServiceIntent);
        }
    }
}
