package com.orca.dotz;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.orca.dotz.activities.SalonLists;

import java.util.GregorianCalendar;

/**
 * Created by saurabh on 11/7/16.
 */
public class AlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub


        // here you can start an activity or service depending on your need
        // for ex you can start an activity to vibrate phone or to ring the phone

        // String phoneNumberReciver="9718202185";// phone number to which SMS to be send
        // Show the toast  like in above screen shot
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Toast.makeText(context, "Alarm reached for Tommorrow by rebbot activity", Toast.LENGTH_LONG).show();
            final String MY_PREFS_NAME = "MyPrefsFile";
            SharedPreferences preferences= context.getSharedPreferences(MY_PREFS_NAME, 0);
            int count=preferences.getInt("count",-1);
            for(int i=0;i<count;i++) {
                long time = preferences.getLong("time_"+i, -2);
                long current_time_diffrence= time-new GregorianCalendar().getTimeInMillis();
                Toast.makeText(context,current_time_diffrence+"", Toast.LENGTH_LONG).show();
                if (current_time_diffrence > 0) {
                    Intent intentAlarm = new Intent(context, AlarmReciever.class);
                    intentAlarm.putExtra("Original", true);

                    // create the object
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                    //set the alarm for particular time
                    alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(context, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
                    Toast.makeText(context, "Alarm Scheduled for Tommorrow by rebbot activity", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Toast.makeText(context, "Alarm Triggered and SMS Sent", Toast.LENGTH_LONG).show();
            //Intent intent1 = new Intent(context, MapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //context.startActivity(intent1);
            Intent resultIntent = new Intent(context, MapsActivity.class);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("My notification")
                            .setContentText("Hello World!");

// Because clicking the notification opens a new ("special") activity, there's
// no need to create an artificial back stack.
            PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
// Sets an ID for the notification
            mBuilder.setContentIntent(resultPendingIntent);
            int mNotificationId = 001+(int)(new GregorianCalendar().getTimeInMillis());
// Gets an instance of the NotificationManager service
            NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
// Builds the notification and issues it.
            mNotifyMgr.notify(mNotificationId, mBuilder.build());
            Toast.makeText(context, "Alarm Scheduled for Tommorrow by rebot activity", Toast.LENGTH_LONG).show();



        }
    }

}