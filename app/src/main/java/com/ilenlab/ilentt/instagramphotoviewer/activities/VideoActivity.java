package com.ilenlab.ilentt.instagramphotoviewer.activities;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import com.ilenlab.ilentt.instagramphotoviewer.R;

/**
 * Created by ADMIN on 3/14/2016.
 */
public class VideoActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        getSupportActionBar().hide();

        VideoView videoView = (VideoView) findViewById(R.id.vvVideo);

        String url = null;
        if(getIntent().getExtras() != null) {
            url = getIntent().getExtras().getString("videoUrl");
            if(url != null) {
                videoView.setMediaController(new MediaController(this));
                videoView.setOnCompletionListener(this);
                videoView.setVideoURI(Uri.parse(url));
                videoView.start();
            }
        }

        if(url == null) {
            throw new IllegalArgumentException("Must set url extra parameter in intent.");
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        finish();
    }

    // convenience method to show a video
    public static void showRemoteVideo(Context context, String url) {
        Intent i = new Intent(context, VideoActivity.class);
        i.putExtra("url", url);
        context.startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; this add items to the action bar if it is present;
        getMenuInflater().inflate(R.menu.menu_video, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         /*
         * handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long
         * as you specify a parent activity in AndroidManifest.xml
         */
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if(id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
