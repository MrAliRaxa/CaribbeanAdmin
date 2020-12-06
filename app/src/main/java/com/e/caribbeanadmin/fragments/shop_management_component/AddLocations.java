package com.e.caribbeanadmin.fragments.shop_management_component;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.e.caribbeanadmin.DatabaseController.DatabaseAddresses;
import com.e.caribbeanadmin.DatabaseController.DatabaseUploader;
import com.e.caribbeanadmin.FireStorageController.FireStorageAddresses;
import com.e.caribbeanadmin.FireStorageController.FireStoreUploader;
import com.e.caribbeanadmin.Listeners.OnFileUploadListeners;
import com.e.caribbeanadmin.Listeners.OnTaskCompleteListeners;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.Util.DialogBuilder;
import com.e.caribbeanadmin.data_model.Shop;
import com.e.caribbeanadmin.data_model.ShopLocation;
import com.e.caribbeanadmin.databinding.FragmentAddLocationsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;



public class AddLocations extends Fragment {

    private Uri imageUri;
    private Shop shop;
    private FragmentAddLocationsBinding mDataBinding;
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


        mDataBinding.addLocationsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
        mDataBinding.addLocationPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog= DialogBuilder.getSimpleLoadingDialog(getContext(),"Loading","Uploading location");

                if(imageUri==null){
                    Toasty.error(getContext(), "Location Image Not Selected ", Toast.LENGTH_SHORT, true).show();

                    //MotionToast.Companion.createToast(getActivity(),"Fail","Shop Location Image Not Selected",MotionToast.TOAST_ERROR,MotionToast.GRAVITY_BOTTOM,MotionToast.LONG_DURATION,ResourcesCompat.getFont(getContext(),R.font.helvetica_regular));
                }else if(mDataBinding.addLocationShopName.getText().toString().isEmpty()){
                    mDataBinding.addLocationShopName.setError("Empty Not Allowed");
                }else if(mDataBinding.addLocationLat.getText().toString().isEmpty()){
                    mDataBinding.addLocationLat.setError("Empty Not Allowed");
                }else if(mDataBinding.addLocationLng.getText().toString().isEmpty()){
                    mDataBinding.addLocationLng.setError("Empty Not Allowed ");
                }else if(mDataBinding.addLocationAddress.getText().toString().isEmpty()){
                    mDataBinding.addLocationAddress.setError("Empty Not allowed");
                }else{
                    progressDialog.show();
                    ShopLocation shopLocation=new ShopLocation();
                    shopLocation.setId(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                    shopLocation.setShopId(shop.getId());
                    shopLocation.setLat(Double.parseDouble(mDataBinding.addLocationLat.getText().toString()));
                    shopLocation.setLng(Double.parseDouble(mDataBinding.addLocationLng.getText().toString()));
                    shopLocation.setName(mDataBinding.addLocationShopName.getText().toString());
                    shopLocation.setShopAddress(mDataBinding.addLocationAddress.getText().toString());
                    FireStoreUploader.uploadPhotos(imageUri, new OnFileUploadListeners() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    shopLocation.setImageUrl(String.valueOf(uri));
                                    DatabaseUploader.publishShopLocation(shopLocation, DatabaseAddresses.getShopLocationCollection(), new OnTaskCompleteListeners() {
                                        @Override
                                        public void onTaskSuccess() {
                                           // MotionToast.Companion.createToast(getActivity(),"Success",,MotionToast.TOAST_SUCCESS,MotionToast.GRAVITY_BOTTOM,MotionToast.LONG_DURATION,ResourcesCompat.getFont(getContext(),R.font.helvetica_regular));
                                            Toasty.success(getContext(), "Shop Location Added", Toast.LENGTH_SHORT, true).show();
                                            progressDialog.dismiss();
                                            getActivity().getSupportFragmentManager().popBackStack();
                                        }

                                        @Override
                                        public void onTaskFail(String e) {
                                           // MotionToast.Companion.createToast(getActivity(),"Error",e,MotionToast.TOAST_ERROR,MotionToast.GRAVITY_BOTTOM,MotionToast.LONG_DURATION,ResourcesCompat.getFont(getContext(),R.font.helvetica_regular));
                                            Toasty.error(getContext(), "Error "+e, Toast.LENGTH_SHORT, true).show();
                                            progressDialog.dismiss();
                                            getActivity().getSupportFragmentManager().popBackStack();

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(data!=null&&data.getData()!=null){
                imageUri=data.getData();
                mDataBinding.addLocationsImage.setImageURI(imageUri);
            }
        }
    }
}