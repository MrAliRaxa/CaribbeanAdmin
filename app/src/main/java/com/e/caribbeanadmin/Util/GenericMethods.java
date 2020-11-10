package com.e.caribbeanadmin.Util;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.e.caribbeanadmin.Constants.ShopType;
import com.e.caribbeanadmin.R;

public class GenericMethods {
    public static void getImages(int code,AppCompatActivity activity){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent,"Select Picture"), code);
    }

    public static void getImage(int code, AppCompatActivity activity){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent,"Select Picture"), code);

    }
    public static void getVideos(int code, AppCompatActivity activity){
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent,"Select Vide"), code);
    }
    public static String shopTypeToString(int shopType){
        if(ShopType.NONE==shopType){
            return "None";
        }else if(ShopType.AABHW==shopType){
            return "AABHW";
        }else if(ShopType.DDSI==shopType){
            return "DDSI";
        }else if(ShopType.DMLI==shopType){
            return "DMLI";
        }else if(ShopType.DS==shopType){
            return "DS";
        }else if(ShopType.DSLI==shopType){
            return "DSLI";
        }else if(ShopType.NSEWT==shopType){
            return "NSEWT";
        }else if(ShopType.PSL==shopType){
            return "PSL";
        }else if(ShopType.WABI==shopType){
            return "WABI";
        }else if(ShopType.SL==shopType){
            return "SL";
        }

        return "";
    }
}
