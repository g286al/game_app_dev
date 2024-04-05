package com.example.game_app.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.game_app.R;
import com.example.game_app.adapters.FetchDataListener;
import com.example.game_app.adapters.ItemClickListener;
import com.example.game_app.adapters.OnScrollListener;
import com.example.game_app.models.Game;
import com.example.game_app.services.DataService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DataService dataService;
    ItemClickListener itemClickListener;
    OnScrollListener onScrollListener;
    FetchDataListener fetchDataListener;
    Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GamesListFragment mFragment = new GamesListFragment(itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(int position) {
                didTapGame(position);
            }
        },onScrollListener = new OnScrollListener() {
            @Override
            public void onScrollEnd() {
                dataService.getNext();
            }
        });

        dataService = new DataService(new FetchDataListener() {
            @Override
            public void didFinishFetchingData(ArrayList<Game> gameList) {
                mFragment.updateData(gameList);
            }
        });

                FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, mFragment).commit();
    }

    void didTapGame(int position) {
        Fragment mFragment = new GameDetails(dataService.getClickedGame(position));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, mFragment).addToBackStack("Shalom").commit();
    }
    void tapNext()
    {

    }
}
