package com.iug.jerusalem_city.ui.add_topic;

import android.content.Context;

import com.iug.jerusalem_city.utils.Constants;

public class AddTopicPresenter {

    private Context context;

    public AddTopicPresenter(Context context) {
        this.context = context;
    }

    public void sendUserTopic(String textTopic, String choiceText) {

    }

    private String getCollectionUserChoosed(String text) {
        switch (text){
            case "تاريخ المدينة":
                return Constants.KEY_HISTORY_COLLECTION;

            case "مناخ المدينة":
                return Constants.KEY_CLIMATE_COLLECTION;

            case "أهم المعالم السياحية":
                return Constants.KEY_TOURISTICAL_COLLECTION;

            default:
                return null;
        }
    }

}
