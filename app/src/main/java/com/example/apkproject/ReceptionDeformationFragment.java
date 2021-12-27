package com.example.apkproject;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class ReceptionDeformationFragment extends Fragment implements View.OnClickListener {

    //region variables
    View mainView;
    OnDataPass dataPasser;
    OrderSavedData orderSavedData;
    ArrayList<Uri> imagesDeformation1, imagesDeformation2, imagesDeformation3, imagesDeformation4,
            imagesDeformation5, imagesDeformation6, imagesDeformation7;
    RecyclerViewAdapter adapterDeformation1, adapterDeformation2, adapterDeformation3,
            adapterDeformation4, adapterDeformation5, adapterDeformation6, adapterDeformation7;
    CheckBox checkboxDeformation1, checkboxDeformation2, checkboxDeformation3, checkboxDeformation4,
            checkboxDeformation5, checkboxDeformation6, checkboxDeformation7;
    LinearLayout layoutDeformation1, layoutDeformation2, layoutDeformation3, layoutDeformation4,
            layoutDeformation5, layoutDeformation6, layoutDeformation7;
    Button addButtonDeformation1, addButtonDeformation2, addButtonDeformation3,
            addButtonDeformation4, addButtonDeformation5, addButtonDeformation6, addButtonDeformation7;
    Button backButton, nextButton;
    RecyclerView rvDeformation1, rvDeformation2, rvDeformation3, rvDeformation4, rvDeformation5,
            rvDeformation6, rvDeformation7;
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
        dataPasser = (OnDataPass)context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reception_deformation, container, false);
        photoPickingIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                .setType("image/*")
                .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        orderSavedData = ((ReceptionMain)getActivity()).dataToFragment();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deformation1_add_button:
                StartPhotoPickingActivity(imagesDeformation1, adapterDeformation1,
                        R.id.deformation1_add_button, R.id.deformation1_rv,
                        R.id.deformation1_checkbox, R.id.deformation1_layout);
                break;
            case R.id.deformation2_add_button:
                StartPhotoPickingActivity(imagesDeformation2, adapterDeformation2,
                        R.id.deformation2_add_button, R.id.deformation2_rv,
                        R.id.deformation2_checkbox, R.id.deformation2_layout);
                break;
            case R.id.deformation3_add_button:
                StartPhotoPickingActivity(imagesDeformation3, adapterDeformation3,
                        R.id.deformation3_add_button, R.id.deformation3_rv,
                        R.id.deformation3_checkbox, R.id.deformation3_layout);
                break;
            case R.id.deformation4_add_button:
                StartPhotoPickingActivity(imagesDeformation4, adapterDeformation4,
                        R.id.deformation4_add_button, R.id.deformation4_rv,
                        R.id.deformation4_checkbox, R.id.deformation4_layout);
                break;
            case R.id.deformation5_add_button:
                StartPhotoPickingActivity(imagesDeformation5, adapterDeformation5,
                        R.id.deformation5_add_button, R.id.deformation5_rv,
                        R.id.deformation5_checkbox, R.id.deformation5_layout);
                break;
            case R.id.deformation6_add_button:
                StartPhotoPickingActivity(imagesDeformation6, adapterDeformation6,
                        R.id.deformation6_add_button, R.id.deformation6_rv,
                        R.id.deformation6_checkbox, R.id.deformation6_layout);
                break;
            case R.id.deformation7_add_button:
                StartPhotoPickingActivity(imagesDeformation7, adapterDeformation7,
                        R.id.deformation7_add_button, R.id.deformation7_rv,
                        R.id.deformation7_checkbox, R.id.deformation7_layout);
                break;
            case R.id.deformation1_checkbox:
                if (checkboxDeformation1.isChecked())
                {
                    layoutDeformation1.setVisibility(View.VISIBLE);
                }
                else
                {
                    layoutDeformation1.setVisibility(View.GONE);
                }
                break;
            case R.id.deformation2_checkbox:
                if (checkboxDeformation2.isChecked())
                {
                    layoutDeformation2.setVisibility(View.VISIBLE);
                }
                else
                {
                    layoutDeformation2.setVisibility(View.GONE);
                }
                break;
            case R.id.deformation3_checkbox:
                if (checkboxDeformation3.isChecked())
                {
                    layoutDeformation3.setVisibility(View.VISIBLE);
                }
                else
                {
                    layoutDeformation3.setVisibility(View.GONE);
                }
                break;
            case R.id.deformation4_checkbox:
                if (checkboxDeformation4.isChecked())
                {
                    layoutDeformation4.setVisibility(View.VISIBLE);
                }
                else
                {
                    layoutDeformation4.setVisibility(View.GONE);
                }
                break;
            case R.id.deformation5_checkbox:
                if (checkboxDeformation5.isChecked())
                {
                    layoutDeformation5.setVisibility(View.VISIBLE);
                }
                else
                {
                    layoutDeformation5.setVisibility(View.GONE);
                }
                break;
            case R.id.deformation6_checkbox:
                if (checkboxDeformation6.isChecked())
                {
                    layoutDeformation6.setVisibility(View.VISIBLE);
                }
                else
                {
                    layoutDeformation6.setVisibility(View.GONE);
                }
                break;
            case R.id.deformation7_checkbox:
                if (checkboxDeformation7.isChecked())
                {
                    layoutDeformation7.setVisibility(View.VISIBLE);
                }
                else
                {
                    layoutDeformation7.setVisibility(View.GONE);
                }
                break;
            case R.id.reception_deformation_back_button:
                SaveData();

                getActivity()
                        .getSupportFragmentManager()
                        .popBackStack();
                break;
            case R.id.reception_deformation_next_button:
                SaveData();

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("frag3")
                        .replace(R.id.data_fragment, new ReceptionDamageFragment())
                        .commit();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ArrayList<Uri> imagesUri = new ArrayList<Uri>();
        ClipData clipData = data.getClipData();
        if (resultCode == RESULT_OK)
        {
            if (clipData == null)
            {
                Uri uri = data.getData();
                imagesUri.add(uri);
            }
            else {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    imagesUri.add(uri);
                }
            }
        }
        else
        {
            if (tempUriList.size() == 0) {
                mainView.findViewById(tempButtonId).setVisibility(View.VISIBLE);
                mainView.findViewById(tempRVId).setVisibility(View.GONE);
            }
        }
        tempUriList.addAll(0, imagesUri);
        tempAdapter.notifyItemRangeInserted(0, imagesUri.size());
    }

    void FindViews(View view)
    {
        backButton = view.findViewById(R.id.reception_deformation_back_button);
        nextButton = view.findViewById(R.id.reception_deformation_next_button);

        checkboxDeformation1 = view.findViewById(R.id.deformation1_checkbox);
        layoutDeformation1 = view.findViewById(R.id.deformation1_layout);
        addButtonDeformation1 = view.findViewById(R.id.deformation1_add_button);
        rvDeformation1 = view.findViewById(R.id.deformation1_rv);

        checkboxDeformation2 = view.findViewById(R.id.deformation2_checkbox);
        layoutDeformation2 = view.findViewById(R.id.deformation2_layout);
        addButtonDeformation2 = view.findViewById(R.id.deformation2_add_button);
        rvDeformation2 = view.findViewById(R.id.deformation2_rv);

        checkboxDeformation3 = view.findViewById(R.id.deformation3_checkbox);
        layoutDeformation3 = view.findViewById(R.id.deformation3_layout);
        addButtonDeformation3 = view.findViewById(R.id.deformation3_add_button);
        rvDeformation3 = view.findViewById(R.id.deformation3_rv);

        checkboxDeformation4 = view.findViewById(R.id.deformation4_checkbox);
        layoutDeformation4 = view.findViewById(R.id.deformation4_layout);
        addButtonDeformation4 = view.findViewById(R.id.deformation4_add_button);
        rvDeformation4 = view.findViewById(R.id.deformation4_rv);

        checkboxDeformation5 = view.findViewById(R.id.deformation5_checkbox);
        layoutDeformation5 = view.findViewById(R.id.deformation5_layout);
        addButtonDeformation5 = view.findViewById(R.id.deformation5_add_button);
        rvDeformation5 = view.findViewById(R.id.deformation5_rv);

        checkboxDeformation6 = view.findViewById(R.id.deformation6_checkbox);
        layoutDeformation6 = view.findViewById(R.id.deformation6_layout);
        addButtonDeformation6 = view.findViewById(R.id.deformation6_add_button);
        rvDeformation6 = view.findViewById(R.id.deformation6_rv);

        checkboxDeformation7 = view.findViewById(R.id.deformation7_checkbox);
        layoutDeformation7 = view.findViewById(R.id.deformation7_layout);
        addButtonDeformation7 = view.findViewById(R.id.deformation7_add_button);
        rvDeformation7 = view.findViewById(R.id.deformation7_rv);
    }

    void CreateRVsAdapters()
    {
        adapterDeformation1 = new RecyclerViewAdapter(getActivity(), imagesDeformation1);
        adapterDeformation2 = new RecyclerViewAdapter(getActivity(), imagesDeformation2);
        adapterDeformation3 = new RecyclerViewAdapter(getActivity(), imagesDeformation3);
        adapterDeformation4 = new RecyclerViewAdapter(getActivity(), imagesDeformation4);
        adapterDeformation5 = new RecyclerViewAdapter(getActivity(), imagesDeformation5);
        adapterDeformation6 = new RecyclerViewAdapter(getActivity(), imagesDeformation6);
        adapterDeformation7 = new RecyclerViewAdapter(getActivity(), imagesDeformation7);
    }

    void TempVariablesAssign(ArrayList<Uri> imagesList, RecyclerViewAdapter adapter,
                                     int buttonId, int rvId, int checkboxId, int layoutId)
    {
        tempUriList = imagesList;
        tempAdapter = adapter;
        tempButtonId = buttonId;
        tempRVId = rvId;
        tempCheckboxId = checkboxId;
        tempLayoutId = layoutId;
    }

    void StartPhotoPickingActivity(ArrayList<Uri> imagesList, RecyclerViewAdapter adapter,
                                   int buttonId, int rvId, int checkboxId, int layoutId)
    {
        TempVariablesAssign(imagesList, adapter, buttonId,
                rvId, checkboxId, layoutId);
        startActivityForResult(photoPickingIntent, 1);
        mainView.findViewById(buttonId).setVisibility(View.GONE);
        mainView.findViewById(rvId).setVisibility(View.VISIBLE);
    }

    void RestoreImageLists()
    {
        imagesDeformation1 = orderSavedData.Deformation1.size() == 0 ? new ArrayList<>() : orderSavedData.Deformation1;
        imagesDeformation2 = orderSavedData.Deformation2.size() == 0 ? new ArrayList<>() : orderSavedData.Deformation2;
        imagesDeformation3 = orderSavedData.Deformation3.size() == 0 ? new ArrayList<>() : orderSavedData.Deformation3;
        imagesDeformation4 = orderSavedData.Deformation4.size() == 0 ? new ArrayList<>() : orderSavedData.Deformation4;
        imagesDeformation5 = orderSavedData.Deformation5.size() == 0 ? new ArrayList<>() : orderSavedData.Deformation5;
        imagesDeformation6 = orderSavedData.Deformation6.size() == 0 ? new ArrayList<>() : orderSavedData.Deformation6;
        imagesDeformation7 = orderSavedData.Deformation7.size() == 0 ? new ArrayList<>() : orderSavedData.Deformation7;
    }

    void SetButtonListeners()
    {
        addButtonDeformation1.setOnClickListener(this);
        addButtonDeformation2.setOnClickListener(this);
        addButtonDeformation3.setOnClickListener(this);
        addButtonDeformation4.setOnClickListener(this);
        addButtonDeformation5.setOnClickListener(this);
        addButtonDeformation6.setOnClickListener(this);
        addButtonDeformation7.setOnClickListener(this);
        checkboxDeformation1.setOnClickListener(this);
        checkboxDeformation2.setOnClickListener(this);
        checkboxDeformation3.setOnClickListener(this);
        checkboxDeformation4.setOnClickListener(this);
        checkboxDeformation5.setOnClickListener(this);
        checkboxDeformation6.setOnClickListener(this);
        checkboxDeformation7.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    void SetAdapterListeners()
    {
        adapterDeformation1.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesDeformation1.size())
                {
                    TempVariablesAssign(imagesDeformation1, adapterDeformation1,
                            R.id.deformation1_add_button, R.id.deformation1_rv,
                            R.id.deformation1_checkbox, R.id.deformation1_layout);
                    startActivityForResult(photoPickingIntent, 1);
                }
                else
                {
                    Toast.makeText(getActivity(), "not implemented, 1 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterDeformation2.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesDeformation2.size())
                {
                    TempVariablesAssign(imagesDeformation2, adapterDeformation2,
                            R.id.deformation2_add_button, R.id.deformation2_rv,
                            R.id.deformation2_checkbox, R.id.deformation2_layout);
                    startActivityForResult(photoPickingIntent, 1);
                }
                else
                {
                    Toast.makeText(getActivity(), "not implemented, 2 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterDeformation3.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesDeformation3.size())
                {
                    TempVariablesAssign(imagesDeformation3, adapterDeformation3,
                            R.id.deformation3_add_button, R.id.deformation3_rv,
                            R.id.deformation3_checkbox, R.id.deformation3_layout);
                    startActivityForResult(photoPickingIntent, 1);
                }
                else
                {
                    Toast.makeText(getActivity(), "not implemented, 3 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterDeformation4.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesDeformation4.size())
                {
                    TempVariablesAssign(imagesDeformation4, adapterDeformation4,
                            R.id.deformation4_add_button, R.id.deformation4_rv,
                            R.id.deformation4_checkbox, R.id.deformation4_layout);
                    startActivityForResult(photoPickingIntent, 1);
                }
                else
                {
                    Toast.makeText(getActivity(), "not implemented, 4 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterDeformation5.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesDeformation5.size())
                {
                    TempVariablesAssign(imagesDeformation5, adapterDeformation5,
                            R.id.deformation5_add_button, R.id.deformation5_rv,
                            R.id.deformation5_checkbox, R.id.deformation5_layout);
                    startActivityForResult(photoPickingIntent, 1);
                }
                else
                {
                    Toast.makeText(getActivity(), "not implemented, 5 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterDeformation6.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesDeformation6.size())
                {
                    TempVariablesAssign(imagesDeformation6, adapterDeformation6,
                            R.id.deformation6_add_button, R.id.deformation6_rv,
                            R.id.deformation6_checkbox, R.id.deformation6_layout);
                    startActivityForResult(photoPickingIntent, 1);
                }
                else
                {
                    Toast.makeText(getActivity(), "not implemented, 6 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterDeformation7.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesDeformation7.size())
                {
                    TempVariablesAssign(imagesDeformation7, adapterDeformation7,
                            R.id.deformation7_add_button, R.id.deformation7_rv,
                            R.id.deformation7_checkbox, R.id.deformation7_layout);
                    startActivityForResult(photoPickingIntent, 1);
                }
                else
                {
                    Toast.makeText(getActivity(), "not implemented, 7 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void SetRVAdapters()
    {
        rvDeformation1.setAdapter(adapterDeformation1);
        rvDeformation2.setAdapter(adapterDeformation2);
        rvDeformation3.setAdapter(adapterDeformation3);
        rvDeformation4.setAdapter(adapterDeformation4);
        rvDeformation5.setAdapter(adapterDeformation5);
        rvDeformation6.setAdapter(adapterDeformation6);
        rvDeformation7.setAdapter(adapterDeformation7);
    }

    void SaveData()
    {
        orderSavedData.Deformation1 = checkboxDeformation1.isChecked() ? imagesDeformation1 : new ArrayList<>();
        orderSavedData.Deformation2 = checkboxDeformation2.isChecked() ? imagesDeformation2 : new ArrayList<>();
        orderSavedData.Deformation3 = checkboxDeformation3.isChecked() ? imagesDeformation3 : new ArrayList<>();
        orderSavedData.Deformation4 = checkboxDeformation4.isChecked() ? imagesDeformation4 : new ArrayList<>();
        orderSavedData.Deformation5 = checkboxDeformation5.isChecked() ? imagesDeformation5 : new ArrayList<>();
        orderSavedData.Deformation6 = checkboxDeformation6.isChecked() ? imagesDeformation6 : new ArrayList<>();
        orderSavedData.Deformation7 = checkboxDeformation7.isChecked() ? imagesDeformation7 : new ArrayList<>();

        dataPasser.onFullDataPass(orderSavedData, "pass data");
    }

    void SetVisibilityBasedOnSavedData()
    {
        if (imagesDeformation1.size() > 0)
        {
            layoutDeformation1.setVisibility(View.VISIBLE);
            rvDeformation1.setVisibility(View.VISIBLE);
            addButtonDeformation1.setVisibility(View.GONE);
            checkboxDeformation1.setChecked(true);
        }
        if (imagesDeformation2.size() > 0)
        {
            layoutDeformation2.setVisibility(View.VISIBLE);
            rvDeformation2.setVisibility(View.VISIBLE);
            addButtonDeformation2.setVisibility(View.GONE);
            checkboxDeformation2.setChecked(true);
        }
        if (imagesDeformation3.size() > 0)
        {
            layoutDeformation3.setVisibility(View.VISIBLE);
            rvDeformation3.setVisibility(View.VISIBLE);
            addButtonDeformation3.setVisibility(View.GONE);
            checkboxDeformation3.setChecked(true);
        }
        if (imagesDeformation4.size() > 0)
        {
            layoutDeformation4.setVisibility(View.VISIBLE);
            rvDeformation4.setVisibility(View.VISIBLE);
            addButtonDeformation4.setVisibility(View.GONE);
            checkboxDeformation4.setChecked(true);
        }
        if (imagesDeformation5.size() > 0)
        {
            layoutDeformation5.setVisibility(View.VISIBLE);
            rvDeformation5.setVisibility(View.VISIBLE);
            addButtonDeformation5.setVisibility(View.GONE);
            checkboxDeformation5.setChecked(true);
        }
        if (imagesDeformation6.size() > 0)
        {
            layoutDeformation6.setVisibility(View.VISIBLE);
            rvDeformation6.setVisibility(View.VISIBLE);
            addButtonDeformation6.setVisibility(View.GONE);
            checkboxDeformation6.setChecked(true);
        }
        if (imagesDeformation7.size() > 0)
        {
            layoutDeformation7.setVisibility(View.VISIBLE);
            rvDeformation7.setVisibility(View.VISIBLE);
            addButtonDeformation7.setVisibility(View.GONE);
            checkboxDeformation7.setChecked(true);
        }
    }
}