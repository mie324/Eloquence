package com.example.urmi.eloquence;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

           Button tts_click = (Button) findViewById(R.id.tts_button);

           Button stt_click = (Button)findViewById(R.id.stt_button);

        tts_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
                    // Set the text input to be synthesized
                    SynthesisInput input = SynthesisInput.newBuilder()
                            .setText("Hello, World!")
                            .build();

                    // Build the voice request, select the language code ("en-US") and the ssml voice gender
                    // ("neutral")
                    VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                            .setLanguageCode("en-US")
                            .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                            .build();

                    // Select the type of audio file you want returned
                    AudioConfig audioConfig = AudioConfig.newBuilder()
                            .setAudioEncoding(AudioEncoding.MP3)
                            .build();

                    // Perform the text-to-speech request on the text input with the selected voice parameters and
                    // audio file type
                    SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice,
                            audioConfig);

                    // Get the audio contents from the response
                    ByteString audioContents = response.getAudioContent();

                    // Write the response to the output file.
                    try (OutputStream out = new FileOutputStream("output.mp3")) {
                        out.write(audioContents.toByteArray());
                        if(out == null){
                            Log.i("Activity","Out is null");
                        }
                        else {
                            System.out.println("Audio content written to file \"output.mp3\"");
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        });

        stt_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}

