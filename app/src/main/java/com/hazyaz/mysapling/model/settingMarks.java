package com.hazyaz.mysapling.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class settingMarks {


    private DatabaseReference mDatabase;


    public settingMarks(String n, String userid, final Context c, String userSaplingId) {


        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.child("users").child(userid).child("saplings").child(userSaplingId).child("marks").setValue(n).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(c, "done", Toast.LENGTH_LONG).show();
            }
        });


    }


}
