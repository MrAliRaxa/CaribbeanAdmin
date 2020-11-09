package com.e.caribbeanadmin.Listeners;

import com.e.caribbeanadmin.DataModel.ShopCategoryModel;

import java.util.List;

public interface OnShopCategoryLoadListeners {
    public void onCategoriesLoaded(List<ShopCategoryModel> shopCategoryModelList);
    public void onEmpty();
    public void onFailure(String e);
}
