package com.example.game_app.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.game_app.R;
import com.example.game_app.adapters.ItemClickListener;

public class MainActivity extends AppCompatActivity {
    ItemClickListener itemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment mFragment = new GamesListFragment(itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(int position) {
                didTapGame(position);
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, mFragment).commit();
    }

    void didTapGame(int position) {
        Fragment mFragment = new GameDetails(position);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, mFragment).addToBackStack("Shalom").commit();
    }
    void tapNext()
    {

    }
}
