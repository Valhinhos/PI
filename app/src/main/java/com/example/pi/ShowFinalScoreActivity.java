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

import com.airbnb.lottie.LottieAnimationView;

public class ShowFinalScoreActivity extends AppCompatActivity {

    TextView name, score, observation;
    LinearLayout pontuationBox;
    String passedName, passedScore;
    LottieAnimationView scoreLottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_final_score);

        score = findViewById(R.id.finalscore);
        scoreLottie = findViewById(R.id.scorelottie);
        observation = findViewById(R.id.observationtv);
        pontuationBox = findViewById(R.id.pontuationbox);

        passedName = getIntent().getStringExtra("keyname");
        passedScore = getIntent().getStringExtra("keyscore");

        score.setText(passedScore);

        addLottieAnimation();
        setObservationText();
        setBoxColor();

    }

    public void addLottieAnimation(){
        int intPassedScore = Integer.parseInt(passedScore);
        if (intPassedScore >= 60){
            scoreLottie.setAnimation(R.raw.champion);
        }else if (intPassedScore >= 30){
            scoreLottie.setAnimation(R.raw.confusedface);
        }else if (intPassedScore <= 29){
            scoreLottie.setAnimation(R.raw.angrycloud);
        }
    }

    public void setObservationText(){
        int intPassedScore = Integer.parseInt(passedScore);
        if (intPassedScore >= 60){
            observation.setText("Pefeito!");
            score.setTextColor(Color.rgb(146,208,80));
        }else if (intPassedScore >= 30){
            observation.setText("Estude mais!");
            score.setTextColor(Color.rgb(243,226,186));
        }else if (intPassedScore <= 29){
            observation.setText("Que pena!!!");
            score.setTextColor(Color.rgb(186,78,78));
        }
    }

    public void setBoxColor(){
        int intPassedScore = Integer.parseInt(passedScore);
        if (intPassedScore >= 60){
           pontuationBox.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(146,208,80)));
        }else if (intPassedScore >= 30){
            pontuationBox.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(255,217,102)));
        }else if (intPassedScore <= 29){
            pontuationBox.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(255,107,107)));
        }
    }

    public void goToMainMenu(View v){
        Intent intent = new Intent(ShowFinalScoreActivity.this, GamesActivity.class);
        startActivity(intent);
        finish();
    }
}