package com.example.mathtutorial;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mathtutorial.databinding.ActivityQuizBinding;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    ArrayList<Question> quizQuestions;
    private MediaPlayer mediaPlayer;
    int Score;
    int qNo;
    TextView Qscore;
    ImageView img;
    TextView question;
    EditText answer;
    Handler handler;
    ActivityQuizBinding binding;
    int currentQuestionIndex = 0;
    private CountDownTimer countDownTimer;
    private TextView tvTimer;
    private final long startTimeInMillis = 15000; // 15 seconds in milliseconds
    private long timeLeftInMillis = startTimeInMillis;
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

       handler = new Handler(Looper.getMainLooper());
Qscore = binding.scoreQuiz;
Score = 0;
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        View view = binding.getRoot();
//        setContentView(view);

        // Initialize views
        question = binding.quizQuestionText;
        answer = binding.quizAnswerText;
        img = binding.changeImage;
        tvTimer = binding.timerQuiz;
        Button enter = findViewById(R.id.enter_btn_quiz);
 qNo =0 ;

        // Initialize quiz questions
        quizQuestions = new ArrayList<>();
        quizQuestions.add(new Question("What is 2 + 2?", "4"));
        quizQuestions.add(new Question("What is 5 - 3?", "2"));
        quizQuestions.add(new Question("What is 4 × 6?", "24"));
        quizQuestions.add(new Question("What is 15 ÷ 3?", "5"));
        quizQuestions.add(new Question("What is 8 + 7?", "15"));
        quizQuestions.add(new Question("What is 10 - 4?", "6"));
        quizQuestions.add(new Question("What is 3 × 5?", "15"));
        quizQuestions.add(new Question("What is 18 ÷ 2?", "9"));
        quizQuestions.add(new Question("What is 9 + 3?", "12"));
        quizQuestions.add(new Question("What is 20 - 11?", "9"));

        // Display first question
        displayQuestion();
        // Button click listener
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });


    }

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        // Create a new CountDownTimer for 15 seconds
        countDownTimer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timerTextView with the remaining time
                tvTimer.setText("Time remaining: " + millisUntilFinished / 1000 + " seconds");
            }

            @Override
            public void onFinish() {
                // Timer finished, do something here if needed
                tvTimer.setText("Timer finished!");
            }
        };

        // Start the timer
        countDownTimer.start();
    }
            private void updateTimer() {
                int seconds = (int) (timeLeftInMillis / 1000);
                String timeLeftFormatted = String.format("%02d", seconds);
                tvTimer.setText("Time left: " + timeLeftFormatted);
            }


            private void displayQuestion() {
                answer.setText("");
                img.setImageResource(R.drawable.quiz_que);
        qNo++;
                if(qNo== quizQuestions.size()-2){
// Create an intent
                    Intent intent = new Intent(this, QuizScore.class);
// Put extra data into the intent
                    intent.putExtra("Score",Score);
// Start the destination activity
                    startActivity(intent);
                    finish();
                }
                startTimer();
                // Check if currentQuestionIndex is within bounds
                if (currentQuestionIndex >= 0 && currentQuestionIndex < quizQuestions.size()) {
                    // Get the current question
                    Question currentQuestion = quizQuestions.get(currentQuestionIndex);
                    // Set the question text
                    question.setText(currentQuestion.getQuestion());

                } else {
                    // Reset text if no more questions
                    question.setText("");
                }
            }

            private void checkAnswer() {
                // Check if currentQuestionIndex is within bounds
                if (currentQuestionIndex >= 0 && currentQuestionIndex < quizQuestions.size()) {
                    // Get the current question
                    Question currentQuestion = quizQuestions.get(currentQuestionIndex);
                    // Get the user's answer
                    String userAnswer = answer.getText().toString();
                    // Check if the answer is correct
                    if (currentQuestion.getAnswer().equals(userAnswer)) {
                        mediaPlayer = MediaPlayer.create(this, R.raw.correct_answer);

                        // Start playback
                        mediaPlayer.start();
                        // Set win image
                        Score++;
                        Qscore.setText("Score : " +Score);
                        img.setImageResource(R.drawable.quiz_win);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Code to be executed after 5 seconds
                                // This code will run on the main thread
                                // Do whatever you need to do after the delay
                                currentQuestionIndex++;
                                // Display the next question
                                displayQuestion();
                            }
                        }, 2000);

                    } else {
                        // Set lose image
                        Score--;
                        Qscore.setText("Score : " +Score);
                        img.setImageResource(R.drawable.quiz_lose);
                        mediaPlayer = MediaPlayer.create(this, R.raw.wrong_answer);

                        // Start playback
                        mediaPlayer.start();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Code to be executed after 5 seconds
                                // This code will run on the main thread
                                // Do whatever you need to do after the delay
                                currentQuestionIndex++;
                                // Display the next question
                                displayQuestion();
                            }
                        }, 2000);
                    }
                    // Move to the next question

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

