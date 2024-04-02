package com.example.mathtutorial;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TrignometryActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trignometry);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.answer_quiz), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button paint = findViewById(R.id.btn_paint_trigno);
        paint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrignometryActivity.this,PaintActivity.class);
                startActivity(intent);
            }
        });

        Button dis = findViewById(R.id.distance_tavedled_trigno);
        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrignometryActivity.this, DistanceTraveledActivity.class);
                startActivity(intent);
            }
        });

        Button clap = findViewById(R.id.clap_btn_trigno);
        clap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrignometryActivity.this,ClapCountingActivity.class);
                startActivity(intent);
            }
        });

Button area = findViewById(R.id.area_btn_trigo);
area.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(TrignometryActivity.this, Area_Calculator_Activity.class);
        startActivity(intent);
        finish();
    }
});

        mediaPlayer = MediaPlayer.create(this, R.raw.trigno);
        // Start playback
        mediaPlayer.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop and release MediaPlayer when no longer needed
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}