package com.hazyaz.mysapling;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class AdminLogin extends AppCompatActivity {

    private Button mAdminStudentLogin;
//    private Button mAdminStudentRegister;
    private EditText mAdminLoginEmail;
    private EditText mAdminLoginPassword;
//    private EditText mAdminRegisterName;
//    private EditText mAdminRegisterEmail;
//    private EditText mAdminRegisterpassword;

    private ProgressDialog mRegProgress;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        mAuth = FirebaseAuth.getInstance();


        mRegProgress = new ProgressDialog(this);

        mAdminStudentLogin = findViewById(R.id.AdminLoginButton);
//        mAdminStudentRegister = findViewById(R.id.AdminregisterButton);

        mAdminLoginEmail = findViewById(R.id.AdminloginEmail);
        mAdminLoginPassword = findViewById(R.id.AdminloginPassword);

//        mAdminRegisterName = findViewById(R.id.AdminregisterName);
//        mAdminRegisterEmail = findViewById(R.id.AdminregisterEmail);
//        mAdminRegisterpassword = findViewById(R.id.AdminregisterPassword);



mAdminStudentLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        mRegProgress.setTitle("Registering User");
        mRegProgress.setMessage("Please wait while we login into your account !");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();

        AdminLogi(mAdminLoginEmail.getEditableText().toString(), mAdminLoginPassword.getEditableText().toString());
    }
    });


    }

    private void AdminLogi(String email, String password) {


        if(email.equals("admin")&& password.equals("3333")){
            mRegProgress.dismiss();
                           Intent intent = new Intent(AdminLogin.this,AdminView.class);
                          startActivity(intent);

        }
        else
        {
            Toast.makeText(AdminLogin.this, "Authentication failed.",
                                  Toast.LENGTH_SHORT).show();

        }



//       mAuth.signInWithEmailAndPassword(email, password)
//               .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                   @Override
//                   public void onComplete(@NonNull Task<AuthResult> task) {
//                       if (task.isSuccessful()) {
//
//                           mRegProgress.dismiss();
//                           Intent intent = new Intent(AdminLogin.this,AdminView.class);
//                           startActivity(intent);
//                       } else {
//
//                           mRegProgress.dismiss();
//                           Toast.makeText(AdminLogin.this, "Authentication failed."
//                                           +task.getException(),
//                                   Toast.LENGTH_SHORT).show();
//
//                       }
//
//
//                   }
//               });

    }




}
