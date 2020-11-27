package com.e.caribbeanadmin.DatabaseController;

import androidx.annotation.NonNull;


import com.e.caribbeanadmin.dataModel.Country;
import com.e.caribbeanadmin.dataModel.Item;
import com.e.caribbeanadmin.dataModel.MenuItem;
import com.e.caribbeanadmin.dataModel.Shop;
import com.e.caribbeanadmin.dataModel.ShopCategoryModel;
import com.e.caribbeanadmin.dataModel.ShopInformationModel;
import com.e.caribbeanadmin.dataModel.ShopLocation;
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
        DatabaseAddresses.getShopDocument(shop.getId())
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
    public static void publishDeal(Item item,OnTaskCompleteListeners onTaskCompleteListeners){
        DatabaseAddresses.getDealsCollection().document(item.getId()).set(item).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public static void publishMenu(MenuItem menuItem,OnTaskCompleteListeners onTaskCompleteListeners){
        DatabaseAddresses.getShopMenuCollection().document(menuItem.getShopId()).set(menuItem)
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

    public static void publishShopLocation(ShopLocation shopLocation,OnTaskCompleteListeners onTaskCompleteListeners){
        DatabaseAddresses.getShopLocationCollection().document(shopLocation.getId()).set(shopLocation)
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
    public static void publishShopInformation(ShopInformationModel shopInformationModel, OnTaskCompleteListeners onTaskCompleteListeners){
        DatabaseAddresses.getShopInformationCollection().document(shopInformationModel.getShopId()).set(shopInformationModel)
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
}
