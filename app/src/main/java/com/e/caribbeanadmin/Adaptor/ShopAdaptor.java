package com.e.caribbeanadmin.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.e.caribbeanadmin.Activities.ShopComponentManagement;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.dataModel.Shop;
import com.e.caribbeanadmin.databinding.ShopManagementRowBinding;

import java.util.List;

public class ShopAdaptor extends RecyclerView.Adapter<ShopAdaptor.ViewHolder> {

    private Context context;
    private List<Shop> shops;

    public ShopAdaptor(Context context, List<Shop> shops) {
        this.context = context;
        this.shops = shops;
    }

    @NonNull
    @Override
    public ShopAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        ShopManagementRowBinding rowBinding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.shop_management_row,parent,false);
        return new ViewHolder(rowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopAdaptor.ViewHolder holder, int position) {
        holder.binding.shopName.setText(shops.get(position).getName());
        Glide.with(context).load(shops.get(position).getLogoUrl()).into(holder.binding.shopImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ShopComponentManagement.class);
                intent.putExtra("shop",shops.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ShopManagementRowBinding binding;
        public ViewHolder(@NonNull ShopManagementRowBinding itemView) {
            super(itemView.getRoot());
            this.binding=itemView;

        }
    }
}
