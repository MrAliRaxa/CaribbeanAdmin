package com.e.caribbeanadmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.e.caribbeanadmin.Constants.ShopType;
import com.e.caribbeanadmin.DatabaseController.DatabaseAddresses;
import com.e.caribbeanadmin.Listeners.OnCategoryLoadListeners;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.Repository.Repository;
import com.e.caribbeanadmin.data_model.Shop;
import com.e.caribbeanadmin.data_model.ShopCategoryModel;
import com.e.caribbeanadmin.databinding.ActivityShopComponentManagementBinding;
import com.e.caribbeanadmin.fragments.shop_management_component.ATMLocationManagement;
import com.e.caribbeanadmin.fragments.shop_management_component.AddATMLocation;
import com.e.caribbeanadmin.fragments.shop_management_component.AddActivities;
import com.e.caribbeanadmin.fragments.shop_management_component.AddAttractions;
import com.e.caribbeanadmin.fragments.shop_management_component.AddBankLocations;
import com.e.caribbeanadmin.fragments.shop_management_component.AddBuildings;
import com.e.caribbeanadmin.fragments.shop_management_component.AddDealsAndPromotions;
import com.e.caribbeanadmin.fragments.shop_management_component.AddDirectory;
import com.e.caribbeanadmin.fragments.shop_management_component.AddEast;
import com.e.caribbeanadmin.fragments.shop_management_component.AddHistoricalSites;
import com.e.caribbeanadmin.fragments.shop_management_component.AddLocations;
import com.e.caribbeanadmin.fragments.shop_management_component.AddMenu;
import com.e.caribbeanadmin.fragments.shop_management_component.AddNorth;
import com.e.caribbeanadmin.fragments.shop_management_component.AddPrices;
import com.e.caribbeanadmin.fragments.shop_management_component.AddShopInformation;
import com.e.caribbeanadmin.fragments.shop_management_component.AddShowRoom;
import com.e.caribbeanadmin.fragments.shop_management_component.AddSouth;
import com.e.caribbeanadmin.fragments.shop_management_component.AddStore;
import com.e.caribbeanadmin.fragments.shop_management_component.AddTobago;
import com.e.caribbeanadmin.fragments.shop_management_component.AddWebsite;
import com.e.caribbeanadmin.fragments.shop_management_component.AddWest;
import com.e.caribbeanadmin.fragments.shop_management_component.AddWildLife;
import com.e.caribbeanadmin.fragments.shop_management_component.BankLocationManagement;
import com.e.caribbeanadmin.fragments.shop_management_component.LocationManagement;

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
                        replaceFragment(dealsAndPromotions);
                    });
                    mDataBinding.addMenu.setOnClickListener(v->{
                        AddMenu addMenu=new AddMenu();
                        replaceFragment(addMenu);
                    });
                    mDataBinding.addLocations.setOnClickListener(v->{

                        replaceFragment(new LocationManagement());
                    });
                    mDataBinding.addInformation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddShopInformation());
                        }
                    });




                }else if(shopCategoryModel.getViewType()== ShopType.DSLI){
                    mDataBinding.addDealsAndPromotions.setVisibility(View.VISIBLE);
                    mDataBinding.addShowroom.setVisibility(View.VISIBLE);
                    mDataBinding.addLocations.setVisibility(View.VISIBLE);
                    mDataBinding.addInformation.setVisibility(View.VISIBLE);

                    mDataBinding.addDealsAndPromotions.setOnClickListener(v->{
                        AddDealsAndPromotions dealsAndPromotions=new AddDealsAndPromotions();
                        replaceFragment(dealsAndPromotions);
                    });
                    mDataBinding.addShowroom.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddShowRoom());

                        }
                    });
                    mDataBinding.addLocations.setOnClickListener(v->{
                        replaceFragment(new LocationManagement());

                    });
                    mDataBinding.addInformation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddShopInformation());
                        }
                    });



                } else if(shopCategoryModel.getViewType()== ShopType.PSLI){
                    mDataBinding.addPrices.setVisibility(View.VISIBLE);
                    mDataBinding.addShowroom.setVisibility(View.VISIBLE);
                    mDataBinding.addLocations.setVisibility(View.VISIBLE);
                    mDataBinding.addInformation.setVisibility(View.VISIBLE);
                    mDataBinding.addPrices.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddPrices());

                        }
                    });
                    mDataBinding.addShowroom.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddShowRoom());

                        }
                    });
                    mDataBinding.addLocations.setOnClickListener(v->{
                        replaceFragment(new LocationManagement());

                    });
                    mDataBinding.addInformation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddShopInformation());
                        }
                    });

                }else if(shopCategoryModel.getViewType()== ShopType.DS){
                    mDataBinding.addDirectory.setVisibility(View.VISIBLE);
                    mDataBinding.addStores.setVisibility(View.VISIBLE);
                    mDataBinding.addDirectory.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddDirectory());
                        }
                    });
                    mDataBinding.addStores.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddStore());
                        }
                    });

                } else if(shopCategoryModel.getViewType()== ShopType.WABI){
                    mDataBinding.addWebsite.setVisibility(View.VISIBLE);
                    mDataBinding.addAtmLocations.setVisibility(View.VISIBLE);
                    mDataBinding.addBankLocations.setVisibility(View.VISIBLE);
                    mDataBinding.addInformation.setVisibility(View.VISIBLE);
                    mDataBinding.addWebsite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddWebsite());
                        }
                    });
                    mDataBinding.addAtmLocations.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new ATMLocationManagement());
                        }
                    });
                    mDataBinding.addInformation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddShopInformation());
                        }
                    });
                    mDataBinding.addBankLocations.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new BankLocationManagement());
                        }
                    });
                }else if(shopCategoryModel.getViewType()== ShopType.SL){
                    mDataBinding.addShowroom.setVisibility(View.VISIBLE);
                    mDataBinding.addLocations.setVisibility(View.VISIBLE);
                    mDataBinding.addShowroom.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddShowRoom());

                        }
                    });
                    mDataBinding.addLocations.setOnClickListener(v->{
                        replaceFragment(new LocationManagement());

                    });

                } else if(shopCategoryModel.getViewType()== ShopType.DDSI){
                    mDataBinding.addDirectory.setVisibility(View.VISIBLE);
                    mDataBinding.addDealsAndPromotions.setVisibility(View.VISIBLE);
                    mDataBinding.addStores.setVisibility(View.VISIBLE);
                    mDataBinding.addInformation.setVisibility(View.VISIBLE);
                    mDataBinding.addDealsAndPromotions.setOnClickListener(v->{
                        AddDealsAndPromotions dealsAndPromotions=new AddDealsAndPromotions();
                        replaceFragment(dealsAndPromotions);
                    });
                    mDataBinding.addDirectory.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddDirectory());
                        }
                    });
                    mDataBinding.addStores.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddStore());
                        }
                    });
                    mDataBinding.addInformation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddShopInformation());
                        }
                    });
                } else if(shopCategoryModel.getViewType()== ShopType.AABHW){
                    mDataBinding.addActivities.setVisibility(View.VISIBLE);
                    mDataBinding.addAttractions.setVisibility(View.VISIBLE);
                    mDataBinding.addBuildings.setVisibility(View.VISIBLE);
                    mDataBinding.addHistoricalSites.setVisibility(View.VISIBLE);
                    mDataBinding.addWildLife.setVisibility(View.VISIBLE);

                    mDataBinding.addActivities.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddActivities());
                        }
                    });

                    mDataBinding.addAttractions.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddAttractions());
                        }
                    });
                    mDataBinding.addBuildings.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddBuildings());
                        }
                    });
                    mDataBinding.addHistoricalSites.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddHistoricalSites());
                        }
                    });


                    mDataBinding.addWildLife.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddWildLife());
                        }
                    });





                } else if(shopCategoryModel.getViewType()== ShopType.NSEWT){
                    mDataBinding.addNorth.setVisibility(View.VISIBLE);
                    mDataBinding.addSouth.setVisibility(View.VISIBLE);
                    mDataBinding.addEast.setVisibility(View.VISIBLE);
                    mDataBinding.addWest.setVisibility(View.VISIBLE);
                    mDataBinding.addTobago.setVisibility(View.VISIBLE);

                    mDataBinding.addNorth.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddNorth());
                        }
                    });
                    mDataBinding.addSouth.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddSouth());
                        }
                    });
                    mDataBinding.addWest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddWest());
                        }
                    });
                    mDataBinding.addEast.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddEast());
                        }
                    });
                    mDataBinding.addTobago.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replaceFragment(new AddTobago());
                        }
                    });
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
        Bundle bundle=new Bundle();
        bundle.putParcelable("shop",shop);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.shopContainer,fragment).addToBackStack(null)
                .commit();
    }

}