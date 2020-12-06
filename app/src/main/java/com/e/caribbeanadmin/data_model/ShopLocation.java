package com.e.caribbeanadmin.data_model;

import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;

public class ShopLocation implements Parcelable {
    private String id;
    private String shopId;
    private String imageUrl;
    private String name;
    private String shopAddress;
    private double lat;
    private double lng;

    public ShopLocation() {
    }


    protected ShopLocation(Parcel in) {
        id = in.readString();
        shopId = in.readString();
        imageUrl = in.readString();
        name = in.readString();
        shopAddress = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
    }

    public static final Creator<ShopLocation> CREATOR = new Creator<ShopLocation>() {
        @Override
        public ShopLocation createFromParcel(Parcel in) {
            return new ShopLocation(in);
        }

        @Override
        public ShopLocation[] newArray(int size) {
            return new ShopLocation[size];
        }
    };

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(shopId);
        dest.writeString(imageUrl);
        dest.writeString(name);
        dest.writeString(shopAddress);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }
}
