package com.iug.jerusalem_city.data.room_database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.iug.jerusalem_city.data.models.TopicModel;

@Database(entities = {TopicModel.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    //create database instance
    private static RoomDB database;
    //Define database name
    private static final String DATABASE_NAME = "jerusalem_database";

    public synchronized static RoomDB getInstance(Context context) {
        //check condition
        if (database == null) {
            //when database = null init database
            database = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    //create dao
    public abstract DaoAccess daoAccess();


}
