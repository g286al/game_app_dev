package com.example.game_app.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.game_app.R;
import com.example.game_app.adapters.EndlessScrollListener;
import com.example.game_app.adapters.GameAdapter;
import com.example.game_app.adapters.ItemClickListener;
import com.example.game_app.adapters.OnScrollListener;
import com.example.game_app.models.Game;
import com.example.game_app.models.WrapContentGridLayoutManager;

import java.util.ArrayList;

public class GamesListFragment extends Fragment {

    public ItemClickListener itemClickListener2;
    public ItemClickListener itemClickListener;

    public OnScrollListener getOnScrollListener() {
        return onScrollListener;
    }

    public OnScrollListener onScrollListener;
    public RecyclerView recyclerView;
    public ArrayList<Game> arrGame = new ArrayList<Game>();
    public GameAdapter gameAdapter;
    public EndlessScrollListener endlessScrollListener;
    public GamesListFragment(ItemClickListener itemClickListener, OnScrollListener onScrollListener) {
        this.itemClickListener = itemClickListener;
        this.onScrollListener = onScrollListener;
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
        WrapContentGridLayoutManager layoutManager = new WrapContentGridLayoutManager(getContext(), 2);
        recyclerView = view.findViewById(R.id.r_view);
        recyclerView.setLayoutManager(layoutManager);
        itemClickListener2 = new ItemClickListener() {
            @Override
            public void onClick(int position) {
                itemClickListener.onClick(position);
            }
        };
        gameAdapter = new GameAdapter(getContext(), arrGame, itemClickListener2);
        recyclerView.setAdapter(gameAdapter);
        endlessScrollListener = new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                onScrollListener.onScrollEnd();
            }
        };
        recyclerView.addOnScrollListener(endlessScrollListener);
        return view;
    }
    public void updateData(ArrayList<Game> updatedArrGame){
        int currArrSize = this.arrGame.size();
        this.arrGame.addAll(updatedArrGame);
        endlessScrollListener.setLoading(false);
        if(currArrSize == 0)
        {
            gameAdapter.notifyItemRangeInserted(0,updatedArrGame.size());
        }
        else{
            gameAdapter.notifyItemRangeInserted(currArrSize - 1,currArrSize);
        }

    }
}