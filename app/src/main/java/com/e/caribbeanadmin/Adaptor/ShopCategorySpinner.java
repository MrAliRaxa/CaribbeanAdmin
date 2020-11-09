package com.e.caribbeanadmin.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.e.caribbeanadmin.DataModel.ShopCategoryModel;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.databinding.ShopCategoryViewLayoutBinding;

import java.util.List;

public class ShopCategorySpinner extends BaseAdapter {

    private List<ShopCategoryModel> shopCategoryModels;
    private Context context;

    public ShopCategorySpinner(List<ShopCategoryModel> shopCategoryModels, Context context) {
        this.shopCategoryModels = shopCategoryModels;
        this.context = context;
    }

    @Override
    public int getCount() {
        return shopCategoryModels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ShopCategoryViewLayoutBinding categoryViewLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.shop_category_view_layout,parent,false);
        categoryViewLayoutBinding.shopCategoryViewTitle.setText(shopCategoryModels.get(position).getTitle());
        String[] viewTypes=context.getResources().getStringArray(R.array.shopViewCategory);
        categoryViewLayoutBinding.shopCategoryViewSubTitle.setText("("+viewTypes[position+1]+")");
        return  categoryViewLayoutBinding.getRoot();
    }
}
