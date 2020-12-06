package com.e.caribbeanadmin.fragments.shop_management_component;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.e.caribbeanadmin.databinding.FragmentLocationManagementBinding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;

import java.util.List;

import es.dmoral.toasty.Toasty;


public class LocationManagement extends Fragment {


    private Shop shop;

    public LocationManagement() {
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
        FragmentLocationManagementBinding mDataBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_location_management, container, false);



        mDataBinding.addLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddLocations addLocations=new AddLocations();
                Bundle bundle=new Bundle();
                bundle.putParcelable("shop",shop);
                addLocations.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.shopContainer,addLocations).addToBackStack(null)
                        .commit();
            }
        });
        Repository.getLocations(shop.getId(), DatabaseAddresses.getShopLocationCollection(), new OnShopLocationLoadListeners() {
            @Override
            public void onLocationsLoaded(List<ShopLocation> locationList) {
                mDataBinding.locationManagementRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                LocationAdaptor locationAdaptor=new LocationAdaptor(getContext(),locationList);
                locationAdaptor.setOnLocationEditListeners(new OnLocationEditListeners() {
                    @Override
                    public void onClick(ShopLocation location) {
                        replaceFragment(new EditShopLocation(),location,shop);
                    }
                });
                locationAdaptor.setOnLocationDeleteListeners(new OnLocationDeleteListeners() {
                    @Override
                    public void onClick(ShopLocation shopLocation,int index) {
                        DatabaseAddresses.getShopLocationCollection().document(shopLocation.getId()).delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toasty.success(getContext(),"Location Removed ").show();
                                        locationList.remove(index);
                                        locationAdaptor.notifyItemRangeRemoved(index,locationList.size());
                                        locationAdaptor.notifyItemRemoved(index);
                                    }
                                });
                    }
                });

                mDataBinding.locationManagementRecyclerView.setAdapter(locationAdaptor);
            }

            @Override
            public void onEmpty() {
                mDataBinding.locationManagementMsg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(String e) {
                mDataBinding.locationManagementMsg.setVisibility(View.VISIBLE);
                mDataBinding.locationManagementMsg.setText("Error "+e);
            }
        });
        return mDataBinding.getRoot();
    }

    private void replaceFragment(Fragment fragment,ShopLocation shopLocation,Shop s){
        Bundle bundle=new Bundle();
        bundle.putParcelable("shop",s);
        bundle.putParcelable("location",shopLocation);
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.shopContainer,fragment).addToBackStack(null)
                .commit();
    }
}