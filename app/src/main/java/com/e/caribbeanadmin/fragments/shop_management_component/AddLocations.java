package com.e.caribbeanadmin.fragments.shop_management_component;

import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.e.caribbeanadmin.DatabaseController.DatabaseUploader;
import com.e.caribbeanadmin.FireStorageController.FireStorageAddresses;
import com.e.caribbeanadmin.FireStorageController.FireStoreUploader;
import com.e.caribbeanadmin.Listeners.OnFileUploadListeners;
import com.e.caribbeanadmin.Listeners.OnTaskCompleteListeners;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.dataModel.Shop;
import com.e.caribbeanadmin.dataModel.ShopLocation;
import com.e.caribbeanadmin.databinding.FragmentAddLocationsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

import www.sanju.motiontoast.MotionToast;


public class AddLocations extends Fragment {

    private Uri imageUri;
    private FragmentAddLocationsBinding mDataBinding;
    private Shop shop;
    public AddLocations() {
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
        mDataBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_add_locations, container, false);

        mDataBinding.addLocationPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imageUri==null){
                    MotionToast.Companion.createToast(getActivity(),"Fail","Shop Location Image Not Selected",MotionToast.TOAST_ERROR,MotionToast.GRAVITY_BOTTOM,MotionToast.LONG_DURATION,ResourcesCompat.getFont(getContext(),R.font.helvetica_regular));
                }else if(mDataBinding.addLocationShopName.getText().toString().isEmpty()){
                    mDataBinding.addLocationShopName.setError("Empty Not Allowed");
                }else if(mDataBinding.addLocationLat.getText().toString().isEmpty()){
                    mDataBinding.addLocationLat.setError("Empty Not Allowed");
                }else if(mDataBinding.addLocationLng.getText().toString().isEmpty()){
                    mDataBinding.addLocationLng.setError("Empty Not Allowed ");
                }else{
                    ShopLocation shopLocation=new ShopLocation();
                    shopLocation.setId(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                    shopLocation.setShopId(shop.getId());
                    shopLocation.setLat(Double.parseDouble(mDataBinding.addLocationLat.getText().toString()));
                    shopLocation.setLng(Double.parseDouble(mDataBinding.addLocationLng.getText().toString()));
                    shopLocation.setName(mDataBinding.addLocationShopName.getText().toString());
                    FireStoreUploader.uploadPhotos(imageUri, new OnFileUploadListeners() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    shopLocation.setImageUrl(String.valueOf(uri));
                                    DatabaseUploader.publishShopLocation(shopLocation, new OnTaskCompleteListeners() {
                                        @Override
                                        public void onTaskSuccess() {
                                            MotionToast.Companion.createToast(getActivity(),"Success","Shop Location Added",MotionToast.TOAST_SUCCESS,MotionToast.GRAVITY_BOTTOM,MotionToast.LONG_DURATION,ResourcesCompat.getFont(getContext(),R.font.helvetica_regular));

                                        }

                                        @Override
                                        public void onTaskFail(String e) {
                                            MotionToast.Companion.createToast(getActivity(),"Error",e,MotionToast.TOAST_ERROR,MotionToast.GRAVITY_BOTTOM,MotionToast.LONG_DURATION,ResourcesCompat.getFont(getContext(),R.font.helvetica_regular));

                                        }
                                    });

                                }
                            });
                        }

                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }

                        @Override
                        public void onFailure(String e) {

                        }
                    }, FireStorageAddresses.getShopComponents());
                }
            }
        });
        return mDataBinding.getRoot();
    }
}