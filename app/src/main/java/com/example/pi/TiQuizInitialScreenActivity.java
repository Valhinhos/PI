package com.example.pi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TiQuizInitialScreenActivity extends AppCompatActivity {

    String passedquiz = "quizti", passedRa, passedUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ti_quiz_initial_screen);

        passedRa = getIntent().getStringExtra("keyra");
        passedUserName = getIntent().getStringExtra("keyusername");
    }

    public void startQuizTi(View v) {
        Intent intent = new Intent(TiQuizInitialScreenActivity.this, QuizActivity.class);
        intent.putExtra("keyra", passedRa);
        intent.putExtra("keyusername", passedUserName);
        intent.putExtra("keyquiz", passedquiz);
        startActivity(intent);
    }

    public void openRankingScreen(View v){
        Intent intent = new Intent(TiQuizInitialScreenActivity.this, QuizRankingActivity.class);
        intent.putExtra("rankingmode", "rankingti");
        intent.putExtra("keyra", passedRa);
        startActivity(intent);
    }
}