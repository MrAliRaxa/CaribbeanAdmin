package com.e.caribbeanadmin.FireStorageController;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.e.caribbeanadmin.Listeners.OnFileUploadListeners;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.util.List;

public class FireStoreUploader {
    public static void uploadPhotos(Uri uri,String countryId, OnFileUploadListeners onFileUploadListeners){


            FireStorageAddresses.getSliderImageRef(countryId).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    onFileUploadListeners.onSuccess(taskSnapshot);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    onFileUploadListeners.onProgress(snapshot);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    onFileUploadListeners.onFailure(e.getMessage());
                }
            });

    }

    public static void uploadPhotos(List<Uri> uriList,String countryId, OnFileUploadListeners onFileUploadListeners){


        for(Uri uri:uriList){
            FireStorageAddresses.getSliderImageRef(countryId).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    onFileUploadListeners.onSuccess(taskSnapshot);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    onFileUploadListeners.onProgress(snapshot);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    onFileUploadListeners.onFailure(e.getMessage());
                }
            });
        }


    }
    public static void uploadVideos(Uri uri,String countryId, OnFileUploadListeners onFileUploadListeners){


            FireStorageAddresses.getSliderVideoRef(countryId).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    onFileUploadListeners.onSuccess(taskSnapshot);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    onFileUploadListeners.onProgress(snapshot);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    onFileUploadListeners.onFailure(e.getMessage());
                }
            });

    }
}
