package com.e.caribbeanadmin.Listeners;

import com.e.caribbeanadmin.data_model.MenuItem;

public interface OnMenuItemLoadListeners {
    public void onMenuLoaded(MenuItem itemList);
    public void onEmpty();
    public void onFailure(String e);
}
