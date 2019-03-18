package com.example.urmi.eloquence;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ResultActivity extends AppCompatActivity {
    private String testScore;
    private String maxScore;
    private String testType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle extras = getIntent().getExtras();
        testScore = extras.getString("TEST_SCORE");
        maxScore = extras.getString("MAX_SCORE");
        testType = extras.getString("TEST_TYPE");
        TextView tv = findViewById(R.id.resultTextView);
        if (testType.equals("training")) {
            TextView tv2 = findViewById(R.id.textView2);
            TextView tv3 = findViewById(R.id.textView3);
            tv.setTextColor(getResources().getColorStateList(R.color.colorBlue, this.getTheme()));
            tv2.setTextColor(getResources().getColorStateList(R.color.colorBlue, this.getTheme()));
            tv3.setTextColor(getResources().getColorStateList(R.color.colorBlue, this.getTheme()));
            Button bt1 = findViewById(R.id.tts_button);
            Button bt2 = findViewById(R.id.stt_button);
            bt1.setBackground(getResources().getDrawable(R.drawable.custom_button_bg_blue, this.getTheme()));
            bt2.setBackground(getResources().getDrawable(R.drawable.custom_button_bg_blue, this.getTheme()));
            ImageView iv = findViewById(R.id.group);
            iv.setVisibility(iv.VISIBLE);
            ImageView iv2 = findViewById(R.id.imageView2);
            iv2.setVisibility(iv2.INVISIBLE);
            ImageView rv = findViewById(R.id.resultImageView);
            rv.setBackground(getResources().getDrawable(R.drawable.bubble_right, this.getTheme()));
            tv.setText("Good word! You got " + testScore +" practice words right");
        } else {
            ImageView rv = findViewById(R.id.resultImageView);
            rv.setBackground(getResources().getDrawable(R.drawable.bubble, this.getTheme()));
            tv.setText("Great job! You got " + testScore + "/" + maxScore + " words correctly");
        }

    }

    public void openTrain(View view) {
        Intent intent = new Intent(this, TrainActivity.class);
        startActivity(intent);
    }

    public void openTest(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
