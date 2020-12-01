package com.e.caribbeanadmin.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.data_model.Item;
import com.e.caribbeanadmin.databinding.DealsPromotionsRowBinding;

import java.util.List;


public class DealsAdaptor extends RecyclerView.Adapter<DealsAdaptor.ViewHolder> {
    private Context context;
    private List<Item> itemList;

    public DealsAdaptor(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
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
