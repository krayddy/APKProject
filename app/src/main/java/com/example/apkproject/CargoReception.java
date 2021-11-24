package com.example.apkproject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CargoReception extends AppCompatActivity {

    Context context;
    Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cargo_reception);

        context = this;
        activity = this;

        Bundle arguments = getIntent().getExtras();

        TextView clientTextView = (TextView)findViewById(R.id.reception_client_text);
        TextView containerTextView = (TextView)findViewById(R.id.reception_container_text);
        TextView rowTextView = (TextView)findViewById(R.id.reception_row_text);

        clientTextView.setText(arguments.getString("client"));
        containerTextView.setText(arguments.getString("container"));
        rowTextView.setText(arguments.getString("row"));
    }
}
