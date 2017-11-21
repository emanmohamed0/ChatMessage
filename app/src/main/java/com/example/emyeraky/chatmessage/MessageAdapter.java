package com.example.emyeraky.chatmessage;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Emy Eraky on 11/7/2017.
 */

public class MessageAdapter extends ArrayAdapter<FriendlyMessage> {
    TextView messageTextView;

    public MessageAdapter(Context context, int resource, List<FriendlyMessage> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FriendlyMessage message = getItem(position);


        if (message.getName().equals(MainActivity.mUsername)) {
//                if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
//            messageTextView.setBackgroundResource(R.color.colorchat);
        } else {
//                if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message_user, parent, false);

        }
        ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        TextView authorTextView = (TextView) convertView.findViewById(R.id.nameTextView);
        ImageView profilephoto = (ImageView) convertView.findViewById(R.id.profile_image);

        boolean isPhoto = message.getPhotoUrl() != null;
        if (isPhoto) {
            messageTextView.setVisibility(View.GONE);
            photoImageView.setVisibility(View.VISIBLE);
            Glide.with(photoImageView.getContext())
                    .load(message.getPhotoUrl())
                    .into(photoImageView);
        } else {
            messageTextView.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.GONE);
            messageTextView.setText(message.getText());
        }
        authorTextView.setText(message.getName());

        Glide.with(profilephoto.getContext())
                .load(message.getProfilphotoUrl())
                .into(profilephoto);
        return convertView;
    }
}

