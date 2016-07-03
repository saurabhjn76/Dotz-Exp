package com.orca.dotz.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orca.dotz.R;
import com.orca.dotz.customviews.ScaleOutPageTransformer;
import com.orca.dotz.fragments.SalonListFragment;
import com.orca.dotz.model.Salon;
import com.pixplicity.multiviewpager.MultiViewPager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SalonLists extends AppCompatActivity {


    final List<Salon> salonListData = new ArrayList<>();
    ArrayList<String> cartArray = new ArrayList<>();
    List<List<String>> listTofilterList = new ArrayList<>();
    private SalonPagerAdapter mPagerAdapter;
    private List<HashMap<String, Long>> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_lists);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cartArray.add("0");
        cartArray.add("1");

        Log.d("cartArray", String.valueOf(cartArray));

        list = new ArrayList<>();
        for (int i = 0; i < cartArray.size(); i++) {
            list.add(new HashMap<String, Long>());
        }


        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("TEST2");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.d("data", dataSnapshot.getValue().toString());
                for (DataSnapshot styleNodes : dataSnapshot.getChildren()) {
                    // Log.d("styles", styleNodes.getKey());
                    if (cartArray.contains(styleNodes.getKey())) {
                        for (int i = 0; i < cartArray.size(); i++) {
                            if (styleNodes.getKey().equals(cartArray.get(i))) {
                                List<String> tempArrayList = new ArrayList<String>();
                                for (DataSnapshot children : styleNodes.getChildren()) {
                                    tempArrayList.add(children.getKey());
                                    // Log.d("child", children.toString());
                                    list.get(i).put(children.getKey(), (Long) children.getValue());
                                }
                                listTofilterList.add(tempArrayList);
                            }
                        }
                    }
                }


                for (int i = 0; i < cartArray.size(); i++) {
                    Log.d("hmap" + i, String.valueOf(list.get(i)));

                }

                if (getCommonElements(listTofilterList) != null) {
                    Log.d("filteredSalons", String.valueOf(getCommonElements(listTofilterList)));
                    getSalonData(getCommonElements(listTofilterList));
                } else throw new IllegalArgumentException("No barbers supported");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    

    private void getSalonData(Set<String> commonElements) {
        final List<String> salonList = new ArrayList<>();
        salonList.addAll(commonElements);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference salonData = firebaseDatabase.getReference("SalonData");
        salonData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot salon : dataSnapshot.getChildren()) {
                        if (salonList.contains(salon.getKey())) {
                            Log.d("Salons", salon.toString());
                            Salon salonModel = salon.getValue(Salon.class);
                            salonModel.setParent(salon.getKey());

                            int totalPrice = 0;
                            for (int i = 0; i < list.size(); i++) {
                                totalPrice = totalPrice + list.get(i).get(salon.getKey()).intValue();
                            }
                            salonModel.setPrice(totalPrice);
                            salonListData.add(salonModel);

                        }
                    }

                    bindSalonDatatoUI();

                } else throw new IllegalArgumentException("No data found on SalonData node");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static <T> Set<T> getCommonElements(Collection<? extends Collection<T>> collections) {

        Set<T> common = new LinkedHashSet<T>();
        if (!collections.isEmpty()) {
            Iterator<? extends Collection<T>> iterator = collections.iterator();
            common.addAll(iterator.next());
            while (iterator.hasNext()) {
                common.retainAll(iterator.next());
            }
        }
        return common;
    }

    private void bindSalonDatatoUI() {

        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        int pxMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());

        MultiViewPager viewPager = (MultiViewPager) findViewById(R.id.viewPagerSalonList);
        mPagerAdapter = new SalonPagerAdapter(getSupportFragmentManager());
        // viewPager.setClipToPadding(false);
        viewPager.setPageTransformer(true, new ScaleOutPageTransformer());
        // viewPager.setPadding(px, 0, px, 0);
        //  viewPager.setOffscreenPageLimit(3);
        //viewPager.setPageMargin(pxMargin);
        viewPager.setAdapter(mPagerAdapter);
    }

    private class SalonPagerAdapter extends FragmentStatePagerAdapter {
        public SalonPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new SalonListFragment(salonListData.get(position));
        }

        @Override
        public int getCount() {
            return salonListData.size();
        }


    }

}
