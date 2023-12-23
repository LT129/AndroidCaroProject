package com.example.caroproject.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.caroproject.Data.UserInfo;
import com.example.caroproject.R;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.FriendRequestViewHolder> {
    Context context;
    List<String> requestUserId;
    View view;
    LinearLayout btnChat;
    private FriendRequestAdapter.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(FriendRequestAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public FriendRequestAdapter(Context context, List<String> requestUserId) {
        this.context = context;
        this.requestUserId = requestUserId;
    }
    @NonNull
    @Override
    public FriendRequestAdapter.FriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_friendrequest_view,parent,false);

        return new FriendRequestAdapter.FriendRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRequestAdapter.FriendRequestViewHolder holder, int position) {
        FirebaseHelper.getInstance().retrieveDataFromDatabase("UserInfo", requestUserId.get(position), UserInfo.class,
                new FirebaseHelper.OnCompleteRetrieveDataListener() {
                    @Override
                    public <T> void onComplete(List<T> list) {
                        UserInfo requestUserInfo = (UserInfo) list.get(0);
                        holder.name.setText(requestUserInfo.getUsername());
                        Glide.with(view).load(requestUserInfo.getAvatar()).error(R.drawable.user_account).into(holder.avatar);
                        holder.avatar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (onItemClickListener != null) {
                                    onItemClickListener.onItemClick(requestUserInfo.getUsername());
                                }
                            }
                        });

                        String currentUserId = FirebaseAuth.getInstance().getUid();

                        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FirebaseHelper.getInstance().retrieveDataFromDatabase("UserInfo", currentUserId,
                                        UserInfo.class, new FirebaseHelper.OnCompleteRetrieveDataListener() {
                                            @Override
                                            public <T> void onComplete(List<T> list) {
                                                UserInfo currentUserInfo = (UserInfo) list.get(0);
                                                List<String> newFriendList = currentUserInfo.getFriends();
                                                if(newFriendList == null) {
                                                    newFriendList = new ArrayList<>();
                                                }
                                                newFriendList.add(requestUserId.get(holder.getAdapterPosition()));
                                                currentUserInfo.setFriendRequest(newFriendList);
                                                requestUserId.remove(holder.getAdapterPosition());
                                                notifyItemRemoved(holder.getAdapterPosition());
                                                notifyItemRangeChanged(holder.getAdapterPosition(),
                                                        requestUserId.size() - holder.getAdapterPosition());
                                                currentUserInfo.setFriendRequest(requestUserId);
                                                FirebaseHelper.getInstance().addDataToDatabase("UserInfo", currentUserId, currentUserInfo);
                                            }
                                        });
                                List<String> newFriendList = requestUserInfo.getFriends();
                                if(newFriendList == null) {
                                    newFriendList = new ArrayList<>();
                                }
                                newFriendList.add(currentUserId);
                                requestUserInfo.setFriends(newFriendList);
                                FirebaseHelper.getInstance().addDataToDatabase("UserInfo", requestUserInfo.getID(), requestUserInfo);
                            }
                        });

                        holder.btnDecline.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FirebaseHelper.getInstance().retrieveDataFromDatabase("UserInfo", currentUserId,
                                        UserInfo.class, new FirebaseHelper.OnCompleteRetrieveDataListener() {
                                            @Override
                                            public <T> void onComplete(List<T> list) {
                                                UserInfo currentUserInfo = (UserInfo) list.get(0);
                                                requestUserId.remove(holder.getAdapterPosition());
                                                notifyItemRemoved(holder.getAdapterPosition());
                                                notifyItemRangeChanged(holder.getAdapterPosition(),
                                                        requestUserId.size() - holder.getAdapterPosition());
                                                currentUserInfo.setFriendRequest(requestUserId);
                                                FirebaseHelper.getInstance().addDataToDatabase("UserInfo", currentUserId, currentUserInfo);
                                            }
                                        });
                            }
                        });

                    }
                });
    }
    public interface OnItemClickListener {
        void onItemClick(String username);
    }
    @Override
    public int getItemCount() {
        return requestUserId.size();
    }

    public static class FriendRequestViewHolder extends RecyclerView.ViewHolder{

        Button btnAccept;
        Button btnDecline;
        TextView name;
        ImageView avatar;

        public FriendRequestViewHolder(@NonNull View custom_friendrequest_view) {
            super(custom_friendrequest_view);
            name = custom_friendrequest_view.findViewById(R.id.txtViewName);
            avatar = custom_friendrequest_view.findViewById(R.id.imgViewAvatar);
            btnAccept = custom_friendrequest_view.findViewById(R.id.btnAccept);
            btnDecline = custom_friendrequest_view.findViewById(R.id.btnDecline);
        }
    }
}