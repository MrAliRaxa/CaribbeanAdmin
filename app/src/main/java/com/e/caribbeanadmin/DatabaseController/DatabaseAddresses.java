package com.e.caribbeanadmin.DatabaseController;

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
}
