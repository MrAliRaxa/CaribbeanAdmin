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
import com.e.caribbeanadmin.Listeners.OnShopLocationLoadListeners;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.Repository.Repository;
import com.e.caribbeanadmin.data_model.Shop;
import com.e.caribbeanadmin.data_model.ShopLocation;
import com.e.caribbeanadmin.databinding.FragmentAddBankLocationsBinding;
import com.e.caribbeanadmin.databinding.FragmentBankLocationManagementBinding;
import com.e.caribbeanadmin.databinding.FragmentLocationManagementBinding;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;


public class BankLocationManagement extends Fragment {

    private FragmentBankLocationManagementBinding mDataBinding;
    private Shop shop;
    private static final String TAG = "BankLocationManagement";
    public BankLocationManagement() {
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
        mDataBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_bank_location_management, container, false);

        Repository.getLocations(shop.getId(), DatabaseAddresses.getBankCollection(), new OnShopLocationLoadListeners() {
            @Override
            public void onLocationsLoaded(List<ShopLocation> locationList) {
                mDataBinding.bankLocationManagementRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                LocationAdaptor locationAdaptor=new LocationAdaptor(getContext(),locationList);
                mDataBinding.bankLocationManagementRecyclerView.setAdapter(locationAdaptor);
                mDataBinding.addBankLocationManagementAddBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        replaceFragment(new AddBankLocations());
                        Log.d(TAG, "onClick: ");
                    }
                });

                locationAdaptor.setOnLocationDeleteListeners(new OnLocationDeleteListeners() {
                    @Override
                    public void onClick(ShopLocation shopLocation, int index) {
                        DatabaseAddresses.getBankCollection().document(shopLocation.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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

                        BankLocationEdit bankLocationEdit=new BankLocationEdit();
                        Bundle bundle=new Bundle();
                        bundle.putParcelable("shop",shop);
                        bundle.putParcelable("location",location);
                        bankLocationEdit.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.shopContainer,bankLocationEdit).addToBackStack(null)
                                .commit();
                    }
                });
            }

            @Override
            public void onEmpty() {
                mDataBinding.addBankLocationManagementMsg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(String e) {
                mDataBinding.addBankLocationManagementMsg.setVisibility(View.VISIBLE);
                mDataBinding.addBankLocationManagementMsg.setText("Error "+e);
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