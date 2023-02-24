package com.example.pi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.pi.models.DatabaseRA;
import com.example.pi.models.QuestionsLog;
import com.example.pi.models.QuestionsRH;
import com.example.pi.models.QuestionsTI;
import com.example.pi.models.StudentScore;
import com.google.firebase.database.FirebaseDatabase;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener{
    ///declaracao das variaveis
    TextView question, numberQuestions, userNameTV;
    Button answer1,answer2,answer3,answer4;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    String passedUserName = "", passedQuiz, passedRa, totalQuestionsString;

    int score = 0;
    int totalquestions;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ///atribui as views as variaveis
//        myDB = new DatabaseRA(this);
        passedQuiz = getIntent().getStringExtra("keyquiz");
        passedRa = getIntent().getStringExtra("keyra");
        passedUserName = getIntent().getStringExtra("keyusername");

        if (passedQuiz.equals("quizlog")){
            totalquestions = QuestionsLog.question.length;
        }else if (passedQuiz.equals("quizrh")){
            totalquestions = QuestionsRH.question.length;
        }else if (passedQuiz.equals("quizti")){
            totalquestions = QuestionsTI.question.length;
        }

        totalQuestionsString = String.valueOf(totalquestions);

        question = findViewById(R.id.perguntatv);
        answer1 = findViewById(R.id.resposta1);
        answer2 = findViewById(R.id.resposta2);
        answer3 = findViewById(R.id.resposta3);
        answer4 = findViewById(R.id.resposta4);

        numberQuestions = findViewById(R.id.nperguntasdetperguntas);
        linearLayout = findViewById(R.id.layoutdasrespostas);
        userNameTV = findViewById(R.id.usernamequiz);


        userNameTV.setText(passedUserName);

        ///seta o metodo de click nos botoes
        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
        answer4.setOnClickListener(this);
        ///coloca o numero de perguntas do quiz
        numberQuestions.setText(currentQuestionIndex + "/" + totalquestions);
        prog();
        ///carrega as perguntas
        loadNewQuestion();
    }

    private void prog() {
        progressBar = findViewById(R.id.progressbarrhquiz);
        progressBar.setMax(totalquestions);
        progressBar.setProgress(currentQuestionIndex);
    }

    private void loadNewQuestion() {
        prog();
        numberQuestions.setText((currentQuestionIndex +1) + "/" + totalquestions);
//        Toast.makeText(this, String.valueOf(currentQuestionIndex), Toast.LENGTH_SHORT).show();

        if((currentQuestionIndex) == totalquestions){
            finishQuiz();
            return;
        }
//        Toast.makeText(this, String.valueOf(score), Toast.LENGTH_SHORT).show();
        ///seta o texto das perguntas e respostas nos botoes

        if (passedQuiz.equals("quizlog")){
            question.setText(" '" + QuestionsLog.question[currentQuestionIndex] + "'");
            answer1.setText(QuestionsLog.choices[currentQuestionIndex][0]);
            answer2.setText(QuestionsLog.choices[currentQuestionIndex][1]);
            answer3.setText(QuestionsLog.choices[currentQuestionIndex][2]);
            answer4.setText(QuestionsLog.choices[currentQuestionIndex][3]);
        }else if (passedQuiz.equals("quizrh")){
            question.setText(" '" + QuestionsRH.question[currentQuestionIndex] + "'");
            answer1.setText(QuestionsRH.choices[currentQuestionIndex][0]);
            answer2.setText(QuestionsRH.choices[currentQuestionIndex][1]);
            answer3.setText(QuestionsRH.choices[currentQuestionIndex][2]);
            answer4.setText(QuestionsRH.choices[currentQuestionIndex][3]);
        }else if (passedQuiz.equals("quizti")){
            question.setText(" '" + QuestionsTI.question[currentQuestionIndex] + "'");
            answer1.setText(QuestionsTI.choices[currentQuestionIndex][0]);
            answer2.setText(QuestionsTI.choices[currentQuestionIndex][1]);
            answer3.setText(QuestionsTI.choices[currentQuestionIndex][2]);
            answer4.setText(QuestionsTI.choices[currentQuestionIndex][3]);

            if (QuestionsTI.choices[currentQuestionIndex][0].equals("")){
                answer1.setVisibility(View.GONE);
            }else {
                answer1.setVisibility(View.VISIBLE);
            }
            if (QuestionsTI.choices[currentQuestionIndex][1].equals("")){
                answer2.setVisibility(View.GONE);
            }else {
                answer2.setVisibility(View.VISIBLE);
            }
            if (QuestionsTI.choices[currentQuestionIndex][2].equals("")){
                answer3.setVisibility(View.GONE);
            }else {
                answer3.setVisibility(View.VISIBLE);
            }
            if (QuestionsTI.choices[currentQuestionIndex][3].equals("")){
                answer4.setVisibility(View.GONE);
            }else {
                answer4.setVisibility(View.VISIBLE);
            }
        }

        answer1.setBackgroundColor(Color.rgb(255,230,153));
        answer2.setBackgroundColor(Color.rgb(255,230,153));
        answer3.setBackgroundColor(Color.rgb(255,230,153));
        answer4.setBackgroundColor(Color.rgb(255,230,153));

    }

    private void finishQuiz() {
        ///mostra os pontos que a pessoa fez
        String getName = passedUserName;

        if (getName.equals(""))
            getName = "Anônimo";
        String passStatus = "";

        StudentScore studentScore = new StudentScore(getName, score, passedRa);

        String id = "id" + System.currentTimeMillis();

        if (passedQuiz.equals("quizlog")){
            FirebaseDatabase.getInstance().getReference().child("rankinglogquiz").child(id).setValue(studentScore);
        }else if (passedQuiz.equals("quizrh")){
            FirebaseDatabase.getInstance().getReference().child("rankingrhquiz").child(id).setValue(studentScore);
        }else if (passedQuiz.equals("quizti")){
            FirebaseDatabase.getInstance().getReference().child("rankingtiquiz").child(id).setValue(studentScore);
        }

        if(score > totalquestions*0.60){
            passStatus = "Passed";
        }else{
            passStatus = "Failed";
        }
        passScore(score);
    }

    private void restartQuiz() {
        ///da restart no quiz
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }

    public void sair(View v){
        finish();
    }

    @Override
    public void onClick(View view) {
        ///coloca o cor dos botoes de volta para o amarelo
        answer1.setBackgroundColor(Color.rgb(255,230,153));
        answer2.setBackgroundColor(Color.rgb(255,230,153));
        answer3.setBackgroundColor(Color.rgb(255,230,153));
        answer4.setBackgroundColor(Color.rgb(255,230,153));

        Button clickedButton = (Button) view;
        ///se selecionar a resposta certa aumenta o score

        selectedAnswer = clickedButton.getText().toString();
        clickedButton.setBackgroundColor(Color.BLUE);

        if (passedQuiz.equals("quizlog")){
            if(selectedAnswer.equals(QuestionsLog.correctAnswers[currentQuestionIndex])){
                score++;
            }
        }else if (passedQuiz.equals("quizrh")){
            if(selectedAnswer.equals(QuestionsRH.correctAnswers[currentQuestionIndex])){
                score++;
            }
        }else if (passedQuiz.equals("quizti")) {
            if (selectedAnswer.equals(QuestionsTI.correctAnswers[currentQuestionIndex])) {
                score++;
            }
        }
        currentQuestionIndex++;
        loadNewQuestion();
    }
//    public String getRaFromDB(){
//        Cursor res = myDB.getAllData();
//        if (res.getCount() == 0){
//        }
//        StringBuffer buffer = new StringBuffer();
////        while (res.moveToNext()){
////            buffer.append(res.getString(0));
////        }
//        res.moveToNext();
//        buffer.append(res.getString(0));
//
//        String ra_text = buffer.toString();
//        return ra_text;
//    }
//concertar erro no nome usuário apos acabar o quiz

    public void passScore(int score){
        String getName = passedUserName;
        String passedQuizS = passedQuiz;
        String scoreString = String.valueOf(score);
        if (getName.equals(""))
            getName = "Anônimo";
        Intent intent = new Intent(QuizActivity.this, ShowFinalScoreActivity.class);
        intent.putExtra("keyscore", scoreString);
        intent.putExtra("keyname", getName);
        intent.putExtra("keyquiz", passedQuizS);
        restartQuiz();
        startActivity(intent);
    }
}