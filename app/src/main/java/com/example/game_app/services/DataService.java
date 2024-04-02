package com.example.game_app.services;

import android.os.StrictMode;

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
    private static final ArrayList<Game> arrGame = new ArrayList<>();
    private static int position;
    public static ArrayList<Game> getArrGame() {
        String gURL = "https://api.rawg.io/api/games?key=2c8eecb3a50b49038dea2be27a8711a9";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
                String rating = obj.get("rating").getAsString();
                JsonArray genereArray = obj.getAsJsonArray("genres");
                String genre = "";
                for (JsonElement jeGenre : genereArray){
                    JsonObject genreObj = jeGenre.getAsJsonObject();
                    genre += genreObj.get("name").getAsString();
                    genre += ",";
                }

                Game game = new Game(name, releaseDate, imgURL,rating,genre);
                arrGame.add(game);
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return arrGame;
    }
    public static Game getClickedGame(int position){
        return arrGame.get(position);
    }
}