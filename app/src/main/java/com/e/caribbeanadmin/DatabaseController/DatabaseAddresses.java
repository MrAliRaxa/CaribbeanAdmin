package com.e.caribbeanadmin.DatabaseController;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DatabaseAddresses {
    public static DocumentReference getUserAccountCollection(String docId){
        return FirebaseFirestore.getInstance().collection("UserAccounts")
                .document(docId);
    }
    public static CollectionReference getShopStoreCollection(){
        return FirebaseFirestore.getInstance().collection("ShopStoreItemCollection");
    }
    public static DocumentReference getCountriesCollection(String docId){
        return FirebaseFirestore.getInstance().collection("CountriesCollection")
                .document(docId);
    }
    public static CollectionReference getShopDirectoryCollection(){
        return FirebaseFirestore.getInstance().collection("ShopDirectoryItemCollection");
    }
    public static DocumentReference getShopDocument(String docId){
        return FirebaseFirestore.getInstance().collection("ShopCollection")
                .document(docId);
    }    public static CollectionReference getShopCollection(){
        return FirebaseFirestore.getInstance().collection("ShopCollection");
    }
    public static CollectionReference getShopCategoryCollection(){
        return FirebaseFirestore.getInstance().collection("ShopCategoryCollection");
    }

    public static DocumentReference getShopsCategorySliderDoc(){
        return FirebaseFirestore.getInstance().collection("SliderCollection")
                .document("ShopsCategorySlider");
    }
    public static DocumentReference getShopsSliderDoc(){
        return FirebaseFirestore.getInstance().collection("SliderCollection")
                .document("ShopsSlider");
    }

    public static CollectionReference getDealsCollection(){
        return FirebaseFirestore.getInstance().collection("DealsAndPromotions");
    }
    public static CollectionReference getShopMenuCollection(){
        return FirebaseFirestore.getInstance().collection("ShopMenu");
    }
    public static CollectionReference getShopLocationCollection(){
        return FirebaseFirestore.getInstance().collection("ShopLocations");
    }

    public static CollectionReference getShopInformationCollection(){
        return FirebaseFirestore.getInstance().collection("ShopInformation");
    }
}
