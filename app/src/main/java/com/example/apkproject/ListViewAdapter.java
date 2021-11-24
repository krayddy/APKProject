package com.example.apkproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.lang.reflect.Array;

public class ListViewAdapter extends BaseAdapter {

    private final Activity context;

    public ListViewAdapter(Activity context)
    {
        super();
        this.context = context;
    }

    @Override
    public int getCount() { return 20; }

    @Override
    public Object getItem(int position) { return null; }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        Holder holder;
        String[] client = {"ООО \"AAA\""};
        String[] cont = {"контейнер"};
        String[] start = {"10.11.21"};
        String[] end = {"31.12.21"};
        String[] row = {"27"};
        String[] type = {"тип"};
        Boolean[] tent = {true};
        Boolean[] emptiness = {false};
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.main_menu_list_item, null);
            holder = new Holder();
            holder.clientTextView = (TextView)convertView.findViewById(R.id.client_text);
            holder.containerTextView = (TextView)convertView.findViewById(R.id.container_text);
            holder.startDateTextView = (TextView)convertView.findViewById(R.id.start_date_text);
            holder.endDateTextView = (TextView)convertView.findViewById(R.id.end_date_text);
            holder.rowTextView = (TextView)convertView.findViewById(R.id.row_text);
            holder.typeTextView = (TextView)convertView.findViewById(R.id.type_text);
            holder.emptinessImageView = (ImageView)convertView.findViewById(R.id.emptiness_checkbox);
            holder.tentImageView = (ImageView)convertView.findViewById(R.id.tent_checkbox);
            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }
        holder.clientTextView.setText(client[0]);
        holder.containerTextView.setText(cont[0]);
        holder.startDateTextView.setText(start[0]);
        holder.endDateTextView.setText(end[0]);
        holder.rowTextView.setText(row[0]);
        holder.typeTextView.setText(type[0]);
        if (emptiness[0])
            holder.emptinessImageView.setImageDrawable(
                    ContextCompat.getDrawable(context, R.drawable.checkbox_enabled));
        else
            holder.emptinessImageView.setImageDrawable(
                    ContextCompat.getDrawable(context, R.drawable.checkbox_disabled));
        if (tent[0])
            holder.tentImageView.setImageDrawable(
                    ContextCompat.getDrawable(context, R.drawable.checkbox_enabled));
        else
            holder.tentImageView.setImageDrawable(
                    ContextCompat.getDrawable(context, R.drawable.checkbox_disabled));
        return convertView;
    }
}
