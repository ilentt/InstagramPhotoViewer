package com.ilenlab.ilentt.instagramphotoviewer.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilenlab.ilentt.instagramphotoviewer.R;
import com.ilenlab.ilentt.instagramphotoviewer.activities.CommentActivity;
import com.ilenlab.ilentt.instagramphotoviewer.activities.VideoActivity;
import com.ilenlab.ilentt.instagramphotoviewer.models.PhotoModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ADMIN on 3/14/2016.
 */
public class PhotoAdapter extends ArrayAdapter<PhotoModel> {
    public PhotoAdapter(Context context, List<PhotoModel> photoModels) {

        super(context, android.R.layout.simple_list_item_1, photoModels);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewContainer viewContainer;
        final PhotoModel photoModel = getItem(position);
        if(convertView == null) {
            viewContainer = new ViewContainer();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);

            viewContainer.user = (TextView) convertView.findViewById(R.id.tvUsername);
            viewContainer.avatar = (ImageView) convertView.findViewById(R.id.ivAvatar);
            viewContainer.time = (TextView) convertView.findViewById(R.id.tvTime);
            viewContainer.photo = (ImageView) convertView.findViewById(R.id.ivPhoto);
            viewContainer.caption = (TextView) convertView.findViewById(R.id.tvCaption);
            viewContainer.comment = (TextView) convertView.findViewById(R.id.tvComment);
            viewContainer.comments = (TextView) convertView.findViewById(R.id.tvComments);
            viewContainer.like = (TextView) convertView.findViewById(R.id.tvLike);
            viewContainer.video = (ImageView) convertView.findViewById(R.id.ivVideo);

            convertView.setTag(viewContainer);
        } else {
            viewContainer = (ViewContainer) convertView.getTag();
        }

        if(photoModel.caption != null) {
            viewContainer.caption.setText(Html.fromHtml(photoModel.caption));
        }

        // clear out the ImageView
        viewContainer.photo.setImageResource(0);
        viewContainer.like.setText("" + photoModel.likes + " likes");
        viewContainer.user.setText(photoModel.username);
        Picasso.with(getContext()).load(photoModel.imageUrl).into(viewContainer.photo);
        Picasso.with(getContext()).load(photoModel.avatar).into(viewContainer.avatar);

        if(photoModel.videoUrl == null) {
            viewContainer.video.setVisibility(View.INVISIBLE);
        } else {
            viewContainer.video.setVisibility(View.VISIBLE);
            viewContainer.video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), VideoActivity.class);
                    intent.putExtra("videoUrl", photoModel.videoUrl);
                    getContext().startActivity(intent);
                }
            });
        }

        int count = photoModel.commentModels.size();
        if(count > 1) {
            viewContainer.comment.setText(Html.fromHtml(photoModel.commentModels.get(count - 1).comment + "<br>" +
            photoModel.commentModels.get(count - 2).comment));
        } else if(count == 1) {
            viewContainer.comment.setText(Html.fromHtml(photoModel.commentModels.get(count - 1).comment));
        }

        // show all comment
        viewContainer.comments.setText("View all " + photoModel.comments + " comments");
        viewContainer.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CommentActivity.class);
                intent.putExtra("photoId", photoModel.id);
                intent.putExtra("profile", photoModel.avatar);
                intent.putExtra("caption", photoModel.caption);
                intent.putExtra("time", photoModel.time);
                getContext().startActivity(intent);
            }
        });

        // setup the display time
        String timeString = DateUtils.getRelativeTimeSpanString(photoModel.time * 1000,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        int index = timeString.indexOf(' ');
        String shortTimeString = timeString.substring(0, index + 2);
        viewContainer.time.setText(shortTimeString);

        return convertView;
    }

    private static class ViewContainer {
        ImageView avatar;
        TextView caption;
        TextView user;
        TextView time;
        ImageView photo;
        TextView like;
        TextView comments;
        TextView comment;
        ImageView video;
    }
}
