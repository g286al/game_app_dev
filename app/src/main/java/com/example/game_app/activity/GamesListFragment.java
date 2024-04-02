package com.example.game_app.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.game_app.R;
import com.example.game_app.adapters.GameAdapter;
import com.example.game_app.adapters.ItemClickListener;
import com.example.game_app.models.Game;
import com.example.game_app.services.DataService;

import java.util.ArrayList;

public class GamesListFragment extends Fragment {

    public ItemClickListener itemClickListener2;
    public ItemClickListener itemClickListener;
    public RecyclerView recyclerView;
    public GameAdapter gameAdapter;

    public GamesListFragment(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

//    public static GamesListFragment newInstance(String param1, String param2) {
//        GamesListFragment fragment = new GamesListFragment(itemClickListener = new ItemClickListener() {
//            @Override
//            public void onClick(int position) {
//                didTapGame(position);
//            }
//        });
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_games_list, container, false);
        recyclerView = view.findViewById(R.id.r_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        ArrayList<Game> arrGame = DataService.getArrGame();
        itemClickListener2 = new ItemClickListener() {
            @Override
            public void onClick(int position) {
                itemClickListener.onClick(position);
            }
        };
        gameAdapter = new GameAdapter(getContext(), arrGame, itemClickListener2);
        recyclerView.setAdapter(gameAdapter);
        return view;
    }
}