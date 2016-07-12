package com.orca.dotz.activities;


import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.orca.dotz.R;
import com.orca.dotz.customviews.ScaleOutPageTransformer;
import com.orca.dotz.customviews.VerticalViewPager;
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
    int clicK_count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_lists);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_view_list_white_24px);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"white\">" +"Select Salon" + "</font>"));
      //  this.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        FirebaseMessaging.getInstance().subscribeToTopic("test");
        String token=FirebaseInstanceId.getInstance().getToken();
       // Toast.makeText(getApplicationContext(),token,Toast.LENGTH_LONG).show();
        Log.e("firebase","Token: "+ token);
       // Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();



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
                                    Log.d("child", children.toString());
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
    public  void animate(View v)
    {
        /*if(clicK_count==0)
        {

            invalidateOptionsMenu();
           // clicK_count=1;
        }
        else
        {
            invalidateOptionsMenu();
            //clicK_count=0;
        }*/
       // invalidateOptionsMenu();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);

       /* if(clicK_count==0){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            final MenuItem item = menu.findItem(R.id.action_search);
            final MenuItem item1 = menu.findItem(R.id.action_cart);
            final MenuItem item2 = menu.findItem(R.id.action_fav);




            // Post delayed so the view can be built,
            // otherwise findViewById(R.id.menu_filter) would be null
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
                    animation.setDuration(1000);

                    // Make item visible and start the animation
                    item.setVisible(true);
                    item1.setVisible(true);
                    item2.setVisible(true);
                    findViewById(R.id.action_search).startAnimation(animation);
                    findViewById(R.id.action_fav).startAnimation(animation);
                    findViewById(R.id.action_cart).startAnimation(animation);


                }
            }, 1);
            clicK_count=1;
        } else{
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
          //  getMenuInflater().inflate(R.menu.menu_list, menu);
            final MenuItem item = menu.findItem(R.id.action_search);
            final MenuItem item1 = menu.findItem(R.id.action_cart);
            final MenuItem item2 = menu.findItem(R.id.action_fav);
            *//*item.setVisible(true);
            item1.setVisible(true);
            item2.setVisible(true);
*//*
            // Post delayed so the view can be built,
            // otherwise findViewById(R.id.menu_filter) would be null
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    AlphaAnimation animation = new AlphaAnimation(0.1f, 0.0f);
                    animation.setFillEnabled(true);
                    animation.setFillAfter(true);
                    animation.setDuration(1000);

                    // start the animation
                    findViewById(R.id.action_search).startAnimation(animation);
                    findViewById(R.id.action_fav).startAnimation(animation);
                    findViewById(R.id.action_cart).startAnimation(animation);
                }
            }, 1);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    item.setVisible(false);
                    item1.setVisible(false);
                    item2.setVisible(false);
                }
            }, 1000); // The animation is finished after 1000ms
            clicK_count=0;
        }*/
        return true;

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
                            Salon salonModel = salon.getValue(Salon.class);
                            salonModel.setParent(salon.getKey());

                            int totalPrice = 0;
                            for (int i = 0; i < list.size(); i++) {
                                Log.d("List",salon.getKey().toString());
                                totalPrice = totalPrice + list.get(i).get(salon.getKey()).intValue();
                            }
                            salonModel.setPrice(totalPrice);
                            salonListData.add(salonModel);

                        }
                    }

                    bindSalonDatatoUI();
                    getSupportActionBar().setTitle(Html.fromHtml("<font color=\"white\">" +salonListData.get(0).getName() + "</font>"));

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

       VerticalViewPager viewPager = (VerticalViewPager) findViewById(R.id.viewPagerSalonList);
        final Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);

        mPagerAdapter = new SalonPagerAdapter(getSupportFragmentManager());
        // viewPager.setClipToPadding(false);
       // viewPager.setPageTransformer(true, new ScaleOutPageTransformer());
        // viewPager.setPadding(px, 0, px, 0);
        //  viewPager.setOffscreenPageLimit(3);
        //viewPager.setPageMargin(pxMargin);
        viewPager.setAdapter(mPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Toast.makeText(getApplicationContext(),"dfdsfsd",Toast.LENGTH_LONG).show();
                getSupportActionBar().setTitle(Html.fromHtml("<font color=\"white\">" +salonListData.get(position).getName() + "</font>"));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
    }

    private class SalonPagerAdapter extends FragmentStatePagerAdapter  {
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
    public HashMap<String, Long> getPriceList(int position)
    {
        return list.get(position);
    }

}
