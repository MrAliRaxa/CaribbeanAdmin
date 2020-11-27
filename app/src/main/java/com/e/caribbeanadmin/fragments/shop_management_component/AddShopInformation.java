package com.e.caribbeanadmin.fragments.shop_management_component;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.caribbeanadmin.DatabaseController.DatabaseUploader;
import com.e.caribbeanadmin.Listeners.OnTaskCompleteListeners;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.dataModel.Shop;
import com.e.caribbeanadmin.dataModel.ShopInformationModel;
import com.e.caribbeanadmin.databinding.FragmentAddShopInformationBinding;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;


public class AddShopInformation extends Fragment {


    private FragmentAddShopInformationBinding mDataBinding;
    private Shop shop;


    public AddShopInformation() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null){
            shop=getArguments().getParcelable("shop");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mDataBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_add_shop_information, container, false);

        mDataBinding.publishShopInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDataBinding.addShopInformationAboutUs.getText().toString().isEmpty()){
                    mDataBinding.addShopInformationAboutUs.setError("Empty Not Allowed");
                    mDataBinding.addShopInformationAboutUs.requestFocus();
                }else if(mDataBinding.mondayOpenTime.getText().toString().isEmpty()||mDataBinding.mondayCloseTime.getText().toString().isEmpty()){
                    mDataBinding.mondayOpenTime.setError("Invalid");
                    mDataBinding.mondayCloseTime.setError("Invalid");
                    mDataBinding.mondayCloseTime.requestFocus();
                }else if(mDataBinding.tuesdayOpenTime.getText().toString().isEmpty()||mDataBinding.tuesdayCloseTime.getText().toString().isEmpty()){
                    mDataBinding.tuesdayOpenTime.setError("Invalid");
                    mDataBinding.tuesdayCloseTime.setError("Invalid");
                    mDataBinding.tuesdayCloseTime.requestFocus();

                }else if(mDataBinding.wednesdayOpenTime.getText().toString().isEmpty()||mDataBinding.wednesdayCloseTime.getText().toString().isEmpty()){
                    mDataBinding.wednesdayOpenTime.setError("Invalid");
                    mDataBinding.wednesdayCloseTime.setError("Invalid");
                    mDataBinding.wednesdayOpenTime.requestFocus();
                }else if(mDataBinding.thursdayOpenTime.getText().toString().isEmpty()||mDataBinding.thursdayCloseTime.getText().toString().isEmpty()){
                    mDataBinding.thursdayOpenTime.setError("Invalid");
                    mDataBinding.thursdayCloseTime.setError("Invalid");
                    mDataBinding.thursdayOpenTime.requestFocus();
                }else if(mDataBinding.fridayOpenTime.getText().toString().isEmpty()||mDataBinding.fridayCloseTime.getText().toString().isEmpty()){
                    mDataBinding.fridayOpenTime.setError("Invalid");
                    mDataBinding.fridayCloseTime.setError("Invalid");
                    mDataBinding.fridayOpenTime.requestFocus();

                }else if(mDataBinding.saturdayOpenTime.getText().toString().isEmpty()||mDataBinding.saturdayCloseTime.getText().toString().isEmpty()){
                    mDataBinding.saturdayOpenTime.setError("Invalid");
                    mDataBinding.saturdayCloseTime.setError("Invalid");
                    mDataBinding.saturdayCloseTime.requestFocus();

                }else if(mDataBinding.sundayOpenTime.getText().toString().isEmpty()||mDataBinding.sundayCloseTime.getText().toString().isEmpty()){
                    mDataBinding.sundayOpenTime.setError("Invalid");
                    mDataBinding.sundayCloseTime.setError("Invalid");
                    mDataBinding.sundayOpenTime.requestFocus();

                }else if(mDataBinding.contactUsEmail.getText().toString().isEmpty()){
                    mDataBinding.contactUsEmail.setError("Empty not allowed");
                    mDataBinding.contactUsEmail.requestFocus();
                }else if(mDataBinding.contactUsPhone.getText().toString().isEmpty()){
                    mDataBinding.contactUsPhone.requestFocus();
                }else{

                    ShopInformationModel shopInformationModel=new ShopInformationModel();
                    shopInformationModel.setShopId(shop.getId());
                    shopInformationModel.setId(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                    shopInformationModel.setPhone(mDataBinding.contactUsPhone.getText().toString());
                    shopInformationModel.setAboutUs(mDataBinding.addShopInformationAboutUs.getText().toString());
                    shopInformationModel.setEmail(mDataBinding.contactUsEmail.getText().toString());
                    shopInformationModel.setEmail(mDataBinding.contactUsPhone.getText().toString());
                    shopInformationModel.setSundayTiming(mDataBinding.sundayOpenTime.getText().toString()+" to "+mDataBinding.sundayCloseTime.getText().toString());
                    shopInformationModel.setMondayTiming(mDataBinding.mondayOpenTime.getText().toString()+" to "+mDataBinding.mondayCloseTime.getText().toString());
                    shopInformationModel.setTuesdayTiming(mDataBinding.tuesdayOpenTime.getText().toString()+" to "+mDataBinding.tuesdayCloseTime.getText().toString());
                    shopInformationModel.setThursdayTiming(mDataBinding.thursdayOpenTime.getText().toString()+" to "+mDataBinding.thursdayCloseTime.getText().toString());
                    shopInformationModel.setWednesdayTiming(mDataBinding.wednesdayCloseTime.getText().toString()+" to "+mDataBinding.fridayCloseTime.getText().toString());
                    shopInformationModel.setFridayTiming(mDataBinding.fridayOpenTime.getText().toString()+" to "+mDataBinding.fridayCloseTime.getText().toString());
                    shopInformationModel.setSaturdayTiming(mDataBinding.saturdayOpenTime.getText().toString()+" to "+mDataBinding.saturdayCloseTime.getText().toString());

                    DatabaseUploader.publishShopInformation(shopInformationModel, new OnTaskCompleteListeners() {
                        @Override
                        public void onTaskSuccess() {
                            Toasty.success(getContext(),"Shop Information Added",Toasty.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().popBackStack();
                        }

                        @Override
                        public void onTaskFail(String e) {
                            Toasty.error(getContext(),"Error "+e).show();
                        }
                    });

                }
            }
        });

        return mDataBinding.getRoot();
    }
}