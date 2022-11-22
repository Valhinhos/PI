package com.example.pi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class uploadImage extends AppCompatActivity {

    Button uploadImagebt;
    EditText projectName;
    EditText professorName;
    EditText projectResume;
    EditText projectContact;

    private static final int IMAGE_REQUEST = 2;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        projectName = findViewById(R.id.nomeprojetoet);
        professorName = findViewById(R.id.professoret);
        projectResume = findViewById(R.id.informationprojectet);
        projectContact = findViewById(R.id.contatoprojetoet);
        uploadImagebt = findViewById(R.id.postarprojetobt);



        uploadImagebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean projectNameCheck = projectName.getText().toString().matches("");
                Boolean professorNameCheck = professorName.getText().toString().matches("");
                Boolean projectResumeCheck = projectResume.getText().toString().matches("");
                Boolean projectContactCheck = projectContact.getText().toString().matches("");
                if (projectNameCheck == true || professorNameCheck == true || projectResumeCheck == true || projectContactCheck == true){
                    Toast.makeText(uploadImage.this, "Um ou mais campos estao vazios", Toast.LENGTH_SHORT).show();
                }else{
                    openImage();
                }
            }
        });
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK) {
            imageUri = data.getData();
            uploadImage();
        }
    }
    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Upload");
        pd.show();

        if (imageUri != null){
            String imageName = System.currentTimeMillis() + "." + getFileExtension(imageUri);

            ///storage the image
            StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("uploads").child(imageName);
            fileRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    ///store the name of the file on the database to after retrieving
                    FirebaseDatabase.getInstance().getReference().child("imagesnames").child("id" + System.currentTimeMillis()).setValue(imageName);
                    FirebaseDatabase.getInstance().getReference().child("nomesdeprojetos").child("id" + System.currentTimeMillis()).setValue(projectName.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("professores").child("id" + System.currentTimeMillis()).setValue(professorName.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("contatos").child("id" + System.currentTimeMillis()).setValue(projectContact.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("resumodeprojetos").child("id" + System.currentTimeMillis()).setValue(projectResume.getText().toString());
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            Log.d("DownloadUrl", url);
                            pd.dismiss();
                            Toast.makeText(uploadImage.this, "O projeto foi postado", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    private void storageImagename() {
    }

    private String getFileExtension(Uri imageUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }
}