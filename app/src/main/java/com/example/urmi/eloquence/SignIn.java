package com.example.urmi.eloquence;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        AutoCompleteTextView mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        EditText mPasswordView = (EditText) findViewById(R.id.password);
        EditText mConfirmPasswordView = (EditText)findViewById(R.id.confirm_pwd);
        EditText mUsernameView = (EditText)findViewById(R.id.username);
        EditText mHearingView = (EditText)findViewById(R.id.hearing_loss);

        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();
        final String confirm_password = mConfirmPasswordView.getText().toString();
        final String username = mUsernameView.getText().toString();
        final String hearing = mHearingView.getText().toString();

    }
}
