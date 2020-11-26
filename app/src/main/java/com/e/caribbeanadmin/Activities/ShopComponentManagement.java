package com.e.caribbeanadmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.e.caribbeanadmin.Constants.ShopType;
import com.e.caribbeanadmin.Listeners.OnCategoryLoadListeners;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.Repository.Repository;
import com.e.caribbeanadmin.dataModel.Shop;
import com.e.caribbeanadmin.dataModel.ShopCategoryModel;
import com.e.caribbeanadmin.databinding.ActivityShopComponentManagementBinding;
import com.e.caribbeanadmin.fragments.shop_management_component.AddDealsAndPromotions;
import com.e.caribbeanadmin.fragments.shop_management_component.AddLocations;
import com.e.caribbeanadmin.fragments.shop_management_component.AddMenu;

public class ShopComponentManagement extends AppCompatActivity {

    private Shop shop;
    private ActivityShopComponentManagementBinding mDataBinding;
    private static final String TAG = "ShopComponentManagement";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding=DataBindingUtil.setContentView(ShopComponentManagement.this,R.layout.activity_shop_component_management);

        if(getIntent().getParcelableExtra("shop")!=null){
            shop=getIntent().getParcelableExtra("shop");
        }
        Repository.getShopCategory(shop.getCategoryId(), new OnCategoryLoadListeners() {
            @Override
            public void onCategoriesLoaded(ShopCategoryModel shopCategoryModel) {

                if(shopCategoryModel.getViewType()== ShopType.DMLI){
                    mDataBinding.addDealsAndPromotions.setVisibility(View.VISIBLE);
                    mDataBinding.addMenu.setVisibility(View.VISIBLE);
                    mDataBinding.addLocations.setVisibility(View.VISIBLE);
                    mDataBinding.addInformation.setVisibility(View.VISIBLE);


                    mDataBinding.addDealsAndPromotions.setOnClickListener(v->{
                        AddDealsAndPromotions dealsAndPromotions=new AddDealsAndPromotions();
                        Bundle bundle=new Bundle();
                        bundle.putParcelable("shop",shop);
                        dealsAndPromotions.setArguments(bundle);
                        replaceFragment(dealsAndPromotions);
                    });
                    mDataBinding.addMenu.setOnClickListener(v->{
                        AddMenu addMenu=new AddMenu();
                        Bundle bundle=new Bundle();
                        bundle.putParcelable("shop",shop);
                        addMenu.setArguments(bundle);
                        replaceFragment(addMenu);
                    });
                    mDataBinding.addLocations.setOnClickListener(v->{
                        AddLocations locations=new AddLocations();
                        Bundle bundle=new Bundle();
                        bundle.putParcelable("shop",shop);
                        locations.setArguments(bundle);
                        replaceFragment(locations);
                        Log.d(TAG, "onCategoriesLoaded: ");
                    });


                }else if(shopCategoryModel.getViewType()== ShopType.DSLI){
                    mDataBinding.addDealsAndPromotions.setVisibility(View.VISIBLE);
                    mDataBinding.addShowroom.setVisibility(View.VISIBLE);
                    mDataBinding.addLocations.setVisibility(View.VISIBLE);
                    mDataBinding.addInformation.setVisibility(View.VISIBLE);
                } else if(shopCategoryModel.getViewType()== ShopType.PSLI){
                    mDataBinding.addPrices.setVisibility(View.VISIBLE);
                    mDataBinding.addShowroom.setVisibility(View.VISIBLE);
                    mDataBinding.addLocations.setVisibility(View.VISIBLE);
                    mDataBinding.addInformation.setVisibility(View.VISIBLE);
                }else if(shopCategoryModel.getViewType()== ShopType.DS){
                    mDataBinding.addDirectory.setVisibility(View.VISIBLE);
                    mDataBinding.addStores.setVisibility(View.VISIBLE);
                } else if(shopCategoryModel.getViewType()== ShopType.WABI){
                    mDataBinding.addWebsite.setVisibility(View.VISIBLE);
                    mDataBinding.addAtmLocations.setVisibility(View.VISIBLE);
                    mDataBinding.addBankLocations.setVisibility(View.VISIBLE);
                    mDataBinding.addInformation.setVisibility(View.VISIBLE);
                }else if(shopCategoryModel.getViewType()== ShopType.SL){
                    mDataBinding.addShowroom.setVisibility(View.VISIBLE);
                    mDataBinding.addLocations.setVisibility(View.VISIBLE);
                } else if(shopCategoryModel.getViewType()== ShopType.DDSI){
                    mDataBinding.addDirectory.setVisibility(View.VISIBLE);
                    mDataBinding.addDealsAndPromotions.setVisibility(View.VISIBLE);
                    mDataBinding.addStores.setVisibility(View.VISIBLE);
                    mDataBinding.addInformation.setVisibility(View.VISIBLE);
                } else if(shopCategoryModel.getViewType()== ShopType.AABHW){
                    mDataBinding.addActivities.setVisibility(View.VISIBLE);
                    mDataBinding.addAttractions.setVisibility(View.VISIBLE);
                    mDataBinding.addBuildings.setVisibility(View.VISIBLE);
                    mDataBinding.addHistoricalSites.setVisibility(View.VISIBLE);
                    mDataBinding.addWildLife.setVisibility(View.VISIBLE);
                } else if(shopCategoryModel.getViewType()== ShopType.NSEWT){
                    mDataBinding.addNorth.setVisibility(View.VISIBLE);
                    mDataBinding.addSouth.setVisibility(View.VISIBLE);
                    mDataBinding.addEast.setVisibility(View.VISIBLE);
                    mDataBinding.addWest.setVisibility(View.VISIBLE);
                    mDataBinding.addTobago.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onEmpty() {

                Toast.makeText(ShopComponentManagement.this, "Shop not found", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String e) {

                Toast.makeText(ShopComponentManagement.this, "Error "+e, Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.shopContainer,fragment).addToBackStack(null)
                .commit();
    }

}