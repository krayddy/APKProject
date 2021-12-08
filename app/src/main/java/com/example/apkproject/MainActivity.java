package com.example.apkproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        activity = this;
        Button receptionButton = findViewById(R.id.main_menu_reception_button);
        Button issuanceButton = findViewById(R.id.main_menu_issuance_button);
        receptionButton.setOnClickListener(this);
        issuanceButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_menu_reception_button:

                break;
            case R.id.main_menu_issuance_button:
                startActivity(new Intent(this, IssuanceMain.class));
                break;
        }
    }
}