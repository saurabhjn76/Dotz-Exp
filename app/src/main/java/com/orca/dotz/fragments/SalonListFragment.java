package com.orca.dotz.fragments;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.orca.dotz.AlarmReciever;
import com.orca.dotz.MapsActivity;
import com.orca.dotz.R;
import com.orca.dotz.model.Salon;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by master on 28/6/16.
 */
public class SalonListFragment extends Fragment {

    private Salon salonData;

    public SalonListFragment() {

    }

    public SalonListFragment(Salon salon) {
        this.salonData = salon;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_salon_card, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        TextView salonName = (TextView) view.findViewById(R.id.name);
        TextView totalPrice = (TextView) view.findViewById(R.id.totalPrice);
        TextView distance = (TextView) view.findViewById(R.id.distance);
        ImageView salonImage = (ImageView) view.findViewById(R.id.image);
        Button selectSalon = (Button) view.findViewById(R.id.select_salon);

        Typeface face = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/quicksand/QuicksandBold.ttf");
        salonName.setTypeface(face);
        salonName.setText(salonData.getName());
        //rating.setText(String.valueOf(salonData.getRating().floatValue()));
        totalPrice.setText("₹" + salonData.getPrice());
        totalPrice.setTypeface(face);
        distance.setTypeface(face);
        distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                getActivity().startActivity(intent);
            }
        });
        selectSalon.setTypeface(face);
        selectSalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//  todo implement using asyncqueryhandler

               /* long calID = 1;
                long startMillis = 0;
                long endMillis = 0;
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(2016, 6, 10, 22, 40);
                startMillis = beginTime.getTimeInMillis();
                Calendar endTime = Calendar.getInstance();
                endTime.set(2016, 6, 10, 22, 50);
                endMillis = endTime.getTimeInMillis();
                ContentResolver cr = getContext().getContentResolver();
                ContentValues values = new ContentValues();
                values.put(CalendarContract.Events.DTSTART, startMillis);
                values.put(CalendarContract.Events.DTEND, endMillis);
                values.put(CalendarContract.Events.TITLE, "Salon Appointment");
                values.put(CalendarContract.Events.DESCRIPTION, "This is a reminder that you have an appointment with affnity salon after 60 minutes at 3pm today.");
                values.put(CalendarContract.Events.EVENT_LOCATION, "salon address.");
                values.put(CalendarContract.Events.CALENDAR_ID, calID);
                TimeZone tz = TimeZone.getDefault();
                values.put(CalendarContract.Events.EVENT_TIMEZONE,tz.getID());
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
                Toast.makeText(getContext(), "Event Added successfully", Toast.LENGTH_LONG).show();
                long eventID = Long.parseLong(uri.getLastPathSegment());

                ContentValues valuesR = new ContentValues();
                valuesR.put(CalendarContract.Reminders.MINUTES, 15);
                valuesR.put(CalendarContract.Reminders.EVENT_ID, eventID);
                valuesR.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                Uri uriR = cr.insert(CalendarContract.Reminders.CONTENT_URI, valuesR);

                Toast.makeText(getContext(), "Reminder Added successfully", Toast.LENGTH_LONG).show();
*/
                //NotificationManager notificationManager = (NotificationManager)getContext().getSystemService(getContext().NOTIFICATION_SERVICE);

// get the event ID that is the last element in the Uri
                Long[] time = new Long[2];
                time[0] = new GregorianCalendar(2016, 6, 11, 19, 51).getTimeInMillis();
                time[1] = new GregorianCalendar(2016, 6, 11, 19, 53).getTimeInMillis();
                final String MY_PREFS_NAME = "MyPrefsFile";
                SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_NAME, 0).edit();
                editor.putInt("count", time.length);
                for (int i = 0; i < time.length; i++) {
                    editor.putLong("time_" + i, time[i]);
                    editor.commit();

                    // create an Intent and set the class which will execute when Alarm triggers, here we have
                    // given AlarmReciever in the Intent, the onRecieve() method of this class will execute when
                    // alarm triggers and
                    //we will write the code to send SMS inside onRecieve() method pf Alarmreciever class
                    long current_time_diffrence = time[i] - new GregorianCalendar().getTimeInMillis();
                    Toast.makeText(getContext(), current_time_diffrence + "", Toast.LENGTH_LONG).show();
                 //   if (current_time_diffrence > 0) {
                    {  Intent intentAlarm = new Intent(getActivity(), AlarmReciever.class);
                        intentAlarm.putExtra("Original", true);

                        // create the object
                        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

                        //set the alarm for particular time
                        alarmManager.set(AlarmManager.RTC_WAKEUP, time[i], PendingIntent.getBroadcast(getContext(), i, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
                        Toast.makeText(getContext(), "Alarm Scheduled for Tommorrow", Toast.LENGTH_LONG).show();

                    }
                }
            }

        });

        Glide.with(getContext()).load(salonData.getImage()).into(salonImage);
    }
}
