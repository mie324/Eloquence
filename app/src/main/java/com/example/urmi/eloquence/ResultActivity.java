package com.example.urmi.eloquence;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ResultActivity extends AppCompatActivity {
    private String testScore;
    private String maxScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle extras = getIntent().getExtras();
        testScore = extras.getString("TEST_SCORE");
        maxScore = extras.getString("MAX_SCORE");
        TextView tv = findViewById(R.id.resultTextView);
        tv.setText("Great job! You got " + testScore + "/" + maxScore + " words correctly");
    }

    public void openTrain(View view) {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    public void openTest(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
