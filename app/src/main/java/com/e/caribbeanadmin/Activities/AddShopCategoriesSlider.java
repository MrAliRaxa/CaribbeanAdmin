package com.e.caribbeanadmin.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.e.caribbeanadmin.DatabaseController.DatabaseUploader;
import com.e.caribbeanadmin.FireStorageController.FireStorageAddresses;
import com.e.caribbeanadmin.FireStorageController.FireStoreUploader;
import com.e.caribbeanadmin.Listeners.OnFileUploadListeners;
import com.e.caribbeanadmin.Listeners.OnTaskCompleteListeners;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.Util.DialogBuilder;
import com.e.caribbeanadmin.Util.GenericMethods;
import com.e.caribbeanadmin.dataModel.SliderContent;
import com.e.caribbeanadmin.databinding.ActivityAddShopCategoriesSliderBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class AddShopCategoriesSlider extends AppCompatActivity {
    private final static int IMAGE_CODE=1;
    private List<Uri> sliderContentList;
    private TextView selectedSliderImageText;
    private ActivityAddShopCategoriesSliderBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop_categories_slider);
        mDataBinding= DataBindingUtil.setContentView(AddShopCategoriesSlider.this,R.layout.activity_add_shop_categories_slider);
        sliderContentList =new ArrayList<>();
        selectedSliderImageText=mDataBinding.addNewTourismSelectedImagesText;
        mDataBinding.addNewTourismAddSliderImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenericMethods.getImages(IMAGE_CODE,AddShopCategoriesSlider.this);
            }
        });
        mDataBinding.addTourismExplorerPublishContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sliderContentList.size()>0){

                    final int[] contentCounter = {0};
                    ProgressDialog dialog= DialogBuilder.getSimpleLoadingDialog(getContext(),"Loading","Uploading content . . ."+contentCounter[0]+"/"+ sliderContentList.size());
                    dialog.show();
                    SliderContent sliderContent=new SliderContent();



                    FireStoreUploader.uploadPhotos(sliderContentList,"Tourism Slider Data", new OnFileUploadListeners() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    contentCounter[0]++;
                                    dialog.setMessage("Uploading content . . ."+contentCounter[0]+"/"+ sliderContentList.size());
                                    sliderContent.getSliderContent().add(String.valueOf(uri));
                                    if(contentCounter[0] >= sliderContentList.size()){

                                        DatabaseUploader.saveShopCategoriesSliderContent(sliderContent, new OnTaskCompleteListeners() {
                                            @Override
                                            public void onTaskSuccess() {
                                                dialog.dismiss();
                                                AlertDialog msgDialog=DialogBuilder.getSimpleMsg(getContext(), "Success", "Content uploaded successfully"
                                                        , new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                finish();
                                                            }
                                                        });
                                                msgDialog.setCanceledOnTouchOutside(false);
                                                msgDialog.setCancelable(false);
                                                msgDialog.show();
                                            }

                                            @Override
                                            public void onTaskFail(String e) {
                                                Toast.makeText(getContext(), "Error "+e, Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                    }
                                }
                            });


                        }

                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }

                        @Override
                        public void onFailure(String e) {
                            Toast.makeText(getContext(), "Error :"+e, Toast.LENGTH_SHORT).show();
                        }
                    }, FireStorageAddresses.getSliderContentRef());
                }else{
                    Toast.makeText(getContext(), "Please Select Video Or Images", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Context getContext(){

        return AddShopCategoriesSlider.this;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_CODE){
            if(resultCode==RESULT_OK){
                sliderContentList.clear();
                if(data.getClipData()!=null){

                    for(int i=0;i<data.getClipData().getItemCount();i++){
                        sliderContentList.add(data.getClipData().getItemAt(i).getUri());
                    }
                    selectedSliderImageText.setText(data.getClipData().getItemCount()+" Images Selected");

                }else if(data!=null&&data.getData()!=null){

                    sliderContentList.clear();
                    sliderContentList.add(data.getData());
                    selectedSliderImageText.setText("1 Image Selected");
                }
            }
        }
    }
}