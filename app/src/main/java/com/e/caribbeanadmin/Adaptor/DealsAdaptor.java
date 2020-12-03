package com.e.caribbeanadmin.Adaptor;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.data_model.Item;
import com.e.caribbeanadmin.databinding.AddDealsBinding;
import com.e.caribbeanadmin.databinding.DealsPromotionsRowBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;

import java.util.List;


public class DealsAdaptor extends RecyclerView.Adapter<DealsAdaptor.ViewHolder> {
    private Context context;
    private List<Item> itemList;
    private CollectionReference reference;

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
                AddDealsBinding dealsBinding;
                dealsBinding=DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.add_deals,null,false);
                AlertDialog addDealsDialog=new AlertDialog.Builder(context).setView(dealsBinding.getRoot()).create();
                dealsBinding.addItemBtn.setText("Update");
                Glide.with(context).load(itemList.get(position).getImageUrl()).into(dealsBinding.addDealsImage);
                dealsBinding.addDealsContent.setText(itemList.get(position).getContent());
                addDealsDialog.show();
                dealsBinding.addItemBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
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
}
