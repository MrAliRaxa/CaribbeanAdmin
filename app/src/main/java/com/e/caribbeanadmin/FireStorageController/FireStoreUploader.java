package com.e.caribbeanadmin.FireStorageController;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.e.caribbeanadmin.Listeners.OnFileUploadListeners;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

public class FireStoreUploader {
    public static void uploadPhotos(Uri uri, OnFileUploadListeners onFileUploadListeners, StorageReference storageReference){


            storageReference.child(uri.getLastPathSegment()).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
    public static void uploadPhotos(Uri uri,String folderName, OnFileUploadListeners onFileUploadListeners, StorageReference storageReference){


        storageReference.child(folderName).child(uri.getLastPathSegment()).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
    public static void uploadPhotos(List<Uri> uriList, OnFileUploadListeners onFileUploadListeners, StorageReference storageReference){


        for(Uri uri:uriList){
            storageReference.child(uri.getLastPathSegment()).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
    public static void uploadPhotos(List<Uri> uriList,String folderName, OnFileUploadListeners onFileUploadListeners, StorageReference storageReference){


        for(Uri uri:uriList){
            storageReference.child(folderName).child(uri.getLastPathSegment()).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
    public static void uploadVideos(Uri uri, OnFileUploadListeners onFileUploadListeners, StorageReference storageReference){


            storageReference.child(uri.getLastPathSegment()).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
