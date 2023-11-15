package com.example.caroproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.caroproject.Adapter.CustomChooseBackgroundAdapter;
import com.example.caroproject.Data.Background;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ChooseBackgroundDialogFragment extends DialogFragment {
    private ArrayList<Background> items;
    private GridView gridView;
    private SharedPreferences pref;
    private Gson gson;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        getData();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_choose_background_dialog, null);
        gridView = view.findViewById(R.id.backgroundGridView);

        CustomChooseBackgroundAdapter adapter = new CustomChooseBackgroundAdapter(getActivity(), R.layout.custom_item_choose_background_gridview, items);
        gridView.setAdapter(adapter);
        gridView.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        gridView.setItemChecked(0, true);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gridView.setItemChecked(position, true);
            }
        });
        builder.setView(view);


        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getActivity().getWindow().setBackgroundDrawableResource(
                        items.get(gridView.getCheckedItemPosition()).getLayoutBackground()
                );
                pref.edit().putString("BG_POSITION", String.valueOf(gridView.getCheckedItemPosition())).apply();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // TODO nothing happen
            }
        });

        return builder.create();
    }

    private void getData() {
        pref = requireActivity().getSharedPreferences("CARO", Context.MODE_PRIVATE);
        gson = new Gson();
        String json = pref.getString("USER_BACKGROUND", null);
        Type type = new TypeToken<ArrayList<Background>>(){}.getType();
        items = gson.fromJson(json, type);
    }

}
