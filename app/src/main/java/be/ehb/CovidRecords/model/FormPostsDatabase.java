package be.ehb.CovidRecords.model;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import be.ehb.CovidRecords.model.util.DateConverter;

@Database(version = 1, entities = {FormPost.class}, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class FormPostsDatabase extends RoomDatabase {
    private static FormPostsDatabase instance;

    public static FormPostsDatabase getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context,
                    FormPostsDatabase.class,
                    "formpostsdatabase.db").build();
        }
        return instance;
    }

    //acties op database
    public abstract FormDAO getFormPostsDAO();

    //werk op de achtergrond
    static final ExecutorService dbExecutor = Executors.newFixedThreadPool(2);
}
