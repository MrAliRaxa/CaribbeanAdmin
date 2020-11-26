package com.e.caribbeanadmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.e.caribbeanadmin.Adaptor.ShopAdaptor;
import com.e.caribbeanadmin.Listeners.OnShopLoadListeners;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.Repository.Repository;
import com.e.caribbeanadmin.dataModel.Shop;
import com.e.caribbeanadmin.databinding.ActivityShopManagementBinding;

import java.util.List;

public class ShopManagement extends AppCompatActivity {

    private ActivityShopManagementBinding mDataBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding= DataBindingUtil.setContentView(ShopManagement.this,R.layout.activity_shop_management);
        mDataBinding.shopRecyclerView.setLayoutManager(new LinearLayoutManager(ShopManagement.this));

        Repository.getAllShops(new OnShopLoadListeners() {
            @Override
            public void onShopsLoaded(List<Shop> shops) {
                mDataBinding.shopRecyclerView.setAdapter(new ShopAdaptor(ShopManagement.this,shops));
            }

            @Override
            public void onEmpty() {
                mDataBinding.shopManagementMsg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(String e) {
                mDataBinding.shopManagementMsg.setVisibility(View.VISIBLE);
                mDataBinding.shopManagementMsg.setText("Error "+e);
            }
        });



    }
}