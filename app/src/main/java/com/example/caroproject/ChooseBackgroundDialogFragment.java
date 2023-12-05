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
import com.example.caroproject.Data.AppData;
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
        // initiate data
        initiateData();
        pref = requireActivity().getSharedPreferences(MainActivity.PREF_FILE, Context.MODE_PRIVATE);

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
                pref.edit().putInt(MainActivity.BACKGROUND, toDataBackgroundPosition(gridView.getCheckedItemPosition())).apply();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // TODO nothing happen
            }
        });

        return builder.create();
    }

    private void initiateData() {
        items = new ArrayList<>();

        for (Background item :
                AppData.getInstance().getBackgrounds()) {
            if (item.wasSold()) {
                items.add(item);
            }
        }
    }

    private int toDataBackgroundPosition(int pos) {
        int i = 0;
        ArrayList<Background> backgrounds = AppData.getInstance().getBackgrounds();
        while(pos >= 0) {
            if(backgrounds.get(i).wasSold()) {
                pos--;
            }
            i++;
        }

        return i - 1;
    }

}
