package com.e.caribbeanadmin.Listeners;

import com.e.caribbeanadmin.data_model.Item;

import java.util.List;

public interface OnItemLoadListeners {
    public void onItemLoaded(List<Item> itemList);
    public void onEmpty();
    public void onFailure(String e);
}
