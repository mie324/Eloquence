package com.example.urmi.eloquence;

import android.content.Intent;
import android.provider.UserDictionary;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private WordsList WordsUtility;
    Random r = new Random();
    private int currentWordIndex;
    private String toSpeak;
    private TextToSpeech t;
    private final int REQUEST_SPEECH_RECOGNIZER = 3000;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        WordsUtility = new WordsList();

        Button tts_click = findViewById(R.id.tts_button);
        Button stt_click = findViewById(R.id.stt_button);
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
                currentWordIndex = r.nextInt(WordsUtility.getWordsLength());
                toSpeak = "Say the word, " + WordsUtility.getWordAt(currentWordIndex);
                t.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
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

                int foundIndex = results.indexOf(WordsUtility.getWordAt(currentWordIndex));

                if (foundIndex > -1) {
                    String mAnswer = results.get(foundIndex);
                    Toast.makeText(MainActivity.this, "You answered '" + mAnswer.toUpperCase() + "', which is correct.", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(MainActivity.this, "You answered '" + results.get(0) + "', which is incorrect.", Toast.LENGTH_SHORT).show();

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

