package com.example.mathtutorial;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mathtutorial.databinding.ActivityQuizScoreBinding;

public class QuizScore extends AppCompatActivity {
MediaPlayer mediaPlayer;
    ActivityQuizScoreBinding binding;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        binding = ActivityQuizScoreBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        // Retrieve the data from the intent
        Intent intent = getIntent();
       int message = intent.getIntExtra("Score",0);

// Use the retrieved data as needed
        binding.scoreScore.setText(message + " / 7");

        Button home = findViewById(R.id.home_quiz);
      home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent xntent = new Intent(QuizScore.this,MainActivity.class);
                startActivity(xntent);
                finish();
            }
        });

        if(message==7){
binding.imageView.setImageResource(R.drawable.thumbs);
            mediaPlayer = MediaPlayer.create(this, R.raw.perfect_score_sound);

            // Start playback
            mediaPlayer.start();
        }
        else if(message<7 && message>4){
            binding.imageView.setImageResource(R.drawable.ok);
            mediaPlayer = MediaPlayer.create(this, R.raw.good_score_sound);

            // Start playback
            mediaPlayer.start();

        } else if (message>0  && message<=4) {
            binding.imageView.setImageResource(R.drawable.sad);
            mediaPlayer = MediaPlayer.create(this, R.raw.study_hard_sound);

            // Start playback
            mediaPlayer.start();
        }
        else{
            binding.imageView.setImageResource(R.drawable.sad);
            mediaPlayer = MediaPlayer.create(this, R.raw.study_hard_sound);

            // Start playback
            mediaPlayer.start();
        }

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