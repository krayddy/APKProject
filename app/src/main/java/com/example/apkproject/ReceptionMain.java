package com.example.apkproject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class ReceptionMain extends FragmentActivity implements OnDataPass {

    public OrderSavedData orderData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reception_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        ReceptionDataFragment fragment = new ReceptionDataFragment();
        fragmentManager.beginTransaction().add(R.id.data_fragment, fragment).commit();
        orderData = new OrderSavedData();
    }

    @Override
    public void onStringDataPass(String data, @Nullable String tag) {
        //if (tag.equals("ContainerNumber")) {
        //    orderData.ContainerNumber = data;
        //}
        Log.e("CN", orderData.ContainerNumber);
        Log.e("CT", orderData.ContainerType);
        Log.e("Date", orderData.Date);
        Log.e("DN", orderData.DriverName);
        Log.e("car", orderData.CarNumber);
    }

    @Override
    public void onImageDataPass(ArrayList<Uri> data, @Nullable String tag) {

    }

    @Override
    public void onFullDataPass(OrderSavedData data, @Nullable String tag) {
        orderData = data;
    }

    public OrderSavedData dataToFragment()
    {
        return orderData;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);
    }
}

