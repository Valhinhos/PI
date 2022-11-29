package com.example.pi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pi.models.DabaseRA;
import com.example.pi.models.DataBaseHelper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailet;
    private EditText password;
    private Button login;
    private DabaseRA dataBaseHelper;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dataBaseHelper = new DabaseRA(LoginActivity.this);
        emailet = findViewById(R.id.edit_ra);
        password = findViewById(R.id.edit_senha);
        login = findViewById(R.id.bt_entrar);

        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                startActivity(new Intent(LoginActivity.this, MainIconsActivity.class));
                finish();
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
        startActivity(new Intent(LoginActivity.this, MainIconsActivity.class));
        finish();
    }
}