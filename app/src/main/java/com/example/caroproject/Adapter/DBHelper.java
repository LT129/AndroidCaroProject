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
    private FirebaseDatabase rootRef;
    private Context mcontext;
    public DBHelper(){
        this.rootRef = FirebaseDatabase.getInstance();
    }
    public DBHelper(Context context){
        this.mcontext = context;
    }
    public void createNewPlayer(final OnPlayerIdGeneratedListener listener) {
        final DatabaseReference countPlayerRef = rootRef.getReference("PlayerInfo").child("CountPlayer");

        countPlayerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    long countPlayer = (long) snapshot.getValue();
                    countPlayerRef.setValue(countPlayer + 1);

                    // Notify the listener with the new player ID
                    listener.onPlayerIdGenerated((int) countPlayer);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
                listener.onPlayerIdGenerated(-1); // Indicate failure
            }
        });
    }

    // Define an interface for the callback
    public interface OnPlayerIdGeneratedListener {
        void onPlayerIdGenerated(int playerId);
    }

    public interface OnRegisterListener{
        void onResult(boolean checker);
    }
    public void registerUser(UserInfo player, final OnRegisterListener listener){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(player.getUsername(), player.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            listener.onResult(true);

                        } else {
                            // If sign in fails, display a message to the user.
                            listener.onResult(false);
                        }
                    }
                });
    }
    public void getFriend(String userName, final  getFriendCallback callback){
        DatabaseReference myRef = rootRef.getReference("PlayerInfo");

        myRef.orderByChild("username").equalTo(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataSnapshot userSnapshot = dataSnapshot.getChildren().iterator().next();
                    UserInfo player = userSnapshot.getValue(UserInfo.class);
                    callback.onResult(player);
                } else {
                    callback.onResult(null); // User not found
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onResult(null); // Error occurred
            }
        });
    }
    public interface getFriendCallback {
        void onResult(UserInfo items);
    }

    public void checkUser(String userName, final CheckUserCallback callback) {
        DatabaseReference myRef = rootRef.getReference("PlayerInfo");

        // Check for the existence of the UserName
        myRef.orderByChild("username").equalTo(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean userExists = dataSnapshot.exists();
                callback.onResult(userExists);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error
                callback.onResult(false);
            }
        });
    }

    public interface CheckUserCallback {
        void onResult(boolean userExists);
    }
}

