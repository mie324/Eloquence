package com.example.urmi.eloquence;

import android.content.Intent;
import android.graphics.Color;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private WordsList WordsUtility;
    Random r = new Random();
    private int MAX_WORDS = 3;
    private int currentWordIndex = 0;
    private int currentScore = 0;
    private String toSpeak;
    private TextToSpeech t;
    private Button tts_click;
    private Button stt_click;
    private final int REQUEST_SPEECH_RECOGNIZER = 3000;
    private List<String> testWords;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        WordsUtility = new WordsList();
        testWords = WordsUtility.getTestWords(MAX_WORDS);

        tts_click = findViewById(R.id.tts_button);
        stt_click = findViewById(R.id.stt_button);
        stt_click.setEnabled(false);
        Button signout =(Button) findViewById(R.id.signout_button);

        t = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t.setLanguage(Locale.US);
                }
            }
        });

        tts_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toSpeak = "Say the word, " + testWords.get(currentWordIndex);
                t.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
                stt_click.setEnabled(true);
            }
        });

        stt_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSpeechRecognizer();
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

    }

    private void startSpeechRecognizer() {
        Intent intent = new Intent
                (RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, toSpeak);
        startActivityForResult(intent, REQUEST_SPEECH_RECOGNIZER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SPEECH_RECOGNIZER) {
            if (resultCode == RESULT_OK) {
                List<String> results = data.getStringArrayListExtra
                        (RecognizerIntent.EXTRA_RESULTS);

                Log.d("Main", results.toString());

                int foundIndex = results.indexOf(testWords.get(currentWordIndex));

                if (foundIndex > -1) {
                    String mAnswer = results.get(foundIndex);
                    Toast.makeText(MainActivity.this, "You answered '" + mAnswer.toUpperCase() + "', which is correct.", Toast.LENGTH_SHORT).show();
                    TextView tv = findViewById(R.id.resultTextView);
                    tv.setTextColor(Color.parseColor("#338323"));
                    tv.setText("Great job! You got it right. :)");
                    currentScore++;
                }
                else{
                    Toast.makeText(MainActivity.this, "You answered '" + results.get(0) + "', which is incorrect.", Toast.LENGTH_SHORT).show();
                    TextView tv = findViewById(R.id.resultTextView);
                    tv.setText("Uh oh! You wanna try it again. :(");
                    tv.setTextColor(Color.parseColor("#D82A17"));
                    findViewById(R.id.resultImageView).setBackgroundResource(R.drawable.ic_clear_red_300_48dp);
                }

                currentWordIndex++;
                stt_click.setEnabled(false);

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                if (currentWordIndex == testWords.size()) {
                                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                                    Bundle extras = new Bundle();
                                    extras.putString("TEST_SCORE", Integer.toString(currentScore));
                                    extras.putString("MAX_SCORE", Integer.toString(MAX_WORDS));
                                    intent.putExtras(extras);
                                    startActivity(intent);
                                }
                            }
                        },
                        1000
                );

                new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            TextView tv = findViewById(R.id.resultTextView);
                            tv.setTextColor(Color.parseColor("#34AADC"));
                            tv.setText("Let's practive our words");
                        }
                    },
                    3000);
            }
        }
    }

    private void signOut() {
        auth.signOut();
        updateUI(null);
    }

    private void updateUI(FirebaseUser user) {
        // hideProgressDialog();
        if (user != null) {

            Toast.makeText(MainActivity.this, "Firebase User Logged In.",
                    Toast.LENGTH_SHORT).show();

        } else {

            Intent intent = new Intent(MainActivity.this, SignIn.class);
            startActivity(intent);

        }
    }

}

