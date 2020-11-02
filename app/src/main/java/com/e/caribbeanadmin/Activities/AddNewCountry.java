package com.e.caribbeanadmin.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.e.caribbeanadmin.Constants.SliderType;
import com.e.caribbeanadmin.DataModel.Country;
import com.e.caribbeanadmin.DatabaseController.DatabaseUploader;
import com.e.caribbeanadmin.FireStorageController.FireStoreUploader;
import com.e.caribbeanadmin.Listeners.OnFileUploadListeners;
import com.e.caribbeanadmin.Listeners.OnTaskCompleteListeners;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.databinding.ActivityAddNewCountryBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddNewCountry extends AppCompatActivity {

    private ActivityAddNewCountryBinding mDataBinding;
    private static final String TAG = "AddNewCountry";
    private static final int SELECT_SLIDER_IMAGE_CODE=1;
    private static final int SELECT_SLIDER_VIDEO_CODE=2;
    private static final int SELECT_FLAG_IMAGE_CODE=3;
    private static final int SELECT_ARM_IMAGE_CODE=4;
    private static final int SELECT_DELICACIES_IMAGE_CODE=5;
    private static final int SELECT_DELICACIES_VIDEO_CODE=6;


    private TextView selectedSliderImageText;
    private TextView selectedSliderVideoText;
    private TextView selectedFlagText;
    private TextView selectedArmFlagText;
    private TextView selectedDelicaciesImageText;
    private TextView selectedDelicaciesVideoText;


    private List<Uri> sliderContent;
    private List<String> sliderContentDownloadUrl;
    private List<Uri> delicaciesContent;
    private List<String> delicaciesContentDownloadUrl;
    private Uri flagUri;
    private Uri armsFlagUri;
    private Country country;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewInit();
        country=new Country();
        sliderContent=new ArrayList<>();
        delicaciesContent=new ArrayList<>();
        sliderContentDownloadUrl=new ArrayList<>();
        delicaciesContentDownloadUrl=new ArrayList<>();
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        selectedFlagText=mDataBinding.addNewCountrySelectedFlagText;
        selectedArmFlagText=mDataBinding.addNewCountrySelectedAramText;
        selectedDelicaciesImageText=mDataBinding.addNewCountrySelectedDelicaciesImagesText;
        selectedDelicaciesVideoText=mDataBinding.addNewCountrySelectedDelicaciesVideosText;

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AddNewCountry.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }

        mDataBinding.addNewCountrySliderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){
                    country.getCountrySlider().setSliderType(SliderType.IMAGE_SLIDER);
                    mDataBinding.addNewCountrySliderImagesLayout.setVisibility(View.VISIBLE);
                    mDataBinding.addNewCountrySliderVideosLayout.setVisibility(View.GONE);


                }else if(position==1){
                    country.getCountrySlider().setSliderType(SliderType.VIDEO);
                    mDataBinding.addNewCountrySliderVideosLayout.setVisibility(View.VISIBLE);
                    mDataBinding.addNewCountrySliderImagesLayout.setVisibility(View.GONE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        mDataBinding.addNewCountryAddSliderImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                country.getCountrySlider().setSliderType(SliderType.IMAGE_SLIDER);
                getImages(SELECT_SLIDER_IMAGE_CODE);

            }
        });


        mDataBinding.addNewCountryAddSliderVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country.getCountrySlider().setSliderType(SliderType.VIDEO);
                getVideos(SELECT_SLIDER_VIDEO_CODE);
            }
        });


        mDataBinding.addNewCountryAddFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage(SELECT_FLAG_IMAGE_CODE);
            }
        });

        mDataBinding.addNewCountryAddArmFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage(SELECT_ARM_IMAGE_CODE);
            }
        });

        mDataBinding.addNewCountryDalicaciesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    country.getDelicacies().setSliderType(SliderType.IMAGE_SLIDER);
                    mDataBinding.addNewCountryDelicaciesImagesLayout.setVisibility(View.VISIBLE);
                    mDataBinding.addNewCountryDalicaciesVideosLayout.setVisibility(View.GONE);
                }else{
                    country.getDelicacies().setSliderType(SliderType.VIDEO);
                    mDataBinding.addNewCountryDelicaciesImagesLayout.setVisibility(View.GONE);
                    mDataBinding.addNewCountryDalicaciesVideosLayout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mDataBinding.addNewCountryAddDelicaciesImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country.getDelicacies().setSliderType(SliderType.IMAGE_SLIDER);
                getImages(SELECT_DELICACIES_IMAGE_CODE);
            }
        });

        mDataBinding.addNewCountryDelicaciesVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country.getDelicacies().setSliderType(SliderType.VIDEO);
                getVideos(SELECT_DELICACIES_VIDEO_CODE);
            }
        });


        mDataBinding.addNewCountryFinsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int[] sliderContentCounter = {0};

                if(mDataBinding.addNewCountryName.getText().toString().isEmpty()){
                    mDataBinding.addNewCountryName.setError("Empty field not allowed");
                    mDataBinding.addNewCountryName.requestFocus();
                    return;
                }else if(mDataBinding.addNewCountryMotto.getText().toString().isEmpty()){
                    mDataBinding.addNewCountryMotto.setError("Empty field not allowed");
                    mDataBinding.addNewCountryMotto.requestFocus();

                    return;

                }else if(mDataBinding.addNewCountryLanguage.getText().toString().isEmpty()){
                    mDataBinding.addNewCountryLanguage.setError("Empty field not allowed");
                    mDataBinding.addNewCountryLanguage.requestFocus();

                    return;

                }else if(mDataBinding.addNewCountryCapital.getText().toString().isEmpty()){
                    mDataBinding.addNewCountryCapital.setError("Empty field not allowed");
                    mDataBinding.addNewCountryCapital.requestFocus();

                    return;

                }else if(mDataBinding.addNewCountryTemperature.getText().toString().isEmpty()){
                    mDataBinding.addNewCountryTemperature.setError("Empty field not allowed");
                    mDataBinding.addNewCountryTemperature.requestFocus();

                    return;
                }else if(mDataBinding.addNewCountryCurrency.getText().toString().isEmpty()){
                    mDataBinding.addNewCountryCurrency.setError("Empty field not allowed");
                    mDataBinding.addNewCountryCurrency.requestFocus();

                    return;
                }else if(mDataBinding.addNewCountryHistory.getText().toString().isEmpty()){
                    mDataBinding.addNewCountryHistory.setError("Empty field not allowed");
                    mDataBinding.addNewCountryHistory.requestFocus();
                    return;
                }else if(mDataBinding.addNewCountryExtraContent.getText().toString().isEmpty()){
                    mDataBinding.addNewCountryExtraContent.setError("Empty field not allowed");
                    mDataBinding.addNewCountryExtraContent.requestFocus();
                    return;
                }else if(mDataBinding.addNewCountryDelicaciesContent.getText().toString().isEmpty()){
                    mDataBinding.addNewCountryDelicaciesContent.setError("Empty field not allowed");
                    mDataBinding.addNewCountryDelicaciesContent.requestFocus();
                    return;
                }


                country.getInformation().setName(mDataBinding.addNewCountryName.getText().toString());
                country.getInformation().setMotto(mDataBinding.addNewCountryMotto.getText().toString());
                country.getInformation().setLanguage(mDataBinding.addNewCountryLanguage.getText().toString());
                country.getInformation().setCapital(mDataBinding.addNewCountryCapital.getText().toString());
                country.getInformation().setTemperature(Double.parseDouble(mDataBinding.addNewCountryTemperature.getText().toString()));
                country.getInformation().setCurrencyName(mDataBinding.addNewCountryCurrency.getText().toString());
                country.setHistory(mDataBinding.addNewCountryHistory.getText().toString());
                country.getInformation().setPopulation(Integer.parseInt(mDataBinding.addNewCountryPopulation.getText().toString()));
                country.setCountryId(String.valueOf(Calendar.getInstance().getTimeInMillis()));


                progressDialog.show();
                progressDialog.setMessage("Uploading country images");
                FireStoreUploader.uploadPhotos(sliderContent, country.getCountryId(), new OnFileUploadListeners() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                sliderContentDownloadUrl.add(String.valueOf(uri));
                                sliderContentCounter[0]++;
                                Log.d(TAG, "onSuccess: slider content "+sliderContentCounter[0]);
                                if(sliderContentCounter[0] >=sliderContent.size()){
                                    Log.d(TAG, "onSuccess: slider content done");

                                    final int[] delicaciesContentCounter = {0};
                                    progressDialog.setMessage("Uploading delicacies images");

                                    FireStoreUploader.uploadPhotos(delicaciesContent, country.getCountryId(), new OnFileUploadListeners() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    delicaciesContentDownloadUrl.add(uri.toString());
                                                    delicaciesContentCounter[0]++;
                                                    Log.d(TAG, "onSuccess: delicacies content "+delicaciesContentCounter[0]);

                                                    if(delicaciesContentCounter[0] >=delicaciesContent.size()){
                                                        Log.d(TAG, "onSuccess: delicacies content done ");
                                                        Log.d(TAG, "onSuccess: flag content start ");
                                                        progressDialog.setMessage("Uploading flag image");

                                                        FireStoreUploader.uploadPhotos(flagUri, country.getCountryId(), new OnFileUploadListeners() {
                                                            @Override
                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                    @Override
                                                                    public void onSuccess(Uri uri) {
                                                                        Log.d(TAG, "onSuccess: flag content done ");
                                                                        Log.d(TAG, "onSuccess: arm flag content start ");
                                                                        progressDialog.setMessage("Uploading Arms Flag images");

                                                                        country.setFlagImageUrl(uri.toString());

                                                                        FireStoreUploader.uploadPhotos(armsFlagUri, country.getCountryId(), new OnFileUploadListeners() {
                                                                            @Override
                                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                    @Override
                                                                                    public void onSuccess(Uri uri) {
                                                                                        Log.d(TAG, "onSuccess: arm flag content done ");
                                                                                        progressDialog.setMessage("Uploading Country Information");

                                                                                        country.setArmFlagUrl(uri.toString());
                                                                                        country.getCountrySlider().setSliderContent(sliderContentDownloadUrl);
                                                                                        country.getDelicacies().setSliderContent(delicaciesContentDownloadUrl);

                                                                                        DatabaseUploader.saveCountryContent(country, new OnTaskCompleteListeners() {
                                                                                            @Override
                                                                                            public void onTaskSuccess() {
                                                                                                Log.d(TAG, "onTaskSuccess: data uploaded");
                                                                                                progressDialog.dismiss();
                                                                                                AlertDialog dialog=new AlertDialog.Builder(AddNewCountry.this)
                                                                                                        .setTitle("Message")
                                                                                                        .setMessage("Country Added Succesfully")
                                                                                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                                                                            @Override
                                                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                                                finish();
                                                                                                            }
                                                                                                        }).create();
                                                                                                dialog.show();
                                                                                            }

                                                                                            @Override
                                                                                            public void onTaskFail(String e) {

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

                    }
                });

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SELECT_SLIDER_IMAGE_CODE){
            if(resultCode==RESULT_OK){
                sliderContent.clear();
                if(data.getClipData()!=null){

                    for(int i=0;i<data.getClipData().getItemCount();i++){
                        sliderContent.add(data.getClipData().getItemAt(i).getUri());
                    }
                    selectedSliderImageText.setText(data.getClipData().getItemCount()+" Images Selected");

                }else if(data.getData()!=null){
                    Log.d(TAG, "onActivityResult: "+data.getData());
                    sliderContent.clear();
                    sliderContent.add(data.getData());
                    selectedSliderImageText.setText("1 Image Selected");

                }
            }
        }else if(requestCode==SELECT_SLIDER_VIDEO_CODE){

            if(data.getData()!=null){
                Log.d(TAG, "onActivityResult: "+data.getData());
                sliderContent.clear();
                sliderContent.add(data.getData());
                selectedSliderVideoText.setText("1 Video Selected");
            }
        }else if(requestCode==SELECT_FLAG_IMAGE_CODE){
            if(data.getData()!=null){
                flagUri=data.getData();
                selectedFlagText.setText("1 Flag Image Selected");
            }
        }else if(requestCode==SELECT_ARM_IMAGE_CODE){
            if(data.getData()!=null){
                armsFlagUri=data.getData();
                selectedArmFlagText.setText("1 Arm Flag Selected");
            }
        }else if(requestCode==SELECT_DELICACIES_IMAGE_CODE){
            if(resultCode==RESULT_OK){

                if(data.getClipData()!=null){
                    delicaciesContent.clear();

                    for(int i=0;i<data.getClipData().getItemCount();i++){
                        delicaciesContent.add(data.getClipData().getItemAt(i).getUri());
                    }
                    selectedDelicaciesImageText.setText(data.getClipData().getItemCount()+" Images Selected");

                }else if(data.getData()!=null){
                    Log.d(TAG, "onActivityResult: "+data.getData());
                    delicaciesContent.clear();
                    delicaciesContent.add(data.getData());
                    selectedDelicaciesImageText.setText("1 Image Selected");
                }
            }
        }else if(requestCode==SELECT_DELICACIES_VIDEO_CODE){

            if(data.getData()!=null){
                Log.d(TAG, "onActivityResult: "+data.getData());
                delicaciesContent.clear();
                delicaciesContent.add(data.getData());
                selectedDelicaciesVideoText.setText("1 Video Selected");

            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults[0]==PackageManager.PERMISSION_DENIED){
                finish();
            }
        }
    }

    private void viewInit(){
        mDataBinding= DataBindingUtil.setContentView(AddNewCountry.this,R.layout.activity_add_new_country);
        selectedSliderImageText=mDataBinding.addNewCountrySelectedImagesText;
        selectedSliderVideoText=mDataBinding.addNewCountrySelectedVideosText;
    }

    private void getImages(int code){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), code);
    }

    private void getImage(int code){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), code);
    }
    private void getVideos(int code){
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Video"), code);
    }
}