package com.example.pi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class ShowFinalScoreActivity extends AppCompatActivity {

    TextView name, score, observation;
    LinearLayout pontuationBox;
    String passedQuiz, passedScore;
    LottieAnimationView scoreLottie;
    int compareGood, compareAverageorBad, totalQuestions;
    int intPassedScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_final_score);

        score = findViewById(R.id.finalscore);
        scoreLottie = findViewById(R.id.scorelottie);
        observation = findViewById(R.id.observationtv);
        pontuationBox = findViewById(R.id.pontuationbox);

        if (getIntent().getBooleanExtra("keyscore", false) == true){
            passedScore = "0";
        }else{
            passedScore = getIntent().getStringExtra("keyscore");
        }

        if (getIntent().getBooleanExtra("keyquiz", false) == true){
            passedQuiz = "none";
        }else{
            passedQuiz = getIntent().getStringExtra("keyquiz");
        }

        if (passedQuiz.equals("quizti")){
            totalQuestions = 11;
            compareGood = 7;
            compareAverageorBad = 3;
        }else if (passedQuiz.equals("quizrh")){
            totalQuestions = 79;
            compareGood = 48;
            compareAverageorBad = 24;
        }else if (passedQuiz.equals("quizlog")){
            totalQuestions = 30;
            compareGood = 18;
            compareAverageorBad = 9;
        }

//        passedName = getIntent().getStringExtra("keyname");
//        passedScore = getIntent().getStringExtra("keyscore");
        totalQuestions = getIntent().getIntExtra("keytotalquestions",0);

        intPassedScore = Integer.parseInt(passedScore);

        score.setText(passedScore);

        addLottieAnimation();
        setObservationText();
        setBoxColor();
//        Toast.makeText(this, "score: " + passedScore + "total q: " + totalQuestions, Toast.LENGTH_SHORT).show();
    }

    public void addLottieAnimation(){
        if (intPassedScore >= compareGood){
            scoreLottie.setAnimation(R.raw.champion);
        }else if (intPassedScore >= compareAverageorBad){
            scoreLottie.setAnimation(R.raw.confusedface);
        }else if (intPassedScore <= compareAverageorBad){
//            Toast.makeText(this, (int) calcTQL, Toast.LENGTH_SHORT).show();
            scoreLottie.setAnimation(R.raw.angrycloud);
        }
    }

    public void setObservationText(){
        if (intPassedScore >= compareGood){
            observation.setText("Perfeito!");
            score.setTextColor(Color.rgb(146,208,80));
        }else if (intPassedScore >= compareAverageorBad){
            observation.setText("Estude mais!");
            score.setTextColor(Color.rgb(243,226,186));
        }else if (intPassedScore <= compareAverageorBad){
            observation.setText("Que pena!!!");
            score.setTextColor(Color.rgb(186,78,78));
        }
    }

    public void setBoxColor(){
        if (intPassedScore >= compareGood){
           pontuationBox.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(146,208,80)));
        }else if (intPassedScore >= compareAverageorBad){
            pontuationBox.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(255,217,102)));
        }else if (intPassedScore <= compareAverageorBad){
            pontuationBox.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(255,107,107)));
        }
    }

    public void goToMainMenu(View v){
        Intent intent = new Intent(ShowFinalScoreActivity.this, MainIconsActivity.class);
        startActivity(intent);
        finish();
    }

    public void returnToQuizInitialScreen(View v){
//        Intent intent = new Intent(ShowFinalScoreActivity.this, RhQuizInitialScreenActivity)
    }
}