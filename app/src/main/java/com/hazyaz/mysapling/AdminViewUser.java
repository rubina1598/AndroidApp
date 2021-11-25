package com.hazyaz.mysapling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hazyaz.mysapling.model.CustomAdapter;

import java.util.ArrayList;
import java.util.Iterator;

public class AdminViewUser extends AppCompatActivity {

    ArrayList<ArrayList<String>> strr;
    CustomAdapter adapter;
    private String id;
    private DatabaseReference mDatabase;
    private RecyclerView mRecyclerView;
    private String TAG = "cfgdgdfgfg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_user);


        final Intent intent = getIntent();
        id = intent.getStringExtra("userKey");


        mDatabase = FirebaseDatabase.getInstance().getReference();


        mRecyclerView = findViewById(R.id.adminUserView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        mDatabase.child("users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> mains;

                String name = dataSnapshot.child("Address").getValue().toString();
                String marks = "NA";
                if (dataSnapshot.child("Marks").exists()) {
                    marks = dataSnapshot.child("Marks").getValue().toString();
                }

                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.child("saplings").getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                strr = new ArrayList<>();

                while (iterator.hasNext()) {
                    DataSnapshot next = iterator.next();

                    Log.i(TAG, "rrrrrrrr" + iterator.next().getKey());

                    String time = next.child("time").getValue().toString();
                    String imageurl = next.child("imageurl").getValue().toString();


                    mains = new ArrayList<>();
                    mains.add(imageurl);
                    mains.add(time);
                    mains.add(name);
                    mains.add(marks);
                    mains.add("FUCKOFF");
                    mains.add(id);
                    mains.add("" + iterator.next().getKey());

                    strr.add(mains);

                }
                Log.d("qnnnnnnno", "" + strr);

                adapter = new CustomAdapter(strr, getApplicationContext());
                mRecyclerView.setAdapter(adapter);
                //  Toast.makeText(getApplicationContext(), ""+strr, Toast.LENGTH_LONG).show();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "coudn't connect to database", Toast.LENGTH_LONG).show();
            }
        });


    }
}
