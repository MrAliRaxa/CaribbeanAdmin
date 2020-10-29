package com.e.caribbeanadmin.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
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
import com.e.caribbeanadmin.DatabaseController.DatabaseAddresses;
import com.e.caribbeanadmin.DatabaseController.DatabaseUploader;
import com.e.caribbeanadmin.FireStorageController.FireStorageAddresses;
import com.e.caribbeanadmin.FireStorageController.FireStoreUploader;
import com.e.caribbeanadmin.Listeners.OnFileUploadListeners;
import com.e.caribbeanadmin.Listeners.OnTaskCompleteListeners;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.databinding.ActivityAddNewCountryBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
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
    private List<Uri> delicaciesContent;
    private Uri flagUri;
    private Uri armsFlagUri;
    private Country country;

    private boolean isSliderContentUploaded;
    private boolean isDelicaciesContentUploaded;
    private boolean isFlagUploaded;
    private boolean isArmFlagUploaded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewInit();
        country=new Country();
        sliderContent=new ArrayList<>();
        delicaciesContent=new ArrayList<>();

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
        AlertDialog dialog=new AlertDialog.Builder(AddNewCountry.this).create();
        dialog.setTitle("Loading . . .");
        dialog.setMessage(" ");
        dialog.setCanceledOnTouchOutside(false);
        mDataBinding.saveContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                country.getInformation().setName(mDataBinding.addNewCountryName.getText().toString());
                country.getInformation().setMotto(mDataBinding.addNewCountryMotto.getText().toString());
                country.getInformation().setLanguage(mDataBinding.addNewCountryLanguage.getText().toString());
                country.getInformation().setCapital(mDataBinding.addNewCountryCapital.getText().toString());
                country.getInformation().setTemperature(Double.parseDouble(mDataBinding.addNewCountryTemperature.getText().toString()));
                country.getInformation().setCurrencyName(mDataBinding.addNewCountryCurrency.getText().toString());
                country.setHistory(mDataBinding.addNewCountryHistory.getText().toString());
                country.getInformation().setPopulation(Integer.parseInt(mDataBinding.addNewCountryPopulation.getText().toString()));
                country.setCountryId(String.valueOf(Calendar.getInstance().getTimeInMillis()));



                dialog.show();

                DatabaseUploader.saveCountryContent(country, new OnTaskCompleteListeners() {
                    @Override
                    public void onTaskSuccess() {
                        Toast.makeText(AddNewCountry.this, "Data Saved", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                    @Override
                    public void onTaskFail(String e) {
                        Toast.makeText(AddNewCountry.this, "Fail to Save data", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });


            }
        });

        mDataBinding.addNewCountryAddSliderImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getVideos(SELECT_SLIDER_IMAGE_CODE);

            }
        });

        mDataBinding.addNewCountryAddSliderImagesPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[] i = {0};
                if(sliderContent.size()>0){
                    dialog.show();
                    FireStoreUploader.uploadPhotos(sliderContent, country.getCountryId(), new OnFileUploadListeners() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            i[0]++;


                            if(i[0] ==sliderContent.size())
                            {
                                dialog.dismiss();
                                Toast.makeText(AddNewCountry.this, "Image is saved", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            dialog.setTitle("Remaining "+i[0]+"/"+sliderContent.size()+"  "+taskSnapshot.getBytesTransferred()/1024);
                        }

                        @Override
                        public void onFailure(String e) {
                            dialog.dismiss();
                        }
                    });
                }else {
                    Toast.makeText(AddNewCountry.this, "Please Select Images", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mDataBinding.addNewCountryAddSliderVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                getImages(SELECT_DELICACIES_IMAGE_CODE);
            }
        });

        mDataBinding.addNewCountryDelicaciesVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVideos(SELECT_DELICACIES_VIDEO_CODE);
            }
        });

//        mDataBinding.uploadData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//




//                if(sliderImagesUrl.size()>0){
//
//                    final int[] totalFiles={0};
//                    final int[] currentStatusFile={0};
//
//                    final long[] totalUploadSize = {0};
//                    final int[] currentUploadingSize = {0};
//
//
//                    List<String> downloadAbleUrls=new ArrayList<>();
//                    totalFiles[0]=sliderImagesUrl.size();
//
//
//
//
//                    for(Uri url:sliderImagesUrl){
//
//                        FireStorageAddresses.getSliderImageRef(country.getCountryId()).child(url.getLastPathSegment()).putFile(url)
//                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                    @Override
//                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                            @Override
//                                            public void onSuccess(Uri uri) {
//                                                downloadAbleUrls.add(String.valueOf(uri));
//                                                if(currentStatusFile[0]==totalFiles[0]){
//                                                    Log.d(TAG, "onSuccess: finished");
//                                                    dialog.setMessage("Uploading... Country information ");
//                                                    country.getCountrySlider().setImages(downloadAbleUrls);
//                                                    DatabaseAddresses.getCountriesCollection(country.getCountryId()).set(country)
//                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                @Override
//                                                                public void onSuccess(Void aVoid) {
//                                                                    dialog.dismiss();
//                                                                }
//                                                            }).addOnFailureListener(new OnFailureListener() {
//                                                        @Override
//                                                        public void onFailure(@NonNull Exception e) {
//                                                            Toast.makeText(AddNewCountry.this, "Fail to upload", Toast.LENGTH_SHORT).show();
//                                                        }
//                                                    });
//                                                }
//                                            }
//                                        });
//
//                                        currentStatusFile[0] +=1;
//                                        Log.d(TAG, "onSuccess: "+currentStatusFile[0]+"/"+totalFiles[0]);
//                                        dialog.setMessage(" Uploading "+currentStatusFile[0]+"/"+totalFiles[0]+"   "+currentUploadingSize[0]+"KB"+"\n");
//
//                                    }
//                                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                                currentUploadingSize[0]+=snapshot.getBytesTransferred()/1024;
//                                dialog.setMessage(" Uploading "+currentStatusFile[0]+"/"+totalFiles[0]+"   "+currentUploadingSize[0]+"KB"+"\n");
//
//                            }
//                        });
//                    }
//                }else{
//                    Log.d(TAG, "onClick: no image selected");
//                }
//            }
//        });

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