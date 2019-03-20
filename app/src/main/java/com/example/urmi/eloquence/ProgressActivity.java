package com.example.urmi.eloquence;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ProgressActivity extends AppCompatActivity {

    private LineChart chart;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private DateFormat sdf = new SimpleDateFormat("s:m:h EE, d M");
    private ArrayList<String> xDataMap;
    private List<Entry> entries = new ArrayList<Entry>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        chart = (LineChart) findViewById(R.id.chart);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "Test " + (Math.round(value) + 1);
            }
        };

        db.collection("test")
                .whereEqualTo("uid", auth.getUid())
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int index = 0;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                int testScore = Integer.parseInt(document.get("testScore").toString());
                                int maxScore = Integer.parseInt(document.get("maxScore").toString());
                                int percent = 100*testScore/maxScore;
                                entries.add(new Entry(index++, percent));

                                Log.d("PROGRESS", document.getId() + " => " + document.getData());
                            }

                            Log.d("PROGRESS", entries.toString());

                            LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
                            Drawable drawable = ContextCompat.getDrawable(ProgressActivity.this, R.drawable.fade_red);
                            dataSet.setDrawFilled(true);
                            dataSet.setFillDrawable(drawable);
//                            dataSet.setColor(...);
//                            dataSet.setValueTextColor(...);

                            LineData lineData = new LineData(dataSet);
                            chart.setData(lineData);
                            XAxis xAxis = chart.getXAxis();
                            xAxis.setValueFormatter(formatter);
                            chart.invalidate();

                        } else {
                            Log.w("PROGRESS", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
