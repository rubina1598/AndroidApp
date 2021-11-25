package com.hazyaz.mysapling;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Userdetails extends AppCompatActivity {


    public static String naan;
    private EditText mUserName;
    private EditText muserAge;
    private EditText muserAddress;
    private EditText muserphno;
    private RadioGroup mGender;
    private RadioButton mRadio;
    private Button mUpdateButton;
    private DatabaseReference mUserdatabase;
    private FirebaseAuth mAuth ;
    private String mCurrentUser;
    private String mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);




        mAuth = FirebaseAuth.getInstance();
        mUserdatabase = FirebaseDatabase.getInstance().getReference();
        mCurrentUser =  mAuth.getCurrentUser().getUid();



        mUserName = findViewById(R.id.UserName);
        muserAge = findViewById(R.id.UserAge);
        muserAddress = findViewById(R.id.UserAddress);
        muserphno = findViewById(R.id.UserPhone);
        mUpdateButton = findViewById(R.id.UserUpdate);
        mGender = findViewById(R.id.radioGroup);



        mActivity = getIntent().getStringExtra("ACTIVITY");

        Log.d("623546234", mActivity);


        mUpdateButton.setText("Update");

        updateUi();
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = mUserName.getEditableText().toString();
                String age = muserAge.getEditableText().toString();
                String address = muserAddress.getEditableText().toString();
                String phno = muserphno.getEditableText().toString();
                String gender = onclickbuttonMethod();

//                -------------------------------------REGEX-------------------------------------------------------
                int Aage = Integer.parseInt(age);

                if (!name.matches("^[\\p{L} .'-]+$")) {
                    Toast.makeText(getApplicationContext(), "Not a Valid Name", Toast.LENGTH_LONG).show();
                } else if (Aage <= 0 && Aage >= 100) {
                    Toast.makeText(getApplicationContext(), "Not a Valid Age", Toast.LENGTH_LONG).show();
                } else if (!address.matches("[\\\\sa-zA-Z0-9\\\\\\\\\\.n°/]*")) {
                    Toast.makeText(getApplicationContext(), "Not a Valid Age", Toast.LENGTH_LONG).show();
                } else if (!phno.matches("^(\\+0?1\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$")) {
                    Toast.makeText(getApplicationContext(), "Not a Valid Age", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Not a Valid Age", Toast.LENGTH_LONG).show();
                }


                //                -------------------------------------REGEX-------------------------------------------------------


                if(mActivity.equals("REGISTER"))
                {
                    mUpdateButton.setText("Next");
                    updateInformation(name, age, address, phno, gender);
                    mUserdatabase.child("users").child(mCurrentUser).child("status").setValue("not_requested");

                    sendToMain();

                } else if (mActivity.equals("MAINACTIVITY")) {

                    updateInformation(name, age, address, phno, gender);
                    sendToMain();
                } else {
                    Toast.makeText(getApplicationContext(), "someethinig weent erong", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    public void sendToMain() {
        Intent intent = new Intent(Userdetails.this, MainActivity.class);
        startActivity(intent);
    }

    public void updateInformation(String name, String age, String Address, String phno, final String gender){


        mUserdatabase.child("users").child(mCurrentUser).child("Name").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    mUserName.setText("");
                    muserAge.setText("");
                    muserAddress.setText("");
                    muserphno.setText("");

                    Toast.makeText(getApplicationContext(),"Data Uploaded succesfully" ,Toast.LENGTH_LONG).show();
                    updateUi();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Data Cannot be uploaded as"+task.getException() ,Toast.LENGTH_LONG).show();

                }

            }
        });


        mUserdatabase.child("users").child(mCurrentUser).child("Age").setValue(age);
        mUserdatabase.child("users").child(mCurrentUser).child("Address").setValue(Address);
        mUserdatabase.child("users").child(mCurrentUser).child("Phno").setValue(phno);
        mUserdatabase.child("users").child(mCurrentUser).child("Gender").setValue(gender);


//        HashMap<String,String> HashMap=new HashMap<String, String>();
//        HashMap.put("Name",name);
//        HashMap.put("Age",age);
//        HashMap.put("Address",Address);
//        HashMap.put("Phno",phno);
//        HashMap.put("Gender",gender);




    }



    public void updateUi(){
        mUserdatabase.child("users").child(mCurrentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                mUserName.setHint(dataSnapshot.child("Name").getValue().toString());
                muserAddress.setHint(dataSnapshot.child("Address").getValue().toString());
                muserAge.setHint(dataSnapshot.child("Age").getValue().toString());
                muserphno.setHint(dataSnapshot.child("Phno").getValue().toString());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String onclickbuttonMethod(){
        int selectedId = mGender.getCheckedRadioButtonId();
        mRadio = findViewById(selectedId);

       return mRadio.getText().toString();


    }


}

