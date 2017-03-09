package com.example.admin.lecture06;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 3/9/2017.
 */

public class CustomAdapter extends ArrayAdapter<String> {
    private ArrayList<String> season_names;
    private ArrayList<Integer> season_images;

//    private String[] season_names;
//    private int[] season_images;
//    public CustomAdapter(Context context, String[] season_names, int[] season_images) {
//        super(context, R.layout.custom_layout, season_names);
//        this.season_names=season_names;
//        this.season_images=season_images;
//    }

    public CustomAdapter(Context context, ArrayList<String> season_names, ArrayList<Integer> season_images) {
        super(context, R.layout.custom_layout, season_names);
        this.season_names=season_names;
        this.season_images=season_images;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        //layout inflater for providing view
        rowView=layoutInflater.inflate(R.layout.custom_layout, parent ,false);

        ImageView seasonImageView=(ImageView)rowView.findViewById(R.id.display_season_image);
        TextView seasonNameTextView=(TextView)rowView.findViewById(R.id.display_season_name);
        final TextView seasonRateTextView=(TextView)rowView.findViewById(R.id.display_season_rate);
        final RatingBar seasonRateBar=(RatingBar)rowView.findViewById(R.id.season_rate);

        //setting values according to arrays
//        seasonImageView.setImageResource(season_images[position]);
//        seasonNameTextView.setText(season_names[position]);

        //setting values according to arraylists
        seasonImageView.setImageResource(season_images.get(position));
        seasonNameTextView.setText(season_names.get(position));

        seasonRateTextView.setText(String.valueOf(seasonRateBar.getRating()));
        seasonRateBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                seasonRateBar.setRating(rating);
                //display changing rating bar on textview
                seasonRateTextView.setText(String.valueOf(seasonRateBar.getRating()));
            }
        });



        return rowView;
    }
}
