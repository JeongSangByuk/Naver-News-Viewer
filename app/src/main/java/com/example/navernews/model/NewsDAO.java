package com.example.navernews.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface NewsDAO {

    @Insert
    void insert(NewsDTO newsDTO);

    @Delete
    void delete(NewsDTO newsDTO);


    @Query("SELECT * FROM News WHERE category = :category")
    List<NewsDTO> selectAll(String category);
}
