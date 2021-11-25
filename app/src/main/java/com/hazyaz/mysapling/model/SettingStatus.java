package com.hazyaz.mysapling.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Raff on 1/27/2020.
 */

public class SettingStatus {

    private DatabaseReference mDatbase;

    SettingStatus(String id) {
        mDatbase = FirebaseDatabase.getInstance().getReference();


    }


}
