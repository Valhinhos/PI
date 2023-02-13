package com.example.pi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MapActivity extends AppCompatActivity {
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        VideoView simpleVideoView = findViewById(R.id.mapvideoview);

        if (mediaController == null) {
            //creates a MediaController object if one is not present
            mediaController = new MediaController(MapActivity.this);
            simpleVideoView.setMediaController(mediaController);
            mediaController.setAnchorView(simpleVideoView);
        }

        simpleVideoView.setMediaController(mediaController);
        simpleVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.map_video));

        simpleVideoView.start();
        simpleVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(getApplicationContext(),
                        "Opa, desculpe, aconteceu um erro, tente novamente!",
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}