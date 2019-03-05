package com.example.urmi.eloquence;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    private static final String TAG = "Sign In";
    String email;
    String password;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        auth = FirebaseAuth.getInstance();

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email1);
        mPasswordView = (EditText) findViewById(R.id.login_password);
        Button SignInButton = (Button) findViewById(R.id.login_signin_btn);
        Button SignUpButton = (Button) findViewById(R.id.loginpg_register_btn);

        email = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();
        


    }
}
