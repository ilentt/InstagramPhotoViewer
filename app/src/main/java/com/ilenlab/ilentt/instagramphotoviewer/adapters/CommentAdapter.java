package com.ilenlab.ilentt.instagramphotoviewer.adapters;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilenlab.ilentt.instagramphotoviewer.R;
import com.ilenlab.ilentt.instagramphotoviewer.models.CommentModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ADMIN on 3/14/2016.
 */
public class CommentAdapter extends ArrayAdapter<CommentModel> {
    public CommentAdapter(Context context, List<CommentModel> commentModels) {
        super(context, android.R.layout.simple_list_item_1, commentModels);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewContainer viewContainer;

        final CommentModel commentModel = getItem(position);

        if(convertView == null) {
            viewContainer = new ViewContainer();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
            viewContainer.avatar = (ImageView) convertView.findViewById(R.id.ivAvatar);
            viewContainer.time = (TextView) convertView.findViewById(R.id.tvComTime);
            viewContainer.comment = (TextView) convertView.findViewById(R.id.tvComment);
            convertView.setTag(viewContainer);
        } else {
            viewContainer = (ViewContainer) convertView.getTag();
        }

        Picasso.with(getContext()).load(commentModel.avatar).into(viewContainer.avatar);
        viewContainer.comment.setText(Html.fromHtml(commentModel.comment));

        // setup and display time
        String timeString = DateUtils.getRelativeTimeSpanString(commentModel.time * 1000,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();

        viewContainer.time.setText(timeString);

        return convertView;
    }

    private static class ViewContainer {
        ImageView avatar;
        TextView time;
        TextView comment;
    }
}
