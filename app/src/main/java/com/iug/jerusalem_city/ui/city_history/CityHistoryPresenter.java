package com.iug.jerusalem_city.ui.city_history;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.iug.jerusalem_city.data.models.TopicModel;
import com.iug.jerusalem_city.data.room_database.RoomDB;
import com.iug.jerusalem_city.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CityHistoryPresenter {

    private Context context;
    private CityHistoryListener mListener;

    private FirebaseFirestore db;
    private RoomDB roomDB;

    private static final String TAG = "CityHistoryPresenter";

    public CityHistoryPresenter(Context context, CityHistoryListener mListener) {
        this.context = context;
        this.mListener = mListener;
        db = FirebaseFirestore.getInstance();
        roomDB = RoomDB.getInstance(context);
    }

    public void loadHistoryTopics() {
        DocumentReference bulletinRef = db.collection(Constants.KEY_MAIN_COLLECTION).document(Constants.KEY_MAIN_COLLECTION_ID);

        bulletinRef.collection(Constants.KEY_HISTORY_COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<TopicModel> data = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                TopicModel topicModel = document.toObject(TopicModel.class);
                                topicModel.setId(document.getId());
                                topicModel.setSaved(roomDB.daoAccess().isExists(topicModel.getId()));
                                data.add(topicModel);
                            }

                            Collections.shuffle(data);
                            mListener.getHistoryTopics(data);

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public interface CityHistoryListener {
        void getHistoryTopics(List<TopicModel> topicData);
    }

}
