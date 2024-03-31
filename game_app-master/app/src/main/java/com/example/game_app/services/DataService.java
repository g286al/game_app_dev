package com.example.game_app.services;

import com.example.game_app.models.Game;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DataService {
    private ArrayList<Game> arrGame = new ArrayList<>();

    public ArrayList<Game> getArrGame() {
        String gURL = "https://api.rawg.io/api/games?key=2c8eecb3a50b49038dea2be27a8711a9";
        try {
            URL url = new URL(gURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));

            JsonObject rootObj = root.getAsJsonObject();
            String nextURL = rootObj.get("next").getAsString();
            String previousURL = rootObj.get("previous").isJsonNull() ? null : rootObj.get("previous").getAsString();
            JsonArray resultsArray = rootObj.getAsJsonArray("results");
            for (JsonElement je : resultsArray) {
                JsonObject obj = je.getAsJsonObject();
                String name = obj.get("name").getAsString();
                String releaseDate = obj.get("released").getAsString();
                String imgURL = obj.get("background_image").getAsString();
                Game game = new Game(name, releaseDate, imgURL);
                arrGame.add(game);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            // Handle exception properly
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception properly
        }

        return arrGame;
    }
}