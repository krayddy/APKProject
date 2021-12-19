package com.example.apkproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class ReceptionDataFragment extends Fragment implements View.OnClickListener {

    ReceptionGeneralPhotoFragment fragment;
    OrderSavedData orderData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reception_main, container, false);
        Button nextButton = view.findViewById(R.id.reception_menu_next_button);
        Button backButton = view.findViewById(R.id.reception_menu_back_button);
        nextButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        fragment = new ReceptionGeneralPhotoFragment();
        orderData = ((ReceptionMain)getActivity()).dataToFragment();
        ((ReceptionMain) getActivity()).onStringDataPass(null, null);
        //Log.e("CN", orderData.ContainerNumber);
        //Log.e("CT", orderData.ContainerType);
        //Log.e("Date", orderData.Date);
        //Log.e("DN", orderData.DriverName);
        //Log.e("car", orderData.CarNumber);
        //if ((orderData != null)) {
        //    EditText temp1 = ((EditText) view.findViewById(R.id.reception_main_container_number));//.setText(orderData.ContainerNumber);
        //    EditText temp2 = ((EditText) view.findViewById(R.id.reception_main_container_type));//.setText(orderData.ContainerType);
        //    EditText temp3 = ((EditText) view.findViewById(R.id.reception_main_date));//.setText(orderData.Date);
        //    EditText temp4 = ((EditText) view.findViewById(R.id.reception_main_driver_name));//.setText(orderData.DriverName);
        //    //EditText temp5 = ((EditText) view.findViewById(R.id.reception_main_car_number));//.setText(orderData.CarNumber);
        //}

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.reception_menu_next_button:

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("frag1")
                        .replace(R.id.data_fragment, fragment)
                        .commit();
                break;
            case R.id.reception_menu_back_button:
                getActivity()
                        .getSupportFragmentManager()
                        .popBackStack();
                break;
        }
    }
}
