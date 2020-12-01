package com.e.caribbeanadmin.Listeners;


import com.e.caribbeanadmin.data_model.ShopCategoryModel;

public interface OnCategoryLoadListeners {
    public void onCategoriesLoaded(ShopCategoryModel shopCategoryModel);
    public void onEmpty();
    public void onFailure(String e);
}
