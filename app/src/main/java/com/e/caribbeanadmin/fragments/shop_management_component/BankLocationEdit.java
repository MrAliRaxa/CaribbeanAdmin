package com.e.caribbeanadmin.fragments.shop_management_component;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.e.caribbeanadmin.databinding.FragmentBankLocationEditBinding;
import com.e.caribbeanadmin.databinding.FragmentEditAtmLocationBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;

import es.dmoral.toasty.Toasty;


public class BankLocationEdit extends Fragment {

    private ShopLocation updateShopLocation;
    private Shop shop;
    private FragmentBankLocationEditBinding mDataBinding;
    private Uri imageUri;
    private static final String TAG = "BankLocationEdit";
    public BankLocationEdit() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            shop=getArguments().getParcelable("shop");
            updateShopLocation=getArguments().getParcelable("location");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mDataBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_bank_location_edit, container, false);
        if(updateShopLocation!=null){
            mDataBinding.setShopLocation(updateShopLocation);
        }

        mDataBinding.editAtmLocationLayout.addLocationPublish.setText("Update Location");
        Glide.with(getContext()).load(updateShopLocation.getImageUrl()).into(mDataBinding.editAtmLocationLayout.addLocationsImage);
        mDataBinding.editAtmLocationLayout.addLocationsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
        mDataBinding.editAtmLocationLayout.addLocationPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog loading= DialogBuilder.getSimpleLoadingDialog(getContext(),"Loading","Updating location");

                if(mDataBinding.editAtmLocationLayout.addLocationShopName.getText().toString().isEmpty()){
                    mDataBinding.editAtmLocationLayout.addLocationShopName.setError("Empty Not Allowed");
                }else if(mDataBinding.editAtmLocationLayout.addLocationLat.getText().toString().isEmpty()){
                    mDataBinding.editAtmLocationLayout.addLocationLat.setError("Empty Not Allowed");
                }else if(mDataBinding.editAtmLocationLayout.addLocationLng.getText().toString().isEmpty()){
                    mDataBinding.editAtmLocationLayout.addLocationLng.setError("Empty Not Allowed ");
                }else if(mDataBinding.editAtmLocationLayout.addLocationAddress.getText().toString().isEmpty()){
                    mDataBinding.editAtmLocationLayout.addLocationAddress.setError("Empty Not allowed");
                }else{
                    ShopLocation shopLocation=updateShopLocation;
                    shopLocation.setLat(Double.parseDouble(mDataBinding.editAtmLocationLayout.addLocationLat.getText().toString()));
                    shopLocation.setLng(Double.parseDouble(mDataBinding.editAtmLocationLayout.addLocationLng.getText().toString()));
                    shopLocation.setName(mDataBinding.editAtmLocationLayout.addLocationShopName.getText().toString());
                    shopLocation.setShopAddress(mDataBinding.editAtmLocationLayout.addLocationAddress.getText().toString());
                    loading.show();
                    if(imageUri!=null){
                        FireStoreUploader.uploadPhotos(imageUri, new OnFileUploadListeners() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        shopLocation.setImageUrl(String.valueOf(uri));
                                        DatabaseUploader.publishShopLocation(shopLocation, DatabaseAddresses.getBankCollection(), new OnTaskCompleteListeners() {
                                            @Override
                                            public void onTaskSuccess() {
                                                // MotionToast.Companion.createToast(getActivity(),"Success",,MotionToast.TOAST_SUCCESS,MotionToast.GRAVITY_BOTTOM,MotionToast.LONG_DURATION,ResourcesCompat.getFont(getContext(),R.font.helvetica_regular));
                                                Toasty.success(getContext(), " Location Added", Toast.LENGTH_SHORT, true).show();
                                                getActivity().getSupportFragmentManager().popBackStack();
                                                loading.dismiss();
                                            }

                                            @Override
                                            public void onTaskFail(String e) {
                                                // MotionToast.Companion.createToast(getActivity(),"Error",e,MotionToast.TOAST_ERROR,MotionToast.GRAVITY_BOTTOM,MotionToast.LONG_DURATION,ResourcesCompat.getFont(getContext(),R.font.helvetica_regular));
                                                Toasty.error(getContext(), "Error "+e, Toast.LENGTH_SHORT, true).show();
                                                loading.dismiss();


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
                    else{
                        DatabaseUploader.publishShopLocation(shopLocation, DatabaseAddresses.getBankCollection(), new OnTaskCompleteListeners() {
                            @Override
                            public void onTaskSuccess() {
                                // MotionToast.Companion.createToast(getActivity(),"Success",,MotionToast.TOAST_SUCCESS,MotionToast.GRAVITY_BOTTOM,MotionToast.LONG_DURATION,ResourcesCompat.getFont(getContext(),R.font.helvetica_regular));
                                Toasty.success(getContext(), " Location Updated", Toast.LENGTH_SHORT, true).show();
                                getActivity().getSupportFragmentManager().popBackStack();
                                loading.dismiss();


                            }

                            @Override
                            public void onTaskFail(String e) {
                                // MotionToast.Companion.createToast(getActivity(),"Error",e,MotionToast.TOAST_ERROR,MotionToast.GRAVITY_BOTTOM,MotionToast.LONG_DURATION,ResourcesCompat.getFont(getContext(),R.font.helvetica_regular));
                                Toasty.error(getContext(), "Error "+e, Toast.LENGTH_SHORT, true).show();
                                loading.dismiss();


                            }
                        });
                    }
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
                mDataBinding.editAtmLocationLayout.addLocationsImage.setImageURI(imageUri);
            }
        }
    }
}