package com.e.caribbeanadmin.DatabaseController;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DatabaseAddresses {
    public static DocumentReference getUserAccountCollection(String docId){
        return FirebaseFirestore.getInstance().collection("UserAccounts")
                .document(docId);
    }

    public static DocumentReference getCountriesCollection(String docId){
        return FirebaseFirestore.getInstance().collection("CountriesCollection")
                .document(docId);
    }

    public static DocumentReference getShopDocument(String docId){
        return FirebaseFirestore.getInstance().collection("ShopCollection")
                .document(docId);
    }
    public static CollectionReference getShopCategoryCollection(){
        return FirebaseFirestore.getInstance().collection("ShopCategoryCollection");
    }
    public static DocumentReference getShopCategoryDocument(String docId){
        return FirebaseFirestore.getInstance().collection("ShopCategoryCollection")
                .document(docId);
    }

    public static DocumentReference getTourismSliderCollection(String docId){
        return FirebaseFirestore.getInstance().collection("TourismSliderCollection")
                .document("TourismSlider");
    }

}
