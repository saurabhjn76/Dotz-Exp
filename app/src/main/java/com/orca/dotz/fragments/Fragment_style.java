package com.orca.dotz.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orca.dotz.R;
import com.orca.dotz.activities.Cart;
import com.orca.dotz.activities.HomeActivity;
import com.orca.dotz.adapters.Adapter_Style;
import com.orca.dotz.dialogs.FilterDialog;
import com.orca.dotz.interfaces.ChangeViewListener;
import com.orca.dotz.model.HairStyle;

/**
 * Created by master on 17/6/16.
 */
public class Fragment_style extends Fragment implements FilterDialog.OnDialogClickedListener {

    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final String TAG = "FragmentService";
    private final int SPAN_COUNT = 2;
    public String previousCode = "NANANA";
    ChangeViewListener changeViewListener;
    private Context context = getActivity();
    private int category = -1;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ValueEventListener valueEventListener;
    private LinearLayoutManager mLayoutManager;
    private LayoutManagerType mCurrentLayoutManagerType;
    private Button switch2;
    private Adapter_Style adapterStyle;
    private TextView filter;
    private TextView cart;
    private TextView filterBar;
    private TextView filterBarClear;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    private DatabaseReference myRef = database.getReference("TEST");
    private ChildEventListener favDataListener;
    private ChildEventListener styleDataListener;
    private ChildEventListener childlistenerfordata;
    private ValueEventListener valueListener;
    private ChildEventListener cartDataListener;


    public Fragment_style() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d("frag" + category, "onCreateView() called");

        this.context = inflater.getContext();
        View rootview = inflater.inflate(R.layout.item_fragment, container, false);
        rootview.setTag(TAG);

        Bundle bundle = getArguments();
        this.category = bundle.getInt("position");

        filterBar = (TextView) rootview.findViewById(R.id.showfiltersbar);
        filterBarClear = (TextView) rootview.findViewById(R.id.clearall);
        filter = (TextView) rootview.findViewById(R.id.filter);
        cart = (TextView) rootview.findViewById(R.id.cart);

