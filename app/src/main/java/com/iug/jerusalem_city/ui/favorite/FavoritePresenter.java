package com.iug.jerusalem_city.ui.favorite;

import android.content.Context;
import android.util.Log;

import com.iug.jerusalem_city.data.models.TopicModel;
import com.iug.jerusalem_city.data.room_database.RoomDB;

import java.util.List;

public class FavoritePresenter {

    private Context context;
    private FavoriteListener mListener;
    private RoomDB db;

    public FavoritePresenter(Context context, FavoriteListener mListener) {
        this.context = context;
        this.mListener = mListener;
        db = RoomDB.getInstance(context);
    }

    public void loadFavorites() {
        if (db.daoAccess().getAllFavorites().isEmpty()) {
            mListener.getAllFavorites(null);
        } else {
            mListener.getAllFavorites(db.daoAccess().getAllFavorites());
        }
    }

    public interface FavoriteListener {
        void getAllFavorites(List<TopicModel> favorites);
    }

}
