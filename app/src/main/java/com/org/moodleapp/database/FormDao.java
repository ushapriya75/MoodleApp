package com.org.moodleapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FormDao {

        @Query("SELECT * FROM form")
        List<Form> getAll();

        @Insert
        void insertAll(Form... formData);

        @Delete
        void delete(Form form);
}


