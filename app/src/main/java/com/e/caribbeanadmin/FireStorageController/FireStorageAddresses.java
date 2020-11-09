package com.e.caribbeanadmin.FireStorageController;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FireStorageAddresses {

    public static StorageReference getSliderContentRef(){
        return FirebaseStorage.getInstance().getReference("Content")
                .child("Country Data").child("ImagesAndVideos");
    }
    public static StorageReference getCountryContentRef(){
        return FirebaseStorage.getInstance().getReference("Content")
                .child("Country Data").child("Content");
    }
    public static StorageReference getShopRef(){
        return FirebaseStorage.getInstance().getReference("Shops")
                .child("Shop Data").child("Content");
    }

    public static StorageReference getShopCategoryRef(){
        return FirebaseStorage.getInstance().getReference("ShopCategories")
                .child("Category Data").child("Content");
    }
}
