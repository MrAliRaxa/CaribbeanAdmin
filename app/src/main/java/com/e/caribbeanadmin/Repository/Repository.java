package com.e.caribbeanadmin.Repository;

import androidx.annotation.NonNull;

import com.e.caribbeanadmin.dataModel.ShopCategoryModel;
import com.e.caribbeanadmin.dataModel.UserProfile;
import com.e.caribbeanadmin.DatabaseController.DatabaseAddresses;
import com.e.caribbeanadmin.Listeners.OnShopCategoryLoadListeners;
import com.e.caribbeanadmin.Listeners.OnUserProfileLoadListeners;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Repository {
        public static void getMyProfile(String userId, OnUserProfileLoadListeners onUserProfileLoadListeners){
            DatabaseAddresses.getUserAccountCollection(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    onUserProfileLoadListeners.onUserProfileLoaded(documentSnapshot.toObject(UserProfile.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    onUserProfileLoadListeners.onFailure(e.getMessage());
                }
            });
        }

        public static void getShopCategories(OnShopCategoryLoadListeners onShopCategoryLoadListeners){
            DatabaseAddresses.getShopCategoryCollection().get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    List<ShopCategoryModel> shopCategoryModels=new ArrayList<>();
                    for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                        shopCategoryModels.add(documentSnapshot.toObject(ShopCategoryModel.class));
                    }

                    if(shopCategoryModels.size()>0){
                        onShopCategoryLoadListeners.onCategoriesLoaded(shopCategoryModels);
                    }else{
                        onShopCategoryLoadListeners.onEmpty();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    onShopCategoryLoadListeners.onFailure(e.getMessage());
                }
            });
        }
}
