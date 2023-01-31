package com.example.pi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pi.models.DatabaseRA;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    CheckBox verSenha;
    private EditText emailet;
    private EditText password;
    private Button login, entrar;
    private DatabaseRA dataBaseHelper;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verSenha = findViewById(R.id.ver_senha);
        dataBaseHelper = new DatabaseRA(LoginActivity.this);
        emailet = findViewById(R.id.edit_ra);
        password = findViewById(R.id.edit_senha);
        login = findViewById(R.id.bt_entrar);
        auth = FirebaseAuth.getInstance();

        verSenha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setBackgroundColor(Color.rgb(39,69,123));
                String txt_email = emailet.getText().toString() + "@senacminas.edu.br";
                ///we will use the ra to store in sqllite and further upload the project with ra and use the ra reference to grant the user delete only his/her project
                String text_password = password.getText().toString();
                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(text_password)){
                    Toast.makeText(LoginActivity.this, "Um dos campos está vazio", Toast.LENGTH_SHORT).show();
                }else{
                    loginUser(txt_email, text_password);

                }
            }
        });
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String ra = emailet.getText().toString();
                dataBaseHelper.insertData(ra);
                Toast.makeText(LoginActivity.this, "Login feito", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, MainIconsActivity.class);
                intent.putExtra("keyra", ra);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "A senha ou RA estão incorretos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void registerScreen(View v){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void enterWithoutUser(View v){
        String ra = "empty";
        dataBaseHelper.insertData(ra);
        Intent intent = new Intent(LoginActivity.this, MainIconsActivity.class);
        intent.putExtra("keyra", "empty");
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        login.setBackgroundColor(Color.rgb(68,114,195));
    }
}