package com.e.caribbeanadmin.Adaptor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.e.caribbeanadmin.Listeners.OnItemClickListeners;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.data_model.Item;
import com.e.caribbeanadmin.databinding.AddDealsBinding;
import com.e.caribbeanadmin.databinding.DealsPromotionsRowBinding;
import com.e.caribbeanadmin.fragments.shop_management_component.EditDeal;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;

import java.util.List;

import es.dmoral.toasty.Toasty;


public class DealsAdaptor extends RecyclerView.Adapter<DealsAdaptor.ViewHolder> {
    private Context context;
    private List<Item> itemList;
    private CollectionReference reference;
    public static CollectionReference collectionReference;


    public DealsAdaptor(Context context, List<Item> itemList, CollectionReference reference) {
        this.context = context;
        this.itemList = itemList;
        this.reference = reference;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DealsPromotionsRowBinding promotionsRowBinding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.deals_promotions_row,parent,false);
        return new ViewHolder(promotionsRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mDataBinding.dealsRowText.setText(itemList.get(position).getContent());
        Glide.with(context).load(itemList.get(position).getImageUrl()).override(512,512).into(holder.mDataBinding.dealsRowImage);
        holder.mDataBinding.dealDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference.document(itemList.get(position).getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        itemList.remove(position);
                        notifyItemRangeRemoved(position,itemList.size());
                        notifyItemRemoved(position);
                    }
                });

            }
        });
        holder.mDataBinding.dealEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new EditDeal(),itemList.get(position));
//                AddDealsBinding dealsBinding;
//                dealsBinding=DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.add_deals,null,false);
//                AlertDialog addDealsDialog=new AlertDialog.Builder(context).setView(dealsBinding.getRoot()).create();
//                dealsBinding.addItemBtn.setText("Update");
//                Glide.with(context).load(itemList.get(position).getImageUrl()).into(dealsBinding.addDealsImage);
//                dealsBinding.addDealsContent.setText(itemList.get(position).getContent());
//                addDealsDialog.show();
//                dealsBinding.addDealsImage.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(onItemClickListeners!=null){
//                            onItemClickListeners.onClick();
//                        }else{
//                            Toasty.error(context,"Image listener not added").show();
//                        }
//                    }
//                });
//
//                dealsBinding.addItemBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Item updatedItem;
//
//                        if(dealsBinding.addDealsContent.getText().toString().isEmpty()){
//                            Toasty.error(context,"empty description not allowed").show();
//                            return;
//                        }
//
//                        updatedItem=itemList.get(position);
//                        updatedItem.setContent(dealsBinding.addDealsContent.getText().toString());
//                        reference.document(itemList.get(position).getId()).set(updatedItem).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//
//                            }
//                        });
//                    }
//                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private DealsPromotionsRowBinding mDataBinding;
        public ViewHolder(@NonNull DealsPromotionsRowBinding itemView) {
            super(itemView.getRoot());
            mDataBinding=itemView;
        }
    }



    private void replaceFragment(Fragment fragment,Item item){
        Bundle bundle=new Bundle();
        bundle.putParcelable("item",item);
        fragment.setArguments(bundle);
        collectionReference=reference;
        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.shopContainer,fragment).addToBackStack(null)
                .commit();
    }
}
