package com.e.caribbeanadmin.FireStorageController;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FireStorageAddresses {

    public static StorageReference getSliderImageRef(String countryId){
        return FirebaseStorage.getInstance().getReference("StorageContent")
                .child(countryId).child("Images");
    }
    public static StorageReference getCountryContentRef(String countryId){
        return FirebaseStorage.getInstance().getReference("CountryContent")
                .child(countryId).child("Content");
    }

    public static StorageReference getSliderVideoRef(String countryId){
        return FirebaseStorage.getInstance().getReference("StorageContent")
                .child(countryId).child("videos");
    }
}
