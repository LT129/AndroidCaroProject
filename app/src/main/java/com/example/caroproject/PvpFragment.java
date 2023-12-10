package com.example.caroproject;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.caroproject.Adapter.NetworkUtils;
import com.example.caroproject.Data.Room;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private String userId;
    private Handler handler;
    private boolean checkBack = true;
    private EditText edtRoomID;
    private int checkCreate = 0;
    private String idRoom = "";
    private ImageButton btnCallBack;
    private String roomId;
    private LinearLayout lnSizeTime;
    private NavController navController;
    private ProgressBar progressBar;
    private RadioGroup playMode, rdoRoom;
    private RadioButton rdoOffline, rdoOnline, rdoSize9, rdoSize15, rdoSize21, time15, time45,
            timeUnlimited, rdoJoinRoom, rdoCreateRoom, rdoRandomRoom;

    private ImageButton chooseShape;
    private ImageButton chooseColor;
    private boolean isValid = false;
    private Button btnPlay;
    private int time, sizeBoard;
    private List<Room> listIdRoom = new ArrayList<>();
    ;

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

    private void showNotLoggedInDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Not Logged In");
        builder.setMessage("You need to log in to perform this action.");
        builder.setCancelable(false);
        builder.setPositiveButton("Log In", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_pvpFragment_to_signInFragment);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showNoInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please check your internet connection and try again.");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pvp, container, false);


        playMode = view.findViewById(R.id.playMode);
        LinearLayout lnRoom = view.findViewById(R.id.lnRoom);
        handler = new Handler(Looper.getMainLooper());
        int delayTime = 100;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference roomRef = database.getReference("room");
                roomRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listIdRoom.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Room room = dataSnapshot.getValue(Room.class);
                            listIdRoom.add(room);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                handler.postDelayed(this, delayTime);
            }
        }, delayTime);
        playMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton mode = (RadioButton) view.findViewById(checkedId);
                switch (mode.getText().toString()) {
                    case "Online": {
                        //TODO setting for online mode
                        lnRoom.setVisibility(lnRoom.VISIBLE);
                        break;
                    }

                    case "Offline": {
                        //TODO setting for offline mode
                        lnRoom.setVisibility(lnRoom.GONE);
                        lnSizeTime.setVisibility(lnSizeTime.VISIBLE);

                        break;
                    }
                }
            }
        });

        rdoRoom = view.findViewById(R.id.Room);
        edtRoomID = view.findViewById(R.id.edtRoomID);
        rdoRoom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton mode = (RadioButton) view.findViewById(checkedId);
                switch (mode.getText().toString()) {
                    case "Join Room": {
                        //TODO setting for online mode
                        edtRoomID.setVisibility(edtRoomID.VISIBLE);
                        lnSizeTime.setVisibility(lnSizeTime.GONE);
                        break;
                    }
                    case "Create Room":
                    case "Random Room": {
                        //TODO setting for online mode
                        edtRoomID.setVisibility(edtRoomID.INVISIBLE);
                        lnSizeTime.setVisibility(lnSizeTime.VISIBLE);
                        break;
                    }
                }
            }
        });

        btnPlay = view.findViewById(R.id.btnPlay);
        rdoOffline = view.findViewById(R.id.offlinePlayMode);
        rdoOnline = view.findViewById(R.id.onlinePlayMode);
        rdoSize9 = view.findViewById(R.id.boardSize9);
        rdoSize15 = view.findViewById(R.id.boardSize15);
        rdoSize21 = view.findViewById(R.id.boardSize21);
        time45 = view.findViewById(R.id.time45sPVE);
        time15 = view.findViewById(R.id.time15sPVE);
        timeUnlimited = view.findViewById(R.id.timeUnlimitedPVE);
        rdoJoinRoom = view.findViewById(R.id.joinRoom);
        rdoCreateRoom = view.findViewById(R.id.createRoom);
        rdoRandomRoom = view.findViewById(R.id.randomRoom);
        lnSizeTime = view.findViewById(R.id.lnSizeTime);
        progressBar = view.findViewById(R.id.progressBar);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBack = false;
                progressBar.setVisibility(View.VISIBLE);

                Bundle bundle = new Bundle();
                if (rdoSize9.isChecked()) {
                    sizeBoard = 10;
                } else if (rdoSize15.isChecked()) {
                    sizeBoard = 15;
                } else {
                    sizeBoard = 20;
                }
                if (time15.isChecked()) {
                    time = 16000;
                } else if (time45.isChecked()) {
                    time = 46000;
                } else {
                    time = 91000;
                }

                if ((rdoSize21.isChecked() || rdoSize15.isChecked() || rdoSize9.isChecked())
                        && (time15.isChecked() || time45.isChecked() || timeUnlimited.isChecked())) {
                    if (rdoOffline.isChecked()) {
                        bundle.putInt("sizeBoard", sizeBoard);
                        bundle.putInt("time", time);
                        navController = Navigation.findNavController(v);
                        navController.navigate(R.id.action_pvpFragment_to_inGameFragment, bundle);
                    } else {
                        // Kiểm tra kết nối internet
                        if (!NetworkUtils.isNetworkAvailable(requireContext())) {
                            showNoInternetDialog();
                            progressBar.setVisibility(View.GONE);
                            return;
                        }
                        if (rdoRandomRoom.isChecked()) {
                            if (!listIdRoom.isEmpty()) {
                                for (Room room : listIdRoom) {
                                    checkCreate++;
                                    if (room.getRematchPlayer1() == 0 && room.isCheckRandom() && room.getPlayer2().isEmpty() && time == room.getTime() && sizeBoard == room.getSizeBoard() && userId != room.getPlayer1()) {
                                        //join player2
                                        checkCreate--;
                                        FirebaseAuth auth = FirebaseAuth.getInstance();
                                        FirebaseUser currentUser = auth.getCurrentUser();
                                        if (currentUser != null) {
                                            String userId = currentUser.getUid();
                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            DatabaseReference roomRef = database.getReference("room");
                                            roomRef.child(room.getRoomId()).child("player2").setValue(userId, new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                    if (error == null) {
                                                        bundle.putInt("sizeBoard", sizeBoard);
                                                        bundle.putInt("time", time);
                                                        bundle.putString("idRoom", room.getRoomId());
                                                        checkBack = true;
                                                        navController = Navigation.findNavController(v);
                                                        navController.navigate(R.id.action_pvpFragment_to_inGameOnlineFragment, bundle);
                                                        return;
                                                    } else {
                                                        Toast.makeText(requireContext(), "Error writing to the database", Toast.LENGTH_SHORT).show();
                                                    }
                                                    progressBar.setVisibility(View.GONE);
                                                }
                                            });
                                        } else {
                                            // xu ly Người dùng chưa đăng nhập
                                            progressBar.setVisibility(View.GONE);
                                            showNotLoggedInDialog(v);
                                        }
                                        break;
                                    }
                                }
                            }

                            if (listIdRoom.isEmpty() || checkCreate == listIdRoom.size()) {
                                //create player1
                                Random random = new Random();
                                int i = 0;
                                do {
                                    idRoom = "r" + random.nextInt(10) + random.nextInt(10)
                                            + random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10);
                                    for (Room room : listIdRoom) {
                                        if (room.getRoomId().equals(idRoom)) {
                                            i = 1;
                                            break;
                                        }
                                    }
                                } while (i != 0);

                                FirebaseAuth auth = FirebaseAuth.getInstance();
                                FirebaseUser currentUser = auth.getCurrentUser();

                                if (currentUser != null) {
                                    userId = currentUser.getUid();
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference roomRef = database.getReference("room");

                                    Room room = new Room(true, false, 0, 0, "", "", time, userId, sizeBoard, -1, userId, "", idRoom, "", false, 0);
                                    roomRef.child(idRoom).setValue(room, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                            if (error == null) {
                                                Bundle bundle = new Bundle();
                                                bundle.putInt("sizeBoard", sizeBoard);
                                                bundle.putInt("time", time);
                                                bundle.putString("idRoom", idRoom);
                                                navController = Navigation.findNavController(v);
                                                navController.navigate(R.id.action_pvpFragment_to_inGameOnlineFragment, bundle);
                                                progressBar.setVisibility(View.GONE);
                                                checkBack = true;
                                            } else {
                                                Toast.makeText(requireContext(), "Error writing to the database", Toast.LENGTH_SHORT).show();
                                            }
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    });
                                } else {
                                    // xu ly Người dùng chưa đăng nhập
                                    progressBar.setVisibility(View.GONE);
                                    showNotLoggedInDialog(v);
                                }
                            }
                        }
                        if (rdoCreateRoom.isChecked()) {
                            Random random = new Random();
                            //dam bao idroom la duy nhat
                            int i = 0;
                            do {
                                idRoom = "c" + random.nextInt(10) + random.nextInt(10)
                                        + random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10);
                                for (Room room : listIdRoom) {
                                    if (room.getRoomId().equals(idRoom)) {
                                        i = 1;
                                        break;
                                    }
                                }
                            } while (i != 0);

                            //nạp currentPlayer, player1
                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            FirebaseUser currentUser = auth.getCurrentUser();

                            if (currentUser != null) {
                                userId = currentUser.getUid();
                                //Room room = new Room(time, sizeBoard, idRoom, false,-1);
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference roomRef = database.getReference("room");

                                Room room = new Room(false, false, 0, 0, "", "", time, userId, sizeBoard, -1, userId, "", idRoom, "", false, 0);
                                roomRef.child(idRoom).setValue(room, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        if (error == null) {
                                            // Chuyển màn hình sau khi ghi dữ liệu thành công
                                            // Đảm bảo rằng màn hình chỉ được chuyển khi dữ liệu đã được ghi thành công
                                            Bundle bundle = new Bundle();
                                            bundle.putInt("sizeBoard", sizeBoard);
                                            bundle.putInt("time", time);
                                            bundle.putString("idRoom", idRoom);
                                            navController = Navigation.findNavController(v);
                                            navController.navigate(R.id.action_pvpFragment_to_inGameOnlineFragment, bundle);
                                            progressBar.setVisibility(View.GONE);
                                            checkBack = true;
                                        } else {
                                            // Xử lý lỗi nếu có
                                            Toast.makeText(requireContext(), "Error writing to the database", Toast.LENGTH_SHORT).show();
                                        }
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });
                            } else {
                                // xu ly Người dùng chưa đăng nhập
                                progressBar.setVisibility(View.GONE);
                                showNotLoggedInDialog(v);
                            }

                        } else if (rdoJoinRoom.isChecked()) {
                            idRoom = edtRoomID.getText().toString().trim();
                            if (!idRoom.isEmpty()) {
                                //nap player2
                                DatabaseReference roomRef2 = FirebaseDatabase.getInstance().getReference("room/" + idRoom + "/roomId");
                                roomRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        roomId = snapshot.getValue(String.class);

                                        if (idRoom.equals(roomId)) {
                                            DatabaseReference roomRef3 = FirebaseDatabase.getInstance().getReference("room/" + idRoom + "/sizeBoard");
                                            DatabaseReference roomRef4 = FirebaseDatabase.getInstance().getReference("room/" + idRoom + "/time");
                                            roomRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    sizeBoard = snapshot.getValue(int.class);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                }
                                            });
                                            roomRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    time = snapshot.getValue(int.class);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                }
                                            });
                                            FirebaseAuth auth = FirebaseAuth.getInstance();
                                            FirebaseUser currentUser = auth.getCurrentUser();

                                            if (currentUser != null) {
                                                String userId = currentUser.getUid();
                                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                DatabaseReference roomRef = database.getReference("room");
                                                roomRef.child(idRoom).child("player2").setValue(userId, new DatabaseReference.CompletionListener() {
                                                    @Override
                                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                        if (error == null) {
                                                            // Chuyển màn hình sau khi ghi dữ liệu thành công
                                                            // Đảm bảo rằng màn hình chỉ được chuyển khi dữ liệu đã được ghi thành công
                                                            bundle.putInt("sizeBoard", sizeBoard);
                                                            bundle.putInt("time", time);
                                                            bundle.putString("idRoom", idRoom);
                                                            navController = Navigation.findNavController(v);
                                                            navController.navigate(R.id.action_pvpFragment_to_inGameOnlineFragment, bundle);
                                                            checkBack = true;
                                                        } else {
                                                            Toast.makeText(requireContext(), "Error writing to the database", Toast.LENGTH_SHORT).show();
                                                        }
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                });
                                            } else {
                                                // xu ly Người dùng chưa đăng nhập
                                                progressBar.setVisibility(View.GONE);
                                                showNotLoggedInDialog(v);
                                            }
                                        } else {
                                            checkBack = true;
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(requireContext(), "RoomId not exists", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });
                            } else {
                                checkBack = true;
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(requireContext(), "RoomId empty", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });

        btnCallBack = view.findViewById(R.id.btnCallBack);
        btnCallBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBack) {
                    getActivity().getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
    }
}