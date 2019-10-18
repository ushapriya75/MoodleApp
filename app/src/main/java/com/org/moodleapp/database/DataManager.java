package com.org.moodleapp.database;

public class DataManager {

    private static DataManager INSTANCE=null;

    private Student loggedInUser=null;

    // made contructor to make it singleton class
    private DataManager(){}


    public static DataManager getInstance(){
        if(INSTANCE==null){
            INSTANCE=new DataManager();
        }
        return INSTANCE;
    }

    public  void setUser(Student userID) {
        loggedInUser=userID;
    }

    public  Student getUser(){
       return loggedInUser;
    }

}
