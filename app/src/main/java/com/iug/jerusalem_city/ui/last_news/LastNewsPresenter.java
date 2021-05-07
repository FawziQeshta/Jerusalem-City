package com.iug.jerusalem_city.ui.last_news;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.iug.jerusalem_city.models.TopicModel;
import com.iug.jerusalem_city.ui.city_climate.CityClimatePresenter;
import com.iug.jerusalem_city.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LastNewsPresenter {

    private Context context;
    private LastNewsListener mListener;

    private FirebaseFirestore db;

    private static final String TAG = "LastNewsPresenter";

    public LastNewsPresenter(Context context, LastNewsListener mListener) {
        this.context = context;
        this.mListener = mListener;
        db = FirebaseFirestore.getInstance();
    }

    public void loadLastNews() {
        DocumentReference bulletinRef = db.collection(Constants.KEY_MAIN_COLLECTION).document(Constants.KEY_MAIN_COLLECTION_ID);

        bulletinRef.collection(Constants.KEY_LAST_NEWS_COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<TopicModel> data = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                TopicModel topicModel = document.toObject(TopicModel.class);
                                data.add(topicModel);
                            }

                            Collections.shuffle(data);
                            mListener.getLastNewsTopics(data);

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }


    public interface LastNewsListener {
        void getLastNewsTopics(List<TopicModel> topicData);
    }


}
