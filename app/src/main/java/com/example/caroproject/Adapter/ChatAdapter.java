package com.example.caroproject.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.caroproject.Data.Message;
import com.example.caroproject.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    Context context;
    ArrayList<Message> MessageArrayList;
    View view;

    public ChatAdapter(Context context, ArrayList<Message> messageArrayList) {
        this.context = context;
        this.MessageArrayList = messageArrayList;
    }
    @NonNull
    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_chat_view,parent,false);
        return new ChatAdapter.ChatViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatViewHolder holder, int position) {
        Message msg = MessageArrayList.get(position);
        Date date = msg.getAddtime().toDate();

        // Format the Date to display only the time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String formattedTime = sdf.format(date);

        if (msg.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            holder.recievedMessage.setVisibility(View.GONE);
            holder.sentMessage.setVisibility(View.VISIBLE);
            holder.txtSentMessage.setText(msg.getContent());
            holder.txtSentDateTime.setText(formattedTime);
        }else {
            holder.sentMessage.setVisibility(View.GONE);
            holder.recievedMessage.setVisibility(View.VISIBLE);
            holder.txtRecieveMessage.setText(msg.getContent());
            holder.txtRecieveDateTime.setText(formattedTime);
        }
    }

    @Override
    public int getItemCount() {
        return MessageArrayList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout sentMessage,recievedMessage;
        TextView txtSentMessage,txtSentDateTime,txtRecieveMessage,txtRecieveDateTime;
        ImageView imgProfile;

        public ChatViewHolder(@NonNull View custom_chat_view) {
            super(custom_chat_view);
            sentMessage = custom_chat_view.findViewById(R.id.layout_sent_message);
            recievedMessage = custom_chat_view.findViewById(R.id.layout_recieved_message);
            txtSentMessage = custom_chat_view.findViewById(R.id.txtSentMessage);
            txtSentDateTime = custom_chat_view.findViewById(R.id.txtSentDateTime);
            txtRecieveMessage = custom_chat_view.findViewById(R.id.txtRecieveMessage);
            txtRecieveDateTime = custom_chat_view.findViewById(R.id.txtRecieveDateTime);
            imgProfile = custom_chat_view.findViewById(R.id.imgProfile);
        }
    }
}
