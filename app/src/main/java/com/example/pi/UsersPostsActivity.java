package com.example.pi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pi.adapters.userPostAdapter;
import com.example.pi.models.PostsRecyclerViewInterface;
import com.example.pi.models.UserInformation;
import com.example.pi.models.userPost;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class UsersPostsActivity extends AppCompatActivity implements PostsRecyclerViewInterface {

    String passedUserName = "None", passedRa = "None", passedUserID = "None", date, profilePictureString = "None", userCourses = "None", passedUsersStats = "None", imageID = "None";
    RecyclerView recyclerView;
    ArrayList<userPost> list;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    userPostAdapter adapter;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    TextView usernameTv;
    EditText postContent;
    ImageView userProfilePictureIv, imagePreview;

    private static final int IMAGE_REQUEST = 2;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_posts);

        recyclerView = findViewById(R.id.recyclerviewposts);
        databaseReference = FirebaseDatabase.getInstance().getReference("usersposts/");
        storageReference = FirebaseStorage.getInstance().getReference("postsuploads/");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        calendar = Calendar.getInstance();

        passedUserName = getIntent().getStringExtra("keyusername");
        passedRa = getIntent().getStringExtra("keyra");
        passedUserID = getIntent().getStringExtra("keyuserid");
        passedUsersStats = getIntent().getStringExtra("keyuserstats");

        usernameTv = findViewById(R.id.usernametv);
        postContent = findViewById(R.id.postcontentet);
        userProfilePictureIv = findViewById(R.id.userpictureiv);
        imagePreview = findViewById(R.id.imagePreview);

        imagePreview.setVisibility(View.GONE);

        checkPassedValues();

        adapter = new userPostAdapter(this, list, passedUserName, passedRa, this);
        recyclerView.setAdapter(adapter);

        usernameTv.setText(passedUserName);

        getTheUsers();
        DisplayPosts();
    }

    public void checkPassedValues(){
        if (getIntent().getBooleanExtra("keyusername", false) == true) {
            passedUserName = "None";
        } else {
            passedUserName = getIntent().getStringExtra("keyusername");
        }

        if (getIntent().getBooleanExtra("keyra", false) == true) {
            passedRa = "None";
        } else {
            passedRa = getIntent().getStringExtra("keyra");
        }

        if (getIntent().getBooleanExtra("keyuserid", false) == true) {
            passedUserID = "None";
        } else {
            passedUserID = getIntent().getStringExtra("keyuserid");
        }

        if (getIntent().getBooleanExtra("keyuserstats", false) == true) {
            passedUsersStats = "None";
        } else {
            passedUsersStats = getIntent().getStringExtra("keyuserstats");
        }
    }

    private void DisplayPosts() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    userPost userPost = dataSnapshot.getValue(userPost.class);
                    updateUserCourse(userPost.getUserID(), userPost.getPostID());
                    list.add(userPost);
                    for (userPost up : list) {
                        if (up.getUserName().equals(passedUserName)) {
                            up.setUserProfilePicture(profilePictureString);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void createPost(View v) {
        if (postContent.getText().toString().matches("")) {
            Toast.makeText(this, "Você não pode fazer um post vazio", Toast.LENGTH_SHORT).show();
        } else {
            String postContentString = postContent.getText().toString();

            String postid = String.valueOf(System.currentTimeMillis());
            dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm");
            date = dateFormat.format(calendar.getTime());
            userPost userPost = new userPost(passedUserName, date, profilePictureString, postContentString, userCourses, passedRa, passedRa + postid, passedUserID, passedUsersStats, imageID);
            FirebaseDatabase.getInstance().getReference().child("usersposts/").child(passedRa + postid).setValue(userPost);
            postContent.setText("");
            Toast.makeText(this, "Sua publicação foi postada", Toast.LENGTH_SHORT).show();
            imagePreview.setVisibility(View.GONE);
        }
    }

    private void getTheUsers() {
        FirebaseDatabase.getInstance().getReference("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    UserInformation userInformation = snapshot1.getValue(UserInformation.class);
//                    verifica se o nome e ra do usuario é igual a de um dos posts, se for igual ele vai permitir excluir
                    if (passedUserName.equals(userInformation.getUserName()) && passedRa.equals(userInformation.getUserRa())) {
                        profilePictureString = userInformation.getProfilePicture();
                        userCourses = userInformation.getCourses();
                        passedUserName = userInformation.getUserName();
                        passedUsersStats = userInformation.getStatus();
//                        studentStatus.setText("status: " + userInformation.getStatus());
                    }

                    storageReference = FirebaseStorage.getInstance().getReference("userspictures/" + passedRa + passedUserID + "/" + userInformation.getProfilePicture());
                    try {
                        File localfile = File.createTempFile("tempfile", ".png");
                        storageReference.getFile(localfile)
                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                                        userProfilePictureIv.setImageBitmap(bitmap);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(UsersPostsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void deletePost(View v) {
        for (userPost up : list) {
            if (up.getUserName().equals(passedUserName) && up.getUserRa().equals(passedRa)) {
                FirebaseDatabase.getInstance().getReference("usersposts/").child(up.getPostID()).removeValue();
            }
        }
    }

    public void onPostLongClick(int position) {
        list.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(UsersPostsActivity.this, LoadClickedProfileActivity.class);
        intent.putExtra("keyusername", list.get(position).getUserName());
        intent.putExtra("keyuserra", list.get(position).getUserRa());
        intent.putExtra("keyusercourses", list.get(position).getUserCourses());
        intent.putExtra("keyuserprofilepicture", list.get(position).getUserProfilePicture());
        intent.putExtra("keyuserid", list.get(position).getUserID());
        intent.putExtra("keyuserstats", list.get(position).getUserStats());
        startActivity(intent);
    }

    public void updateUserCourse(String userId, String postId) {
        FirebaseDatabase.getInstance().getReference("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    UserInformation userInformation = snapshot1.getValue(UserInformation.class);
                    if (userInformation.getUserId().equals(userId)){
                        FirebaseDatabase.getInstance().getReference().child("usersposts/").child(postId).child("userCourses").setValue(userInformation.getCourses());
                        FirebaseDatabase.getInstance().getReference().child("usersposts/").child(postId).child("userName").setValue(userInformation.getUserName());
                        FirebaseDatabase.getInstance().getReference().child("usersposts/").child(postId).child("userStats").setValue(userInformation.getStatus());
                        break;
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void openImage(View v) {
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
            imagePreview.setVisibility(View.VISIBLE);
            imagePreview.setImageURI(imageUri);
            uploadImage();
        }
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Upload");
        pd.show();

        if (imageUri != null){
            imageID = String.valueOf(System.currentTimeMillis());
            ///the ra will be picked from here using sql lite and inserted in a string to be put inside the projectinformation object

            ///storage the image
//            StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("uploads").child(imageName + "." + getFileExtension(imageUri));
            StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("postImage").child(imageID);
            fileRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    ///store the name of the file on the database to after retrieving
                    String projectid = "id" + imageID;
                    ///storage the project id to exclude after #implement
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            Log.d("DownloadUrl", url);
                            pd.dismiss();
                        }
                    });
                }
            });
        }
    }
}
