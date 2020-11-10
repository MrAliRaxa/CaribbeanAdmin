package com.e.caribbeanadmin.Listeners;

import com.e.caribbeanadmin.dataModel.UserProfile;

public interface OnUserProfileLoadListeners {
    public void onUserProfileLoaded(UserProfile userProfile);
    public void onFailure(String e);
}
