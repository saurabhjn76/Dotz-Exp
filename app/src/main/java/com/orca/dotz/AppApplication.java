package com.orca.dotz;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by master on 12/6/16.
 */
public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("TEST");
        DatabaseReference dataRef1 = FirebaseDatabase.getInstance().getReference("SalonData");
        dataRef.keepSynced(true);
        dataRef1.keepSynced(true);



    }
}
