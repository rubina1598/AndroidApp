package com.hazyaz.mysapling;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hazyaz.mysapling.model.CustomAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int GALLERY_PICK = 1;
    private static ArrayList<ArrayList<String>> strr;
    private static String mUserName = "";
    public final ArrayList<ArrayList<String>> sdf = new ArrayList<>();
    HashMap<String, String> map;
    CustomAdapter adapter;
    String xUserName;
    String xUserMarks;
    String id;
    Button saplingRebButton;
    FloatingActionButton fab;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private String mCurrentUser;
    private StorageReference mStorageRef;
    private RecyclerView mRecyclerView;
    private ProgressDialog mProgressDialog;
    private Uri filePath;
    private DatabaseReference mNameDatabase;
    private DatabaseReference mStatusDatabase;
    private String status;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mUserDatabase = FirebaseDatabase.getInstance().getReference();
        mNameDatabase = FirebaseDatabase.getInstance().getReference();
        mStatusDatabase = FirebaseDatabase.getInstance().getReference();


        if (mAuth.getCurrentUser() != null) {
            mCurrentUser = mAuth.getCurrentUser().getUid();
        } else if (id != null) {
            mCurrentUser = id;
        }


        mRecyclerView = findViewById(R.id.userSaplingList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        mUserDatabase.child("users").child(mCurrentUser).addValueEventListener(new ValueEventListener() {

            public static final String TAG = "ddddddddddd";


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> mains;

                String Address;
                String marks;
                String img;
                String tim;

                Address = dataSnapshot.child("Address").getValue().toString();
                if (dataSnapshot.child("Marks").exists()) {
                    marks = dataSnapshot.child("Marks").getValue().toString();
                } else {
                    marks = "NA";
                }

                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.child("saplings").getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                strr = new ArrayList<>();

                while (iterator.hasNext()) {
                    DataSnapshot next = iterator.next();
                    img = next.child("imageurl").getValue().toString();
                    tim = next.child("time").getValue().toString();


                    Log.d("ffdsf33333df", img);
                    mains = new ArrayList<>();
                    mains.add(img);
                    mains.add(tim);

                    mains.add(Address);

                    mains.add(marks);
                    mains.add("mainac");

                    strr.add(mains);


                }

                adapter = new CustomAdapter(strr, getApplicationContext());
                mRecyclerView.setAdapter(adapter);
                //  Toast.makeText(getApplicationContext(), ""+strr, Toast.LENGTH_LONG).show();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "coudn't connect to database", Toast.LENGTH_LONG).show();
            }
        });


        Log.d("pppppppppp", "" + status);


        fab = findViewById(R.id.fab);
        saplingRebButton = findViewById(R.id.requestForSapling);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagechooser();

            }
        });

        saplingRebButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                checkStatus();

            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My saplings");


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            sentToStart();
        } else {

            mStatusDatabase.child("users").child(mCurrentUser).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.child("status").exists()) {
                        String statusOfuser = dataSnapshot.child("status").getValue().toString();

                        if (statusOfuser.equals("not_requested")) {
                            fab.setVisibility(View.GONE);
                            saplingRebButton.setVisibility(View.VISIBLE);
                        } else if (statusOfuser.equals("requested")) {
                            fab.setVisibility(View.GONE);
                            saplingRebButton.setVisibility(View.GONE);
                        } else if (statusOfuser.equals("given")) {
                            fab.setVisibility(View.VISIBLE);
                            saplingRebButton.setVisibility(View.GONE);

                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }


    }


    public void sentToStart() {
        Intent intent = new Intent(MainActivity.this, StartActivitty.class);
        startActivity(intent);
        finish();
    }

//343434343434343434343434343434343434343434343434343434343434343434343434343434343434343434


    public void checkStatus() {

        String[] s = {"Mango ", "Apple ", "Banana ", "Seabuckthon ", "Cactus "
        };

        final ArrayAdapter<String> adp = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, s);


        final Spinner sp = new Spinner(MainActivity.this);
        sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        sp.setAdapter(adp);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                Toast.makeText(MainActivity.this, sp.getSelectedItem().toString(), Toast.LENGTH_LONG).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(MainActivity.this, "Please select any one Sapling", Toast.LENGTH_LONG).show();

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Select Your Sapling");
        builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                mStatusDatabase.child("users").child(mCurrentUser).child("status").setValue("requested");
                mStatusDatabase.child("users").child(mCurrentUser).child("sapling_name").setValue(sp.getSelectedItem().toString());
                Toast.makeText(MainActivity.this, "You will be given sapling under 7 days", Toast.LENGTH_LONG).show();
                saplingRebButton.setVisibility(View.GONE);

            }
        });


        builder.setView(sp);
        builder.create().show();


    }


//    0000000000000000000 All code realted to server image upload 000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000


    private void imagechooser() {


        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK && data != null && data.getData() != null) {


            filePath = data.getData();


            if (filePath != null) {

                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();


                StorageReference ref = mStorageRef.child("saplings").child(mCurrentUser).child(UUID.randomUUID().toString());
                ref.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();


                                long time = System.currentTimeMillis();
                                String currentTime = String.valueOf(time);


                                map = new HashMap<>();

                                map.put("imageurl", taskSnapshot.getDownloadUrl().toString());
                                map.put("time", currentTime);


                                mUserDatabase.child("users").child(mCurrentUser).child("saplings").push().setValue(map);


                                Toast.makeText(MainActivity.this, "Uploaded"
                                        , Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            }
                        });
            }
        }
    }


    //    0000000000000000000 All code realted to server image upload 000000000000000000000000000000


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            sentToStart();
        } else if (id == R.id.action_details) {
            Intent intent = new Intent(MainActivity.this, Userdetails.class);
            intent.putExtra("ACTIVITY", "MAINACTIVITY");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}