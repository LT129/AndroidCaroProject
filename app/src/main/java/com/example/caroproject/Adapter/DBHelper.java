package com.example.caroproject.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.caroproject.Data.PlayerInfo;
import com.google.android.gms.common.internal.ConnectionTelemetryConfiguration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DBHelper {
    private FirebaseDatabase rootRef;
    public DBHelper(){
        this.rootRef = FirebaseDatabase.getInstance();
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

    public boolean AddPlayer(final PlayerInfo newPlayer) {
        createNewPlayer(new OnPlayerIdGeneratedListener() {
            @Override
            public void onPlayerIdGenerated(int playerId) {
                if (playerId != -1) {
                    String ID = String.valueOf(playerId);
                    DatabaseReference myRef = rootRef.getReference("PlayerInfo").child(ID);
                    myRef.child("UserName").setValue(newPlayer.getUserName());
                    myRef.child("PassWord").setValue(newPlayer.getPassword());
                    myRef.child("Status").setValue(false);
                    myRef.child("FriendList").setValue("");
                    myRef.child("MatchHistory").setValue("");
                    myRef.child("Avatar").setValue("");

                    // Do other actions if needed
                } else {
                    // Handle the case where generating player ID failed
                }
            }
        });

        return true;
    }


    public void checkUser(String userName, final CheckUserCallback callback) {
        DatabaseReference myRef = rootRef.getReference("PlayerInfo");

        // Check for the existence of the UserName
        myRef.orderByChild("UserName").equalTo(userName).addListenerForSingleValueEvent(new ValueEventListener() {
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


    public void checkCredentials(PlayerInfo player, final OnCredentialsCheckListener listener) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("PlayerInfo");

        // Check for the existence of the UserName
        myRef.orderByChild("UserName").equalTo(player.getUserName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User with the provided username exists
                    DataSnapshot userSnapshot = dataSnapshot.getChildren().iterator().next();

                    // Check if the password matches
                    String storedPassword = userSnapshot.child("PassWord").getValue(String.class);
                    if (storedPassword != null && storedPassword.equals(player.getPassword())) {
                        // Password matches
                        listener.onCredentialsCheckResult(true);
                    } else {
                        // Password does not match
                        listener.onCredentialsCheckResult(false);
                    }
                } else {
                    // User does not exist
                    listener.onCredentialsCheckResult(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error
                listener.onCredentialsCheckResult(false); // Assume failure in case of error
            }
        });
    }

    // Define an interface for the callback
    public interface OnCredentialsCheckListener {
        void onCredentialsCheckResult(boolean credentialsMatch);
    }


}

