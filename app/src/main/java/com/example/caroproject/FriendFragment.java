package com.example.caroproject;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caroproject.Adapter.DBHelper;
import com.example.caroproject.Adapter.FriendListAdapter;
import com.example.caroproject.Data.UserInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FriendFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context context;

    private UserInfo[] items;
    private EditText edtSearchBar;
    private Button btnOK;
    private RecyclerView recyclerViewFriend;
    private ArrayList<UserInfo> userInfoArrayList;
    private ProgressBar progressBar;
    FriendListAdapter adapter;
    FirebaseFirestore db;
    Dialog dialog;

    public FriendFragment() {
        //Empty constructor
    }

    public static FriendFragment newInstance(String param1, String param2) {
        FriendFragment fragment = new FriendFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity(); // use this reference to invoke main callbacks
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout fragment_friendlist = (LinearLayout) inflater.inflate(R.layout.fragment_friendlist, null);
        edtSearchBar = (EditText) fragment_friendlist.findViewById(R.id.edtSearchBar);
        recyclerViewFriend = (RecyclerView) fragment_friendlist.findViewById(R.id.recyclerViewFriend);
        btnOK = (Button) fragment_friendlist.findViewById(R.id.btnOK);
        progressBar = (ProgressBar)fragment_friendlist.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerViewFriend.setHasFixedSize(true);
        recyclerViewFriend.setLayoutManager(new LinearLayoutManager(requireContext()));

        db = FirebaseFirestore.getInstance();
        userInfoArrayList = new ArrayList<UserInfo>();

        // Initialize the global adapter variable
        adapter = new FriendListAdapter(context, userInfoArrayList);
        recyclerViewFriend.setAdapter(adapter);

        EventChangeListener();

        return fragment_friendlist;
    }

    private void EventChangeListener() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("UserInfo").document(currentUserId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Error:", error.getMessage());
                            return;
                        }

                        if (value != null && value.exists()) {
                            UserInfo currentUser = value.toObject(UserInfo.class);

                            if (currentUser != null && currentUser.getFriends() != null) {
                                progressBar.setVisibility(View.GONE);
                                loadFriendsData(currentUser.getFriends());
                            } else {
                                // Display a message indicating no friends
                                showNoFriendsMessage();
                            }
                        }
                    }
                });
    }

    private void showNoFriendsMessage() {
        new AlertDialog.Builder(requireContext()).setMessage("You do not have any friend yet!")
                .setNegativeButton("OK", null)
                .show();
    }

    private void loadFriendsData(List<String> friendIds) {
        for (String friendId : friendIds) {
            db.collection("UserInfo").document(friendId)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot != null && documentSnapshot.exists()) {
                                UserInfo friend = documentSnapshot.toObject(UserInfo.class);

                                if (friend != null) {
                                    userInfoArrayList.add(friend);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
    }
}