package com.ilenlab.ilentt.instagramphotoviewer.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ADMIN on 3/14/2016.
 */
public class PhotoModel implements Serializable {
    public String username;
    public String caption;
    public String imageUrl;
    public int imageHeight;
    public String videoUrl;
    public int likes;
    public String avatar;
    public long time;
    public int comments;
    public String id;
    public ArrayList<CommentModel> commentModels;

    public PhotoModel(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getString("id");
            this.username = jsonObject.getJSONObject("user").getString("username");
            this.avatar = jsonObject.getJSONObject("user").getString("profile_picture");
            this.caption = this.username;

            // show caption if available
            if(!jsonObject.isNull("caption")) {
                this.caption += jsonObject.getJSONObject("caption").getString("text");
            }

            this.imageUrl = jsonObject.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
            this.imageHeight = jsonObject.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
            this.likes = jsonObject.getJSONObject("likes").getInt("count");

            /*
             * Only up to 8 comments stored at popular endpoint
             * for all comments, get it from ../media/{media-id}/comments/
             */
            this.comments = jsonObject.getJSONObject("comments").getInt("count");
            JSONArray commentJson = jsonObject.getJSONObject("comments").getJSONArray("data");
            commentModels = new ArrayList<>();
            commentModels.addAll(CommentModel.fromJSONArray(commentJson));

            this.time = jsonObject.getLong("create_time");
            String type = jsonObject.getString("type");
            if(type.equals("video")) {
                this.videoUrl = jsonObject.getJSONObject("videos").getJSONObject("standard_resolution").getString("url");
            }
        }catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<PhotoModel> fromJSONArray(JSONArray jsonArray) {
        ArrayList<PhotoModel> photos = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                photos.add(new PhotoModel(jsonArray.getJSONObject(i)));
            }catch(JSONException e) {
                e.printStackTrace();
            }
        }
        return photos;
    }
}
