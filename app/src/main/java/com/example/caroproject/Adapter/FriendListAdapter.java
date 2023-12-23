package com.example.caroproject.Adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caroproject.Data.UserInfo;
import com.example.caroproject.R;

import java.util.ArrayList;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder> {
    Context context;
    ArrayList<UserInfo> userInfoArrayList;
    View view;
    LinearLayout btnChat;
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public FriendListAdapter(Context context, ArrayList<UserInfo> userInfoArrayList) {
        this.context = context;
        this.userInfoArrayList = userInfoArrayList;
    }
    @NonNull
    @Override
    public FriendListAdapter.FriendListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_friendlist_view,parent,false);

        return new FriendListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendListAdapter.FriendListViewHolder holder, int position) {
        UserInfo user = userInfoArrayList.get(position);

        holder.name.setText(user.getUsername());
        System.out.println(user.isOnline());
        if(user.isOnline()){
            holder.status.setText("Online");
        }else {
            holder.status.setText("Offline");
        }
        Glide.with(view).load(user.getAvatar()).error(R.drawable.user_account).into(holder.avatar);
        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(user.getUsername());
                }
            }
        });

        btnChat = view.findViewById(R.id.btnChat);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("UserID",user.getID());
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_mainMenuFragment_to_chatFragment, args);
            }
        });

    }
    public interface OnItemClickListener {
        void onItemClick(String username);
    }
    @Override
    public int getItemCount() {
        return userInfoArrayList.size();
    }

    public static class FriendListViewHolder extends RecyclerView.ViewHolder{

        TextView name,status;
        ImageView avatar;
        public FriendListViewHolder(@NonNull View custom_friendlist_view) {
            super(custom_friendlist_view);
            name = custom_friendlist_view.findViewById(R.id.txtViewName);
            status = custom_friendlist_view.findViewById(R.id.txtViewStatus);
            avatar = custom_friendlist_view.findViewById(R.id.imgViewAvatar);
        }
    }
}
