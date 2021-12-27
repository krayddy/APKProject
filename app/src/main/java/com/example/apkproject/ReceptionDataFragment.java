package com.example.apkproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReceptionDataFragment extends Fragment implements View.OnClickListener {

    View mainView;
    ReceptionGeneralPhotoFragment fragment;
    OrderSavedData orderData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reception_main, container, false);
        Button nextButton = view.findViewById(R.id.reception_menu_next_button);
        Button backButton = view.findViewById(R.id.reception_menu_back_button);
        Button calendarButton = view.findViewById(R.id.reception_menu_date_pick_button);
        nextButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        calendarButton.setOnClickListener(this);
        //String[] containerTypes = {"Контейнер 20\n", "Контейнер 3 т.", "Контейнер 40\'"};
        Spinner spinner = (Spinner) view.findViewById(R.id.reception_main_container_type);
        //ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(getContext(),
        //        R.layout.spinner_dropdown_item, R.id.spinner_textview, containerTypes);
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(getContext(),
                R.array.container_types, R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapterSpinner);
        fragment = new ReceptionGeneralPhotoFragment();
        orderData = ((ReceptionMain)getActivity()).dataToFragment();
        ((ReceptionMain) getActivity()).onStringDataPass(null, null);


        mainView = view;
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
                getActivity().finish();
                break;

            case R.id.reception_menu_date_pick_button:
                showDateTimePicker();
                break;
        }
    }

    String dateToSet;
    Calendar date;
    public void showDateTimePicker() {
        dateToSet = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);

                        dateToSet = sdf.format(date.getTime());
                        ((EditText)mainView.findViewById(R.id.reception_main_date)).setText(dateToSet);
                        Log.v(getTag(), "The choosen one " + date.getTime());
                        Log.v(getTag(), "The choosen one " + dateToSet);
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }
}
