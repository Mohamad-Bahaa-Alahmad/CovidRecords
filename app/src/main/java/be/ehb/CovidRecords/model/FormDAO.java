package be.ehb.CovidRecords.model;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FormDAO {
    @Query("SELECT * FROM FormPost")
    LiveData<List<FormPost>> getAllPosts();

    @Insert
    void insertFormPost(FormPost n);
}
