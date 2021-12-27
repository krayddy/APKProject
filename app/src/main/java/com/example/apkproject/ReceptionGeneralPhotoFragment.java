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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class ReceptionGeneralPhotoFragment extends Fragment implements View.OnClickListener {

    //region variables
    View mainView;
    private OnDataPass dataPasser;
    private ArrayList<Uri> imagesFromLeft;
    private ArrayList<Uri> imagesFromRight;
    private ArrayList<Uri> imagesInside;
    private ArrayList<Uri> imagesDoors;
    private ArrayList<Uri> tempUriList;
    ArrayList<String> imagesEncodedList;
    RecyclerViewAdapter adapterRight;
    RecyclerViewAdapter adapterLeft;
    RecyclerViewAdapter adapterInside;
    RecyclerViewAdapter adapterDoors;
    RecyclerViewAdapter tempAdapter;
    Intent photoPicking;
    int tempListId;
    int tempButtonId;
    OrderSavedData orderSavedData;
    //endregion

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass)context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reception_general_photo, container, false);
        photoPicking = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                .setType("image/*")
                .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        orderSavedData = ((ReceptionMain)getActivity()).dataToFragment();
        tempUriList = new ArrayList<>();
        InitializeVariables();
        InitializeButtons(view);
        InitializeRVs(view);
        SetRVsVisibility(view);
        mainView = view;
        return view;
    }

    private void InitializeButtons(View view) {
        Button fromRightAddButton = view.findViewById(R.id.reception_general_photo_right_add_button);
        Button fromLeftAddButton = view.findViewById(R.id.reception_general_photo_left_add_button);
        Button fromInsideAddButton = view.findViewById(R.id.reception_general_photo_inside_add_button);
        Button doorsAddButton = view.findViewById(R.id.reception_general_photo_doors_add_button);
        Button backButton = view.findViewById(R.id.reception_general_photo_back_button);
        Button nextButton = view.findViewById(R.id.reception_general_photo_next_button);
        fromRightAddButton.setOnClickListener(this);
        fromLeftAddButton.setOnClickListener(this);
        fromInsideAddButton.setOnClickListener(this);
        doorsAddButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
    }

    private void SetRVsVisibility(View view) {

        if (imagesFromLeft.size() > 0)
        {
            view.findViewById(R.id.reception_general_photo_left_add_button).setVisibility(View.GONE);
            view.findViewById(R.id.reception_general_photo_left_recyclerview).setVisibility(View.VISIBLE);
        }
        if (imagesFromRight.size() > 0)
        {
            view.findViewById(R.id.reception_general_photo_right_add_button).setVisibility(View.GONE);
            view.findViewById(R.id.reception_general_photo_right_recyclerview).setVisibility(View.VISIBLE);
        }
        if (imagesInside.size() > 0)
        {
            view.findViewById(R.id.reception_general_photo_inside_add_button).setVisibility(View.GONE);
            view.findViewById(R.id.reception_general_photo_inside_recyclerview).setVisibility(View.VISIBLE);
        }
        if (imagesDoors.size() > 0)
        {
            view.findViewById(R.id.reception_general_photo_doors_add_button).setVisibility(View.GONE);
            view.findViewById(R.id.reception_general_photo_doors_recyclerview).setVisibility(View.VISIBLE);
        }
    }

    private void InitializeVariables() {
        imagesFromLeft = orderSavedData.GeneralLeft.size() == 0 ? new ArrayList<>() : orderSavedData.GeneralLeft;
        imagesFromRight = orderSavedData.GeneralRight.size() == 0 ? new ArrayList<>() : orderSavedData.GeneralRight;
        imagesInside = orderSavedData.GeneralInside.size() == 0 ? new ArrayList<>() : orderSavedData.GeneralInside;
        imagesDoors = orderSavedData.GeneralDoors.size() == 0 ? new ArrayList<>() : orderSavedData.GeneralDoors;
    }

    private void InitializeRVs(View view) {
        RecyclerView photosFromRightRecyclerView = view.findViewById(R.id.reception_general_photo_right_recyclerview);
        adapterRight = new RecyclerViewAdapter(getActivity(), imagesFromRight);
        adapterRight.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesFromRight.size())
                {
                    TempVariablesAssign(imagesFromRight, adapterRight, R.id.reception_general_photo_right_add_button,
                            R.id.reception_general_photo_right_recyclerview);
                    startActivityForResult(photoPicking, 1);
                }
                else
                {
                    Toast.makeText(getActivity(), "not implemented, right clicked", Toast.LENGTH_LONG).show();
                }
            }
        });
        photosFromRightRecyclerView.setAdapter(adapterRight);
        RecyclerView photosFromLeftRecyclerView = view.findViewById(R.id.reception_general_photo_left_recyclerview);
        adapterLeft = new RecyclerViewAdapter(getActivity(), imagesFromLeft);
        adapterLeft.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesFromLeft.size())
                {
                    TempVariablesAssign(imagesFromLeft, adapterLeft, R.id.reception_general_photo_left_add_button,
                            R.id.reception_general_photo_left_recyclerview);
                    startActivityForResult(photoPicking, 1);
                }
                else
                {
                    Toast.makeText(getActivity(), "not implemented, left clicked", Toast.LENGTH_LONG).show();
                }
            }
        });
        photosFromLeftRecyclerView.setAdapter(adapterLeft);
        RecyclerView photosInsideRecyclerView = view.findViewById(R.id.reception_general_photo_inside_recyclerview);
        adapterInside = new RecyclerViewAdapter(getActivity(), imagesInside);
        adapterInside.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesInside.size())
                {
                    TempVariablesAssign(imagesInside, adapterInside, R.id.reception_general_photo_inside_add_button,
                            R.id.reception_general_photo_inside_recyclerview);
                    startActivityForResult(photoPicking, 1);
                }
                else
                {
                    Toast.makeText(getActivity(), "not implemented, inside clicked", Toast.LENGTH_LONG).show();
                }
            }
        });
        photosInsideRecyclerView.setAdapter(adapterInside);
        RecyclerView photosDoorsRecyclerView = view.findViewById(R.id.reception_general_photo_doors_recyclerview);
        adapterDoors = new RecyclerViewAdapter(getActivity(), imagesDoors);
        adapterDoors.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == imagesDoors.size())
                {
                    TempVariablesAssign(imagesDoors, adapterDoors, R.id.reception_general_photo_doors_add_button,
                            R.id.reception_general_photo_doors_recyclerview);
                    startActivityForResult(photoPicking, 1);
                }
                else
                {
                    Toast.makeText(getActivity(), "not implemented, doors clicked", Toast.LENGTH_LONG).show();
                }
            }
        });
        photosDoorsRecyclerView.setAdapter(adapterDoors);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reception_general_photo_left_add_button:
                TempVariablesAssign(imagesFromLeft, adapterLeft, R.id.reception_general_photo_left_add_button,
                        R.id.reception_general_photo_left_recyclerview);
                startActivityForResult(photoPicking, 1);
                v.findViewById(R.id.reception_general_photo_left_add_button).setVisibility(View.GONE);
                mainView.findViewById(R.id.reception_general_photo_left_recyclerview).setVisibility(View.VISIBLE);

                break;
            case R.id.reception_general_photo_right_add_button:
                TempVariablesAssign(imagesFromRight, adapterRight, R.id.reception_general_photo_right_add_button,
                        R.id.reception_general_photo_right_recyclerview);
                startActivityForResult(photoPicking, 1);
                v.findViewById(R.id.reception_general_photo_right_add_button).setVisibility(View.GONE);
                mainView.findViewById(R.id.reception_general_photo_right_recyclerview).setVisibility(View.VISIBLE);

                break;
            case R.id.reception_general_photo_inside_add_button:
                TempVariablesAssign(imagesInside, adapterInside, R.id.reception_general_photo_inside_add_button,
                        R.id.reception_general_photo_inside_recyclerview);
                startActivityForResult(photoPicking, 1);
                v.findViewById(R.id.reception_general_photo_inside_add_button).setVisibility(View.GONE);
                mainView.findViewById(R.id.reception_general_photo_inside_recyclerview).setVisibility(View.VISIBLE);

                break;
            case R.id.reception_general_photo_doors_add_button:
                TempVariablesAssign(imagesDoors, adapterDoors, R.id.reception_general_photo_doors_add_button,
                        R.id.reception_general_photo_doors_recyclerview);
                startActivityForResult(photoPicking, 1);
                v.findViewById(R.id.reception_general_photo_doors_add_button).setVisibility(View.GONE);
                mainView.findViewById(R.id.reception_general_photo_doors_recyclerview).setVisibility(View.VISIBLE);

                break;
            case R.id.reception_general_photo_next_button:

                SaveRVsData();

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("frag2")
                        .replace(R.id.data_fragment, new ReceptionDeformationFragment())
                        .commit();
                break;
            case R.id.reception_general_photo_back_button:
                SaveRVsData();
                getActivity()
                        .getSupportFragmentManager()
                        .popBackStack();
                break;
        }
    }

    private void SaveRVsData() {
        orderSavedData.GeneralRight = imagesFromRight;
        orderSavedData.GeneralLeft = imagesFromLeft;
        orderSavedData.GeneralInside = imagesInside;
        orderSavedData.GeneralDoors = imagesDoors;

        dataPasser.onFullDataPass(orderSavedData, "tag");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        ArrayList<String> imagesEncodedList = new ArrayList<String>();
        ArrayList<Uri> imagesUri = new ArrayList<Uri>();
        if (resultCode == RESULT_OK)
        {
            ClipData clipData = data.getClipData();

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
                mainView.findViewById(tempListId).setVisibility(View.GONE);
            }
        }
        tempUriList.addAll(0, imagesUri);
        tempAdapter.notifyItemRangeInserted(0, imagesUri.size());
    }

    private void TempVariablesAssign(ArrayList<Uri> imagesList, RecyclerViewAdapter adapter,
                                     int buttonId, int rvId){
        tempUriList = imagesList;
        tempAdapter = adapter;
        tempButtonId = buttonId;
        tempListId = rvId;
    }
}
