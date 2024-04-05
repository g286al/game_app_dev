package com.example.game_app.services;

import android.os.StrictMode;

import com.example.game_app.adapters.FetchDataListener;
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
    public String url = "https://api.rawg.io/api/games?key=2c8eecb3a50b49038dea2be27a8711a9";
    private String nextURL;
    private String previousURL;
    private ArrayList<Game> arrGame = new ArrayList<>();
    public FetchDataListener fetchDataListener;

    private  int position;

    public DataService(FetchDataListener fetchDataListener) {
        this.fetchDataListener = fetchDataListener;
        this.arrGame = new  ArrayList<Game>();
        this.arrGame = getArrGame(this.url);
    }

    public ArrayList<Game> getArrGame(String gURL) {
        arrGame = new ArrayList<Game>();
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
                String developers = getDeveloperById(id);

                Game game = new Game(name, releaseDate, imgURL,rating,genre,description,developers);
                arrGame.add(game);
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fetchDataListener.didFinishFetchingData(arrGame);

        return arrGame;
    }
    public Game getClickedGame(int position){
        return arrGame.get(position);
    }
    public ArrayList<Game> getArrGame() {
        return arrGame;
    }

    public ArrayList<Game> getNext(){
        return getArrGame(nextURL);
    }

    public ArrayList<Game> getPrev() {
        if (previousURL == null)
        {
            return null;
        }

        return getArrGame(previousURL);
    }

    private String getDescriptionById(String id) {
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
    private String getDeveloperById(String id) {
        try {
            URL url = new URL("https://api.rawg.io/api/games"+ "/"+ id +"?key=2c8eecb3a50b49038dea2be27a8711a9");
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));

            JsonObject rootObj = root.getAsJsonObject();
            JsonArray developersarray = rootObj.getAsJsonArray("developers");
            String developers = "";
            for (int i = 0; i < developersarray.size(); i++) {
                JsonObject genreObj = developersarray.get(i).getAsJsonObject();
                developers += genreObj.get("name").getAsString();
                if (i < developersarray.size() - 1) {
                    developers += ", ";
                }
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