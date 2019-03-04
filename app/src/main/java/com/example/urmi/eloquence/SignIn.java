package com.example.urmi.eloquence;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignIn extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        auth = FirebaseAuth.getInstance();

        AutoCompleteTextView mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        EditText mPasswordView = (EditText) findViewById(R.id.password);
        EditText mConfirmPasswordView = (EditText)findViewById(R.id.confirm_pwd);
        EditText mUsernameView = (EditText)findViewById(R.id.username);
        EditText mHearingView = (EditText)findViewById(R.id.hearing_loss);
        Button signin = (Button)findViewById(R.id.loginpage_btn);
        Button signup = (Button)findViewById(R.id.registerpage_btn);

        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();
        final String confirm_password = mConfirmPasswordView.getText().toString();
        final String username = mUsernameView.getText().toString();
        final String hearing = mHearingView.getText().toString();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {

            Intent intent = new Intent(SignIn.this, MainActivity.class);
            startActivity(intent);

        } else  {
            onResume();
        }
    }
}
