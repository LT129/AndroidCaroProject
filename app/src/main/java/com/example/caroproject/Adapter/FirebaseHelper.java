package com.example.caroproject.Adapter;

import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHelper {
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;


    /**
     * @param collection table name
     * @param data data object that need to up to database
     */
    public void addDataToDatabase(String collection, Object data) {
        firestore.collection(collection).add(data);
    }

    /**
     * @param collection table name
     * @param id element id
     * @param data data object that need to up to database
     */
    public void addDataToDatabase(String collection, String id, Object data) {
        firestore.collection(collection).document(id).set(data);
    }

    /**
     * @param collection table name
     * @param id element id
     * @param type data type
     * @param listener the interface that return the object
     */
    public<T> void retrieveDataFromDatabase(String collection, String id, Class<T> type, OnCompleteRetrieveDataListener listener) {
        firestore.collection(collection).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        List<T> result = new ArrayList<>();
                        result.add(documentSnapshot.toObject(type));
                        listener.onComplete(result);
                    } else {
                        listener.onComplete(null);
                    }
                }
            }
        });
    }

    /**
     * @param collection table name
     * @param listener the interface that return the object
     */
    public<T> void retrieveDataFromDatabase(String collection, Class<T> type, OnCompleteRetrieveDataListener listener) {
        firestore.collection(collection).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();

                    if(!querySnapshot.isEmpty()) {
                        List<T> result = new ArrayList<>();
                        for (DocumentSnapshot documentSnapshot :
                                querySnapshot) {
                            result.add(documentSnapshot.toObject(type));
                        }
                        listener.onComplete(result);

                    }else {
                        listener.onComplete(null);
                    }
                }

            }
        });
    }

    public void logOut() {
        auth.signOut();
    }



    public void changePassword(String password) {
        FirebaseUser user = auth.getCurrentUser();
        user.updatePassword(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //DO NOTHING
                    }
                });
    }

    public void reAuthenticate(String password, OnResultListener listener) {
        FirebaseUser user = auth.getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), password);
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            listener.onResult(true);
                        } else {
                            listener.onResult(false);
                        }
                    }
                });
    }

    public interface OnCompleteRetrieveDataListener {
        <T>void onComplete(List<T> list);
    }


    public interface OnResultListener{
        void onResult(boolean result);
    }







    private static FirebaseHelper instance;
    private FirebaseHelper() {
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }
    public static FirebaseHelper getInstance() {
        if(instance != null) {
            return  instance;
        } else {
            return new FirebaseHelper();
        }
    }
}
