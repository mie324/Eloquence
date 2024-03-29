package com.example.urmi.eloquence;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class homePage extends AppCompatActivity {

    final Context context = this;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        auth = FirebaseAuth.getInstance();

        Button start = findViewById(R.id.start_button);
        Button progress = findViewById(R.id.progress_button);
        Button illustrate = findViewById(R.id.illustrate_button);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(homePage.this,TestTypeActivity.class);
                startActivity(intent);
            }
        });

        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(homePage.this,ProgressActivity.class);
                startActivity(intent);
            }
        });

        illustrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("Lets Play!!");

                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.text);
                text.setText("Welcome to Eloquence! We are going to practice our speech together so that we can keep learning new words. " +
                        "There are two things we can do, we can practice saying words or we can test how well we can say words played for us. If you are ready to practice your words press “START”.  If you want to see your progress so far, press “How am I doing?");

                text.setTextSize(16);
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }

    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(homePage.this, ConnectActivity.class);
        startActivity(intent);
        finish();
    }


    public void signout(View view) {
        auth.signOut();
        updateUI(null);
    }
}
