package com.e.caribbeanadmin.Listeners;

import com.e.caribbeanadmin.dataModel.Item;
import com.e.caribbeanadmin.dataModel.MenuItem;

import java.util.List;

public interface OnMenuItemLoadListeners {
    public void onMenuLoaded(MenuItem itemList);
    public void onEmpty();
    public void onFailure(String e);
}
