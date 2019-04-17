package com.example.urmi.eloquence;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class TrainActivity extends AppCompatActivity {

    private WordsList WordsUtility;
    Random r = new Random();
    private int MAX_WORDS = 2;
    private int currentWordIndex = 0;
    private int currentScore = 0;
    private String toSpeak;
    private TextToSpeech t;
    private Button tts_click;
    private Button stt_click;
    private final int REQUEST_SPEECH_RECOGNIZER = 3000;
    private List<String> testWords;
    private TextView tv;

    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        checkPermission();

        auth = FirebaseAuth.getInstance();
        tv = findViewById(R.id.resultTextView);

        WordsUtility = new WordsList();
        testWords = WordsUtility.getTestWords(MAX_WORDS, "training");

        tts_click = findViewById(R.id.tts_button);
        stt_click = findViewById(R.id.stt_button);
        stt_click.setEnabled(false);

        t = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t.setLanguage(Locale.US);
                }
            }
        });

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                Log.d("SPEECH", "ready");
            }

            @Override
            public void onBeginningOfSpeech() {
                Log.d("SPEECH", "begin");
            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {
                Log.d("SPEECH", "end of speech");
            }

            @Override
            public void onError(int error) {
                Log.d("SPEECH_ERROR", "there was an error" + error);
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                Log.d("SPEECH", matches.toString());

                if (matches != null) {
                    String saidWord = testWords.get(currentWordIndex);
                    Log.d("SPEECH", matches.toString());
                    boolean foundIndex = matches.contains(saidWord);

                    if (foundIndex) {
                        Toast.makeText(TrainActivity.this, "You answered '" + saidWord.toUpperCase() + "', which is correct.", Toast.LENGTH_SHORT).show();

                        tv.setTextColor(Color.parseColor("#338323"));
                        tv.setText("Great job! You got it right. :)");
                        currentScore++;
                    }
                    else{
                        Toast.makeText(TrainActivity.this, "You answered '" + matches.get(0).toUpperCase() + "', which is incorrect.", Toast.LENGTH_SHORT).show();

                        tv.setText("Uh oh! You wanna try it again. :(");
                        tv.setTextColor(Color.parseColor("#D82A17"));
                        findViewById(R.id.resultImageView).setBackgroundResource(R.drawable.ic_clear_red_300_48dp);
                    }

                    currentWordIndex++;
                    stt_click.setEnabled(false);
                }

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                if (currentWordIndex == testWords.size()) {
                                    Intent intent = new Intent(TrainActivity.this, ResultActivity.class);
                                    Bundle extras = new Bundle();
                                    extras.putString("TEST_SCORE", Integer.toString(currentScore));
                                    extras.putString("MAX_SCORE", Integer.toString(MAX_WORDS));
                                    extras.putString("TEST_TYPE", "training");
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
                                tv.setTextColor(Color.parseColor("#34AADC"));
                                tv.setText("Let's practive our words");
                            }
                        },
                        3000);
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

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

        stt_click.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        tv.setText("Let's practive our words");
                        mSpeechRecognizer.stopListening();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        tv.setText("Say the word you heard!");
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        break;
                }
                return false;
            }
        });
    }

    private void checkPermission () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }
    }

    private void updateUI(FirebaseUser user) {
        // hideProgressDialog();
        if (user != null) {

            Toast.makeText(TrainActivity.this, "Firebase User Logged In.",
                    Toast.LENGTH_SHORT).show();

        } else {

            Intent intent = new Intent(TrainActivity.this, ConnectActivity.class);
            startActivity(intent);
            finish();

        }
    }

    public void openHomepage(View view) {
        Intent intent = new Intent(TrainActivity.this, homePage.class);
        startActivity(intent);
        finish();
    }
}
