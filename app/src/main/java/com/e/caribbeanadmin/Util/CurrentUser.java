package com.e.caribbeanadmin.Util;

import com.e.caribbeanadmin.DataModel.UserProfile;
import com.e.caribbeanadmin.Exceptions.CurrentUserNotFoundException;
import com.google.firebase.firestore.auth.User;

public class CurrentUser {

    private static UserProfile userProfile;

    public static UserProfile getInstance() throws CurrentUserNotFoundException {

        if(userProfile!=null){
            return userProfile;
        }else{
            throw new CurrentUserNotFoundException();
        }
    }

    public static UserProfile getUserProfile() {
        return userProfile;
    }

    public static void setUserProfile(UserProfile userProfile) {
        CurrentUser.userProfile = userProfile;
    }
}
