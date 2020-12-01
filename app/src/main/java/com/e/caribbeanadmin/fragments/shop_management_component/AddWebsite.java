package com.e.caribbeanadmin.fragments.shop_management_component;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.caribbeanadmin.DatabaseController.DatabaseUploader;
import com.e.caribbeanadmin.Listeners.OnStringLoadListeners;
import com.e.caribbeanadmin.Listeners.OnTaskCompleteListeners;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.Repository.Repository;
import com.e.caribbeanadmin.data_model.Shop;
import com.e.caribbeanadmin.databinding.FragmentAddWebsiteBinding;


public class AddWebsite extends Fragment {


    private FragmentAddWebsiteBinding mDataBinding;
    private Shop shop;

    public AddWebsite() {
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
        mDataBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_add_website, container, false);

        Repository.getShopWebsite(shop.getId(), new OnStringLoadListeners() {
            @Override
            public void onStringLoaded(String s) {
                mDataBinding.websiteUrl.setText(s);
            }

            @Override
            public void onEmpty() {
                mDataBinding.websiteUrl.setText("Not Website Added");
            }

            @Override
            public void onFailure(String e) {
                mDataBinding.websiteUrl.setText("Error "+e);
            }
        });

        mDataBinding.addWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View weView=getLayoutInflater().inflate(R.layout.add_website_layout,null,false);
                AlertDialog dialog=new AlertDialog.Builder(getContext()).setView(weView).setNegativeButton("Cancel",null).create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                EditText web=weView.findViewById(R.id.addWebUrlEditText);
                Button add=weView.findViewById(R.id.addWebUrlAddBtn);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(web.getText().toString().isEmpty()){
                            web.setError("Empty Not Allowed");
                        }else{
                            DatabaseUploader.publishWebsite(shop.getId(), web.getText().toString(), new OnTaskCompleteListeners() {
                                @Override
                                public void onTaskSuccess() {
                                    Toast.makeText(getContext(), "Website Added", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }

                                @Override
                                public void onTaskFail(String e) {
                                    Toast.makeText(getContext(), "Error "+e, Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }
                });

            }
        });

        return mDataBinding.getRoot();
    }
}