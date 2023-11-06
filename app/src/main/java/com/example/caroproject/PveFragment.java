package com.example.caroproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PveFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnStartPlay;
    private RadioButton rdoSize9, rdoSize15, rdoSize21;

    public PveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PvcFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PveFragment newInstance(String param1, String param2) {
        PveFragment fragment = new PveFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pve, container, false);
        btnStartPlay = view.findViewById(R.id.btnPlayPVE);
        rdoSize9=view.findViewById(R.id.boardSize9PVE);
        rdoSize15=view.findViewById(R.id.boardSize15PVE);
        rdoSize21=view.findViewById(R.id.boardSize21PVE);
        btnStartPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO create an argument and pass it to next fragment
                Bundle bundle = new Bundle();
                // Đặt dữ liệu vào Bundle, ví dụ:
                int sizeBoard;
                if (rdoSize9.isChecked()) {
                    sizeBoard = 10;
                } else if (rdoSize15.isChecked()) {
                    sizeBoard = 15;
                } else {
                    sizeBoard = 20;
                }
                bundle.putInt("sizeBoard", sizeBoard);
                if ((rdoSize21.isChecked() || rdoSize15.isChecked() || rdoSize9.isChecked())) {
                    NavController navController = Navigation.findNavController(v);
                    navController.navigate(R.id.action_pveFragment_to_inGameAIFragment, bundle);
                }
            }
        });
        ImageButton btnCallBack = view.findViewById(R.id.btnCallBackPVE);
        btnCallBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().getOnBackPressedDispatcher().onBackPressed();
                NavController navController=Navigation.findNavController(v);
                navController.navigate(R.id.action_pveFragment_to_gameModeFragment);
            }
        });

        return view;
    }
}