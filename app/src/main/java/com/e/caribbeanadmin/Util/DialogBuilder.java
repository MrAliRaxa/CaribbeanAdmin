package com.e.caribbeanadmin.Util;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class DialogBuilder {
    public static AlertDialog.Builder getSimpleLoadingBuilder(Context context){
        return new AlertDialog.Builder(context);
    }
}
