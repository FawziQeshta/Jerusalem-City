package com.iug.jerusalem_city.ui.city_climate;

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

public class CityClimatePresenter {

    private Context context;
    private CityClimateListener mListener;

    private FirebaseFirestore db;
    private RoomDB roomDB;

    private static final String TAG = "CityClimatePresenter";

    public CityClimatePresenter(Context context, CityClimateListener mListener) {
        this.context = context;
        this.mListener = mListener;
        db = FirebaseFirestore.getInstance();
        roomDB = RoomDB.getInstance(context);
    }

    public void loadClimateTopics() {
        DocumentReference bulletinRef = db.collection(Constants.KEY_MAIN_COLLECTION).document(Constants.KEY_MAIN_COLLECTION_ID);

        bulletinRef.collection(Constants.KEY_CLIMATE_COLLECTION)
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
                            mListener.getClimateTopics(data);

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public interface CityClimateListener {
        void getClimateTopics(List<TopicModel> topicData);
    }

}
