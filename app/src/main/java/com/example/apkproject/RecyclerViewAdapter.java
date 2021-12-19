package com.example.apkproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Uri> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;
    private int VIEW_TYPE_DEFAULT=0;
    private int VIEW_TYPE_IMAGE=1;

    RecyclerViewAdapter(Context context, List<Uri> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_IMAGE)
        {
            View view = mInflater.inflate(R.layout.photos_list_view_item, parent, false);
            return new ViewHolder(view);
        }
        else
        {
            View view = mInflater.inflate(R.layout.photos_list_default_item, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position != mData.size()) {
            Uri uri = mData.get(position);
            holder.ImageView.setImageURI(uri);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        android.widget.ImageView ImageView;

        ViewHolder(View itemView) {
            super(itemView);
            ImageView = itemView.findViewById(R.id.list_item_imageview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    //String getItem(int id) {
    //    return mData.get(id);
    //}

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mData.size()) ? VIEW_TYPE_DEFAULT : VIEW_TYPE_IMAGE;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
