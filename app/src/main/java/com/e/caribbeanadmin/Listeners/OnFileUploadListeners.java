package com.e.caribbeanadmin.Listeners;

import com.google.firebase.storage.UploadTask;

public interface OnFileUploadListeners {
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot);
    public void onProgress(UploadTask.TaskSnapshot taskSnapshot);
    public void onFailure(String e);
}
