package com.example.caroproject;

import android.app.Dialog;
import android.content.DialogInterface;
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

public class ChooseBackgroundDialogFragment extends DialogFragment {
    private Background[] items;
    private GridView gridView;

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
                        items[gridView.getCheckedItemPosition()].getLayoutBackground()
                );
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
        items = new Background[4];
        items[0] = new Background(R.drawable.temp_background_1, R.drawable.background_1, R.drawable.custom_button_1, R.drawable.custom_edittext);
        items[1] = new Background(R.drawable.temp_background_2, R.drawable.background_2, R.drawable.custom_button_2, R.drawable.custom_edittext);
        items[2] = new Background(R.drawable.temp_background_3, R.drawable.background_3, R.drawable.custom_button_1, R.drawable.custom_edittext);
        items[3] = new Background(R.drawable.temp_background_4, R.drawable.background_4, R.drawable.custom_button_2, R.drawable.custom_edittext);
    }

}
