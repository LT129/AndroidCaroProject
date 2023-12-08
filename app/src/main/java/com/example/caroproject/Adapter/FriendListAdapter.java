package com.example.caroproject.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caroproject.Data.UserInfo;
import com.example.caroproject.R;

import java.util.ArrayList;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder> {
    Context context;
    ArrayList<UserInfo> userInfoArrayList;

    public FriendListAdapter(Context context, ArrayList<UserInfo> userInfoArrayList) {
        this.context = context;
        this.userInfoArrayList = userInfoArrayList;
    }

    @NonNull
    @Override
    public FriendListAdapter.FriendListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_friendlist_view,parent,false);

        return new FriendListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendListAdapter.FriendListViewHolder holder, int position) {
        UserInfo user = userInfoArrayList.get(position);

        holder.name.setText(user.getUsername());
        if(user.isOnline()){
            holder.status.setText("Online");
        }else holder.status.setText("Offline");
        //holder.avatar.setImageResource(user.getAvatar());
    }

    @Override
    public int getItemCount() {
        return userInfoArrayList.size();
    }

    public static class FriendListViewHolder extends RecyclerView.ViewHolder{

        TextView name,status;
        //ImageView avatar;
        public FriendListViewHolder(@NonNull View custom_friendlist_view) {
            super(custom_friendlist_view);
            name = custom_friendlist_view.findViewById(R.id.txtViewName);
            status = custom_friendlist_view.findViewById(R.id.txtViewStatus);
            //avatar = custom_friendlist_view.findViewById(R.id.imgViewAvatar);
        }
    }
}
