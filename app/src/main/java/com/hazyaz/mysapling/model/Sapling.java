package com.hazyaz.mysapling.model;

/**
 * Created by Raff on 1/7/2020.
 */

public class Sapling {


    private String mTime;
    private String mImage;



  public Sapling(String img ,String tim){

      this.mTime = tim;
      this.mImage = img;
    }




    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
}
