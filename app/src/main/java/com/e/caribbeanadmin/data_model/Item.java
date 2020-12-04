package com.e.caribbeanadmin.data_model;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private String shopId;
    private String Id;
    private String content;
    private String imageUrl;

    public Item() {
    }

    protected Item(Parcel in) {
        shopId = in.readString();
        Id = in.readString();
        content = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shopId);
        dest.writeString(Id);
        dest.writeString(content);
        dest.writeString(imageUrl);
    }
}
