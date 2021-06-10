package com.iug.jerusalem_city.ui.last_news;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.iug.jerusalem_city.data.api.ApiClient;
import com.iug.jerusalem_city.data.models.Article;
import com.iug.jerusalem_city.data.models.LastNewsModel;
import com.iug.jerusalem_city.data.models.TopicModel;
import com.iug.jerusalem_city.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LastNewsPresenter {

    private Context context;
    private LastNewsListener mListener;

//    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "LastNewsPresenter";

    public LastNewsPresenter(Context context, LastNewsListener mListener) {
        this.context = context;
        this.mListener = mListener;
    }

    public void loadLastNews() {
        String currentDate = getCurrentDate();
        String yesterday = getYesterdayDate();

        Call<LastNewsModel> call = ApiClient.getInstance().getLastNews("القدس", yesterday, currentDate, "popularity", "ar", Constants.API_KEY);

        call.enqueue(new Callback<LastNewsModel>() {
            @Override
            public void onResponse(@NonNull Call<LastNewsModel> call, @NonNull Response<LastNewsModel> response) {
                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        mListener.getLastNewsTopics(response.body().getArticles());

                    }

                } else {
                    Log.e("SalonFragment", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LastNewsModel> call, @NonNull Throwable t) {
                if (t.getMessage() != null) {
                    if (t.getMessage().contains("Unable to resolve host") && context != null) {
                        Toast.makeText(context, "الرجاء فحص الاتصال بالانترنت", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(c);
    }

    private String getYesterdayDate() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date c = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(c);
    }

    /*public void loadLastFirebase() {
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
    }*/

    public interface LastNewsListener {
        void getLastNewsTopics(List<Article> topicData);
    }


}
