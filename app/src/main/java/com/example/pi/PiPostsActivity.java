package com.example.pi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pi.adapters.ImagesAdapter;
import com.example.pi.models.DatabaseRA;
import com.example.pi.models.PostsRecyclerViewInterface;
import com.example.pi.models.ProjectInformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class PiPostsActivity extends AppCompatActivity implements PostsRecyclerViewInterface {

    RecyclerView recyclerView;
    ArrayList<ProjectInformation> list;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ImagesAdapter adapter;
    DatabaseRA myDB;
    Boolean deleteButtonPressed = false;
    String passedUserName, passedRa;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PiPostsActivity.this, MainIconsActivity.class);
        intent.putExtra("keyusername", passedUserName);
        intent.putExtra("keyra", passedRa);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pi_posts);

        if (getIntent().getBooleanExtra("keyusername", false) == true){
            passedUserName = "None";
        }else{
            passedUserName = getIntent().getStringExtra("keyusername");
        }

        if (getIntent().getBooleanExtra("keyra", false) == true){
            passedRa = "None";
        }else{
            passedRa = getIntent().getStringExtra("keyra");
        }

        recyclerView = findViewById(R.id.recyclerviewpi);
        databaseReference = FirebaseDatabase.getInstance().getReference("projects");
        storageReference = FirebaseStorage.getInstance().getReference("uploads/");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ImagesAdapter(this, list, passedUserName, passedRa);
        recyclerView.setAdapter(adapter);
        myDB = new DatabaseRA(this);


        if (passedUserName.equals("")){
            Toast.makeText(this, passedUserName, Toast.LENGTH_SHORT).show();
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ProjectInformation projectInformation = dataSnapshot.getValue(ProjectInformation.class);
                    list.add(projectInformation);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void postarProjeto(View v){
        Intent intent = new Intent(PiPostsActivity.this, ProjectsUploadActivity.class);
        intent.putExtra("keyusername", passedUserName);
        intent.putExtra("keyra", passedRa);
        startActivity(intent);
    }

    public String getRaFromDB(){
        Cursor res = myDB.getAllData();
        if (res.getCount() == 0){
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append(res.getString(0));
        }
        String ra_text = buffer.toString();
        return ra_text;
    }
//    codigo que vai ser usado para alterar valores
    public void changeProjectName(View v){
        FirebaseDatabase.getInstance().getReference("projects").child("id1671392851600").child("projectName").setValue("aprovado");
    }

    @Override
    public void onItemClick(int position) {

    }
}