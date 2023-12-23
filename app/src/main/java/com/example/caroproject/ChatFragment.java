package com.example.caroproject;

import static java.lang.Thread.sleep;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.caroproject.Adapter.ChatAdapter;
import com.example.caroproject.Adapter.FirebaseHelper;
import com.example.caroproject.Data.ChatRoom;
import com.example.caroproject.Data.Message;
import com.example.caroproject.Data.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context context;
    private CircleImageView imgViewAvatar;
    private ImageButton btnCallBack;
    private TextView txtViewName,txtViewStatus;
    private RecyclerView chatView;
    private String otherUserID;
    private String myID;
    private EditText edtMessage;
    private Button btnSend;
    private ArrayList<Message> msgArrayList;
    private String chatroomID;
    private ChatRoom chatRoom;
    ChatAdapter adapter;
    FirebaseFirestore db;
    FirebaseHelper firebaseHelper;
    private View view;

    public ChatFragment() {
        //Empty constructor
    }

    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, null);
        imgViewAvatar =  view.findViewById(R.id.imgViewAvatar);
        txtViewName = view.findViewById(R.id.txtViewName);
        txtViewStatus =  view.findViewById(R.id.txtViewStatus);
        chatView = view.findViewById(R.id.chatView);
        edtMessage =  view.findViewById(R.id.edtMessage);
        btnSend =  view.findViewById(R.id.btnSend);

        otherUserID = getArguments().getString("UserID");
        myID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db = FirebaseFirestore.getInstance();
        firebaseHelper = FirebaseHelper.getInstance();

        chatroomID = firebaseHelper.getChatRoomID(myID,otherUserID);

        msgArrayList = new ArrayList<Message>();

        adapter = new ChatAdapter(context, msgArrayList);
        chatView.setAdapter(adapter);
        chatView.setLayoutManager(new LinearLayoutManager(context));
        getOrCreateChatRoom();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = edtMessage.getText().toString().trim();
                if(msg.isEmpty())
                    return;
                sendMessageToUsers(msg);
            }
        });

        btnCallBack = view.findViewById(R.id.btnCallBack);
        btnCallBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
        EventChangeListener();

        return view;
    }

    private void sendMessageToUsers(String message) {
        chatRoom.setLastMsgTimeStamp(Timestamp.now());
        chatRoom.setLastMsgID(myID);
        firebaseHelper.getChatRoomRef(chatroomID).set(chatRoom);

        Message msg = new Message(message,myID,Timestamp.now());
        firebaseHelper.getMsgRef(chatroomID).add(msg)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){
                            edtMessage.setText("");
                        }
                    }
                });
    }

    void getOrCreateChatRoom(){
            firebaseHelper.getChatRoomRef(chatroomID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        chatRoom = task.getResult().toObject(ChatRoom.class);
                        if(chatRoom==null){
                            chatRoom = new ChatRoom(
                                    chatroomID,
                                    Arrays.asList(otherUserID,myID),
                                    Timestamp.now(),
                                    ""
                            );
                        }
                        firebaseHelper.getChatRoomRef(chatroomID).set(chatRoom);
                    }
                }
            });
        }
    private void EventChangeListener(){
        LoadUserData();
        db.collection("ChatRoom").document(chatroomID).collection("chats")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null) {
                                for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                    // Get the document ID
                                    String documentId = document.getId();
                                    LoadMsgData(documentId);
                                }
                            }
                        } else {
                            Log.e("Firestore", "Error getting documents.", task.getException());
                        }
                    }
                });

    }


    private void LoadMsgData(String msgID){
        db.collection("ChatRoom").document(chatroomID).collection("chats").document(msgID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            Message msg = documentSnapshot.toObject(Message.class);
                            if (msg != null) {
                                msgArrayList.add(msg);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    private void LoadUserData() {
        db.collection("UserInfo").document(otherUserID)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Error:", error.getMessage());
                            return;
                        }

                        if (value != null && value.exists()) {
                            UserInfo currentUser = value.toObject(UserInfo.class);

                            if (currentUser != null) {
                                Glide.with(view).load(currentUser.getAvatar()).error(R.drawable.user_account).into(imgViewAvatar);
                                txtViewName.setText(currentUser.getUsername());
                                if(currentUser.isOnline()){
                                    txtViewStatus.setText("Online");
                                }else txtViewStatus.setText("Offline");

                            } else {
                                return;
                            }
                        }
                    }
                });
    }
}
