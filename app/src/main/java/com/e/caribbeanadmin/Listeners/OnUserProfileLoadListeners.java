package com.e.caribbeanadmin.Listeners;

import com.e.caribbeanadmin.DataModel.UserProfile;
import com.google.firebase.firestore.auth.User;

import java.nio.file.attribute.UserPrincipal;

public interface OnUserProfileLoadListeners {
    public void onUserProfileLoaded(UserProfile userProfile);
    public void onFailure(String e);
}
