package com.e.caribbeanadmin.DatabaseController;

import androidx.annotation.NonNull;


import com.e.caribbeanadmin.DataModel.Country;
import com.e.caribbeanadmin.DataModel.UserProfile;
import com.e.caribbeanadmin.Listeners.OnTaskCompleteListeners;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

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
}
