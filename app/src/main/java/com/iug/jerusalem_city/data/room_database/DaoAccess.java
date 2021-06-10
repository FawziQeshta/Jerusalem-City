package com.iug.jerusalem_city.data.room_database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iug.jerusalem_city.data.models.TopicModel;

import java.util.List;

@Dao
public interface DaoAccess {

    //insert query
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorites(TopicModel favorite);

    //delete all query
    @Query("DELETE FROM TopicModel")
    void deleteFavorites();

    //delete specific query
    @Query("DELETE FROM TopicModel where id = :Id")
    void deleteSpecificFavorites(String Id);

    @Query("SELECT EXISTS (SELECT id FROM TopicModel WHERE id = :Id)")
    boolean isExists(String Id);

    //get all data query
    @Query("SELECT * FROM TopicModel")
    List<TopicModel> getAllFavorites();

}
