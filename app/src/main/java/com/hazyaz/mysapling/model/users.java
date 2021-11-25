package com.hazyaz.mysapling.model;

/**
 * Created by Raff on 1/10/2020.
 */

public class users {


    private String mUserName;
    private String mUserMarks;


    public users(String name, String marks) {
        this.mUserName = name;
        this.mUserMarks = marks;
    }


    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmUserMarks() {
        return mUserMarks;
    }

    public void setmUserMarks(String mUserMarks) {
        this.mUserMarks = mUserMarks;
    }
}
