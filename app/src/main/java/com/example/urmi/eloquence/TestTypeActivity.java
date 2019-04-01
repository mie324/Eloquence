package com.example.urmi.eloquence;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TestTypeActivity extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_type);

        auth = FirebaseAuth.getInstance();
    }

    public void openTest(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openTrain(View view) {
        Intent intent = new Intent(this, TrainActivity.class);
        startActivity(intent);
    }

    public void openHomepage(View view) {
        Intent intent = new Intent(TestTypeActivity.this, homePage.class);
        startActivity(intent);
        finish();
    }
}
