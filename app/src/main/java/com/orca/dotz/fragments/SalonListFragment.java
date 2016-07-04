package com.orca.dotz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
      //  TextView rating = (TextView) view.findViewById(R.id.rating);
        ImageView salonImage = (ImageView) view.findViewById(R.id.image);

        salonName.setText(salonData.getName());
        //rating.setText(String.valueOf(salonData.getRating().floatValue()));
        totalPrice.setText("Rs. " + salonData.getPrice() + "/-");
        Glide.with(getContext()).load(salonData.getImage()).into(salonImage);
    }
}
