package com.example.navernews.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface NewsDAO {

    @Insert
    void insert(NewsDTO newsDTO);

    @Query("SELECT * FROM News")
    List<NewsDTO> selectAll();

}