        setclicklistners();
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerView);

        mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }

        Log.d("frag" + category, "layoutmanager" + mCurrentLayoutManagerType.toString());

        setRecyclerViewAttr();

        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d("frag" + category, "onViewCreated() called");


        if (category == 0) {

            styleDataListener = initStyleDataListener();
            favDataListener = initFavDataListener();
            cartDataListener = initCartDataListener();

            adapterStyle = new Adapter_Style(context, mCurrentLayoutManagerType);
            setmyLayoutManager(mCurrentLayoutManagerType);

            recyclerView.setAdapter(adapterStyle);


            if (FilterDialog.hairLength.equals("NA") && FilterDialog.hairQuality.equals("NA") && FilterDialog.faceCut.equals("NA"))
                initQueryforData(styleDataListener, favDataListener);
            else
                onDialogClicked(FilterDialog.hairLength, FilterDialog.hairQuality, FilterDialog.faceCut);

        }

    }


    private void initQueryforData(ChildEventListener styleDataListener, final ChildEventListener favDataListener) {
        final Query query = myRef;
        query.addChildEventListener(styleDataListener);

        valueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("datavalue", "we are done with loading data");
                initQueryforFav();
                initQueryforCart();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        query.addValueEventListener(valueListener);
    }


    private void initQueryforFav() {
        Query favquery = userRef.child("fav");
        favquery.addChildEventListener(favDataListener);
        favquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("favvalue", "we are done with loading fav");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initQueryforCart() {
        Query favquery = userRef.child("cart");
        favquery.addChildEventListener(cartDataListener);
        favquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("cartvalue", "we are done with loading cart");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private ChildEventListener initFavDataListener() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("fav item added", dataSnapshot.getValue().toString());
                adapterStyle.setFav(dataSnapshot.getValue().toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("fav removed", dataSnapshot.getValue().toString());
                adapterStyle.removeFav(dataSnapshot.getValue().toString());

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    private ChildEventListener initCartDataListener() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("cart item added", dataSnapshot.getValue().toString());
                adapterStyle.setCart(dataSnapshot.getValue().toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("cart item removed", dataSnapshot.getValue().toString());
                adapterStyle.removeCart(dataSnapshot.getValue().toString());

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }


    private ChildEventListener initStyleDataListener() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
                if (!dataSnapshot.getValue().toString().isEmpty()) {
                    HairStyle hairStyle = dataSnapshot.getValue(HairStyle.class);
                    hairStyle.setParent(dataSnapshot.getKey());
                    adapterStyle.add(hairStyle);

                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("child", "changed");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("child", "rem");

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d("child", "moved");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    public void setLayoutManagerNow() {
        if (mCurrentLayoutManagerType == LayoutManagerType.GRID_LAYOUT_MANAGER) {
            setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER);
            mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        } else {
            setRecyclerViewLayoutManager(LayoutManagerType.GRID_LAYOUT_MANAGER);
            mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
        }
    }

    public void setmyLayoutManager(LayoutManagerType mCurrentLayoutManagerType) {
        switch (mCurrentLayoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                break;

        }
        mLayoutManager.setRecycleChildrenOnDetach(true);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        if (recyclerView.getLayoutManager() != null) {
            if (mCurrentLayoutManagerType == LayoutManagerType.LINEAR_LAYOUT_MANAGER)
                scrollPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                        .findFirstVisibleItemPosition();
            else
                scrollPosition = ((GridLayoutManager) recyclerView.getLayoutManager())
                        .findFirstVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                adapterStyle.toggleItemViewType();

                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                adapterStyle.toggleItemViewType();
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mLayoutManager.setRecycleChildrenOnDetach(true);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.scrollToPosition(scrollPosition);

        Log.d("scroll", String.valueOf(scrollPosition));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d("frag" + category, "onSaveInstanceState() called");
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onDialogClicked(final String hairLength, String hairQuality, String faceCut) {
        StringBuilder code = new StringBuilder();

        if (!hairLength.equals("NA")) {
            code.append(hairLength);
        }
        if (!hairQuality.equals("NA")) {
            code.append(hairQuality);
        }
        if (!faceCut.equals("NA"))
            code.append(faceCut);
        if (hairLength.equals("NA") && hairQuality.equals("NA") && faceCut.equals("NA"))
            code.append("NANANA");

        Log.d("filterCode", code.toString());
        Log.d("previousCode", previousCode.toString());
        if (!code.toString().isEmpty())
            if (!previousCode.equals(code.toString())) {
                previousCode = code.toString();
                adapterStyle.clear();
                filterBar.setVisibility(View.VISIBLE);
                filterBarClear.setVisibility(View.VISIBLE);
                filterBar.setText(setfilterBartext(hairLength, hairQuality, faceCut).toString());


                Query query;
                if (!code.toString().equals("NANANA"))
                    query = myRef.orderByChild(code.toString()).equalTo("true");
                else {
                    query = myRef;
                    filterBar.setVisibility(View.GONE);
                    filterBarClear.setVisibility(View.GONE);
                }
                childlistenerfordata = new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (!dataSnapshot.getValue().toString().isEmpty()) {

                            HairStyle hairStyle = dataSnapshot.getValue(HairStyle.class);
                            hairStyle.setParent(dataSnapshot.getKey());
                            // hairStyle.setLiked(liked);
                            adapterStyle.add(hairStyle);

                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                query.addChildEventListener(childlistenerfordata);
                initQueryforFav();
            }
    }

    private StringBuilder setfilterBartext(String hairLength, String hairQuality, String faceCut) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Filters- ");
        if (!hairLength.equals("NANANA")) {
            if (hairLength.equals(FilterDialog.HAIRLENGTH_SMALL))
                stringBuilder.append("Length: small ");
            else if (hairLength.equals(FilterDialog.HAIRLENGTH_MEDIUM))
                stringBuilder.append("Length: medium ");
            else if (hairLength.equals(FilterDialog.HAIRLENGTH_LARGE))
                stringBuilder.append("Length: large ");

        }
        if (!hairQuality.equals("NANANA")) {
            if (hairQuality.equals(FilterDialog.HAIRQUALITY_SOFT))
                stringBuilder.append("Quality: soft ");
            else if (hairQuality.equals(FilterDialog.HAIRQUALITY_HARD))
                stringBuilder.append("Quality: hard ");

        }
        if (!faceCut.equals("NANANA")) {
            if (faceCut.equals(FilterDialog.FACECUT_OBLONG))
                stringBuilder.append("FaceCut: oblong");
            else if (faceCut.equals(FilterDialog.FACECUT_DIAMOND))
                stringBuilder.append("FaceCut: diamond");
            else if (faceCut.equals(FilterDialog.FACECUT_HEART))
                stringBuilder.append("FaceCut: heart");

        }
        return stringBuilder;


    }

    private void setclicklistners() {
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category == 0)
                    showFilterDialog();

            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "Not implemented yet", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context, Cart.class));
            }
        });


        filterBarClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogClicked("NA", "NA", "NA");
                FilterDialog.hairLength = "NA";
                FilterDialog.hairQuality = "NA";
                FilterDialog.faceCut = "NA";
            }
        });
    }

    private void showFilterDialog() {
        FragmentManager fm = getFragmentManager();
        FilterDialog filterDialog = new FilterDialog();


        filterDialog.setTargetFragment(Fragment_style.this, 300);
        filterDialog.show(fm, "fragment_filter");


    }

    private void setRecyclerViewAttr() {
        recyclerView.hasFixedSize();
        recyclerView.setItemViewCacheSize(6);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setRecycledViewPool(HomeActivity.recycledViewPool);
        Log.d("rec Pool", String.valueOf(recyclerView.getRecycledViewPool()));
        recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                Log.d("viewRecycled", Integer.toString(holder.getLayoutPosition()));
                ImageView img = (ImageView) holder.itemView.findViewById(R.id.img);
                if (img.getDrawable() != null)
                    img.setImageDrawable(null);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("frag" + category, "onDetach() called");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("frag" + category, "onStart() called");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("frag" + category, "onStop() called");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("frag" + category, "onDestroy() called");
        if (favDataListener != null)
            myRef.removeEventListener(favDataListener);
        if (styleDataListener != null)

            myRef.removeEventListener(styleDataListener);
        if (childlistenerfordata != null)

            myRef.removeEventListener(childlistenerfordata);
        if (valueEventListener != null)

            myRef.removeEventListener(valueEventListener);
    }

    public static enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }
}
