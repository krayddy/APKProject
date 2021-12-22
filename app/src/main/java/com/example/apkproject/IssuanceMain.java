package com.example.apkproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class IssuanceMain extends AppCompatActivity implements View.OnClickListener {

    Context context;
    @Override
    protected void onStart() {
        overridePendingTransition(0, 0);
        super.onStart();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.issuance_main);
        Button backButton = findViewById(R.id.issuance_menu_back_button);
        Button saveButton = findViewById(R.id.issuance_menu_save_button);
        Button datePickButton = findViewById(R.id.issuance_menu_date_pick_button);
        saveButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        datePickButton.setOnClickListener(this);
        context = this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.issuance_menu_save_button:

                break;
            case R.id.issuance_menu_back_button:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.issuance_menu_date_pick_button:
                showDateTimePicker();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }

    String dateToSet;
    Calendar date;
    public void showDateTimePicker() {
        dateToSet = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);

                        dateToSet = sdf.format(date.getTime());
                        ((EditText)findViewById(R.id.issuance_menu_date)).setText(dateToSet);
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }
}
