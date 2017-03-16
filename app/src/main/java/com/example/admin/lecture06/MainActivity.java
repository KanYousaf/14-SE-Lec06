package com.example.admin.lecture06;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> season_names_AL;
    private ArrayList<Integer> season_images_AL;
    private CustomAdapter myOwnAdapter;
    private ArrayAdapter<String> adapter;
    private ListView myFavSeasonList;
    private static final String[] myFavSeasonArray = {
            "Silicon Valley",
            "Game of Thrones",
            "Big Bang Theory",
            "Prison Break",
            "Citizen Khan",
            "Divinci Demons",
            "Mr. Robot",
            "House of Cards",
            "Sherlock Holmes"};

    private int[] myFavSeasonImageArray = {
            R.drawable.siliconvalley,
            R.drawable.gameofthrones,
            R.drawable.bigbangtheory,
            R.drawable.prisonbreak,
            R.drawable.citizenkhan,
            R.drawable.divincidemons,
            R.drawable.mrrobot,
            R.drawable.houseofcards,
            R.drawable.sherlockholmes};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myFavSeasonList = (ListView) this.findViewById(R.id.list_of_seasons);
        //if loading app for very first time
        if(savedInstanceState==null) {
            //put data of arrays into arraylists
            season_names_AL = new ArrayList<>(Arrays.asList(myFavSeasonArray));
            season_images_AL = new ArrayList<>();
            for (int i = 0; i < myFavSeasonImageArray.length; i++) {
                season_images_AL.add(myFavSeasonImageArray[i]);
            }
        }else{
            loadSaveData(this);
//            loadSaveData();
        }

        //array adapter for displaying list items using fixed layout
//        adapter=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,
//                                        myFavSeasonArray);
        //custom adapter for arrays data
//        myOwnAdapter=new CustomAdapter(MainActivity.this, myFavSeasonArray,myFavSeasonImageArray);
        //custom adapter for array list data
        myOwnAdapter = new CustomAdapter(MainActivity.this, season_names_AL, season_images_AL);


        //set adapater for listview
//        myFavSeasonList.setAdapter(adapter);

        myFavSeasonList.setAdapter(myOwnAdapter);
        myFavSeasonList.setFocusable(true);
        myFavSeasonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String season_name = myFavSeasonList.getItemAtPosition(position).toString();
                Float season_rate_value = ((RatingBar) view.findViewById(R.id.season_rate)).getRating();
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("season_name", season_name);
                intent.putExtra("season_rating", season_rate_value);
                startActivity(intent);
            }
        });

        //remove list row data
        myFavSeasonList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                String seasonNameToDelete = myFavSeasonList.getItemAtPosition(position).toString();
                final AlertDialog.Builder alertBox = new AlertDialog.Builder(MainActivity.this);
                alertBox.setTitle("Do You want to Delete this: " + seasonNameToDelete);
                //right side button
                alertBox.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                //left side button
                alertBox.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        which = position;
//                season_names_AL.remove(position);
//                season_images_AL.remove(position);
                        season_names_AL.remove(which);
                        season_images_AL.remove(which);
                        myOwnAdapter.notifyDataSetChanged();
                    }
                });

                AlertDialog dialog = alertBox.create();
                dialog.show();
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        saveSeasonData();
        super.onPause();
    }

    @Override
    protected void onStop() {
        saveSeasonData();
        super.onStop();
    }
    //using shared preferences to store data in "mySeasonData" file
    public boolean saveSeasonData(){
        //1. Shared preferences option choice
        SharedPreferences sharedPreferences=getSharedPreferences("mySeasonData",MODE_PRIVATE);
        //editor to insert data in shared preferences
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("seasonNameALSize",season_names_AL.size());
        editor.putInt("seasonImageALSize", season_images_AL.size());

        for(int i=0; i < season_names_AL.size(); i++){
            //editor adds by default key of each data. Remove that for our convenience
            editor.remove("seasonName"+i);
            editor.putString("seasonName", season_names_AL.get(i));
        }

        for(int i=0; i < season_images_AL.size(); i++){
            editor.remove("seasonImage"+i);
            editor.putInt("seasonImage", season_images_AL.get(i));
        }

        //commit or apply the input to shared preferences

        return editor.commit();
    }

    public void loadSaveData(Context mContext){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(mContext);
        //retrieve size of array lists for displaying data
        int sharedSeasonNameSize=sharedPreferences.getInt("seasonNameALSize",0);
        int sharedSeasonImageSize=sharedPreferences.getInt("seasonImageALSize",0);

        for(int i=0; i<sharedSeasonNameSize; i++){
            season_names_AL.add(sharedPreferences.getString("seasonName"+i,null));
        }

        for(int i=0; i<sharedSeasonImageSize; i++){
            season_images_AL.add(sharedPreferences.getInt("seasonImage"+i,0));
        }
    }
}
