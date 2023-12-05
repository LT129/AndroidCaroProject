package com.example.caroproject.Adapter;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirestoreDatabaseHelper {
    private FirebaseFirestore db;


    /**
     * @param collection table name
     * @param data data object that need to up to database
     */
    public void addDataToDatabase(String collection, Object data) {
        db.collection(collection).add(data);
    }

    /**
     * @param collection table name
     * @param id element id
     * @param data data object that need to up to database
     */
    public void addDataToDatabase(String collection, String id, Object data) {
        db.collection(collection).add(data);
    }

    /**
     * @param collection table name
     * @param id element id
     * @return object that is retrieve
     */
    public Object retrieveDataFromDatabase(String collection, String id) {
        return db.collection(collection).document(id).get().getResult().toObject(Object.class);
    }

    /**
     * @param collection table name
     * @return list of object that is retrieve
     */
    public List<Object> retrieveDataFromDatabase(String collection) {
        QuerySnapshot queryDocumentSnapshots = db.collection(collection).get().getResult();
        List<Object> result = new ArrayList<>();

        for (DocumentSnapshot documentSnapshot :
                queryDocumentSnapshots) {
            result.add(documentSnapshot.toObject(Object.class));
        }

        return result;
    }









    private static FirestoreDatabaseHelper instance;
    private FirestoreDatabaseHelper() {
        db = FirebaseFirestore.getInstance();
    }
    public static FirestoreDatabaseHelper getInstance() {
        if(instance != null) {
            return  instance;
        } else {
            return new FirestoreDatabaseHelper();
        }
    }
}
