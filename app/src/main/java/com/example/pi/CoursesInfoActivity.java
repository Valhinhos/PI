package com.example.pi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CoursesInfoActivity extends AppCompatActivity {
    //String text="Cursos do Senac";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_information);
    }
    public void tecnicoTI (View v) {
        Intent tecnico_TI = new Intent(this, InfoTiActivity.class);
        startActivity(tecnico_TI);
    }
    public void tecnicoEN (View v) {
        Intent tecnico_EN = new Intent(this, InfoEnfActivity.class);
        startActivity(tecnico_EN);
    }
    public void tecnicoagro (View v) {
        Intent tecnico_agro = new Intent(this, InfoAgroActivity.class);
        startActivity(tecnico_agro);
    }
    public void tecnicoadm (View v) {
        Intent tecnico_adm = new Intent(this,InfoAdmActivity.class);
        startActivity(tecnico_adm);
    }

    public void tecnicorh (View v) {
        Intent tecnico_rh = new Intent(this,InfoRhActivity.class);
        startActivity(tecnico_rh);
    }
    public void tecnicosegtrabalho (View v) {
        Intent tecnico_segtrabalho = new Intent(this, InfoSegActivity.class);
        startActivity(tecnico_segtrabalho);
    }
    public void tecnicomarketing (View v) {
        Intent tecnico_marketing = new Intent(this, InfoMarkActivity.class);
        startActivity(tecnico_marketing);
    }
    public void tecnicoest (View v) {
        Intent tecnico_est = new Intent(this, InfoEstActivity.class);
        startActivity(tecnico_est);
    }
    public void tecnicolog (View v) {
        Intent tecnico_log = new Intent(this, InfoLogActivity.class);
        startActivity(tecnico_log);
    }
}