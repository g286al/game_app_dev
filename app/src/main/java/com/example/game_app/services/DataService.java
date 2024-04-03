package com.example.game_app.services;

import android.os.StrictMode;

import com.example.game_app.models.Game;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DataService {
    private static String url = "https://api.rawg.io/api/games?key=2c8eecb3a50b49038dea2be27a8711a9";
    private static String nextURL;
    private static String previousURL;
    private static final ArrayList<Game> arrGame = new ArrayList<>();
    private static int position;
    public static ArrayList<Game> getArrGame(String gURL) {
        String key  = "?key=2c8eecb3a50b49038dea2be27a8711a9";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL(gURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));

            JsonObject rootObj = root.getAsJsonObject();
            nextURL = rootObj.get("next").getAsString();
            previousURL = rootObj.get("previous").isJsonNull() ? null : rootObj.get("previous").getAsString();
            JsonArray resultsArray = rootObj.getAsJsonArray("results");
            for (JsonElement je : resultsArray) {
                JsonObject obj = je.getAsJsonObject();
                String id = obj.get("id").getAsString();
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
                String description = getDescriptionById(id);

                Game game = new Game(name, releaseDate, imgURL,rating,genre,description);
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
    public static ArrayList<Game> getArrGame() {
        return getArrGame(url);
    }

    public static ArrayList<Game> getNext(){
        return getArrGame(nextURL);
    }

    public static ArrayList<Game> getPrev() {
        if (previousURL == null)
        {
            return null;
        }

        return getArrGame(previousURL);
    }

    private static String getDescriptionById(String id) {
        try {
            URL url = new URL("https://api.rawg.io/api/games"+ "/"+ id +"?key=2c8eecb3a50b49038dea2be27a8711a9");
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));

            JsonObject rootObj = root.getAsJsonObject();
            String description = rootObj.get("description").getAsString();
            String  encodedString = StringEscapeUtils.escapeJava(description);
            encodedString = encodedString.replaceAll("<p>|<br />", "");
            int index = encodedString.indexOf(".</p>\\nEspa\\u00F1ol");
            encodedString = (index != -1) ? encodedString.substring(0, index) : encodedString;
            return StringEscapeUtils.unescapeJava(encodedString);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static String getDeveloperById(String id) {
        try {
            URL url = new URL("https://api.rawg.io/api/games"+ "/"+ id +"?key=2c8eecb3a50b49038dea2be27a8711a9");
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));

            JsonObject rootObj = root.getAsJsonObject();
            JsonArray developersarray = rootObj.getAsJsonArray("developers");
            String developers = "";
            for (JsonElement je : developersarray){
                JsonObject genreObj = je.getAsJsonObject();
                developers += genreObj.get("name").getAsString();
                developers += ",";
            }
            return developers;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}