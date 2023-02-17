package com.example.pi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.pi.models.DatabaseRA;
import com.example.pi.models.MessageDialog;
import com.example.pi.models.UserInformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainIconsActivity extends AppCompatActivity {
    DatabaseRA myDB;
    DatabaseReference databaseReference;
    Boolean logged = true, canOpenNetworkScreens = false;
    String passedRa = "empty", passedUserName = "None", passedUserID = "None", passedOldProfilePicture = "None", passedStats = "None";
    TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_icons);

        passedRa = getIntent().getStringExtra("keyra");
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        userName = findViewById(R.id.username);

        myDB = new DatabaseRA(this);
        String ra = getRaFromDB();
        if (ra.equals("empty")){
            logged = false;
        }else{
            logged = true;
        }
        getUserFromFB();
    }
    @Override
    protected void onResume() {
        super.onResume();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        getUserFromFB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_icons, menu);
        return true;
    }

    public void openFrequency(View v){
        int id = R.id.iconfrequencia;
        animate(id);
        abrirLink("https://www.mg.senac.br/ambienteacademico/detalheCurso");
    }

    public void openMap(View v){
        int id = R.id.iconmapeamento;
        animate(id);
        Intent intent = new Intent(MainIconsActivity.this, MapActivity.class);
        startActivity(intent);
    }

    public void openAva(View v){
        int id = R.id.iconava;
        animate(id);
        abrirLink("https://ava.mg.senac.br/edu/");
    }

    public void openBiblio(View v){
        int id = R.id.iconbiblioteca;
        animate(id);
        abrirLink("https://pergamum.mg.senac.br/pergamum/biblioteca_s/php/login_usu.php");
    }

    public void openSenacCourses(View v){
        int id = R.id.iconcursosenac;
        animate(id);
        Intent intent = new Intent(MainIconsActivity.this, CoursesInfoActivity.class);
        startActivity(intent);
    }

    public void openCourses(View v){
        int id = R.id.iconcursosdisponiveis;
        animate(id);
        abrirLink("https://www.mg.senac.br/programasenacdegratuidade/vagas.aspx");
    }

    public void openAC(View v){
        int id = R.id.iconaprendizagemcoemrcial;
        animate(id);
        abrirLink("https://www.mg.senac.br/Paginas/aprendizagem-comercial.aspx");
    }

    public void openRedeC(View v){
        int id = R.id.iconredecarreiras;
        animate(id);
        abrirLink("https://www.mg.senac.br/Paginas/rededecarreiras.aspx");
    }

    public void openPI(View v){
        int id = R.id.iconpi;
        animate(id);
            if (logged) {
                if (canOpenNetworkScreens) {
                Intent projint = new Intent(this, PiPostsActivity.class);
                projint.putExtra("keyusername", passedUserName);
                projint.putExtra("keyra", passedRa);
                startActivity(projint);
            }
        }else {
                Toast.makeText(this, "Você deve estar logado para usar esta ferramenta", Toast.LENGTH_SHORT).show();
            }
    }

    public void openCredits(View v){
        int id = R.id.iconcreditos;
        animate(id);
        Intent cred = new Intent(this, CreditsActivity.class);
        startActivity(cred);

    }

    public void abrirLink(String link){
        Uri uri = Uri.parse(link);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    public void openGames(View v){
        int id = R.id.icongame;
        animate(id);
            if (logged) {
                if (canOpenNetworkScreens) {
                Intent intent = new Intent(MainIconsActivity.this, GamesActivity.class);
                intent.putExtra("keyra", passedRa);
                intent.putExtra("keyusername", passedUserName);
                intent.putExtra("keyuseroldprofilepic", passedOldProfilePicture);
                startActivity(intent);
            }
        }else {
                Toast.makeText(this, "Você deve estar logado para usar esta ferramenta", Toast.LENGTH_SHORT).show();
            }
    }

    public void openStudentProfile(View v) {
        int id = R.id.iconprofile;
        animate(id);
        if (logged) {
            if (canOpenNetworkScreens) {
                Intent intent = new Intent(MainIconsActivity.this, UserProfileActivity.class);
            intent.putExtra("keyra", passedRa);
            intent.putExtra("keyusername", passedUserName);
            intent.putExtra("keyuserid", passedUserID);
            intent.putExtra("keyuseroldprofilepic", passedOldProfilePicture);
            startActivity(intent);
        }
        }else {
            Toast.makeText(this, "Você deve estar logado para usar esta ferramenta", Toast.LENGTH_SHORT).show();
        }
    }

    public void openUsersPost(View v){
        int id = R.id.iconposts;
        animate(id);
            if (logged) {
                if (canOpenNetworkScreens) {
                    Intent intent = new Intent(MainIconsActivity.this, UsersPostsActivity.class);
                intent.putExtra("keyra", passedRa);
                intent.putExtra("keyusername", passedUserName);
                intent.putExtra("keyuserid", passedUserID);
                intent.putExtra("keyuserstats", passedStats);
                startActivity(intent);
            }
        } else {
                Toast.makeText(this, "Você deve estar logado para usar esta ferramenta", Toast.LENGTH_SHORT).show();
            }
    }

    public void showUpDialogMessage(String txt, String title){
        MessageDialog messageDialog = new MessageDialog(txt, title);
        messageDialog.show(getSupportFragmentManager(), "mensagem");
    }

    public String getRaFromDB(){
        Cursor res = myDB.getAllData();
        if (res.getCount() == 0){
        }
        StringBuffer buffer = new StringBuffer();
//        while (res.moveToNext()){
//            buffer.append(res.getString(0));
//        }
        res.moveToNext();
        buffer.append(res.getString(0));

        String ra_text = buffer.toString();
        return ra_text;
    }

    public void getUserFromFB(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    UserInformation userInformation = snapshot1.getValue(UserInformation.class);
                    if (userInformation.getUserRa().equals(getRaFromDB())){
                        userName.setText("Bem vindo(a) " +userInformation.getUserName());
                        passedUserName = userInformation.getUserName();
                        passedUserID = userInformation.getUserId();
                        passedOldProfilePicture = userInformation.getOldProfilePicture();
                        passedStats = userInformation.getStatus();
                        canOpenNetworkScreens = true;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void animate(int id){
        YoYo.with(Techniques.Flash)
                .duration(300)
                .repeat(0)
                .playOn(findViewById(id));

    }
}