package com.e.caribbeanadmin.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.e.caribbeanadmin.Adaptor.ShopCategorySpinnerAdaptor;
import com.e.caribbeanadmin.Constants.ShopType;
import com.e.caribbeanadmin.dataModel.Shop;
import com.e.caribbeanadmin.dataModel.ShopCategoryModel;
import com.e.caribbeanadmin.DatabaseController.DatabaseUploader;
import com.e.caribbeanadmin.FireStorageController.FireStorageAddresses;
import com.e.caribbeanadmin.FireStorageController.FireStoreUploader;
import com.e.caribbeanadmin.Listeners.OnFileUploadListeners;
import com.e.caribbeanadmin.Listeners.OnShopCategoryLoadListeners;
import com.e.caribbeanadmin.Listeners.OnTaskCompleteListeners;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.Repository.Repository;
import com.e.caribbeanadmin.Util.DialogBuilder;
import com.e.caribbeanadmin.Util.GenericMethods;
import com.e.caribbeanadmin.databinding.ActivityAddNewShopBinding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddNewShop extends AppCompatActivity {

    private final int SELECT_LOGO_IMAGE=1;
    private final int SELECT_BANNER_IMAGE=2;
    private Uri bannerUri;
    private Uri logoUri;
    private ActivityAddNewShopBinding mDataBinding;
    private List<ShopCategoryModel> shopCategories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding= DataBindingUtil.setContentView(AddNewShop.this,R.layout.activity_add_new_shop);
        shopCategories=new ArrayList<>();


        mDataBinding.addNewShopLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenericMethods.getImage(SELECT_LOGO_IMAGE,AddNewShop.this);
            }
        });

        mDataBinding.addNewShopBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenericMethods.getImage(SELECT_BANNER_IMAGE,AddNewShop.this);
            }
        });

        Repository.getShopCategories(new OnShopCategoryLoadListeners() {
            @Override
            public void onCategoriesLoaded(List<ShopCategoryModel> shopCategoryModelList) {
                shopCategories=shopCategoryModelList;
                ShopCategoryModel categoryModel=new ShopCategoryModel();
                categoryModel.setTitle("Select Shop Category");
                categoryModel.setViewType(ShopType.NONE);
                List<ShopCategoryModel> newCategories=new ArrayList<>();
                newCategories.add(categoryModel);
                newCategories.addAll(shopCategoryModelList);
                ShopCategorySpinnerAdaptor shopCategorySpinnerAdaptor =new ShopCategorySpinnerAdaptor(newCategories,AddNewShop.this);
                mDataBinding.newShopCategory.setAdapter(shopCategorySpinnerAdaptor);

            }

            @Override
            public void onEmpty() {
                ShopCategoryModel categoryModel=new ShopCategoryModel();
                categoryModel.setTitle("No Shop Category Found");
                categoryModel.setViewType(ShopType.NONE);
                List<ShopCategoryModel> newCategories=new ArrayList<>();
                newCategories.add(categoryModel);
                ShopCategorySpinnerAdaptor shopCategorySpinnerAdaptor =new ShopCategorySpinnerAdaptor(newCategories,AddNewShop.this);

                mDataBinding.newShopCategory.setAdapter(shopCategorySpinnerAdaptor);
            }

            @Override
            public void onFailure(String e) {

            }
        });

        mDataBinding.publishShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mDataBinding.newShopName.getText().toString().isEmpty()){
                    mDataBinding.newShopName.setError("Empty not allowed");
                    mDataBinding.newShopName.requestFocus();
                    return;
                }else if(mDataBinding.newShopContactNumber.getText().toString().isEmpty()){
                    mDataBinding.newShopContactNumber.setError("Empty Not Allowed");
                    mDataBinding.newShopContactNumber.requestFocus();
                    return;
                }else if(mDataBinding.newShopLat.getText().toString().isEmpty()){
                    mDataBinding.newShopLat.setError("Empty Not Allowed");
                    mDataBinding.newShopLat.requestFocus();
                    return;
                }else if(mDataBinding.newShopLng.getText().toString().isEmpty()){
                    mDataBinding.newShopLng.setError("Empty Not Allowed");
                    mDataBinding.newShopLng.requestFocus();
                    return;
                }else if(mDataBinding.newShopCategory.getSelectedItemPosition()==0){
                    Toast.makeText(AddNewShop.this, "Please Select Shop Category", Toast.LENGTH_SHORT).show();
                    return;
                }else if(logoUri==null){
                    Toast.makeText(AddNewShop.this, "Please Add Logo Image", Toast.LENGTH_SHORT).show();
                    return;
                }else if(bannerUri==null){
                    Toast.makeText(AddNewShop.this, "Please Add Banner Image", Toast.LENGTH_SHORT).show();
                    return;
                }


               ProgressDialog loading= DialogBuilder.getSimpleLoadingDialog(AddNewShop.this,"Loading","Uploading data ...");
                loading.setCanceledOnTouchOutside(false);
                loading.setCancelable(false);
                loading.show();
                Shop shop=new Shop();
                shop.setId(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                shop.setName(mDataBinding.newShopName.getText().toString());
                shop.setContact(mDataBinding.newShopContactNumber.getText().toString());
                shop.setCategoryId(shopCategories.get(mDataBinding.newShopCategory.getSelectedItemPosition()-1).getId());
                shop.setLat(Double.parseDouble(mDataBinding.newShopLat.getText().toString()));
                shop.setLng(Double.parseDouble(mDataBinding.newShopLng.getText().toString()));
                loading.setMessage("Uploading logo image . . .");


                FireStoreUploader.uploadPhotos(logoUri, new OnFileUploadListeners() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                loading.setMessage("Uploading banner image. . .");

                                shop.setLogoUrl(String.valueOf(uri));
                                FireStoreUploader.uploadPhotos(bannerUri, new OnFileUploadListeners() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                shop.setBannerUrl(String.valueOf(uri));
                                                loading.setMessage("Uploading information . . .");

                                                DatabaseUploader.publishNewShop(shop, new OnTaskCompleteListeners() {
                                                    @Override
                                                    public void onTaskSuccess() {
                                                        Toast.makeText(AddNewShop.this, "Shop Added", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    }

                                                    @Override
                                                    public void onTaskFail(String e) {
                                                        Toast.makeText(AddNewShop.this, "Error :"+e, Toast.LENGTH_SHORT).show();
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
                                }, FireStorageAddresses.getShopRef());
                            }
                        });
                    }

                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    }

                    @Override
                    public void onFailure(String e) {
                        Toast.makeText(AddNewShop.this, "Error :"+e, Toast.LENGTH_SHORT).show();
                    }
                },FireStorageAddresses.getShopRef());





            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==SELECT_LOGO_IMAGE){
            if(data.getData()!=null){
                mDataBinding.addNewShopLogoSelectedText.setText("1 Image Selected");
                logoUri=data.getData();
            }
        }else if(requestCode==SELECT_BANNER_IMAGE){

            if(data.getData()!=null){
                mDataBinding.addNewShopBannerSelectedText.setText("1 Image Selected");
                bannerUri=data.getData();
            }


        }
    }
}