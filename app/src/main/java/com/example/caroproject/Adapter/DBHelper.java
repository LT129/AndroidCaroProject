package com.example.caroproject.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.caroproject.Data.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DBHelper {
    private Context mcontext;


    public DBHelper(Context context) {
        this.mcontext = context;
    }


    public void getFriend(String userName) {
    }
}

