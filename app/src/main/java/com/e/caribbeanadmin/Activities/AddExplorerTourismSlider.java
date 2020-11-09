package com.e.caribbeanadmin.Activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.e.caribbeanadmin.Constants.SliderType;
import com.e.caribbeanadmin.DataModel.TourismSlider;
import com.e.caribbeanadmin.DatabaseController.DatabaseUploader;
import com.e.caribbeanadmin.FireStorageController.FireStorageAddresses;
import com.e.caribbeanadmin.FireStorageController.FireStoreUploader;
import com.e.caribbeanadmin.Listeners.OnFileUploadListeners;
import com.e.caribbeanadmin.Listeners.OnTaskCompleteListeners;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.Util.DialogBuilder;
import com.e.caribbeanadmin.Util.GenericMethods;
import com.e.caribbeanadmin.databinding.ActivityAddExplorerTourismSliderBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddExplorerTourismSlider extends AppCompatActivity {

    private final static int IMAGE_CODE=1;
    private final static int VIDEO_CODE=2;
    private List<Uri> sliderContent;
    private TextView selectedSliderImageText;
    private TextView selectedSliderVideoText;
    private ActivityAddExplorerTourismSliderBinding mDataBinding;
    private Context context;
    private TourismSlider tourismSlider;
    private static final String TAG = "AddExplorerTourismSlide";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding= DataBindingUtil.setContentView(AddExplorerTourismSlider.this,R.layout.activity_add_explorer_tourism_slider);
        sliderContent=new ArrayList<>();
        selectedSliderImageText=mDataBinding.addNewTourismSelectedImagesText;
        selectedSliderVideoText=mDataBinding.addNewTourismSelectedVideosText;
        context=AddExplorerTourismSlider.this;
        tourismSlider=new TourismSlider();
        Spinner contentSpinner=mDataBinding.addNewTourismSliderSpinner;
        contentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(contentSpinner.getSelectedItemPosition()==0){
                    mDataBinding.addNewTourismSliderImagesLayout.setVisibility(View.VISIBLE);
                    mDataBinding.addNewTourismSliderVideosLayout.setVisibility(View.GONE);
                }else{
                    mDataBinding.addNewTourismSliderImagesLayout.setVisibility(View.GONE);
                    mDataBinding.addNewTourismSliderVideosLayout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mDataBinding.addNewTourismAddSliderImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenericMethods.getImages(IMAGE_CODE,AddExplorerTourismSlider.this);
                tourismSlider.setSliderType(SliderType.IMAGE_SLIDER);
            }
        });
        mDataBinding.addNewTourismAddSliderVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tourismSlider.setSliderType(SliderType.VIDEO);
                GenericMethods.getVideos(VIDEO_CODE,AddExplorerTourismSlider.this);
            }
        });

        mDataBinding.addTourismExplorerPublishContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sliderContent.size()>0){

                    final int[] contentCounter = {0};
                    ProgressDialog dialog= DialogBuilder.getSimpleLoadingDialog(context,"Loading","Uploading content . . ."+contentCounter[0]+"/"+sliderContent.size());
                    dialog.show();

                    tourismSlider.setId(String.valueOf(Calendar.getInstance().getTimeInMillis()));

                    FireStoreUploader.uploadPhotos(sliderContent,"Tourism Slider Data", new OnFileUploadListeners() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    tourismSlider.getSliderContent().add(uri.toString());
                                    contentCounter[0]++;
                                    dialog.setMessage("Uploading content . . ."+contentCounter[0]+"/"+sliderContent.size());
                                    if(contentCounter[0] >=sliderContent.size()){

                                        DatabaseUploader.saveTourismSliderContent(tourismSlider, new OnTaskCompleteListeners() {
                                            @Override
                                            public void onTaskSuccess() {
                                                dialog.dismiss();
                                                AlertDialog msgDialog=DialogBuilder.getSimpleMsg(context, "Success", "Content uploaded successfully"
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
                                                Toast.makeText(context, "Error "+e, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AddExplorerTourismSlider.this, "Error :"+e, Toast.LENGTH_SHORT).show();
                        }
                    }, FireStorageAddresses.getSliderContentRef());
                }else{
                    Toast.makeText(AddExplorerTourismSlider.this, "Please Select Video Or Images", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_CODE){
            if(resultCode==RESULT_OK){
                sliderContent.clear();
                if(data.getClipData()!=null){

                    for(int i=0;i<data.getClipData().getItemCount();i++){
                        sliderContent.add(data.getClipData().getItemAt(i).getUri());
                    }
                    selectedSliderImageText.setText(data.getClipData().getItemCount()+" Images Selected");

                }else if(data!=null&&data.getData()!=null){
                    Log.d(TAG, "onActivityResult: "+data.getData());
                    sliderContent.clear();
                    sliderContent.add(data.getData());
                    selectedSliderImageText.setText("1 Image Selected");
                }
            }
        }else if(requestCode==VIDEO_CODE){

            if(data!=null&&data.getData()!=null){
                Log.d(TAG, "onActivityResult: "+data.getData());
                sliderContent.clear();
                sliderContent.add(data.getData());
                Log.d(TAG, "onActivityResult: "+sliderContent.size());
                selectedSliderVideoText.setText("1 Video Selected");
            }
        }
    }
}