package com.e.caribbeanadmin.Listeners;

import com.e.caribbeanadmin.dataModel.Item;
import com.e.caribbeanadmin.dataModel.ShopCategoryModel;

import java.util.List;

public interface OnItemLoadListeners {
    public void onItemLoaded(List<Item> itemList);
    public void onEmpty();
    public void onFailure(String e);
}
