package com.org.moodleapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM student")
    List<Student> getAll();

    @Query("SELECT * FROM student where studentId = :studentId ")
    Student findByStudentId(String studentId);

    @Insert
    void insertAll(Student... students);

    @Delete
    void delete(Student student);
}