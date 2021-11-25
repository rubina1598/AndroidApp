
package com.hazyaz.mysapling;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hazyaz.mysapling.model.AdminAdapter;

import java.util.ArrayList;



public class AdminView extends AppCompatActivity {

    AdminAdapter adminAdapter;
    ArrayList<ArrayList<String>> userData = new ArrayList<ArrayList<String>>();
    private DatabaseReference mAllUserDatabase;
    private FirebaseAuth mAuth;
    private RecyclerView tRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);


        getSupportActionBar().setTitle("My saplings Admin");

        tRecyclerView = findViewById(R.id.adminRecyclerView);

        tRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mAllUserDatabase = FirebaseDatabase.getInstance().getReference();

        mAllUserDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {


            public static final String TAG = "shdgfsddddddddf";

            ArrayList<String> noe;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                    String userId = childDataSnapshot.getKey();
                    Log.d("dddddddddddddddd", userId);

                    String name = childDataSnapshot.child("Name").getValue().toString();
                    String status = childDataSnapshot.child("status").getValue().toString();




                    Log.v(TAG, "" + childDataSnapshot.child("Name").getValue());

                    noe = new ArrayList<>();
                    noe.add(name);
                    noe.add(userId);
                    noe.add(status);

                    userData.add(noe);

                }
                adminAdapter = new AdminAdapter(userData, getApplicationContext());
                tRecyclerView.setAdapter(adminAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}