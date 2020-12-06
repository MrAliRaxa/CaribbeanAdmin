package com.e.caribbeanadmin.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.e.caribbeanadmin.Listeners.OnLocationDeleteListeners;
import com.e.caribbeanadmin.Listeners.OnLocationEditListeners;
import com.e.caribbeanadmin.Listeners.OnShopClick;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.data_model.ShopLocation;
import com.e.caribbeanadmin.databinding.ShopRowLayoutBinding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class LocationAdaptor extends RecyclerView.Adapter<LocationAdaptor.ViewHolder> {


    private Context context;
    private List<ShopLocation> shopLocations;
    private OnShopClick onShopClick;
    private OnLocationDeleteListeners onLocationDeleteListeners;
    private OnLocationEditListeners onLocationEditListeners;


    public LocationAdaptor(Context context, List<ShopLocation> shopLocations) {
        this.context = context;
        this.shopLocations = shopLocations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ShopRowLayoutBinding shopRowLayoutBinding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.shop_row_layout,parent,false);
        return new ViewHolder(shopRowLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShopLocation shopLocation=shopLocations.get(position);
        holder.shopRowLayoutBinding.locationName.setText(shopLocation.getName());
        holder.shopRowLayoutBinding.locationAddress.setText(shopLocation.getShopAddress());

        holder.shopRowLayoutBinding.locationDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onLocationDeleteListeners!=null)
                onLocationDeleteListeners.onClick(shopLocation,position);
            }
        });

        holder.shopRowLayoutBinding.locationEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onLocationEditListeners!=null)
                onLocationEditListeners.onClick(shopLocation);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onShopClick!=null){
                    onShopClick.onClick(new LatLng(shopLocation.getLat(),shopLocation.getLng()),position);
                }
            }
        });

        Glide.with(context).load(shopLocation.getImageUrl()).override(256,256).into(holder.shopRowLayoutBinding.locationImage);
    }

    @Override
    public int getItemCount() {
        return shopLocations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ShopRowLayoutBinding shopRowLayoutBinding;
        public ViewHolder(@NonNull ShopRowLayoutBinding itemView) {
            super(itemView.getRoot());
            this.shopRowLayoutBinding=itemView;
        }
    }

    public void setOnShopClick(OnShopClick onShopClick){
        this.onShopClick=onShopClick;
    }
    public void setOnLocationDeleteListeners(OnLocationDeleteListeners onLocationDeleteListeners){
        this.onLocationDeleteListeners=onLocationDeleteListeners;
    }

    public void setOnLocationEditListeners(OnLocationEditListeners onLocationEditListeners){
        this.onLocationEditListeners=onLocationEditListeners;
    }
}
