package com.example.urmi.eloquence;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ConnectActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        auth = FirebaseAuth.getInstance();
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI1(currentUser);
    }

    public void openSignup(View view) {
        Intent intent = new Intent(ConnectActivity.this, SignUp.class);
        startActivity(intent);
    }

    public void openSignin (View view) {
        Intent intent = new Intent(ConnectActivity.this, SignIn.class);
        startActivity(intent);
    }

    private void updateUI1(FirebaseUser user) {
        if (user != null) {

            Intent intent = new Intent(ConnectActivity.this, TestTypeActivity.class);
            startActivity(intent);

        } else  {
            Toast.makeText(ConnectActivity.this,"Enter Details to Log In."  ,
                    Toast.LENGTH_SHORT).show();
            onResume();
        }
    }
}
