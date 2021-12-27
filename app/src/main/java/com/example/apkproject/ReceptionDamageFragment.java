package com.example.apkproject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

public class ReceptionDamageFragment extends Fragment implements View.OnClickListener {

    private Bitmap bitmap;
    //region variables
    View mainView;
    OnDataPass dataPasser;
    OrderSavedData orderSavedData;
    ArrayList<Uri> imagesDamage1, imagesDamage2, imagesDamage3, imagesDamage4, imagesDamage5,
            imagesDamage6, imagesDamage7, imagesDamage8, imagesDamage9, imagesDamage10,
            imagesDamage11, imagesDamage12, imagesDamage13;
    RecyclerViewAdapter adapterDamage1, adapterDamage2, adapterDamage3, adapterDamage4, adapterDamage5,
            adapterDamage6, adapterDamage7, adapterDamage8, adapterDamage9, adapterDamage10,
            adapterDamage11, adapterDamage12, adapterDamage13;
    CheckBox checkboxDamage1, checkboxDamage2, checkboxDamage3, checkboxDamage4, checkboxDamage5,
            checkboxDamage6, checkboxDamage7, checkboxDamage8, checkboxDamage9, checkboxDamage10,
            checkboxDamage11, checkboxDamage12, checkboxDamage13;
    LinearLayout layoutDamage1, layoutDamage2, layoutDamage3, layoutDamage4, layoutDamage5,
            layoutDamage6, layoutDamage7, layoutDamage8, layoutDamage9, layoutDamage10,
            layoutDamage11, layoutDamage12, layoutDamage13;
    Button addButtonDamage1, addButtonDamage2, addButtonDamage3, addButtonDamage4, addButtonDamage5,
            addButtonDamage6, addButtonDamage7, addButtonDamage8, addButtonDamage9, addButtonDamage10,
            addButtonDamage11, addButtonDamage12, addButtonDamage13;
    Button backButton, nextButton;
    RecyclerView rvDamage1, rvDamage2, rvDamage3, rvDamage4, rvDamage5, rvDamage6, rvDamage7,
            rvDamage8, rvDamage9, rvDamage10, rvDamage11, rvDamage12, rvDamage13;
    Intent photoPickingIntent;
    //endregion
    //region temp_variables
    ArrayList<Uri> tempUriList;
    RecyclerViewAdapter tempAdapter;
    int tempButtonId;
    int tempRVId;
    int tempLayoutId;
    int tempCheckboxId;
    //endregion

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reception_damage, container, false);
        photoPickingIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                .setType("image/*")
                .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        orderSavedData = ((ReceptionMain) getActivity()).dataToFragment();
        mainView = view;

        RestoreImageLists();
        FindViews(view);
        SetButtonListeners();
        CreateRVsAdapters();
        SetAdapterListeners();
        SetRVAdapters();
        SetVisibilityBasedOnSavedData();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ArrayList<Uri> imagesUri = new ArrayList<Uri>();
        if (resultCode == RESULT_OK) {
            ClipData clipData = data.getClipData();
            if (clipData == null) {
                Uri uri = data.getData();
                imagesUri.add(uri);
            } else {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    imagesUri.add(uri);
                }
            }
        } else {
            if (tempUriList.size() == 0) {
                mainView.findViewById(tempButtonId).setVisibility(View.VISIBLE);
                mainView.findViewById(tempRVId).setVisibility(View.GONE);
            }
        }
        tempUriList.addAll(0, imagesUri);
        tempAdapter.notifyItemRangeInserted(0, imagesUri.size());
    }

    private void SetVisibilityBasedOnSavedData() {
        if (imagesDamage1.size() > 0) {
            layoutDamage1.setVisibility(View.VISIBLE);
            rvDamage1.setVisibility(View.VISIBLE);
            addButtonDamage1.setVisibility(View.GONE);
            checkboxDamage1.setChecked(true);
        }
        if (imagesDamage2.size() > 0) {
            layoutDamage2.setVisibility(View.VISIBLE);
            rvDamage2.setVisibility(View.VISIBLE);
            addButtonDamage2.setVisibility(View.GONE);
            checkboxDamage2.setChecked(true);
        }
        if (imagesDamage3.size() > 0) {
            layoutDamage3.setVisibility(View.VISIBLE);
            rvDamage3.setVisibility(View.VISIBLE);
            addButtonDamage3.setVisibility(View.GONE);
            checkboxDamage3.setChecked(true);
        }
        if (imagesDamage4.size() > 0) {
            layoutDamage4.setVisibility(View.VISIBLE);
            rvDamage4.setVisibility(View.VISIBLE);
            addButtonDamage4.setVisibility(View.GONE);
            checkboxDamage4.setChecked(true);
        }
        if (imagesDamage5.size() > 0) {
            layoutDamage5.setVisibility(View.VISIBLE);
            rvDamage5.setVisibility(View.VISIBLE);
            addButtonDamage5.setVisibility(View.GONE);
            checkboxDamage5.setChecked(true);
        }
        if (imagesDamage6.size() > 0) {
            layoutDamage6.setVisibility(View.VISIBLE);
            rvDamage6.setVisibility(View.VISIBLE);
            addButtonDamage6.setVisibility(View.GONE);
            checkboxDamage6.setChecked(true);
        }
        if (imagesDamage7.size() > 0) {
            layoutDamage7.setVisibility(View.VISIBLE);
            rvDamage7.setVisibility(View.VISIBLE);
            addButtonDamage7.setVisibility(View.GONE);
            checkboxDamage7.setChecked(true);
        }
        if (imagesDamage8.size() > 0) {
            layoutDamage8.setVisibility(View.VISIBLE);
            rvDamage8.setVisibility(View.VISIBLE);
            addButtonDamage8.setVisibility(View.GONE);
            checkboxDamage8.setChecked(true);
        }
        if (imagesDamage9.size() > 0) {
            layoutDamage9.setVisibility(View.VISIBLE);
            rvDamage9.setVisibility(View.VISIBLE);
            addButtonDamage9.setVisibility(View.GONE);
            checkboxDamage9.setChecked(true);
        }
        if (imagesDamage10.size() > 0) {
            layoutDamage10.setVisibility(View.VISIBLE);
            rvDamage10.setVisibility(View.VISIBLE);
            addButtonDamage10.setVisibility(View.GONE);
            checkboxDamage10.setChecked(true);
        }
        if (imagesDamage11.size() > 0) {
            layoutDamage11.setVisibility(View.VISIBLE);
            rvDamage11.setVisibility(View.VISIBLE);
            addButtonDamage11.setVisibility(View.GONE);
            checkboxDamage11.setChecked(true);
        }
        if (imagesDamage12.size() > 0) {
            layoutDamage12.setVisibility(View.VISIBLE);
            rvDamage12.setVisibility(View.VISIBLE);
            addButtonDamage12.setVisibility(View.GONE);
            checkboxDamage12.setChecked(true);
        }
        if (imagesDamage13.size() > 0) {
            layoutDamage13.setVisibility(View.VISIBLE);
            rvDamage13.setVisibility(View.VISIBLE);
            addButtonDamage13.setVisibility(View.GONE);
            checkboxDamage13.setChecked(true);
        }
    }

    private void SetRVAdapters() {
        rvDamage1.setAdapter(adapterDamage1);
        rvDamage2.setAdapter(adapterDamage2);
        rvDamage3.setAdapter(adapterDamage3);
        rvDamage4.setAdapter(adapterDamage4);
        rvDamage5.setAdapter(adapterDamage5);
        rvDamage6.setAdapter(adapterDamage6);
        rvDamage7.setAdapter(adapterDamage7);
        rvDamage8.setAdapter(adapterDamage8);
        rvDamage9.setAdapter(adapterDamage9);
        rvDamage10.setAdapter(adapterDamage10);
        rvDamage11.setAdapter(adapterDamage11);
        rvDamage12.setAdapter(adapterDamage12);
        rvDamage13.setAdapter(adapterDamage13);
    }

    private void CreateRVsAdapters() {
        adapterDamage1 = new RecyclerViewAdapter(getActivity(), imagesDamage1);
        adapterDamage2 = new RecyclerViewAdapter(getActivity(), imagesDamage2);
        adapterDamage3 = new RecyclerViewAdapter(getActivity(), imagesDamage3);
        adapterDamage4 = new RecyclerViewAdapter(getActivity(), imagesDamage4);
        adapterDamage5 = new RecyclerViewAdapter(getActivity(), imagesDamage5);
        adapterDamage6 = new RecyclerViewAdapter(getActivity(), imagesDamage6);
        adapterDamage7 = new RecyclerViewAdapter(getActivity(), imagesDamage7);
        adapterDamage8 = new RecyclerViewAdapter(getActivity(), imagesDamage8);
        adapterDamage9 = new RecyclerViewAdapter(getActivity(), imagesDamage9);
        adapterDamage10 = new RecyclerViewAdapter(getActivity(), imagesDamage10);
        adapterDamage11 = new RecyclerViewAdapter(getActivity(), imagesDamage11);
        adapterDamage12 = new RecyclerViewAdapter(getActivity(), imagesDamage12);
        adapterDamage13 = new RecyclerViewAdapter(getActivity(), imagesDamage13);
    }

    private void SetButtonListeners() {
        nextButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        checkboxDamage1.setOnClickListener(this);
        checkboxDamage2.setOnClickListener(this);
        checkboxDamage3.setOnClickListener(this);
        checkboxDamage4.setOnClickListener(this);
        checkboxDamage5.setOnClickListener(this);
        checkboxDamage6.setOnClickListener(this);
        checkboxDamage7.setOnClickListener(this);
        checkboxDamage8.setOnClickListener(this);
        checkboxDamage9.setOnClickListener(this);
        checkboxDamage10.setOnClickListener(this);
        checkboxDamage11.setOnClickListener(this);
        checkboxDamage12.setOnClickListener(this);
        checkboxDamage13.setOnClickListener(this);
        addButtonDamage1.setOnClickListener(this);
        addButtonDamage2.setOnClickListener(this);
        addButtonDamage3.setOnClickListener(this);
        addButtonDamage4.setOnClickListener(this);
        addButtonDamage5.setOnClickListener(this);
        addButtonDamage6.setOnClickListener(this);
        addButtonDamage7.setOnClickListener(this);
        addButtonDamage8.setOnClickListener(this);
        addButtonDamage9.setOnClickListener(this);
        addButtonDamage10.setOnClickListener(this);
        addButtonDamage11.setOnClickListener(this);
        addButtonDamage12.setOnClickListener(this);
        addButtonDamage13.setOnClickListener(this);
    }

    private void FindViews(View view) {
        backButton = view.findViewById(R.id.reception_damage_back_button);
        nextButton = view.findViewById(R.id.reception_damage_next_button);

        checkboxDamage1 = view.findViewById(R.id.damage1_checkbox);
        layoutDamage1 = view.findViewById(R.id.damage1_layout);
        addButtonDamage1 = view.findViewById(R.id.damage1_add_button);
        rvDamage1 = view.findViewById(R.id.damage1_rv);

        checkboxDamage2 = view.findViewById(R.id.damage2_checkbox);
        layoutDamage2 = view.findViewById(R.id.damage2_layout);
        addButtonDamage2 = view.findViewById(R.id.damage2_add_button);
        rvDamage2 = view.findViewById(R.id.damage2_rv);

        checkboxDamage3 = view.findViewById(R.id.damage3_checkbox);
        layoutDamage3 = view.findViewById(R.id.damage3_layout);
        addButtonDamage3 = view.findViewById(R.id.damage3_add_button);
        rvDamage3 = view.findViewById(R.id.damage3_rv);

        checkboxDamage4 = view.findViewById(R.id.damage4_checkbox);
        layoutDamage4 = view.findViewById(R.id.damage4_layout);
        addButtonDamage4 = view.findViewById(R.id.damage4_add_button);
        rvDamage4 = view.findViewById(R.id.damage4_rv);

        checkboxDamage5 = view.findViewById(R.id.damage5_checkbox);
        layoutDamage5 = view.findViewById(R.id.damage5_layout);
        addButtonDamage5 = view.findViewById(R.id.damage5_add_button);
        rvDamage5 = view.findViewById(R.id.damage5_rv);

        checkboxDamage6 = view.findViewById(R.id.damage6_checkbox);
        layoutDamage6 = view.findViewById(R.id.damage6_layout);
        addButtonDamage6 = view.findViewById(R.id.damage6_add_button);
        rvDamage6 = view.findViewById(R.id.damage6_rv);

        checkboxDamage7 = view.findViewById(R.id.damage7_checkbox);
        layoutDamage7 = view.findViewById(R.id.damage7_layout);
        addButtonDamage7 = view.findViewById(R.id.damage7_add_button);
        rvDamage7 = view.findViewById(R.id.damage7_rv);

        checkboxDamage8 = view.findViewById(R.id.damage8_checkbox);
        layoutDamage8 = view.findViewById(R.id.damage8_layout);
        addButtonDamage8 = view.findViewById(R.id.damage8_add_button);
        rvDamage8 = view.findViewById(R.id.damage8_rv);

        checkboxDamage9 = view.findViewById(R.id.damage9_checkbox);
        layoutDamage9 = view.findViewById(R.id.damage9_layout);
        addButtonDamage9 = view.findViewById(R.id.damage9_add_button);
        rvDamage9 = view.findViewById(R.id.damage9_rv);

        checkboxDamage10 = view.findViewById(R.id.damage10_checkbox);
        layoutDamage10 = view.findViewById(R.id.damage10_layout);
        addButtonDamage10 = view.findViewById(R.id.damage10_add_button);
        rvDamage10 = view.findViewById(R.id.damage10_rv);

        checkboxDamage11 = view.findViewById(R.id.damage11_checkbox);
        layoutDamage11 = view.findViewById(R.id.damage11_layout);
        addButtonDamage11 = view.findViewById(R.id.damage11_add_button);
        rvDamage11 = view.findViewById(R.id.damage11_rv);

        checkboxDamage12 = view.findViewById(R.id.damage12_checkbox);
        layoutDamage12 = view.findViewById(R.id.damage12_layout);
        addButtonDamage12 = view.findViewById(R.id.damage12_add_button);
        rvDamage12 = view.findViewById(R.id.damage12_rv);

        checkboxDamage13 = view.findViewById(R.id.damage13_checkbox);
        layoutDamage13 = view.findViewById(R.id.damage13_layout);
        addButtonDamage13 = view.findViewById(R.id.damage13_add_button);
        rvDamage13 = view.findViewById(R.id.damage13_rv);
    }

    private void RestoreImageLists() {
        imagesDamage1 = orderSavedData.Damage1.size() == 0 ? new ArrayList<>() : orderSavedData.Damage1;
        imagesDamage2 = orderSavedData.Damage2.size() == 0 ? new ArrayList<>() : orderSavedData.Damage2;
        imagesDamage3 = orderSavedData.Damage3.size() == 0 ? new ArrayList<>() : orderSavedData.Damage3;
        imagesDamage4 = orderSavedData.Damage4.size() == 0 ? new ArrayList<>() : orderSavedData.Damage4;
        imagesDamage5 = orderSavedData.Damage5.size() == 0 ? new ArrayList<>() : orderSavedData.Damage5;
        imagesDamage6 = orderSavedData.Damage6.size() == 0 ? new ArrayList<>() : orderSavedData.Damage6;
        imagesDamage7 = orderSavedData.Damage7.size() == 0 ? new ArrayList<>() : orderSavedData.Damage7;
        imagesDamage8 = orderSavedData.Damage8.size() == 0 ? new ArrayList<>() : orderSavedData.Damage8;
        imagesDamage9 = orderSavedData.Damage9.size() == 0 ? new ArrayList<>() : orderSavedData.Damage9;
        imagesDamage10 = orderSavedData.Damage10.size() == 0 ? new ArrayList<>() : orderSavedData.Damage10;
        imagesDamage11 = orderSavedData.Damage11.size() == 0 ? new ArrayList<>() : orderSavedData.Damage11;
        imagesDamage12 = orderSavedData.Damage12.size() == 0 ? new ArrayList<>() : orderSavedData.Damage12;
        imagesDamage13 = orderSavedData.Damage13.size() == 0 ? new ArrayList<>() : orderSavedData.Damage13;
    }

    void TempVariablesAssign(ArrayList<Uri> imagesList, RecyclerViewAdapter adapter,
                             int buttonId, int rvId, int checkboxId, int layoutId) {
        tempUriList = imagesList;
        tempAdapter = adapter;
        tempButtonId = buttonId;
        tempRVId = rvId;
        tempCheckboxId = checkboxId;
        tempLayoutId = layoutId;
    }

    void StartPhotoPickingActivity(ArrayList<Uri> imagesList, RecyclerViewAdapter adapter,
                                   int buttonId, int rvId, int checkboxId, int layoutId) {
        TempVariablesAssign(imagesList, adapter, buttonId,
                rvId, checkboxId, layoutId);
        startActivityForResult(photoPickingIntent, 1);
        mainView.findViewById(buttonId).setVisibility(View.GONE);
        mainView.findViewById(rvId).setVisibility(View.VISIBLE);
    }

    void SaveData() {
        orderSavedData.Damage1 = checkboxDamage1.isChecked() ? imagesDamage1 : new ArrayList<>();
        orderSavedData.Damage2 = checkboxDamage2.isChecked() ? imagesDamage2 : new ArrayList<>();
        orderSavedData.Damage3 = checkboxDamage3.isChecked() ? imagesDamage3 : new ArrayList<>();
        orderSavedData.Damage4 = checkboxDamage4.isChecked() ? imagesDamage4 : new ArrayList<>();
        orderSavedData.Damage5 = checkboxDamage5.isChecked() ? imagesDamage5 : new ArrayList<>();
        orderSavedData.Damage6 = checkboxDamage6.isChecked() ? imagesDamage6 : new ArrayList<>();
        orderSavedData.Damage7 = checkboxDamage7.isChecked() ? imagesDamage7 : new ArrayList<>();
        orderSavedData.Damage8 = checkboxDamage8.isChecked() ? imagesDamage8 : new ArrayList<>();
        orderSavedData.Damage9 = checkboxDamage9.isChecked() ? imagesDamage9 : new ArrayList<>();
        orderSavedData.Damage10 = checkboxDamage10.isChecked() ? imagesDamage10 : new ArrayList<>();
        orderSavedData.Damage11 = checkboxDamage11.isChecked() ? imagesDamage11 : new ArrayList<>();
        orderSavedData.Damage12 = checkboxDamage12.isChecked() ? imagesDamage12 : new ArrayList<>();
        orderSavedData.Damage13 = checkboxDamage13.isChecked() ? imagesDamage13 : new ArrayList<>();

        dataPasser.onFullDataPass(orderSavedData, "pass data");
    }

    private void SetAdapterListeners() {
        adapterDamage1.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesDamage1.size()) {
                    TempVariablesAssign(imagesDamage1, adapterDamage1, R.id.damage1_add_button,
                            R.id.damage1_rv, R.id.damage1_checkbox, R.id.damage1_layout);
                    startActivityForResult(photoPickingIntent, 1);
                } else {
                    Toast.makeText(getActivity(), new File(imagesDamage1.get(position).getPath()).getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterDamage2.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesDamage2.size()) {
                    TempVariablesAssign(imagesDamage2, adapterDamage2, R.id.damage2_add_button,
                            R.id.damage2_rv, R.id.damage2_checkbox, R.id.damage2_layout);
                    startActivityForResult(photoPickingIntent, 1);
                } else {
                    Toast.makeText(getActivity(), "not implemented, 2 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterDamage3.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesDamage3.size()) {
                    TempVariablesAssign(imagesDamage3, adapterDamage3, R.id.damage3_add_button,
                            R.id.damage3_rv, R.id.damage3_checkbox, R.id.damage3_layout);
                    startActivityForResult(photoPickingIntent, 1);
                } else {
                    Toast.makeText(getActivity(), "not implemented, 3 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterDamage4.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesDamage4.size()) {
                    TempVariablesAssign(imagesDamage4, adapterDamage4, R.id.damage4_add_button,
                            R.id.damage4_rv, R.id.damage4_checkbox, R.id.damage4_layout);
                    startActivityForResult(photoPickingIntent, 1);
                } else {
                    Toast.makeText(getActivity(), "not implemented, 4 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterDamage5.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesDamage5.size()) {
                    TempVariablesAssign(imagesDamage5, adapterDamage5, R.id.damage5_add_button,
                            R.id.damage5_rv, R.id.damage5_checkbox, R.id.damage5_layout);
                    startActivityForResult(photoPickingIntent, 1);
                } else {
                    Toast.makeText(getActivity(), "not implemented, 5 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterDamage6.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesDamage6.size()) {
                    TempVariablesAssign(imagesDamage6, adapterDamage6, R.id.damage6_add_button,
                            R.id.damage6_rv, R.id.damage6_checkbox, R.id.damage6_layout);
                    startActivityForResult(photoPickingIntent, 1);
                } else {
                    Toast.makeText(getActivity(), "not implemented, 6 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterDamage7.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesDamage7.size()) {
                    TempVariablesAssign(imagesDamage7, adapterDamage7, R.id.damage7_add_button,
                            R.id.damage7_rv, R.id.damage7_checkbox, R.id.damage7_layout);
                    startActivityForResult(photoPickingIntent, 1);
                } else {
                    Toast.makeText(getActivity(), "not implemented, 7 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterDamage8.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesDamage8.size()) {
                    TempVariablesAssign(imagesDamage8, adapterDamage8, R.id.damage8_add_button,
                            R.id.damage8_rv, R.id.damage8_checkbox, R.id.damage8_layout);
                    startActivityForResult(photoPickingIntent, 1);
                } else {
                    Toast.makeText(getActivity(), "not implemented, 8 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterDamage9.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesDamage9.size()) {
                    TempVariablesAssign(imagesDamage9, adapterDamage9, R.id.damage9_add_button,
                            R.id.damage9_rv, R.id.damage9_checkbox, R.id.damage9_layout);
                    startActivityForResult(photoPickingIntent, 1);
                } else {
                    Toast.makeText(getActivity(), "not implemented, 9 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterDamage10.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesDamage10.size()) {
                    TempVariablesAssign(imagesDamage10, adapterDamage10, R.id.damage10_add_button,
                            R.id.damage10_rv, R.id.damage10_checkbox, R.id.damage10_layout);
                    startActivityForResult(photoPickingIntent, 1);
                } else {
                    Toast.makeText(getActivity(), "not implemented, 10 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterDamage11.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesDamage11.size()) {
                    TempVariablesAssign(imagesDamage11, adapterDamage11, R.id.damage11_add_button,
                            R.id.damage11_rv, R.id.damage11_checkbox, R.id.damage11_layout);
                    startActivityForResult(photoPickingIntent, 1);
                } else {
                    Toast.makeText(getActivity(), "not implemented, 11 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterDamage12.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesDamage12.size()) {
                    TempVariablesAssign(imagesDamage12, adapterDamage12, R.id.damage12_add_button,
                            R.id.damage12_rv, R.id.damage12_checkbox, R.id.damage12_layout);
                    startActivityForResult(photoPickingIntent, 1);
                } else {
                    Toast.makeText(getActivity(), "not implemented, 12 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterDamage13.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesDamage13.size()) {
                    TempVariablesAssign(imagesDamage13, adapterDamage13, R.id.damage13_add_button,
                            R.id.damage13_rv, R.id.damage13_checkbox, R.id.damage13_layout);
                    startActivityForResult(photoPickingIntent, 1);
                } else {
                    Toast.makeText(getActivity(), "not implemented, 13 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.damage1_add_button:
                StartPhotoPickingActivity(imagesDamage1, adapterDamage1,
                        R.id.damage1_add_button, R.id.damage1_rv,
                        R.id.damage1_checkbox, R.id.damage1_layout);
                break;
            case R.id.damage2_add_button:
                StartPhotoPickingActivity(imagesDamage2, adapterDamage2,
                        R.id.damage2_add_button, R.id.damage2_rv,
                        R.id.damage2_checkbox, R.id.damage2_layout);
                break;
            case R.id.damage3_add_button:
                StartPhotoPickingActivity(imagesDamage3, adapterDamage3,
                        R.id.damage3_add_button, R.id.damage3_rv,
                        R.id.damage3_checkbox, R.id.damage3_layout);
                break;
            case R.id.damage4_add_button:
                StartPhotoPickingActivity(imagesDamage4, adapterDamage4,
                        R.id.damage4_add_button, R.id.damage4_rv,
                        R.id.damage4_checkbox, R.id.damage4_layout);
                break;
            case R.id.damage5_add_button:
                StartPhotoPickingActivity(imagesDamage5, adapterDamage5,
                        R.id.damage5_add_button, R.id.damage5_rv,
                        R.id.damage5_checkbox, R.id.damage5_layout);
                break;
            case R.id.damage6_add_button:
                StartPhotoPickingActivity(imagesDamage6, adapterDamage6,
                        R.id.damage6_add_button, R.id.damage6_rv,
                        R.id.damage6_checkbox, R.id.damage6_layout);
                break;
            case R.id.damage7_add_button:
                StartPhotoPickingActivity(imagesDamage7, adapterDamage7,
                        R.id.damage7_add_button, R.id.damage7_rv,
                        R.id.damage7_checkbox, R.id.damage7_layout);
                break;
            case R.id.damage8_add_button:
                StartPhotoPickingActivity(imagesDamage8, adapterDamage8,
                        R.id.damage8_add_button, R.id.damage8_rv,
                        R.id.damage8_checkbox, R.id.damage8_layout);
                break;
            case R.id.damage9_add_button:
                StartPhotoPickingActivity(imagesDamage9, adapterDamage9,
                        R.id.damage9_add_button, R.id.damage9_rv,
                        R.id.damage9_checkbox, R.id.damage9_layout);
                break;
            case R.id.damage10_add_button:
                StartPhotoPickingActivity(imagesDamage10, adapterDamage10,
                        R.id.damage10_add_button, R.id.damage10_rv,
                        R.id.damage10_checkbox, R.id.damage10_layout);
                break;
            case R.id.damage11_add_button:
                StartPhotoPickingActivity(imagesDamage11, adapterDamage11,
                        R.id.damage11_add_button, R.id.damage11_rv,
                        R.id.damage11_checkbox, R.id.damage11_layout);
                break;
            case R.id.damage12_add_button:
                StartPhotoPickingActivity(imagesDamage12, adapterDamage12,
                        R.id.damage12_add_button, R.id.damage12_rv,
                        R.id.damage12_checkbox, R.id.damage12_layout);
                break;
            case R.id.damage13_add_button:
                StartPhotoPickingActivity(imagesDamage13, adapterDamage13,
                        R.id.damage13_add_button, R.id.damage13_rv,
                        R.id.damage13_checkbox, R.id.damage13_layout);
                break;
            case R.id.damage1_checkbox:
                if (checkboxDamage1.isChecked())
                    layoutDamage1.setVisibility(View.VISIBLE);
                else
                    layoutDamage1.setVisibility(View.GONE);
                break;
            case R.id.damage2_checkbox:
                if (checkboxDamage2.isChecked())
                    layoutDamage2.setVisibility(View.VISIBLE);
                else
                    layoutDamage2.setVisibility(View.GONE);
                break;
            case R.id.damage3_checkbox:
                if (checkboxDamage3.isChecked())
                    layoutDamage3.setVisibility(View.VISIBLE);
                else
                    layoutDamage3.setVisibility(View.GONE);
                break;
            case R.id.damage4_checkbox:
                if (checkboxDamage4.isChecked())
                    layoutDamage4.setVisibility(View.VISIBLE);
                else
                    layoutDamage4.setVisibility(View.GONE);
                break;
            case R.id.damage5_checkbox:
                if (checkboxDamage5.isChecked())
                    layoutDamage5.setVisibility(View.VISIBLE);
                else
                    layoutDamage5.setVisibility(View.GONE);
                break;
            case R.id.damage6_checkbox:
                if (checkboxDamage6.isChecked())
                    layoutDamage6.setVisibility(View.VISIBLE);
                else
                    layoutDamage6.setVisibility(View.GONE);
                break;
            case R.id.damage7_checkbox:
                if (checkboxDamage7.isChecked())
                    layoutDamage7.setVisibility(View.VISIBLE);
                else
                    layoutDamage7.setVisibility(View.GONE);
                break;
            case R.id.damage8_checkbox:
                if (checkboxDamage8.isChecked())
                    layoutDamage8.setVisibility(View.VISIBLE);
                else
                    layoutDamage8.setVisibility(View.GONE);
                break;
            case R.id.damage9_checkbox:
                if (checkboxDamage9.isChecked())
                    layoutDamage9.setVisibility(View.VISIBLE);
                else
                    layoutDamage9.setVisibility(View.GONE);
                break;
            case R.id.damage10_checkbox:
                if (checkboxDamage10.isChecked())
                    layoutDamage10.setVisibility(View.VISIBLE);
                else
                    layoutDamage10.setVisibility(View.GONE);
                break;
            case R.id.damage11_checkbox:
                if (checkboxDamage11.isChecked())
                    layoutDamage11.setVisibility(View.VISIBLE);
                else
                    layoutDamage11.setVisibility(View.GONE);
                break;
            case R.id.damage12_checkbox:
                if (checkboxDamage12.isChecked())
                    layoutDamage12.setVisibility(View.VISIBLE);
                else
                    layoutDamage12.setVisibility(View.GONE);
                break;
            case R.id.damage13_checkbox:
                if (checkboxDamage13.isChecked())
                    layoutDamage13.setVisibility(View.VISIBLE);
                else
                    layoutDamage13.setVisibility(View.GONE);
                break;
            case R.id.reception_damage_next_button:
                SaveData();

                orderSavedData = ((ReceptionMain) getActivity()).dataToFragment();
                ListOfListsForUpload();
                Log.e("def size", orderSavedData.Deformations.size() + "");
                Log.e("dam size", orderSavedData.Damages.size() + "");
                for (int i = 0; i < orderSavedData.GeneralLeft.size(); i++) {
                    Uri uri = orderSavedData.GeneralLeft.get(i);
                    uploadTask(uri, "generalLeft"+i);
                }
                for (int i = 0; i < orderSavedData.GeneralRight.size(); i++) {
                    Uri uri = orderSavedData.GeneralRight.get(i);
                    uploadTask(uri, "generalRight"+i);
                }
                for (int i = 0; i < orderSavedData.GeneralDoors.size(); i++) {
                    Uri uri = orderSavedData.GeneralDoors.get(i);
                    uploadTask(uri, "generalDoors"+i);
                }
                for (int i = 0; i < orderSavedData.GeneralInside.size(); i++) {
                    Uri uri = orderSavedData.GeneralInside.get(i);
                    uploadTask(uri, "generalInside"+i);
                }
                for (int i = 0; i < orderSavedData.Deformations.size(); i++)
                {
                    for (int j = 0; j < orderSavedData.Deformations.get(i).size(); j++)
                    {
                        Uri uri = orderSavedData.Deformations.get(i).get(j);
                        uploadTask(uri, "deformation" + i + "_" + j);
                    }
                }
                for (int i = 0; i < orderSavedData.Damages.size(); i++)
                {
                    ArrayList<Uri> temp = orderSavedData.Damages.get(i);
                    for (int j = 0; j < temp.size(); j++)
                    {
                        Uri uri = temp.get(j);
                        uploadTask(uri, "damage" + i + "_" + j);
                    }
                }

                ((ReceptionMain) getActivity()).finish();

                break;
            case R.id.reception_damage_back_button:
                SaveData();

                getActivity()
                        .getSupportFragmentManager()
                        .popBackStack();
                break;
        }
    }

    private void ListOfListsForUpload() {
        orderSavedData.Deformations.add(orderSavedData.Deformation1);
        orderSavedData.Deformations.add(orderSavedData.Deformation2);
        orderSavedData.Deformations.add(orderSavedData.Deformation3);
        orderSavedData.Deformations.add(orderSavedData.Deformation4);
        orderSavedData.Deformations.add(orderSavedData.Deformation5);
        orderSavedData.Deformations.add(orderSavedData.Deformation6);
        orderSavedData.Deformations.add(orderSavedData.Deformation7);
        orderSavedData.Damages.add(orderSavedData.Damage1);
        orderSavedData.Damages.add(orderSavedData.Damage2);
        orderSavedData.Damages.add(orderSavedData.Damage3);
        orderSavedData.Damages.add(orderSavedData.Damage4);
        orderSavedData.Damages.add(orderSavedData.Damage5);
        orderSavedData.Damages.add(orderSavedData.Damage6);
        orderSavedData.Damages.add(orderSavedData.Damage7);
        orderSavedData.Damages.add(orderSavedData.Damage8);
        orderSavedData.Damages.add(orderSavedData.Damage9);
        orderSavedData.Damages.add(orderSavedData.Damage10);
        orderSavedData.Damages.add(orderSavedData.Damage11);
        orderSavedData.Damages.add(orderSavedData.Damage12);
        orderSavedData.Damages.add(orderSavedData.Damage13);
    }

    protected void uploadTask(Uri uri, String imageName) {
        decodeFile(uri);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] data = bos.toByteArray();
        String file = Base64.encodeToString(data, 0);
        //Log.e("base64 string", file);
        new ImageUploadTask(file, imageName).execute();
    }

    public void decodeFile(Uri uri) {
        if (Build.VERSION.SDK_INT >= 29) {
            try (ParcelFileDescriptor pfd = getActivity().getContentResolver().openFileDescriptor(uri, "r")) {
                if (pfd != null) {
                    bitmap = BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor());
                }
            } catch (IOException ex) {

            }
        } else{
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(getRealPathFromURI(uri), o);

                // The new size we want to scale to
                final int REQUIRED_SIZE = 1*1024*1024;

                // Find the correct scale value. It should be the power of 2.
                int width_tmp = o.outWidth, height_tmp = o.outHeight;
                int scale = 1;
                while (true) {
                    if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                        break;
                    width_tmp /= 2;
                    height_tmp /= 2;
                    scale *= 2;
                }

                BitmapFactory.Options o2 = new BitmapFactory.Options();
                o2.inSampleSize = scale;
                bitmap = BitmapFactory.decodeFile(getRealPathFromURI(uri), o2);
            }

    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
