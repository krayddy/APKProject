package com.example.apkproject;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public interface OnDataPass{
    public void onStringDataPass(String data, @Nullable String tag);
    public void onImageDataPass(ArrayList<Uri> data, @Nullable String tag);
    public void onFullDataPass(OrderSavedData data, @Nullable String tag);
}
