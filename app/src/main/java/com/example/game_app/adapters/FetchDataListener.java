package com.example.game_app.adapters;

import com.example.game_app.models.Game;

import java.util.ArrayList;

public interface FetchDataListener {
    void didFinishFetchingData(ArrayList<Game> gameList);

}
