package com.example.apkproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Context context;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        activity = this;
        ListView listView = findViewById(R.id.main_menu_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, CargoReception.class);
                TextView client = (TextView)findViewById(R.id.client_text);
                TextView container = (TextView)findViewById(R.id.container_text);
                TextView row = (TextView)findViewById(R.id.row_text);
                intent.putExtra("client", client.getText());
                intent.putExtra("container", container.getText());
                intent.putExtra("row", row.getText());
                startActivity(intent);
            }
        });

        ListViewAdapter listViewAdapter = new ListViewAdapter(activity);
        listView.setAdapter(listViewAdapter);
    }
}