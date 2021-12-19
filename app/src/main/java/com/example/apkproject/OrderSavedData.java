package com.example.apkproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class OrderSavedData{
    public String ContainerNumber;
    public String ContainerType;
    public String Date;
    public String DriverName;
    public String CarNumber;
    public ArrayList<Uri> GeneralLeft, GeneralRight, GeneralInside, GeneralDoors;
    public ArrayList<Uri> Deformation1, Deformation2, Deformation3, Deformation4, Deformation5,
            Deformation6, Deformation7, Damage1, Damage2, Damage3, Damage4, Damage5, Damage6,
            Damage7, Damage8, Damage9, Damage10, Damage11, Damage12, Damage13;

    public OrderSavedData()
    {
        ContainerNumber = new String();
        ContainerType = new String();
        Date = new String();
        DriverName = new String();
        CarNumber = new String();
        GeneralLeft = new ArrayList<>();
        GeneralRight = new ArrayList<>();
        GeneralInside = new ArrayList<>();
        GeneralDoors = new ArrayList<>();
        Deformation1 = new ArrayList<>();
        Deformation2 = new ArrayList<>();
        Deformation3 = new ArrayList<>();
        Deformation4 = new ArrayList<>();
        Deformation5 = new ArrayList<>();
        Deformation6 = new ArrayList<>();
        Deformation7 = new ArrayList<>();
        Damage1 = new ArrayList<>();
        Damage2 = new ArrayList<>();
        Damage3 = new ArrayList<>();
        Damage4 = new ArrayList<>();
        Damage5 = new ArrayList<>();
        Damage6 = new ArrayList<>();
        Damage7 = new ArrayList<>();
        Damage8 = new ArrayList<>();
        Damage9 = new ArrayList<>();
        Damage10 = new ArrayList<>();
        Damage11 = new ArrayList<>();
        Damage12 = new ArrayList<>();
        Damage13 = new ArrayList<>();

    }
}
