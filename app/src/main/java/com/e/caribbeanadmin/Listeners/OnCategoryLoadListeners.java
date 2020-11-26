package com.e.caribbeanadmin.Listeners;


import com.e.caribbeanadmin.dataModel.ShopCategoryModel;

public interface OnCategoryLoadListeners {
    public void onCategoriesLoaded(ShopCategoryModel shopCategoryModel);
    public void onEmpty();
    public void onFailure(String e);
}
