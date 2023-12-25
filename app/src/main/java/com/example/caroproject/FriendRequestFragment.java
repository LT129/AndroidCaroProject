package com.example.caroproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.caroproject.Adapter.FirebaseHelper;
import com.example.caroproject.Adapter.FriendRequestAdapter;
import com.example.caroproject.Data.UserInfo;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class FriendRequestFragment extends Fragment {

    private ImageButton btnCallBack;
    private RecyclerView recyclerView;
    private FriendRequestAdapter adapter;



    public FriendRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_request, container, false);
        btnCallBack = view.findViewById(R.id.btnCallBack);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));


        FirebaseHelper.getInstance().retrieveDataFromDatabase("UserInfo",
                FirebaseAuth.getInstance().getUid(), UserInfo.class, new FirebaseHelper.OnCompleteRetrieveDataListener() {
                    @Override
                    public <T> void onComplete(List<T> list) {
                        UserInfo userInfo = (UserInfo) list.get(0);
                        adapter = new FriendRequestAdapter(requireContext(), userInfo.getFriendRequest());
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(new FriendRequestAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(String username) {
                                FirebaseHelper.getInstance().getUserIdByUsername(username, new FirebaseHelper.OnGetUserIdListener() {
                                    @Override
                                    public void onSuccess(String userId) {
                                        Bundle args = new Bundle();
                                        args.putString(ShowInfoFragment.USER_ID, userId);
                                        View view = getView();
                                        if(view!=null) {
                                            NavController navController = Navigation.findNavController(view);
                                            navController.navigate(R.id.action_friendRequestFragment_to_showInfoFragment, args);
                                        }
                                    }
                                    @Override
                                    public void onFailure(Exception e) {
                                        Log.e("Error",e.getMessage());
                                    }
                                });

                            }
                        });
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