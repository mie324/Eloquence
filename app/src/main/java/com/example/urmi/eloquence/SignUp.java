package com.example.urmi.eloquence;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "REGISTER";
    private static final String USERNAME = "Username";
    private static final String HEARING = "Hearing_Info";
    private static final String DOB = "dob";
    private static final String NAME = "fullname";

    AutoCompleteTextView mEmailView;
    EditText mPasswordView;
    EditText mConfirmPasswordView;
    EditText mUsernameView;
    EditText mHearingView;
    EditText dobField;
    EditText nameField;

    String email;
    String password;
    String confirm_password;
    String username;
    String hearing;


    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mConfirmPasswordView = (EditText)findViewById(R.id.confirm_pwd);
        mUsernameView = (EditText)findViewById(R.id.username);
        mHearingView = (EditText)findViewById(R.id.hearing_loss);
        nameField = (EditText) findViewById(R.id.nameField);
        dobField = (EditText) findViewById(R.id.dobField);

        Button signup = (Button)findViewById(R.id.registerpage_btn);

        email = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();
        confirm_password = mConfirmPasswordView.getText().toString();
        username = mUsernameView.getText().toString();
        hearing = mHearingView.getText().toString();

        setDate fromDate = new setDate(dobField, this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }

    private boolean validate() {

        boolean valid = true;

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String c_password = mConfirmPasswordView.getText().toString();
        String username = mUsernameView.getText().toString();
        String hearing = mHearingView.getText().toString();
        String name = nameField.getText().toString();
        String dob = dobField.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            nameField.setError("Please enter a valid name");
            focusView = nameField;
            cancel = true;
            valid = false;
        }

        if (TextUtils.isEmpty(dob)) {
            dobField.setError("Please enter a valid date of birth");
            focusView = dobField;
            cancel = true;
            valid = false;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
            valid = false;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
            valid = false;

        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
            valid = false;
        }

        if (TextUtils.isEmpty(c_password)) {
            mConfirmPasswordView.setError(getString(R.string.error_field_required));
            focusView = mConfirmPasswordView;
            cancel = true;
            valid = false;
        }

        if (password.equals(c_password)) {
            cancel = false;
            valid = true;
        }else{
            mConfirmPasswordView.setError(getString(R.string.error_not_match));
            focusView = mConfirmPasswordView;
            cancel = true;
            valid = false;
        }

        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
            valid = false;
        }

        if (TextUtils.isEmpty(hearing)) {
            mHearingView.setError(getString(R.string.error_field_required));
            focusView = mHearingView;
            cancel = true;
            valid = false;
        }

        if (cancel) {
          focusView.requestFocus();
        }
        return valid;
    }


    private void attemptRegister(){
        Log.d(TAG, "signIn:" + mEmailView + "----------------------------------");

        if(!validate()) {
            return;
        }

        Log.d(TAG, "createUserWithEmail:part 1 ----------------------------");

        email = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d(TAG, "createUserWithEmail:entered");
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);

                        }
                        else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {
            String id = user.getUid();
            System.out.println(id);

            Map< String, Object > User = new HashMap< >();
            User.put(USERNAME, mUsernameView.getText().toString().trim());
            User.put(HEARING, mHearingView.getText().toString().trim());
            User.put(DOB, dobField.getText().toString().trim());
            User.put(NAME, nameField.getText().toString().trim());

            Log.d(TAG, "createUserWithEmail:in UpdateUI");
            DocumentReference mRef = db.collection("UserMetaData").document(user.getUid());
            mRef.set(User);

            Intent intent =   new Intent(SignUp.this, TestTypeActivity.class);
            startActivity(intent);
        }

        else {

            Toast.makeText(SignUp.this, "Something went wrong Account not registered.",
                    Toast.LENGTH_SHORT).show();
         }
    }

}


