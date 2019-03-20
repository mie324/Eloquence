package com.example.urmi.eloquence;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {
    private String testScore;
    private String maxScore;
    private String testType;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle extras = getIntent().getExtras();
        testScore = extras.getString("TEST_SCORE");
        maxScore = extras.getString("MAX_SCORE");
        testType = extras.getString("TEST_TYPE");
        TextView tv = findViewById(R.id.resultTextView);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        Map<String, Object> result = new HashMap<>();
        result.put("testScore", Integer.parseInt(testScore));
        result.put("maxScore", Integer.parseInt(maxScore));
        result.put("timestamp", new Date().getTime());
        result.put("uid", user.getUid());

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

            db.collection("training")
                    .add(result)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("RESULT", "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("RESULT", "Error adding document", e);
                        }
                    });
        } else {
            ImageView rv = findViewById(R.id.resultImageView);
            rv.setBackground(getResources().getDrawable(R.drawable.bubble, this.getTheme()));
            tv.setText("Great job! You got " + testScore + "/" + maxScore + " words correctly");
            db.collection("test")
                    .add(result)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("RESULT", "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("RESULT", "Error adding document", e);
                        }
                    });
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

    public void openProgress(View view) {
        Intent intent = new Intent(this, ProgressActivity.class);
        startActivity(intent);
    }
}
