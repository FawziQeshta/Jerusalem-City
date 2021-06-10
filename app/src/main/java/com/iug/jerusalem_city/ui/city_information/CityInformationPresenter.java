package com.iug.jerusalem_city.ui.city_information;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.iug.jerusalem_city.data.models.InformationModel;
import com.iug.jerusalem_city.utils.Constants;

public class CityInformationPresenter {

    private Context context;
    private CityInformationListener mListener;
    private FirebaseFirestore db;

    private static final String TAG = "CityInformationPresente";

    public CityInformationPresenter(Context context, CityInformationListener mListener) {
        this.context = context;
        this.mListener = mListener;
        db = FirebaseFirestore.getInstance();
    }

    public void loadCityInfo() {
        DocumentReference bulletinRef = db.collection(Constants.KEY_MAIN_COLLECTION).document(Constants.KEY_MAIN_COLLECTION_ID);

        bulletinRef.collection(Constants.KEY_INFO_COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                InformationModel informationModel = document.toObject(InformationModel.class);
                                mListener.getCityInfo(informationModel);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public interface CityInformationListener {
        void getCityInfo(InformationModel data);
    }

}
