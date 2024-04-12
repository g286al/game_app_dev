package com.example.game_app.services;

import com.example.game_app.models.Game;

import java.util.ArrayList;

public interface DataFetchListener {
    void onDataFetched(ArrayList<Game> games);
}
