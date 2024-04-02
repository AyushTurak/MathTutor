package com.example.mathtutorial;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

public class ClapCountingActivity extends AppCompatActivity {

    final int RECORD_AUDIO = 0;
    ImageButton btnStart;
    Spinner spinDuration;
    int clipDuration = 0;
    LongOperation recordAudioSync = null;
    String[] durationItems = {"5", "10", "15", "20"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_clap_counting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeHandles();
        ArrayAdapter<String> spinDurationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, durationItems);
        spinDurationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDuration.setAdapter(spinDurationAdapter);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clipDuration = Integer.parseInt(spinDuration.getSelectedItem().toString());
                if(ActivityCompat.checkSelfPermission(ClapCountingActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ClapCountingActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO);
                }else{
                    recordAudioSync = new LongOperation();
                    recordAudioSync.execute("");
                }
            }
        });
    }

    private void initializeHandles() {
        btnStart = findViewById(R.id.start);
        spinDuration = findViewById(R.id.duration);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(recordAudioSync != null && recordAudioSync.getStatus() != AsyncTask.Status.FINISHED){
            recordAudioSync.cancel(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(recordAudioSync != null && recordAudioSync.getStatus() != AsyncTask.Status.FINISHED){
            recordAudioSync.cancel(true);
        }
    }

    private class LongOperation extends AsyncTask<String, Void, Integer> {

        MediaRecorder recorder;
        int clapDetectedNumber;

        @Override
        protected Integer doInBackground(String... strings) {
            return recordAudio();
        }

        @Override
        protected void onPreExecute() {
            btnStart.setImageResource(R.drawable.baseline_battery_std_24);
            btnStart.setEnabled(false);

        }

        @Override
        protected void onPostExecute(Integer clapCount) {
            Toast.makeText(ClapCountingActivity.this, "You Clapped: " + clapCount + " times", Toast.LENGTH_SHORT).show();
            btnStart.setEnabled(true);
            btnStart.setImageResource(R.drawable.baseline_battery_alert_24);
        }

        private int recordAudio() {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile("/data/data/" + getPackageName() + "/music.3gp");
            int startAmplitude = 0;
            int finishAmplitude;
            int amplitudeThreshold = 18000;
            int counter = 0;
            try {
                recorder.prepare();
                recorder.start();
                startAmplitude = recorder.getMaxAmplitude();
            } catch (IOException e) {
                e.printStackTrace();
            }
            do{
                if(isCancelled()){
                    break;
                }
                counter++;
                waitSome();
                finishAmplitude = recorder.getMaxAmplitude();
                if(finishAmplitude >= amplitudeThreshold){
                    clapDetectedNumber++;
                }
            }while(counter < (clipDuration * 4));
            done();
            return clapDetectedNumber;
        }

        private void done() {
            if(recorder != null){
                recorder.stop();
                recorder.release();
            }
        }

        private void waitSome() {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
