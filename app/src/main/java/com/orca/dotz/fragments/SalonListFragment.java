package com.orca.dotz.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orca.dotz.MapsActivity;
import com.orca.dotz.R;
import com.orca.dotz.model.Salon;

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
        Button selectSalon =(Button)view.findViewById(R.id.select_salon);

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

        Glide.with(getContext()).load(salonData.getImage()).into(salonImage);
    }
}
