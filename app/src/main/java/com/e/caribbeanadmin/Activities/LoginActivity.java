package com.e.caribbeanadmin.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.e.caribbeanadmin.data_model.UserProfile;
import com.e.caribbeanadmin.Listeners.OnUserProfileLoadListeners;
import com.e.caribbeanadmin.R;
import com.e.caribbeanadmin.Repository.Repository;
import com.e.caribbeanadmin.Util.CurrentUser;
import com.e.caribbeanadmin.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private ActivityLoginBinding activityLoginBinding;
    private static final String TAG = "LoginActivity";
    private FirebaseAuth.AuthStateListener authStateListener;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth= FirebaseAuth.getInstance();
        setAuthStateListener();
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("please wait . . .");
        progressDialog.setCanceledOnTouchOutside(false);
        activityLoginBinding= DataBindingUtil.setContentView(LoginActivity.this,R.layout.activity_login);

        activityLoginBinding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserNow();
            }
        });

        activityLoginBinding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUpScreen();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }


    private void startDashboard(){
        startActivity(new Intent(LoginActivity.this, Dashboard.class));
        finish();
    }
    private void startSignUpScreen(){
        startActivity(new Intent(LoginActivity.this, SignUp.class));
    }



    private void setAuthStateListener(){
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                progressDialog.show();
                if(firebaseAuth.getCurrentUser()!=null){
                    Repository.getMyProfile(firebaseAuth.getUid(), new OnUserProfileLoadListeners() {
                        @Override
                        public void onUserProfileLoaded(UserProfile userProfile) {
                            CurrentUser.setUserProfile(userProfile);
                            progressDialog.dismiss();
                            startDashboard();
                        }
                        @Override
                        public void onFailure(String e) {
                            Toast.makeText(LoginActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });

                }else{
                    progressDialog.dismiss();
                }

            }
        };
    }

    private void loginUserNow(){
        String email=activityLoginBinding.emailEditText.getEditText().getText().toString();
        String password=activityLoginBinding.passwordEditText.getEditText().getText().toString();
        if(email.isEmpty()){
            Toast.makeText(LoginActivity.this, "invalid email", Toast.LENGTH_SHORT).show();
            return;
        }else if(password.isEmpty()||password.length()<6){
            Toast.makeText(LoginActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email,password).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                if(e instanceof FirebaseAuthInvalidCredentialsException){
                    Toast.makeText(LoginActivity.this, "Wrong Email Password", Toast.LENGTH_SHORT).show();
                }else if(e instanceof FirebaseAuthInvalidUserException){
                    Toast.makeText(LoginActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
                }else if(e instanceof FirebaseAuthWeakPasswordException){
                    Toast.makeText(LoginActivity.this, "Password too weak", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}