package com.e.caribbeanadmin.DatabaseController;

import androidx.annotation.NonNull;


import com.e.caribbeanadmin.data_model.Country;
import com.e.caribbeanadmin.data_model.Item;
import com.e.caribbeanadmin.data_model.MenuItem;
import com.e.caribbeanadmin.data_model.Shop;
import com.e.caribbeanadmin.data_model.ShopCategoryModel;
import com.e.caribbeanadmin.data_model.ShopInformationModel;
import com.e.caribbeanadmin.data_model.ShopLocation;
import com.e.caribbeanadmin.data_model.SliderContent;
import com.e.caribbeanadmin.data_model.UserProfile;
import com.e.caribbeanadmin.Listeners.OnTaskCompleteListeners;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;

import java.util.HashMap;
import java.util.Map;

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
    public static void publishItem(Item item, CollectionReference collectionReference, OnTaskCompleteListeners onTaskCompleteListeners){
        collectionReference.document(item.getId()).set(item).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public static void publishMenu(MenuItem menuItem,CollectionReference reference,OnTaskCompleteListeners onTaskCompleteListeners){
        reference.document(menuItem.getShopId()).set(menuItem)
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
    public static void publishShopLocation(ShopLocation shopLocation,CollectionReference reference,OnTaskCompleteListeners onTaskCompleteListeners){
        reference.document(shopLocation.getId()).set(shopLocation)
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
    public static void publishWebsite(String shopId,String websiteLink,OnTaskCompleteListeners onTaskCompleteListeners){
        Map<String,Object> map=new HashMap<>();
        map.put("WebUrl",websiteLink);
        DatabaseAddresses.getShopWebsiteCollection().document(shopId).set(map)
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
