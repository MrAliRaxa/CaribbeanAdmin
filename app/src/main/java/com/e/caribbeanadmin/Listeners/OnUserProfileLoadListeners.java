package com.e.caribbeanadmin.Listeners;

import com.e.caribbeanadmin.data_model.UserProfile;

public interface OnUserProfileLoadListeners {
    public void onUserProfileLoaded(UserProfile userProfile);
    public void onFailure(String e);
}
