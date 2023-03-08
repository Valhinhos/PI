package com.example.pi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class CoursesInfoActivity extends AppCompatActivity {

    private LinearLayout tiLayout, enLayout, agroLayout, admLayout, rhLayout, segLayout, markLayout, estLayout, logLayout;
    private Boolean displayVisibilityTI = false, displayVisibilityEN = false, displayVisibilityAgro = false, displayVisibilityAdm = false;
    private Boolean displayVisibilityRH = false, displayVisibilitySeg = false, displayVisibilityMark = false, displayVisibilityEst = false, displayVisibilityLog = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_information);

        findView();
    }

    public void findView(){
        tiLayout = findViewById(R.id.tilayout);
        enLayout = findViewById(R.id.enlayout);
        agroLayout = findViewById(R.id.agrolayout);
        admLayout = findViewById(R.id.admlayout);
        rhLayout = findViewById(R.id.rhlayout);
        segLayout = findViewById(R.id.seglayout);
        markLayout = findViewById(R.id.marklayout);
        estLayout = findViewById(R.id.estlayout);
        logLayout = findViewById(R.id.loglayout);
    }

    public void tecnicoTI (View v) {
        if (displayVisibilityTI){
            displayVisibilityTI = false;
            tiLayout.setVisibility(View.GONE);
        }else {
            displayVisibilityTI = true;
            tiLayout.setVisibility(View.VISIBLE);
        }
    }

    public void tecnicoEN (View v) {
        if (displayVisibilityEN){
            displayVisibilityEN = false;
            enLayout.setVisibility(View.GONE);
        }else {
            displayVisibilityEN = true;
            enLayout.setVisibility(View.VISIBLE);
        }
    }

    public void tecnicoagro (View v) {
        if (displayVisibilityAgro){
            displayVisibilityAgro = false;
            agroLayout.setVisibility(View.GONE);
        }else {
            displayVisibilityAgro = true;
            agroLayout.setVisibility(View.VISIBLE);
        }
    }

    public void tecnicoadm (View v) {
        if (displayVisibilityAdm){
            displayVisibilityAdm = false;
            admLayout.setVisibility(View.GONE);
        }else {
            displayVisibilityAdm = true;
            admLayout.setVisibility(View.VISIBLE);
        }
    }

    public void tecnicorh (View v) {
        if (displayVisibilityRH){
            displayVisibilityRH = false;
            rhLayout.setVisibility(View.GONE);
        }else {
            displayVisibilityRH = true;
            rhLayout.setVisibility(View.VISIBLE);
        }
    }

    public void tecnicosegtrabalho (View v) {
        if (displayVisibilitySeg){
            displayVisibilitySeg = false;
            segLayout.setVisibility(View.GONE);
        }else {
            displayVisibilitySeg = true;
            segLayout.setVisibility(View.VISIBLE);
        }
    }

    public void tecnicomarketing (View v) {
        if (displayVisibilityMark){
            displayVisibilityMark = false;
            markLayout.setVisibility(View.GONE);
        }else {
            displayVisibilityMark = true;
            markLayout.setVisibility(View.VISIBLE);
        }
    }

    public void tecnicoest (View v) {
        if (displayVisibilityEst){
            displayVisibilityEst = false;
            estLayout.setVisibility(View.GONE);
        }else {
            displayVisibilityEst = true;
            estLayout.setVisibility(View.VISIBLE);
        }
    }

    public void tecnicolog (View v) {
        if (displayVisibilityLog){
            displayVisibilityLog = false;
            logLayout.setVisibility(View.GONE);
        }else {
            displayVisibilityLog = true;
            logLayout.setVisibility(View.VISIBLE);
        }
    }
}