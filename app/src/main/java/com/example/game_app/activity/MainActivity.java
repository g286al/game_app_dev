package com.example.game_app.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.game_app.R;
import com.example.game_app.adapters.ItemClickListener;
import com.example.game_app.adapters.OnScrollListener;
import com.example.game_app.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements OnScrollListener, ItemClickListener {
    GamesListFragment mFragment;

    MainActivityViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new MainActivityViewModel();
        setContentView(R.layout.activity_main);

        mFragment = new GamesListFragment(this,this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, mFragment).commit();
        viewModel.newItems.observe(this,games -> {
            mFragment.updateData(games);
        });
    }

    void didTapGame(String name) {
        Fragment mFragment = new GameDetails(viewModel.getGameByName(name));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, mFragment).addToBackStack("Shalom").commit();
    }

    @Override
    public void onScrollEnd() {
        viewModel.fetchMoreData();

//        viewModel.newItems.observe(this,games -> {
//            mFragment.updateData(games);
//        });

    }

    @Override
    public void onClick(String name) {
        didTapGame(name);
    }
}
