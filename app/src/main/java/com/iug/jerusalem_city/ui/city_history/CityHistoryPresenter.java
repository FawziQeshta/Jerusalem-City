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
import com.iug.jerusalem_city.models.InformationData;
import com.iug.jerusalem_city.models.TopicData;
import com.iug.jerusalem_city.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class CityHistoryPresenter {

    private Context context;
    private CityHistoryListener mListener;

    private FirebaseFirestore db;

    private static final String TAG = "CityHistoryPresenter";

    public CityHistoryPresenter(Context context, CityHistoryListener mListener) {
        this.context = context;
        this.mListener = mListener;
        db = FirebaseFirestore.getInstance();
    }

    public void loadHistoryTopics() {
        DocumentReference bulletinRef = db.collection(Constants.KEY_MAIN_COLLECTION).document(Constants.KEY_MAIN_COLLECTION_ID);

        bulletinRef.collection(Constants.KEY_HISTORY_COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<TopicData> data = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                TopicData topicData = document.toObject(TopicData.class);
                                data.add(topicData);
                            }

                            mListener.getHistoryTopics(data);

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public interface CityHistoryListener {
        void getHistoryTopics(List<TopicData> topicData);
    }

}
