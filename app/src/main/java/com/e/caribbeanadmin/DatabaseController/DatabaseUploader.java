package com.e.caribbeanadmin.DatabaseController;

import androidx.annotation.NonNull;


import com.e.caribbeanadmin.dataModel.Country;
import com.e.caribbeanadmin.dataModel.Shop;
import com.e.caribbeanadmin.dataModel.ShopCategoryModel;
import com.e.caribbeanadmin.dataModel.SliderContent;
import com.e.caribbeanadmin.dataModel.UserProfile;
import com.e.caribbeanadmin.Listeners.OnTaskCompleteListeners;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Calendar;

public class DatabaseUploader {

    public static void setUserRecord(UserProfile userProfile, OnTaskCompleteListeners onTaskCompleteListeners){
        DatabaseAddresses.getUserAccountCollection(userProfile.getUid())
                .set(userProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    onTaskCompleteListeners.onTaskSuccess();
                }else{
                    onTaskCompleteListeners.onTaskFail(task.getException().getMessage());
                }
            }
        });
    }

    public static void publishNewCategory(ShopCategoryModel shopCategoryModel,OnTaskCompleteListeners onTaskCompleteListeners){
        DatabaseAddresses.getShopCategoryCollection().document(shopCategoryModel.getId()).set(shopCategoryModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        onTaskCompleteListeners.onTaskSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                onTaskCompleteListeners.onTaskFail(e.getMessage());
            }
        });
    }
    public static void publishNewShop(Shop shop,OnTaskCompleteListeners onTaskCompleteListeners){
        DatabaseAddresses.getShopDocument(String.valueOf(Calendar.getInstance().getTimeInMillis()))
                .set(shop).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                onTaskCompleteListeners.onTaskSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                onTaskCompleteListeners.onTaskFail(e.getMessage());
            }
        });
    }

    public static void saveCountryContent(Country country, OnTaskCompleteListeners onTaskCompleteListeners){
        DatabaseAddresses.getCountriesCollection(country.getCountryId())
                .set(country).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    onTaskCompleteListeners.onTaskSuccess();
                }else{
                    onTaskCompleteListeners.onTaskFail(task.getException().getMessage());
                }
            }
        });
    }
    public static void saveShopCategoriesSliderContent(SliderContent sliderContent, OnTaskCompleteListeners onTaskCompleteListeners){
        DatabaseAddresses.getShopsCategorySliderDoc()
                .set(sliderContent).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    onTaskCompleteListeners.onTaskSuccess();
                }else{
                    onTaskCompleteListeners.onTaskFail(task.getException().getMessage());
                }
            }
        });
    }
    public static void saveShopSliderContent(SliderContent sliderContent, OnTaskCompleteListeners onTaskCompleteListeners){
        DatabaseAddresses.getShopsSliderDoc()
                .set(sliderContent).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    onTaskCompleteListeners.onTaskSuccess();
                }else{
                    onTaskCompleteListeners.onTaskFail(task.getException().getMessage());
                }
            }
        });
    }

}
