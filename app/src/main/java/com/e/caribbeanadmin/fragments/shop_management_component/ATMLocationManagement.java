package com.e.caribbeanadmin.fragments.shop_management_component;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.caribbeanadmin.Adaptor.LocationAdaptor;
import com.e.caribbeanadmin.DatabaseController.DatabaseAddresses;
import com.e.caribbeanadmin.Listeners.OnLocationDeleteListeners;
import com.e.caribbeanadmin.Listeners.OnLocationEditListeners;
import com.e.caribbeanadmin.Listeners.OnShopClick;
import com.e.caribbeanadmin.Listeners.OnShopLocationLoadListeners;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.Repository.Repository;
import com.e.caribbeanadmin.data_model.Shop;
import com.e.caribbeanadmin.data_model.ShopLocation;
import com.e.caribbeanadmin.databinding.FragmentATMLocationManagementBinding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;


public class ATMLocationManagement extends Fragment {


    private FragmentATMLocationManagementBinding mDataBinding;
    private Shop shop;
    private static final String TAG = "ATMLocationManagement";
    public ATMLocationManagement() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            shop=getArguments().getParcelable("shop");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mDataBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_a_t_m_location_management, container, false);



        Repository.getLocations(shop.getId(), DatabaseAddresses.getShopATMCollection(), new OnShopLocationLoadListeners() {
            @Override
            public void onLocationsLoaded(List<ShopLocation> locationList) {
                mDataBinding.atmLocationManagementRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                LocationAdaptor locationAdaptor=new LocationAdaptor(getContext(),locationList);
                mDataBinding.atmLocationManagementRecyclerView.setAdapter(locationAdaptor);
                mDataBinding.addAtmLocationManagementAddBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        replaceFragment(new AddATMLocation());
                        Log.d(TAG, "onClick: ");
                    }
                });

                locationAdaptor.setOnLocationDeleteListeners(new OnLocationDeleteListeners() {
                    @Override
                    public void onClick(ShopLocation shopLocation, int index) {
                        DatabaseAddresses.getShopATMCollection().document(shopLocation.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                locationList.remove(index);
                                locationAdaptor.notifyItemRemoved(index);
                                locationAdaptor.notifyItemRangeRemoved(index,locationList.size());
                            }
                        });
                    }
                });
                locationAdaptor.setOnLocationEditListeners(new OnLocationEditListeners() {
                    @Override
                    public void onClick(ShopLocation location) {

                        EditAtmLocation editAtmLocation=new EditAtmLocation();
                        Bundle bundle=new Bundle();
                        bundle.putParcelable("shop",shop);
                        bundle.putParcelable("location",location);
                        editAtmLocation.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.shopContainer,editAtmLocation).addToBackStack(null)
                                .commit();
                    }
                });
            }

            @Override
            public void onEmpty() {
                mDataBinding.addAtmLocationManagementMsg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(String e) {
                mDataBinding.addAtmLocationManagementMsg.setVisibility(View.VISIBLE);
                mDataBinding.addAtmLocationManagementMsg.setText("Error "+e);
            }
        });


        return mDataBinding.getRoot();
    }

    private void replaceFragment(Fragment fragment){
        Bundle bundle=new Bundle();
        bundle.putParcelable("shop",shop);
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.shopContainer,fragment).addToBackStack(null)
                .commit();
    }
}