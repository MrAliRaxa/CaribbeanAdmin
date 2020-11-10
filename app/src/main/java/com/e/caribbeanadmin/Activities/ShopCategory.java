package com.e.caribbeanadmin.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.e.caribbeanadmin.dataModel.ShopCategoryModel;
import com.e.caribbeanadmin.DatabaseController.DatabaseUploader;
import com.e.caribbeanadmin.FireStorageController.FireStorageAddresses;
import com.e.caribbeanadmin.FireStorageController.FireStoreUploader;
import com.e.caribbeanadmin.Listeners.OnFileUploadListeners;
import com.e.caribbeanadmin.Listeners.OnTaskCompleteListeners;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.Util.DialogBuilder;
import com.e.caribbeanadmin.Util.GenericMethods;
import com.e.caribbeanadmin.databinding.ActivityAddNewCategoryBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class ShopCategory extends AppCompatActivity {

    private ActivityAddNewCategoryBinding mDataBinding;
    private Uri categoryImageUri;
    private final int SELECT_IMAGE_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_new_category);
        mDataBinding= DataBindingUtil.setContentView(ShopCategory.this,R.layout.activity_add_new_category);

        mDataBinding.addNewShopCategoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenericMethods.getImage(SELECT_IMAGE_CODE,ShopCategory.this);
            }
        });

        mDataBinding.addShopCategoryPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mDataBinding.addNewShopCategoryName.getEditText().getText().toString().isEmpty()){
                    mDataBinding.addNewShopCategoryName.getEditText().setError("Empty not allowed");
                    return;
                }else if(mDataBinding.addNewShopCategoryViewCategory.getSelectedItemPosition()==0){
                    Toast.makeText(ShopCategory.this, "Please select view category", Toast.LENGTH_SHORT).show();
                    return;
                }else if(categoryImageUri==null){
                    Toast.makeText(ShopCategory.this, "Select Category Image", Toast.LENGTH_SHORT).show();
                    return;
                }

                ProgressDialog loading= DialogBuilder.getSimpleLoadingDialog(ShopCategory.this,"Loading ","Uploading Image. . .");
                loading.setCanceledOnTouchOutside(false);
                loading.show();

                ShopCategoryModel shopCategoryModel=new ShopCategoryModel();
                shopCategoryModel.setId(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                shopCategoryModel.setTitle(mDataBinding.addNewShopCategoryName.getEditText().getText().toString());
                shopCategoryModel.setViewType(mDataBinding.addNewShopCategoryViewCategory.getSelectedItemPosition()+1);
                FireStoreUploader.uploadPhotos(categoryImageUri, new OnFileUploadListeners() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        loading.setMessage("Getting Image Url . . .");
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                shopCategoryModel.setImageUrl(String.valueOf(uri));
                                loading.setMessage("Publishing Category . . .");
                                DatabaseUploader.publishNewCategory(shopCategoryModel, new OnTaskCompleteListeners() {
                                    @Override
                                    public void onTaskSuccess() {
                                        Toast.makeText(ShopCategory.this, "Category Added", Toast.LENGTH_SHORT).show();
                                        loading.dismiss();
                                        finish();
                                    }

                                    @Override
                                    public void onTaskFail(String e) {
                                        Toast.makeText(ShopCategory.this, "Error "+e, Toast.LENGTH_SHORT).show();
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
                }, FireStorageAddresses.getShopCategoryRef());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null)
        if(data.getData()!=null){
            if(requestCode==SELECT_IMAGE_CODE){

                mDataBinding.addNewShopCategorySelectedText.setText("1 Image Selected");
                categoryImageUri=data.getData();

            }
        }
    }
}