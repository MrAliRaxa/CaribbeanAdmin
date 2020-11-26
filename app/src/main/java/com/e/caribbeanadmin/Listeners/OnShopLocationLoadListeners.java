package com.e.caribbeanadmin.Listeners;

import com.e.caribbeanadmin.dataModel.MenuItem;
import com.e.caribbeanadmin.dataModel.ShopLocation;

import java.util.List;

public interface OnShopLocationLoadListeners {
    public void onLocationsLoaded(List<ShopLocation> locationList);
    public void onEmpty();
    public void onFailure(String e);
}
