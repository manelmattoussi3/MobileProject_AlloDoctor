package tn.esprit.doctormobile.Helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import tn.esprit.doctormobile.R;

public class BroadCastRemind  extends BroadcastReceiver {
//Notification bch tebt3ath lel application
    //BROADCASTRECEVIER bch tjini 7aja men 5arej app ta3i
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = "time is up to your visit to doctor";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context, "book_notification")//id notification
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)//icon notification
                .setContentTitle("New Notification")//title notif
                .setContentText(message)//description
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);//incrementation des notifications

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);//win bch naffichia
        notificationManager.notify(200, builder.build());//bch n'execution

    }
}

