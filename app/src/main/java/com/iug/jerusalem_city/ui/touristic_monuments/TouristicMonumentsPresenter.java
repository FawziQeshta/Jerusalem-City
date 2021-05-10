package com.iug.jerusalem_city.ui.touristic_monuments;

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
import com.iug.jerusalem_city.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TouristicMonumentsPresenter {

    private Context context;
    private CityTouristicMonumentsListener mListener;

    private FirebaseFirestore db;

    private static final String TAG = "TouristicMonumentsPrese";

    public TouristicMonumentsPresenter(Context context, CityTouristicMonumentsListener mListener) {
        this.context = context;
        this.mListener = mListener;
        db = FirebaseFirestore.getInstance();
    }

    public void loadTouristicMonumentsTopics() {
        DocumentReference bulletinRef = db.collection(Constants.KEY_MAIN_COLLECTION).document(Constants.KEY_MAIN_COLLECTION_ID);

        bulletinRef.collection(Constants.KEY_TOURISTICAL_COLLECTION)
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
                            mListener.getTouristicMonumentsTopics(data);

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }


    public interface CityTouristicMonumentsListener {
        void getTouristicMonumentsTopics(List<TopicModel> topicData);
    }

}
