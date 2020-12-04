package com.e.caribbeanadmin.fragments.shop_management_component;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.e.caribbeanadmin.Adaptor.DealsAdaptor;
import com.e.caribbeanadmin.FireStorageController.FireStorageAddresses;
import com.e.caribbeanadmin.FireStorageController.FireStoreUploader;
import com.e.caribbeanadmin.Listeners.OnFileUploadListeners;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.Util.DialogBuilder;
import com.e.caribbeanadmin.data_model.Item;
import com.e.caribbeanadmin.databinding.AddDealsBinding;
import com.e.caribbeanadmin.databinding.FragmentEditDealBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.storage.UploadTask;

import es.dmoral.toasty.Toasty;

public class EditDeal extends Fragment {


    private FragmentEditDealBinding mDataBinding;

    private Item item;
    private CollectionReference reference;
    private Uri imageUri;
    public EditDeal() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null){
            item=getArguments().getParcelable("item");
        }
        if(DealsAdaptor.collectionReference!=null){
            reference=DealsAdaptor.collectionReference;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mDataBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_edit_deal, container, false);

        if(item!=null&&reference!=null){
            AddDealsBinding dealsBinding;
            dealsBinding=mDataBinding.addDeals;
            dealsBinding.addItemBtn.setText("Update");
            Glide.with(getContext()).load(item.getImageUrl()).into(dealsBinding.addDealsImage);
            dealsBinding.addDealsImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, 1);
                }
            });
            dealsBinding.addDealsContent.setText(item.getContent());
            dealsBinding.addItemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Item updatedItem;

                    if(dealsBinding.addDealsContent.getText().toString().isEmpty()){
                        Toasty.error(getContext(),"empty description not allowed").show();
                        return;
                    }

                    updatedItem=item;
                    updatedItem.setContent(dealsBinding.addDealsContent.getText().toString());
                    ProgressDialog progressDialog= DialogBuilder.getSimpleLoadingDialog(getContext(),"Loading","Uploading data");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    if(imageUri!=null){
                        FireStoreUploader.uploadPhotos(imageUri, new OnFileUploadListeners() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        updatedItem.setImageUrl(String.valueOf(uri));
                                        reference.document(item.getId()).set(updatedItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toasty.success(getContext(),"Item Updated").show();
                                                progressDialog.dismiss();
                                                getActivity().getSupportFragmentManager().popBackStack();


                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toasty.success(getContext(),"Error "+e.getMessage()).show();
                                                progressDialog.dismiss();

                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toasty.success(getContext(),"Error "+e.getMessage()).show();

                                    }
                                });
                            }

                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            }

                            @Override
                            public void onFailure(String e) {

                            }
                        }, FireStorageAddresses.getShopComponents());
                    }else{
                        reference.document(item.getId()).set(updatedItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toasty.success(getContext(),"Item Updated").show();
                                progressDialog.dismiss();
                                getActivity().getSupportFragmentManager().popBackStack();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toasty.error(getContext(),"Error "+e.getMessage()).show();
                                progressDialog.dismiss();

                            }
                        });
                    }

                }
            });
        }
        return mDataBinding.getRoot();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1) {
            if(data!=null&&data.getData()!=null){
                mDataBinding.addDeals.addDealsImage.setImageURI(data.getData());
                imageUri=data.getData();
            }
        }
    }
}