package com.hazyaz.mysapling;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentLogin extends AppCompatActivity {



    private Button mStudentLogin;
    private Button mStudentRegister;
    private EditText mLoginEmail;
    private EditText mLoginPassword;
    private EditText mRegisterName;
    private EditText mRegisterEmail;
    private EditText mRegisterpassword;
    private String TAG="fghg";
    private FirebaseAuth mAuth;

    private DatabaseReference mUserDatabase;
    private ProgressDialog mRegProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        mAuth = FirebaseAuth.getInstance();



        mRegProgress = new ProgressDialog(this);

        mStudentLogin = findViewById(R.id.LoginButton);
        mStudentRegister = findViewById(R.id.registerButton);

        mLoginEmail = findViewById(R.id.loginEmail);
        mLoginPassword = findViewById(R.id.loginPassword);

        mRegisterName = findViewById(R.id.registerName);
        mRegisterEmail = findViewById(R.id.registerEmail);
        mRegisterpassword = findViewById(R.id.registerPassword);



      mStudentLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {



                mRegProgress.setTitle("Registering User");
                mRegProgress.setMessage("Please wait while we login into your account !");
                mRegProgress.setCanceledOnTouchOutside(false);
                mRegProgress.show();


                studLogin(mLoginEmail.getEditableText().toString(), mLoginPassword.getEditableText().toString());
      }

    });


      mStudentRegister.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              mRegProgress.setTitle("Registering User");
              mRegProgress.setMessage("Please wait while we create your account !");
              mRegProgress.setCanceledOnTouchOutside(false);
              mRegProgress.show();

              studRegister(mRegisterEmail.getEditableText().toString(),mRegisterpassword.getEditableText().toString()
              ,mRegisterName.getText().toString());

          }
      });



    }

private  void studLogin(String email, String password){

    mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        mRegProgress.dismiss();
                        Intent intent = new Intent(StudentLogin.this,MainActivity.class);
                        startActivity(intent);
                    } else {

                        mRegProgress.dismiss();
                        Toast.makeText(StudentLogin.this, "Authentication failed."
                                        +task.getException(),
                                Toast.LENGTH_SHORT).show();

                    }


                }
            });


}

private void studRegister(final String email, String password , final String Name){

    mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        String user = mAuth.getCurrentUser().getUid();

                        mUserDatabase = FirebaseDatabase.getInstance().getReference()
                                .child("users").child(user);
                        mRegProgress.dismiss();

                        Intent intent = new Intent(StudentLogin.this,Userdetails.class);
                        intent.putExtra("ACTIVITY", "REGISTER");
                        startActivity(intent);



                    } else {
                        // If sign in fails, display a message to the user.
                        mRegProgress.dismiss();

                        Toast.makeText(StudentLogin.this, "Authentication failed."+
                                        task.getException()+email,
                                Toast.LENGTH_SHORT).show();
                    }

                }
            });


}





}