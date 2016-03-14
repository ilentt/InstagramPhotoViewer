package com.ilenlab.ilentt.instagramphotoviewer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ilenlab.ilentt.instagramphotoviewer.R;
import com.ilenlab.ilentt.instagramphotoviewer.adapters.CommentAdapter;
import com.ilenlab.ilentt.instagramphotoviewer.models.CommentModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ADMIN on 3/14/2016.
 */
public class CommentActivity extends AppCompatActivity  {

    public static final String CLIENT_ID = "f5648e5d9fc84485828eb75a5582361a";
    private ArrayList<CommentModel> commentModels;
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comment);

        Intent i = getIntent();
        String photoId = i.getStringExtra("photoId");
        String profile = i.getStringExtra("profile");
        String caption = i.getStringExtra("caption");
        long time = i.getLongExtra("time", 0);

        if(caption != null) {
            TextView tvCaption = (TextView) findViewById(R.id.tvCaption);
            tvCaption.setText(Html.fromHtml(caption));
        }

        ImageView avatar = (ImageView) findViewById(R.id.ivAvatar);
        Picasso.with(this).load(profile).into(avatar);

        TextView tvTime = (TextView) findViewById((R.id.tvTime));

        if (time != 0) {
            String timeString = DateUtils.getRelativeTimeSpanString(time * 1000,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
            tvTime.setText(timeString);
        }

        commentModels = new ArrayList<>();
        commentAdapter = new CommentAdapter(this, commentModels);

        ListView lvComments = (ListView) findViewById(R.id.lvComments);
        lvComments.setAdapter(commentAdapter);

        fetchComments(photoId);
    }

    private void fetchComments(String photoId) {
        String commentUrl = "https://api.instagram.com/v1/media/" + photoId + "/comments?client_id=" + CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(commentUrl, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("data");
                    commentAdapter.addAll(CommentModel.fromJSONArray(jsonArray));
                }catch(JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; this add items to the action bar if it is present;
        getMenuInflater().inflate(R.menu.menu_comment, menu);
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
