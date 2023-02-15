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
    String passedName, passedScore, totalQuestions;
    LottieAnimationView scoreLottie;
    int calcTQH, calcTQA,calcTQL;

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
        totalQuestions = getIntent().getStringExtra("keytotalquestions");
        calcTQH = Integer.parseInt(totalQuestions) / 100 *  60;
        calcTQA = Integer.parseInt(totalQuestions) / 100 *  60;
        calcTQL = Integer.parseInt(totalQuestions) / 100 *  60;

        score.setText(passedScore);

        addLottieAnimation();
        setObservationText();
        setBoxColor();
        Toast.makeText(this, "score: " + passedScore + "total q: " + totalQuestions, Toast.LENGTH_SHORT).show();

    }

    public void addLottieAnimation(){
        int intPassedScore = Integer.parseInt(passedScore);
        if (intPassedScore >= calcTQH){
            scoreLottie.setAnimation(R.raw.champion);
        }else if (intPassedScore >= calcTQA){
            scoreLottie.setAnimation(R.raw.confusedface);
        }else if (intPassedScore <= calcTQL){
            scoreLottie.setAnimation(R.raw.angrycloud);
        }
    }

    public void setObservationText(){
        int intPassedScore = Integer.parseInt(passedScore);
        if (intPassedScore >= calcTQH){
            observation.setText("Pefeito!");
            score.setTextColor(Color.rgb(146,208,80));
        }else if (intPassedScore >= calcTQA){
            observation.setText("Estude mais!");
            score.setTextColor(Color.rgb(243,226,186));
        }else if (intPassedScore <= 3){
            observation.setText("Que pena!!!");
            score.setTextColor(Color.rgb(186,78,78));
        }
    }

    public void setBoxColor(){
        int intPassedScore = Integer.parseInt(passedScore);
        if (intPassedScore >= calcTQH){
           pontuationBox.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(146,208,80)));
        }else if (intPassedScore >= calcTQA){
            pontuationBox.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(255,217,102)));
        }else if (intPassedScore <= calcTQL){
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