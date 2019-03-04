package com.example.urmi.eloquence;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.google.protobuf.ByteString;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private WordsList WordsUtility;
    Random r = new Random();
    private int currentWordIndex;
    private String toSpeak;
    private TextToSpeech t;
    private final int REQUEST_SPEECH_RECOGNIZER = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WordsUtility = new WordsList();

        Button tts_click = findViewById(R.id.tts_button);
        Button stt_click = findViewById(R.id.stt_button);

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
                String mAnswer = results.get(0);

                if (mAnswer.toLowerCase().indexOf(WordsUtility.getWordAt(currentWordIndex)) > -1)
                    Toast.makeText(MainActivity.this, "You answered '" + mAnswer.toUpperCase() + "', which is correct.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "You answered '" + mAnswer.toUpperCase() + "', which is incorrect.", Toast.LENGTH_SHORT).show();

            }
        }
    }

}

