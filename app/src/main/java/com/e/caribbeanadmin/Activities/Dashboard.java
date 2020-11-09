package com.e.caribbeanadmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.databinding.ActivityMainBinding;

public class Dashboard extends AppCompatActivity {


    private ActivityMainBinding mDataBinding;
    private static final String TAG = "Dashboard";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding=DataBindingUtil.setContentView(Dashboard.this,R.layout.activity_main);


        mDataBinding.addNewCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,AddNewCountry.class));
            }
        });

        mDataBinding.addTourismExplorerSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,AddExplorerTourismSlider.class));
            }
        });
        mDataBinding.addTourismAddShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,AddNewShop.class));

            }
        });
        mDataBinding.addShopCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, ShopCategory.class));

            }
        });

    }
}