package com.example.pi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.pi.models.MessageDialog;

public class CreditsActivity extends AppCompatActivity {

    String updateChanges = "-Corrigido erro ao adicionar uma foto de perfil pela primeira vez \n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_icons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sobre) {
            showUpDialogMessage("Mudanças da versão atual \n" + updateChanges, "Sobre o app");

        }
        return super.onOptionsItemSelected(item);
    }

    public void showUpDialogMessage(String txt, String title) {
        MessageDialog messageDialog = new MessageDialog(txt, title);
        messageDialog.show(getSupportFragmentManager(), "mensagem");
    }
}