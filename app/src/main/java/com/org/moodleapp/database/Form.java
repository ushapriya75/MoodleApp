package com.org.moodleapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "form")
public class Form {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String studentName;

    @ColumnInfo(name = "student_id")
    private String studentId;

    @ColumnInfo(name = "course")
    private String course;

    @ColumnInfo(name = "year")
    private String year;

    @ColumnInfo(name = "request_type")
    private String request;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ColumnInfo(name = "email")
    private String email;

    @Ignore
    public Form() {
    }

    public Form(String studentName, String studentId, String course, String year, String request,String email) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.course = course;
        this.year = year;
        this.request = request;
        this.email=email;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
