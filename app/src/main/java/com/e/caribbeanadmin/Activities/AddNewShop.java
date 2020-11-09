package com.e.caribbeanadmin.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.e.caribbeanadmin.DataModel.Shop;
import com.e.caribbeanadmin.DataModel.ShopCategoryModel;
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
import com.e.caribbeanadmin.databinding.ActivityAddNewCountryBinding;
import com.e.caribbeanadmin.databinding.ActivityAddNewShopBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;

import java.util.List;

public class AddNewShop extends AppCompatActivity {

    private final int SELECT_LOGO_IMAGE=1;
    private final int SELECT_BANNER_IMAGE=2;
    private Uri bannerUri;
    private Uri logoUri;
    private ActivityAddNewShopBinding mDataBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_new_shop);
        mDataBinding= DataBindingUtil.setContentView(AddNewShop.this,R.layout.activity_add_new_shop);
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


                ArrayAdapter arrayAdapter=new ArrayAdapter(AddNewShop.this, android.R.layout.simple_spinner_dropdown_item,shopCategoryModelList);
                mDataBinding.newShopCategory.setAdapter(arrayAdapter);

            }

            @Override
            public void onEmpty() {
                String categoryList
                ArrayAdapter arrayAdapter=new ArrayAdapter(AddNewShop.this, android.R.layout.simple_spinner_dropdown_item,shopCategoryModelList);
                mDataBinding.newShopCategory.setAdapter(arrayAdapter);
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
                shop.setName(mDataBinding.newShopName.getText().toString());
                shop.setContact(mDataBinding.newShopContactNumber.getText().toString());
                shop.setCategory(mDataBinding.newShopCategory.getSelectedItemPosition()+1);

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