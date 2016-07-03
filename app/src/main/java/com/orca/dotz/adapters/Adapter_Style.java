package com.orca.dotz.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.orca.dotz.R;
import com.orca.dotz.fragments.Fragment_style;
import com.orca.dotz.model.HairStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by master on 15/6/16.
 */
public class Adapter_Style extends RecyclerView.Adapter<Adapter_Style.ViewHolder> {
    private static final int GRID_ITEM = -10;
    private static final int LIST_ITEM = -20;
    private Context context;
    private ArrayList<HairStyle> list = new ArrayList<>();
    private int mCurrentViewType = -10;


    public Adapter_Style(Context context, Fragment_style.LayoutManagerType mCurrentLayoutManagerType) {
        this.context = context;
        if (mCurrentLayoutManagerType == Fragment_style.LayoutManagerType.GRID_LAYOUT_MANAGER)
            mCurrentViewType = -10;
        else mCurrentViewType = -20;

    }


    public Adapter_Style(Context context) {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("Adapter", "onCreateViewHolder() called");

        Log.d("viewtype", String.valueOf(viewType));
        View view;
        if (viewType == GRID_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_style_grid_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_style_list_item, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("Adapter", "onBindViewHolder() called");
        if (!list.get(position).getImage().isEmpty())
            Glide.with(context).load(list.get(position).getImage()).into(holder.mImageView);

        if (!list.get(position).getName().isEmpty())
            holder.mTextView.setText(list.get(position).getName());

        if (list.get(position).isLiked()) {
            if (mCurrentViewType == GRID_ITEM)
                holder.fav.setImageResource(R.drawable.ic_favorite_red_18dp);
            else holder.fav.setImageResource(R.drawable.ic_favorite_red_24dp);

            holder.mTextView.setTextColor(Color.parseColor("#ffff4444"));
        } else {
            if (mCurrentViewType == GRID_ITEM)
                holder.fav.setImageResource(R.drawable.ic_favorite_border_black_18dp);
            else holder.fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);

            holder.mTextView.setTextColor(Color.parseColor("#7B1FA2"));
        }


        if (list.get(position).isCart()) {
            if (mCurrentViewType == GRID_ITEM)
                holder.cart.setImageResource(R.drawable.ic_shopping_cart_red_18px);
            else holder.cart.setImageResource(R.drawable.ic_shopping_cart_red_24px);

        } else {
            if (mCurrentViewType == GRID_ITEM)
                holder.cart.setImageResource(R.drawable.ic_add_shopping_cart_black_18dp);
            else holder.cart.setImageResource(R.drawable.ic_add_shopping_cart_black_24dp);

        }

        if (!list.get(position).getNumLikes().equals("NA")) {
            holder.numLikes.setText(list.get(position).getNumLikes());
        } else holder.numLikes.setText("0");
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List payloads) {
        Log.d("Adapter", "onBindViewHolder() payload called");

        if (payloads.isEmpty())
            onBindViewHolder(holder, position);
        if (!payloads.isEmpty()) {
            Log.d("payload", payloads.toString());
            if (payloads.contains(true)) {
                if (mCurrentViewType == GRID_ITEM)
                    holder.fav.setImageResource(R.drawable.ic_favorite_red_18dp);
                else holder.fav.setImageResource(R.drawable.ic_favorite_red_24dp);
                holder.mTextView.setTextColor(Color.parseColor("#ffff4444"));
            } else if (payloads.contains(false)) {
                if (mCurrentViewType == GRID_ITEM)
                    holder.fav.setImageResource(R.drawable.ic_favorite_border_black_18dp);
                else holder.fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }

            if (payloads.contains(1)) {
                if (mCurrentViewType == GRID_ITEM)
                    holder.cart.setImageResource(R.drawable.ic_shopping_cart_red_18px);
                else holder.cart.setImageResource(R.drawable.ic_shopping_cart_red_24px);
            } else if (payloads.contains(0)) {
                if (mCurrentViewType == GRID_ITEM)
                    holder.cart.setImageResource(R.drawable.ic_add_shopping_cart_black_18dp);
                else holder.cart.setImageResource(R.drawable.ic_add_shopping_cart_black_24dp);
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mCurrentViewType;
    }

    public void toggleItemViewType() {
        if (mCurrentViewType == GRID_ITEM) {
            mCurrentViewType = LIST_ITEM;
            notifyDataSetChanged();
        } else {
            mCurrentViewType = GRID_ITEM;
            notifyDataSetChanged();
        }
    }

    public void add(HairStyle value) {
        list.add(value);
        notifyItemInserted(list.size() - 1);
    }


    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public void setFav(String s) {
        boolean UPDATE_LIKE = false;
        for (int i = 0; i < list.size() - 1; i++) {
            if (s.equals(list.get(i).getParent())) {
                Log.d("setFav", "" + s);
                UPDATE_LIKE = true;
                list.get(i).setLiked(true);
                notifyItemChanged(i, UPDATE_LIKE);
                break;
            }
        }

    }

    public void removeFav(String s) {
        boolean UPDATE = true;
        for (int i = 0; i < list.size() - 1; i++) {
            if (s.equals(list.get(i).getParent())) {
                Log.d("removeFav", "" + s);
                UPDATE = false;
                list.get(i).setLiked(false);
                notifyItemChanged(i, UPDATE);
                break;
            }
        }
    }

    public void setCart(String s) {
        int UPDATE = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            if (s.equals(list.get(i).getParent())) {
                Log.d("cartFav", "" + s);
                UPDATE = 1;
                list.get(i).setCart(true);
                notifyItemChanged(i, UPDATE);
                break;
            }
        }
    }

    public void removeCart(String s) {
        int UPDATE = 1;
        for (int i = 0; i < list.size() - 1; i++) {
            if (s.equals(list.get(i).getParent())) {
                Log.d("removeCart", "" + s);
                UPDATE = 0;
                list.get(i).setCart(false);
                notifyItemChanged(i, UPDATE);
                break;
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView fav;
        private final ImageView cart;
        private final TextView numLikes;
        public TextView mTextView;
        public ImageView mImageView;
        private View ItemView;

        public ViewHolder(View itemView) {
            super(itemView);

            ItemView = itemView;
            mImageView = (ImageView) itemView.findViewById(R.id.img);
            fav = (ImageView) itemView.findViewById(R.id.fav);
            cart = (ImageView) itemView.findViewById(R.id.cart);
            numLikes = (TextView) itemView.findViewById(R.id.numLikes);
            mTextView = (TextView) itemView.findViewById(R.id.text);


            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // fav.setImageResource(R.drawable.ic_favorite_red_18dp);

                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (firebaseUser != null) {
                        if (!list.get(getAdapterPosition()).isLiked()) {
                            Map<String, Object> hashMap = new HashMap<>();
                            hashMap.put(list.get(getAdapterPosition()).getParent(), list.get(getAdapterPosition()).getParent());
                            final FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReferenced = database.getReference("User").child(firebaseUser.getUid()).child("fav");
                            databaseReferenced.updateChildren(hashMap);
                            DatabaseReference databaseReferenced1 = database.getReference("TEST").child(list.get(getAdapterPosition()).getParent()).child("numLikes");
                            databaseReferenced1.runTransaction(new Transaction.Handler() {
                                @Override
                                public Transaction.Result doTransaction(MutableData mutableData) {
                                    Log.d("trans", mutableData.getValue().toString());
                                    return null;
                                }

                                @Override
                                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                                }
                            });

                        } else {
                            final FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReferenced = database.getReference("User").child(firebaseUser.getUid())
                                    .child("fav").child(list.get(getAdapterPosition()).getParent());
                            databaseReferenced.removeValue();

                        }
                    }

                }
            });
            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (firebaseUser != null) {
                        if (!list.get(getAdapterPosition()).isCart()) {
                            Map<String, Object> hashMap = new HashMap<>();
                            hashMap.put(list.get(getAdapterPosition()).getParent(), list.get(getAdapterPosition()).getParent());
                            final FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReferenced = database.getReference("User").child(firebaseUser.getUid()).child("cart");
                            databaseReferenced.updateChildren(hashMap);
                            DatabaseReference databaseReferenced1 = database.getReference("TEST").child(list.get(getAdapterPosition()).getParent()).child("numLikes");
                            databaseReferenced1.runTransaction(new Transaction.Handler() {
                                @Override
                                public Transaction.Result doTransaction(MutableData mutableData) {
                                    Log.d("trans", mutableData.getValue().toString());
                                    return null;
                                }

                                @Override
                                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                                }
                            });

                        } else {
                            final FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReferenced = database.getReference("User").child(firebaseUser.getUid())
                                    .child("cart").child(list.get(getAdapterPosition()).getParent());
                            databaseReferenced.removeValue();

                        }
                    }
                }
            });

        }
    }


}
