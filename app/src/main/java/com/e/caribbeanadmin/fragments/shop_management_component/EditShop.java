package com.e.caribbeanadmin.fragments.shop_management_component;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.databinding.FragmentEditShopBinding;


public class EditShop extends Fragment {


    private FragmentEditShopBinding mDataBinding;
    public EditShop() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mDataBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_edit_shop, container, false);

        return mDataBinding.getRoot();
    }
}