package com.ilenlab.ilentt.instagramphotoviewer.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ADMIN on 3/14/2016.
 */
public class CommentModel implements Serializable {

    public String username;
    public String avatar;
    public String comment;
    public long time;

    public CommentModel(JSONObject jsonObject) {
        try {
            // markup name account
            this.username = "<b><font color='#3F729B'>" + jsonObject.getJSONObject("from").getString("username") + "</font></b> ";
            this.avatar = jsonObject.getJSONObject("from").getString("profile_picture");
            this.comment = this.username + jsonObject.getString("text");
            this.time = jsonObject.getLong("created_time");
        }catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<CommentModel> fromJSONArray(JSONArray jsonArray) {
        ArrayList<CommentModel> comments = new ArrayList<>();
        for(int i =0; i < jsonArray.length(); i++) {
            try {
                comments.add(new CommentModel(jsonArray.getJSONObject(i)));
            }catch(JSONException e) {
                e.printStackTrace();
            }
        }
        return comments;
    }
}
