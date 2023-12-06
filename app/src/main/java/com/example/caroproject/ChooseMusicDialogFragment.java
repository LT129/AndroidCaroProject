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
import com.example.caroproject.Adapter.CustomChooseMusicAdapter;
import com.example.caroproject.Data.AppData;
import com.example.caroproject.Data.Background;
import com.example.caroproject.Data.Music;
import com.example.caroproject.Data.SoundMaking;
import com.example.caroproject.Data.StoreItem;
import com.example.caroproject.Data.UserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ChooseMusicDialogFragment extends DialogFragment {
    private ArrayList<StoreItem> items;
    private ArrayList<Boolean> userStatus;
    private GridView gridView;
    private SharedPreferences pref;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // initiate data
        initiateData();

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_choose_background_dialog, null);
        gridView = view.findViewById(R.id.backgroundGridView);

        CustomChooseMusicAdapter adapter = new CustomChooseMusicAdapter(getActivity(), R.layout.custom_item_choose_background_gridview, items);
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
                SoundMaking.getInstance().releaseMusic();
                SoundMaking.getInstance().createMusic(requireContext(),
                        ((Music)items.get(gridView.getCheckedItemPosition()).getItem()).getSourceId());
                SoundMaking.getInstance().playMusic();
                pref.edit().putInt(MainActivity.MUSIC, toDataMusicPosition(gridView.getCheckedItemPosition())).apply();
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
        pref = requireActivity().getSharedPreferences(MainActivity.PREF_FILE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString("USER_INFORMATION", null);
        Type type = new TypeToken<UserInfo>() {
        }.getType();
        UserInfo userInfo = gson.fromJson(json, type);


        items = new ArrayList<>();
        userStatus = userInfo.getMusicStatus();

        for (int i = 0; i < AppData.getInstance().getMusicList().size(); i++) {
            if (userStatus.get(i)) {
                items.add(AppData.getInstance().getMusicList().get(i));
            }
        }
    }

    private int toDataMusicPosition(int pos) {
        int i = 0;
        while(pos >= 0) {
            if(userStatus.get(i)) {
                pos--;
            }
            i++;
        }

        return i - 1;
    }
}
