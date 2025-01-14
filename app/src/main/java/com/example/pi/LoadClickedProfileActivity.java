package com.example.pi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class LoadClickedProfileActivity extends AppCompatActivity {

    String userName = "", userRa = "", userCourses = "", userStatus = "", userProfilePicture = "", userID = "";
    TextView userNametv, userCousestv, userStatustv;
    ImageView userProfilePictureiv;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_clicked_profile);

        checkPassedValues();
        connectViews();
        setValuesonViews();
    }

    public void checkPassedValues(){
        if (getIntent().getBooleanExtra("keyusername", false) == true){
            userName = "None";
        }else{
            userName = getIntent().getStringExtra("keyusername");
        }

        if (getIntent().getBooleanExtra("keyuserra", false) == true){
            userRa = "None";
        }else{
            userRa = getIntent().getStringExtra("keyuserra");
        }

        if (getIntent().getBooleanExtra("keyusercourses", false) == true){
            userCourses = "None";
        }else{
            userCourses = getIntent().getStringExtra("keyusercourses");
        }

        if (getIntent().getBooleanExtra("keyuserprofilepicture", false) == true){
            userProfilePicture = "None";
        }else{
            userProfilePicture = getIntent().getStringExtra("keyuserprofilepicture");
        }

        if (getIntent().getBooleanExtra("keyuserstats", false) == true){
            userStatus = "None";
        }else{
            userStatus = getIntent().getStringExtra("keyuserstats");
        }

        if (getIntent().getBooleanExtra("keyuserid", false) == true){
            userID = "None";
        }else{
            userID = getIntent().getStringExtra("keyuserid");
        }
    }

    public void connectViews(){
        userNametv = findViewById(R.id.studentnametextviewloadclickedprofile);
        userCousestv = findViewById(R.id.studentcoursestextviewloadclickedprofile);
        userStatustv = findViewById(R.id.studentstatstextviewloadclickedprofile);
        userProfilePictureiv = findViewById(R.id.studentpictureimageviewloadclickedprofile);
    }

    public Bitmap rotateBitMap(Bitmap bitmap){

        Matrix matrix = new Matrix();

        matrix.postRotate(90);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

        return  rotatedBitmap;

    }

    public void setValuesonViews(){
        userNametv.setText(userName);
        userCousestv.setText("cursos: " +userCourses);
        userStatustv.setText("status: " + userStatus);
//        pegar o userid
        storageReference = FirebaseStorage.getInstance().getReference("userspictures/" + userRa + userID + "/" + userProfilePicture);
        try {
            File localfile = File.createTempFile("tempfile", ".png");
            storageReference.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            Bitmap rotatedBitmap = rotateBitMap(bitmap);
                            userProfilePictureiv.setImageBitmap(rotatedBitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            userProfilePictureiv.setImageResource(R.drawable.unknownprofilepicture);
//                            Toast.makeText(LoadClickedProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}