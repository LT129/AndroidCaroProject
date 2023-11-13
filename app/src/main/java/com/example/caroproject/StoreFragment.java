package com.example.caroproject;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.caroproject.Adapter.CustomStoreGridviewAdapter;
import com.example.caroproject.Data.Coins;
import com.example.caroproject.Data.StoreItems;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoreFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private GridView viewItems;
    private StoreItems storeItems[];
    private ImageButton btnCallBack;
    private ImageView showItem;

    private RadioGroup groupButton;
    private RadioButton btnBackground;
    private RadioButton btnShape;

    public StoreFragment() {
        // Required empty public constructor
    }
    public static StoreFragment newInstance(String param1, String param2) {
        StoreFragment fragment = new StoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        showBuyDialog();
        showItem = view.findViewById(R.id.showItem);
        viewItems = view.findViewById(R.id.viewItems);
        CustomStoreGridviewAdapter customStoreGridviewAdapter =
                new CustomStoreGridviewAdapter(view.getContext(), R.layout.store_item_gridview, storeItems);
        viewItems.setAdapter(customStoreGridviewAdapter);
        viewItems.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        viewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewItems.setItemChecked(position, true);
                showItem.setImageResource(storeItems[position].getLayoutBackground());
            }
        });

        groupButton = view.findViewById(R.id.groupButton);
        btnBackground = view.findViewById(R.id.btnBackground);
        btnShape = view.findViewById(R.id.btnShape);

        groupButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                btnBackground.setBackgroundResource(R.drawable.store_wait_choose_button);
                btnShape.setBackgroundResource(R.drawable.store_wait_choose_button);
                view.findViewById(checkedId).setBackgroundResource(R.drawable.store_on_choose_button);
            }
        });

        btnCallBack = view.findViewById(R.id.btnCallBack);
        btnCallBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });

        return view;
    }

    private void getData() {
        storeItems = new StoreItems[4];
        storeItems[0] = new StoreItems(R.drawable.temp_background_1, R.drawable.background_1, R.drawable.custom_button_1, R.drawable.custom_edittext, new Coins(200));
        storeItems[1] = new StoreItems(R.drawable.temp_background_2, R.drawable.background_2, R.drawable.custom_button_2, R.drawable.custom_edittext, new Coins(300));
        storeItems[2] = new StoreItems(R.drawable.temp_background_3, R.drawable.background_3, R.drawable.custom_button_1, R.drawable.custom_edittext, new Coins(400));
        storeItems[3] = new StoreItems(R.drawable.temp_background_4, R.drawable.background_4, R.drawable.custom_button_2, R.drawable.custom_edittext, new Coins(500));
    }

    private Dialog showBuyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_ask_to_buy_item)
                .setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO check the usercoins
                    }
                })
                .setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO nothing happen
                    }
                });
        return builder.create();
    }

}