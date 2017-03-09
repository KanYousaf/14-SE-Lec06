package com.example.admin.lecture06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    private TextView season_info_tv,display_details_season_tv;
    public static final String[] Season_Details={"Silicon Valley","Silicon Valley is about Richard Henricks and his company named pied piper",
            "Game of Thrones","Game of Thrones is a fantasy drama",
            "Big Bang Theory","Big Bang Theory is scientific sci-fi drama",
            "Prison Break","Prison Break is about the story of Micheal Scofield and his brother",
            "Citizen Khan","Citizen Khan: Mr. Khan , a pakistani citizen living abroad in UK",
            "Divinci Demons","Divinci Demons: Story about Leonardo Divinci",
            "Mr. Robot","Mr. Robot is about hacker's story and how he wants to take revenge on society",
            "House of Cards","House of Cards is about underwood and his compaign to become president of USA",
            "Sherlock Holmes","Sherlock Holmes: Detective to solve crimes"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        season_info_tv=(TextView)this.findViewById(R.id.display_info);
        display_details_season_tv=(TextView)this.findViewById(R.id.details_season);

        //get data from MainActivity
        Intent intent=getIntent();
        String season_name_received=intent.getExtras().getString("season_name");
        Float season_rate_value=intent.getExtras().getFloat("season_rating");

        season_info_tv.setText("The Season Selected is: " + season_name_received+
                            ": rating is :"+season_rate_value);
        for(int i=0 ; i< Season_Details.length; i+=2){
            if(Season_Details[i].equals(season_name_received)){
                display_details_season_tv.setText(Season_Details[i+1]);
            }
        }

    }
}
