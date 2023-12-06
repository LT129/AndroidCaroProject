package com.example.caroproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.caroproject.Data.Room;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PvpFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private EditText edtRoomID;
    private ImageButton btnCallBack;

    private RadioGroup playMode, rdoRoom;
    private RadioButton rdoOffline, rdoOnline, rdoSize9, rdoSize15, rdoSize21, time15, time45,
            timeUnlimited, rdoJoinRoom, rdoCreateRoom, rdoRandomRoom;

    private Button btnPlay;
    public PvpFragment() {
        // Required empty public constructor
    }
    public static PvpFragment newInstance(String param1, String param2) {
        PvpFragment fragment = new PvpFragment();
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
        View view = inflater.inflate(R.layout.fragment_pvp, container, false);


        playMode = view.findViewById(R.id.playMode);
        LinearLayout lnRoom=view.findViewById(R.id.lnRoom);
        playMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton mode = (RadioButton) view.findViewById(checkedId);
                switch (mode.getText().toString()) {
                    case"Online": {
                        //TODO setting for online mode
                        lnRoom.setVisibility(lnRoom.VISIBLE);
                        break;
                    }

                    case"Offline": {
                        //TODO setting for offline mode
                        lnRoom.setVisibility(lnRoom.GONE);
                        break;
                    }
                }
            }
        });

        rdoRoom=view.findViewById(R.id.Room);
        edtRoomID=view.findViewById(R.id.edtRoomID);
        rdoRoom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton mode = (RadioButton) view.findViewById(checkedId);
                switch (mode.getText().toString()) {
                    case"Join Room": {
                        //TODO setting for online mode
                        edtRoomID.setVisibility(edtRoomID.VISIBLE);
                        break;
                    }
                }
            }
        });

        btnPlay = view.findViewById(R.id.btnPlay);
        rdoOffline=view.findViewById(R.id.offlinePlayMode);
        rdoOnline=view.findViewById(R.id.onlinePlayMode);
        rdoSize9=view.findViewById(R.id.boardSize9);
        rdoSize15=view.findViewById(R.id.boardSize15);
        rdoSize21=view.findViewById(R.id.boardSize21);
        time45=view.findViewById(R.id.time45sPVE);
        time15=view.findViewById(R.id.time15sPVE);
        timeUnlimited=view.findViewById(R.id.timeUnlimitedPVE);
        rdoJoinRoom=view.findViewById(R.id.joinRoom);
        rdoCreateRoom=view.findViewById(R.id.createRoom);
        rdoRandomRoom=view.findViewById(R.id.randomRoom);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO create an argument and pass it to next fragment

                // Tạo một Bundle để chứa dữ liệu
                Bundle bundle = new Bundle();
                // Đặt dữ liệu vào Bundle, ví dụ:
                int sizeBoard;
                if(rdoSize9.isChecked()){
                    sizeBoard=10;
                } else if (rdoSize15.isChecked()) {
                    sizeBoard=15;
                } else{
                    sizeBoard=20;
                }
                int time;
                if(time15.isChecked()){
                    time=16000;
                } else if (time45.isChecked()) {
                    time=46000;
                }else{
                    time=-1;
                }


                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference roomRef=database.getReference("room");
                List<Room> listIdRoom=new ArrayList<>();
                roomRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listIdRoom.clear();
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            Room room=dataSnapshot.getValue(Room.class);
                            listIdRoom.add(room);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

                bundle.putInt("sizeBoard", sizeBoard);
                bundle.putInt("time", time);
                String idRoom="";
                if((rdoSize21.isChecked()||rdoSize15.isChecked()||rdoSize9.isChecked())
                        &&(time15.isChecked()||time45.isChecked()||timeUnlimited.isChecked())) {
                    if (rdoOffline.isChecked()) {
                        NavController navController = Navigation.findNavController(v);
                        navController.navigate(R.id.action_pvpFragment_to_inGameFragment, bundle);
                    }
                    else{
                        if(rdoCreateRoom.isChecked()){
                            Random random=new Random();
                            int i=0;
                            do {
                                idRoom = "r" + random.nextInt(10) + random.nextInt(10)
                                        + random.nextInt(10) + random.nextInt(10);
                                for (Room room : listIdRoom) {
                                    if (room.getRoomId().equals(idRoom)) {
                                        i=1;
                                        break;
                                    }
                                }
                            } while (i!=0);

                            Room room=new Room(time, idRoom, false);
                            roomRef.child(idRoom).setValue(room);


                            //nạp currentPlayer, player1
                        } else if (rdoJoinRoom.isChecked()) {
                            idRoom=edtRoomID.getText().toString().trim();
                            for (Room room : listIdRoom) {
                                if (room.getRoomId().equals(idRoom)) {
                                    //nap player2
                                    break;
                                }
                            }
                        }
                        bundle.putString("idRoom", idRoom);
                        NavController navController = Navigation.findNavController(v);
                        navController.navigate(R.id.action_pvpFragment_to_inGameOnlineFragment, bundle);
                    }
                }
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
}