package com.example.pi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import com.example.pi.models.UserInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private EditText email, password, name;
    DatabaseReference databaseReference;
    private Button register;
    private FirebaseAuth auth;
    CheckBox verSenha;
    int raCount = 0;
    String message = "";
    Boolean canRegister = true;
    ArrayList<String> ras = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.edit_racadastro);
        password = findViewById(R.id.edit_senhacadastro);
        register = findViewById(R.id.cadastrarbt);
        name = findViewById(R.id.edit_nomecadastro);
        verSenha = findViewById(R.id.ver_senhareg);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        verifyExistingra();

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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String verifyRA = email.getText().toString();

                for (String i : ras) {
                    if (i.equals(verifyRA)) {
                        canRegister = false;
                        break;
                    }

                }

                if (canRegister) {
                String txt_email = email.getText().toString() + "@senacminas.edu.br";
                String txt_password = password.getText().toString();
                String txt_name = name.getText().toString();

                String userId = String.valueOf(System.currentTimeMillis());


                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_name)) {
                        Toast.makeText(RegisterActivity.this, "Um dos campos está vazio", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "A senha deve conter mais de 6 caracteres", Toast.LENGTH_SHORT).show();
                } else {
                    UserInformation userInformation = new UserInformation(txt_name, email.getText().toString(), "", "", "", "", userId);
                    FirebaseDatabase.getInstance().getReference().child("users").child(userId).setValue(userInformation);
                    registerUser(txt_email, txt_password);
                }
            }else {
                    Toast.makeText(RegisterActivity.this, "O RA inserido já está registrado, por favor digite um RA diferente", Toast.LENGTH_SHORT).show();
                }
                canRegister = true;
                }
        });
    }

    private void verifyExistingra() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    UserInformation userInformation = snapshot1.getValue(UserInformation.class);
                    ras.add(userInformation.getUserRa());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void registerUser(String txt_email, String txt_password) {
        auth.createUserWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this, "Falha no cadastro!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}